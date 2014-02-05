package net.minecraft.server;

// AAM's modification - New packet OwO

/*
Packet ID - 0x70 (112)

Client Addr        128bits IPv6 addr (IPv4 addr with ::ffff:0:0/96 prefix)
Client Port        16bits port number
Server Addr        the same as client
Server Port        the same as client
*/

import java.util.Arrays;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class PacketLoginInRealIP extends Packet {

    private byte[] cli_addr;
    private int cli_port;
    private byte[] svr_addr;
    private int svr_port;

    public void a(PacketDataSerializer packetdataserializer) {
        this.cli_addr = new byte[16];
        packetdataserializer.readBytes(this.cli_addr);
        this.cli_port = packetdataserializer.readUnsignedShort();
        this.svr_addr = new byte[16];
        packetdataserializer.readBytes(this.svr_addr);
        this.svr_port = packetdataserializer.readUnsignedShort();
    }

    public void b(PacketDataSerializer packetdataserializer) {
        packetdataserializer.writeBytes(this.cli_addr);
        packetdataserializer.writeShort(this.cli_port); // we won't generate this packet on server side, so just ignore this
        packetdataserializer.writeBytes(this.svr_addr);
        packetdataserializer.writeShort(this.svr_port); // we won't generate this packet on server side, so just ignore this
    }

    public void a(PacketLoginInListener packetlogininlistener) {
        packetlogininlistener.a(this);
    }

    public String b() {
        //return String.format("message=\'%s\'", new Object[] { this.message});
        return "I'm lazy :)";
    }

    public void handle(PacketListener packetlistener) {
        this.a((PacketLoginInListener) packetlistener);
    }
    
    public InetAddress getClientAddress() throws UnknownHostException {
        return getAddrFromBytes(this.cli_addr);
    }
    
    public int getClientPort() {
        return this.cli_port;
    }
    
    public InetAddress getServerAddress() throws UnknownHostException {
        return getAddrFromBytes(this.svr_addr);
    }
    
    public int getServerPort() {
        return this.svr_port;
    }
    
    private InetAddress getAddrFromBytes(byte[] baddr) throws UnknownHostException {
        // IPv4 addr
        if (baddr[0] == 0 && baddr[1] == 0 && baddr[2] == 0 && baddr[3] == 0 && baddr[4] == 0 && baddr[5] == 0 && baddr[6] == 0 && baddr[7] == 0 && baddr[8] == 0 && baddr[9] == 0 && baddr[10] == 0xff && baddr[11] == 0xff) {
            return InetAddress.getByAddress(Arrays.copyOfRange(baddr, 12, 16));
        } else {
            return InetAddress.getByAddress(baddr);
        }
    }
}
