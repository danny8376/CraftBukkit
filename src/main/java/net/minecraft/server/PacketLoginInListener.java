package net.minecraft.server;

public abstract interface PacketLoginInListener extends PacketListener {
    public abstract void a(PacketLoginInStart paramPacketLoginInStart);
    public abstract void a(PacketLoginInEncryptionBegin paramPacketLoginInEncryptionBegin);
    public abstract void a(PacketLoginInRealIP packet); // AAM's modification - process real ip packet
}
