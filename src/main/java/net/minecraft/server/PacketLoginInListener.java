package net.minecraft.server;

public interface PacketLoginInListener extends PacketListener {
    void a(PacketLoginInStart packetlogininstart);
    void a(PacketLoginInEncryptionBegin packetlogininencryptionbegin);
    void a(PacketLoginInRealIP packet); // AAM's modification - process real ip packet
}
