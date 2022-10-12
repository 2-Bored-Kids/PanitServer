public class ImagePacket extends Packet{
    public String ID, IMG;

    public ImagePacket(String id, String img){
        super(PacketIds.IMAGE);
        this.ID = id;
        this.IMG = img;
    }

    public ImagePacket(String[] str){
        super(PacketIds.IMAGE);
        this.ID = str[1] + str[2];
        this.IMG = str[3];
    }

    public ImagePacket(String str){
        this(decode(str));
    }
}
