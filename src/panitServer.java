import sum.netz.*;

import java.util.Objects;

public class panitServer extends Server{

    public panitServer(int port){
        super(port, true);
    }

    public void bearbeiteVerbindungsaufbau(String ip, int port){
        sendeAnEinen(ip, port,packetIds.PACKET_CONNECT+ "");
    }

    public void bearbeiteNachricht(String ip, int port, String message){

        String[] packet = message.split(packetIds.SEPARATOR + "");

        if (Objects.equals(packet[0], packetIds.PACKET_DISCONNECT + "")){
            schliesseVerbindung(ip, port);
        }else {
            sendeAnAlle(message);
        }
    }

    public void bearbeiteVerbindungsverlust(String ip, int port){
        sendeAnEinen(ip, port, packetIds.PACKET_DISCONNECT + "");
    }

    public void bearbeiteVerbindungsende(String ip, int port){
        sendeAnEinen(ip, port, packetIds.PACKET_DISCONNECT + "");
    }

    static public void main(String[] args){
        new panitServer(Integer.parseInt(args[0]));
    }
}
