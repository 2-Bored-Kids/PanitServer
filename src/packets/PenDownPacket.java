public class PenDownPacket extends Packet {
  public int X, Y;

  public PenDownPacket(int x, int y) {
    super(PacketIds.DOWN);
    this.X = x;
    this.Y = y;
  }

  public PenDownPacket(String str) { this(decode(str)); }

  public PenDownPacket(String[] str) {
    super(PacketIds.DOWN);
    this.X = Integer.parseInt(str[1]);
    this.Y = Integer.parseInt(str[2]);
  }

  @Override
  public String encode() {
    return Integer.toString(PacketIds.DOWN) + merge(new Object[] { X, Y });
  }
}
