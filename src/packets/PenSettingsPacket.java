import sum.ereignis.Buntstift;
import java.awt.Color;

public class PenSettingsPacket extends Packet {
    public PenSettingsPacket() {
        super(PacketIds.SETTING);
    }

    public String ID;
    public Buntstift PEN;
    public Color PEN_COLOR;
    public byte FILL_MODE, PAINT_MODE;

    public PenSettingsPacket(String id, Buntstift pen, byte fillMode, Color color, byte paintMode) {
        super(PacketIds.SETTING);
        this.ID = id;
        this.PEN = pen;
        this.PEN_COLOR = color;
        this.FILL_MODE = fillMode;
        this.PAINT_MODE = paintMode;
    }

    public PenSettingsPacket(String str){
        this(decode(str));
    }

    public PenSettingsPacket(String[] str){
        super(PacketIds.SETTING);

        ID = str[2] + str[2];

        PEN = new Buntstift();

        PEN.setzeFarbe(new Color(Integer.parseInt(str[3]), Integer.parseInt(str[4]), Integer.parseInt(str[5])));

        PEN.setzeFuellMuster(Byte.parseByte(str[7]));
        PEN.setzeLinienBreite(Integer.parseInt(str[8]));

        PEN.bewegeBis(Integer.parseInt(str[9]), Integer.parseInt(str[10]));

        if (str[6] == "true") {
            PEN.runter();
        }

        this.PAINT_MODE = Byte.parseByte(str[11]);
    }

    @Override
    public String encode(){
        return Integer.toString(PacketIds.SETTING) + merge(new Object[]{ID, PEN_COLOR.getRed(), PEN_COLOR.getGreen(), PEN_COLOR.getBlue(), PEN.istUnten(), FILL_MODE, PEN.linienBreite(), (int)PEN.hPosition(), (int)PEN.vPosition(), PAINT_MODE});
    }
}
