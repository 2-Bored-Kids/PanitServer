public class FillModePacket extends Packet {

  public byte FILLMODE;

  public FillModePacket(byte fillmode) {
    super(PacketIds.FILLMODE);
    this.FILLMODE = fillmode;
  }

  public FillModePacket(String[] str) {
    super(PacketIds.FILLMODE);
    this.FILLMODE = Byte.parseByte(str[1]);
  }

  public FillModePacket(String str) { this(decode(str)); }

  @Override
  public String encode() {
    return Integer.toString(PacketIds.FILLMODE) + PacketIds.SEPARATOR +
      Byte.toString(FILLMODE);
  }
}
