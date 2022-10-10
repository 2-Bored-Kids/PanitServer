import sum.netz.*;

public class panitServer extends Server {
    public panitServer(int port) {
        super(port, false);
        System.out.println("Server wurde unter dem Port " + port + " gestartet!\n");
    }

    public void bearbeiteVerbindungsaufbau(String ip, int port) {
        sendeAnEinen(ip, port, ip + PacketIds.SEPARATOR  + Integer.toString(port) + PacketIds.SEPARATOR + Integer.toString(PacketIds.CONNECT));
        System.out.println("Neue Verbindung: " + ip + ":" + Integer.toString(port) + "\nVerbindungen: " + this.zahlDerVerbindungen() + "\n");
        sendeAnAlleAusser(ip, port, ip + PacketIds.SEPARATOR  + Integer.toString(port) + PacketIds.SEPARATOR +  Integer.toString(PacketIds.JOIN));
    }

    public void bearbeiteNachricht(String ip, int port, String message) {
        String[] packet = message.split(PacketIds.SEPARATOR);
        if (packet[0].equals(Integer.toString(PacketIds.DISCONNECT))) {
            schliesseVerbindung(ip, port);
            System.out.println("Verbindung getrennt: " + ip + ":" + Integer.toString(port) + "\nVerbindungen: " + this.zahlDerVerbindungen() + "\n");
            sendeAnAlleAusser(ip, port, ip + PacketIds.SEPARATOR  + Integer.toString(port) + PacketIds.SEPARATOR +  Integer.toString(PacketIds.QUIT));
        } else {
            sendeAnAlleAusser(ip, port, ip + PacketIds.SEPARATOR  + Integer.toString(port) + PacketIds.SEPARATOR +  message);
        }
    }

    public void bearbeiteVerbindungsverlust(String ip, int port) {
        sendeAnEinen(ip, port, ip + PacketIds.SEPARATOR  + Integer.toString(port) + PacketIds.SEPARATOR + Integer.toString(PacketIds.DISCONNECT));
        System.out.println("Verbindung verloren: " + ip + ":" + Integer.toString(port) + "\nVerbindungen: " + this.zahlDerVerbindungen() + "\n");
        sendeAnAlleAusser(ip, port, ip + PacketIds.SEPARATOR  + Integer.toString(port) + PacketIds.SEPARATOR +  Integer.toString(PacketIds.QUIT));
    }

    public void bearbeiteVerbindungsende(String ip, int port) {
        sendeAnEinen(ip, port, ip + PacketIds.SEPARATOR  + Integer.toString(port) + PacketIds.SEPARATOR + Integer.toString(PacketIds.DISCONNECT));
        System.out.println("Verbindung getrennt: " + ip + ":" + Integer.toString(port) + "\nVerbindungen: " + this.zahlDerVerbindungen() + "\n");
        sendeAnAlleAusser(ip, port, ip + PacketIds.SEPARATOR  + Integer.toString(port) + PacketIds.SEPARATOR +  Integer.toString(PacketIds.QUIT));
    }

    public void sendeAnAlleAusser(String ip, int port, String message){
        for(int lNr = 0; lNr < clientListe().size(); ++lNr) {
            Serververbindung lSerververbindung = (Serververbindung)clientListe().elementAt(lNr);
            if (!(lSerververbindung.partnerAdresse().equals(ip) && lSerververbindung.partnerPort() == port)){
                lSerververbindung.sende(message);
            }
        }
    }
}


