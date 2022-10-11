import sum.netz.Server;
import sum.netz.Serververbindung;
import sum.ereignis.Buntstift;
import java.util.HashMap;
import java.util.Vector;

import packets.*;

public class PanitServer extends Server {
    HashMap<String, Buntstift> userPens = new HashMap<>();

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

        System.out.println("Neue Verbindung: " + ip + ":" + port + "\nVerbindungen: " + this.zahlDerVerbindungen() + "\n");

        sendeAnAlleAusser(id, new JoinPacket().encode());

        Vector<Serververbindung> list = clientListe();
        for (Serververbindung verbindung : list) {
            String usrId = getId(verbindung.partnerAdresse(), verbindung.partnerPort());
            if (!usrId.equals(id)) {
                sendeAnEinen(ip, port, usrId + new JoinPacket().encode());
            }
        }
    }

    public void bearbeiteNachricht(String ip, int port, String message) {
        String id = getId(ip, port);
        String[] packet = message.split(PacketIds.SEPARATOR);

        switch (Integer.parseInt(packet[0])) {
            case PacketIds.DISCONNECT:
                schliesseVerbindung(ip, port);
            break;
            case PacketIds.CONNECT:
                //Send all infos
            break;
            default:
                sendeAnAlleAusser(id, message);
            break;
        }
    }

    public void bearbeiteVerbindungsverlust(String ip, int port) {
        String id = getId(ip, port);

        System.out.println("Verbindung getrennt: " + ip + ":" + port + "\nVerbindungen: " + this.zahlDerVerbindungen() + "\n");

        sendeAnAlle(id + new QuitPacket().encode());
    }

    public void bearbeiteVerbindungsende(String ip, int port) {
        String id = getId(ip, port);

        sendeAnEinen(ip, port, id + new DisconnectPacket().encode());

        bearbeiteVerbindungsverlust(ip, port);
    }

    public void sendeAnAlleAusser(String id, String message){
        Vector<Serververbindung> list = clientListe();
        for (Serververbindung verbindung : list) {
            if (!getId(verbindung.partnerAdresse(), verbindung.partnerPort()).equals(id)) {
                verbindung.sende(id + message);
            }
        }
    }
}


