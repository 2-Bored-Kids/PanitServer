import java.util.Vector;
import sum.netz.Server;
import sum.netz.Serververbindung;

public class PanitServer extends Server {
  public PanitServer(int port) {
    super(port, false);
    System.out.println("Server started at port " + port);
  }

  public String getId(String ip, int port) {
    return ip + PacketIds.SEPARATOR + port + PacketIds.SEPARATOR;
  }

  public void bearbeiteVerbindungsaufbau(String ip, int port) {
    String id = getId(ip, port);

    sendeAnEinen(ip, port, id + new ConnectPacket().encode());

    System.out.println("Neue Verbindung: " + ip + ":" + port +
                       "\nVerbindungen: " + this.zahlDerVerbindungen() + "\n");

    sendeAnAlleAusser(id, new JoinPacket().encode());

    @SuppressWarnings("unchecked")
    Vector<Serververbindung> list = clientListe();

    for (Serververbindung verbindung : list) {
      String usrId =
        getId(verbindung.partnerAdresse(), verbindung.partnerPort());
      if (!usrId.equals(id)) {
        sendeAnEinen(ip, port, usrId + new JoinPacket().encode());
      }
    }

    String firstIp = list.get(0).partnerAdresse();
    int firstPort = list.get(0).partnerPort();

    if (getId(firstIp, firstPort) != id) {
      sendeAnEinen(firstIp,
                   firstPort,
                   getId(firstIp, firstPort) +
                     new ImagePacket(id, " ").encode());
    }
  }

  public void bearbeiteNachricht(String ip, int port, String message) {
    String id = getId(ip, port);
    String[] packet = message.split(PacketIds.SEPARATOR);

    switch (Integer.parseInt(packet[0])) {
      case PacketIds.DISCONNECT:
        schliesseVerbindung(ip, port);
        break;
      case PacketIds.SETTING:
        if (packet[1] + packet[2] != "0:0") {
          sendeAnEinen(
            packet[1], Integer.parseInt(packet[2]), getId(ip, port) + message);
        } else {
          sendeAnAlleAusser(id, message);
        }
        break;
      case PacketIds.IMAGE:
        if (packet[1].equals(" ") && packet[2].equals(" ")) {
          sendeAnAlleAusser(id, message);
        } else {
          sendeAnEinen(packet[1],
                       Integer.parseInt(packet[2]),
                       getId(packet[1], Integer.parseInt(packet[2])) + message);
        }
        break;
      default:
        sendeAnAlleAusser(id, message);
        break;
    }
  }

  public void bearbeiteVerbindungsverlust(String ip, int port) {
    String id = getId(ip, port);

    System.out.println("Verbindung getrennt: " + ip + ":" + port +
                       "\nVerbindungen: " + this.zahlDerVerbindungen() + "\n");

    sendeAnAlle(id + new QuitPacket().encode());
  }

  public void bearbeiteVerbindungsende(String ip, int port) {
    String id = getId(ip, port);

    sendeAnEinen(ip, port, id + new DisconnectPacket().encode());

    bearbeiteVerbindungsverlust(ip, port);
  }

  public void sendeAnAlleAusser(String id, String message) {
    @SuppressWarnings("unchecked")
    Vector<Serververbindung> list = clientListe();

    for (Serververbindung verbindung : list) {
      if (!getId(verbindung.partnerAdresse(), verbindung.partnerPort())
             .equals(id)) {
        verbindung.sende(id + message);
      }
    }
  }
}
