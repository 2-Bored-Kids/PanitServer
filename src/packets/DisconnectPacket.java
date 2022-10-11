package server.src.packets;

public class DisconnectPacket extends Packet {
    public DisconnectPacket() {
        super(PacketIds.DISCONNECT);
    }
}
