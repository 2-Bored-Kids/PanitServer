import sum.netz.*;

public class Main extends Server{

    public Main(int port){
        super(port, true);
    }

    public void bearbeiteVerbindungsaufbau(String ip, int port){
        sendeAnAlle(ip + " connected");
        sendeAnEinen(ip, "!connected");
    }

    public void bearbeiteNachricht(String ip, int port, String message){
        if(message.startsWith("?requestDisconnect")){
            this.beendeVerbindung(ip, port);
        }else{
            sendeAnAlle(message);
        }
    }

    public void bearbeiteVerbindungsverlust(String ip, int port){
        sendeAnAlle(ip + " disconnected");
        sendeAnEinen(ip, "!disconnect");
    }

    public void bearbeiteVerbindungsende(String ip, int port){
        sendeAnAlle(ip + " disconnected");
        sendeAnEinen(ip, "!disconnect");
    }

    static public void main(String[] args){
        Main main = new Main(20002);
    }
}