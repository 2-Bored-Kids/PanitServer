import sum.ereignis.Buntstift;
import java.awt.Color;

public class PenSettingsPacket extends Packet {
    public PenSettingsPacket() {
        super(PacketIds.SETTING);
    }

    public String ID;
    public Buntstift PEN;
    public Color PEN_COLOR;
    public byte FILL_MODE;

    public PenSettingsPacket(String id, Buntstift pen, byte fillMode, Color color) {
        super(PacketIds.SETTING);
        this.ID = id;
        this.PEN = pen;
        this.PEN_COLOR = color;
        this.FILL_MODE = fillMode;
    }

    public PenSettingsPacket(String str){
        this(decode(str));
    }

    public PenSettingsPacket(String[] str){
        super(PacketIds.SETTING);

        ID = str[1];

        PEN = new Buntstift();

        PEN.setzeFarbe(new Color(Integer.parseInt(str[2]), Integer.parseInt(str[3]), Integer.parseInt(str[4])));

        PEN.setzeFuellMuster(Byte.parseByte(str[6]));
        PEN.setzeLinienBreite(Integer.parseInt(str[7]));

        PEN.bewegeBis(Integer.parseInt(str[8]), Integer.parseInt(str[9]));

        if (str[4] == "true") {
            PEN.runter();
        }
    }

    @Override
    public String encode(){
        return Integer.toString(PacketIds.SETTING) + merge(new Object[]{ID, PEN_COLOR.getRed(), PEN_COLOR.getGreen(), PEN_COLOR.getBlue(), PEN.istUnten(), FILL_MODE, PEN.linienBreite(), PEN.hPosition(), PEN.vPosition()});
    }
}
