public class ImagePacket extends Packet{
    public String ID, IMG;

    public ImagePacket(String id, String img){
        super(PacketIds.IMAGE);
        this.ID = id;
        this.IMG = img;
    }

    public ImagePacket(String[] str){
        super(PacketIds.IMAGE);
        this.ID = str[1] + PacketIds.SEPARATOR + str[2];
        this.IMG = str[3];
    }

    public ImagePacket(String str){
        this(decode(str));
    }

    @Override
    public String encode(){
        return Integer.toString(PacketIds.IMAGE) + PacketIds.SEPARATOR + ID + PacketIds.SEPARATOR + IMG;
    }
}
