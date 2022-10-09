import sum.netz.*;

public class panitServer extends Server {

    public panitServer(int port) {
        super(port, false);
        System.out.println("Server wurde unter dem Port " + port + " gestartet!\n");
    }

    public void bearbeiteVerbindungsaufbau(String ip, int port) {
        sendeAnEinen(ip, port, Integer.toString(packetIds.PACKET_CONNECT));
        System.out.println("Neue Verbindung: " + ip + ":" + Integer.toString(port) + "\nVerbindungen: " + this.zahlDerVerbindungen() + "\n");
    }

    public void bearbeiteNachricht(String ip, int port, String message) {

        String[] packet = message.split(Integer.toString(packetIds.SEPARATOR));

        if (packet[0].equals(Integer.toString(packetIds.PACKET_DISCONNECT))) {
            schliesseVerbindung(ip, port);
        } else {
            sendeAnAlle(message);
        }
    }

    public void bearbeiteVerbindungsverlust(String ip, int port) {
        sendeAnEinen(ip, port, Integer.toString(packetIds.PACKET_DISCONNECT));
        System.out.println("Verbindung getrennt: " + ip + ":" + Integer.toString(port) + "\nVerbindungen: " + this.zahlDerVerbindungen());
    }

    public void bearbeiteVerbindungsende(String ip, int port) {
        sendeAnEinen(ip, port, Integer.toString(packetIds.PACKET_DISCONNECT));
        System.out.println("Verbindung getrennt: " + ip + ":" + Integer.toString(port) + "\nVerbindungen: " + this.zahlDerVerbindungen());
    }

    static public void main(String[] args) {
        new panitServer(Integer.parseInt(args[0]));
    }
}
