import sum.netz.*;

public class panitServer extends Server{

    public panitServer(int port){
        super(port, true);
    }

    public void bearbeiteVerbindungsaufbau(String ip, int port){
        sendeAnAlle(ip + " connected");
        sendeAnEinen(ip, packetIds.PACKET_CONNECT+ "");
    }

    public void bearbeiteNachricht(String ip, String message){

        String[] packet = message.split(packetIds.SEPARATOR + "");

        if (packet[0] == packetIds.PACKET_DISCONNECT + ""){
            schliesseVerbindung(ip);
        }else {
            sendeAnAlle(message);
        }
    }

    public void bearbeiteVerbindungsverlust(String ip, int port){
        sendeAnAlle(ip + " disconnected");
        sendeAnEinen(ip, packetIds.PACKET_DISCONNECT + "");
    }

    public void bearbeiteVerbindungsende(String ip, int port){
        sendeAnAlle(ip + " disconnected");
        sendeAnEinen(ip, packetIds.PACKET_DISCONNECT + "");
    }

    static public void main(String[] args){
        panitServer main = new panitServer(Integer.parseInt(args[0]));
    }
}
