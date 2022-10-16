public class FillModePacket extends Packet {

  public byte FILLMODE;

  public FillModePacket(byte fillmode) {
    super(PacketIds.FILLING);
    this.FILLMODE = fillmode;
  }

  public FillModePacket(String[] str) {
    super(PacketIds.FILLING);
    this.FILLMODE = Byte.parseByte(str[1]);
  }

  public FillModePacket(String str) { this(decode(str)); }

  @Override
  public String encode() {
    return Integer.toString(PacketIds.FILLING) + PacketIds.SEPARATOR +
      Byte.toString(FILLMODE);
  }
}
