public class RunterPacket extends Packet {
  public int X, Y;

  public RunterPacket(int x, int y) {
    super(PacketIds.DOWN);
    this.X = x;
    this.Y = y;
  }

  public RunterPacket(String str) { this(decode(str)); }

  public RunterPacket(String[] str) {
    super(PacketIds.DOWN);
    this.X = Integer.parseInt(str[1]);
    this.Y = Integer.parseInt(str[2]);
  }

  @Override
  public String encode() {
    return Integer.toString(PacketIds.DOWN) + merge(new Object[] { X, Y });
  }
}
