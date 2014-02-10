package net.minecraft.server;

// AAM's modification - manager of login block OwO

import java.util.HashSet;
import java.util.HashMap;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import net.minecraft.util.org.apache.commons.lang3.time.DurationFormatUtils;

class LoginBlockingManager {
    
    private HashSet<InetAddress> blockedForever;
    private HashMap<InetAddress, BlockInfo> blockList;

    LoginBlockingManager() {
        this.blockedForever = new HashSet<InetAddress>();
        this.blockList = new HashMap<InetAddress, BlockInfo>();
    }
    
    private InetAddress getAddr(SocketAddress addr) {
        try {
            return ((InetSocketAddress)addr).getAddress();
        } catch (Exception ex) {
            return null;
        }
    }
    
    private long getStageTime(int stage) {
        switch (stage) {
        case 0: // first block => 15 min
            return 15 * 60 * 1000;
        case 1: // second block => 1 hour
            return 1 * 60 * 60 * 1000;
        case 2: // third block => forever (till server restart)
        default:
            return -1; // -1 represents forever
        }
    }
    
    public String getKickMessage(InetAddress addr) {
        int stage = -1;
        long blockDur = -1, timeLeft = -1;
        BlockInfo inf = this.blockList.get(addr);
        
        if (inf != null) {
            stage = inf.blockStage;
            blockDur = this.getStageTime(stage);
            timeLeft = blockDur + inf.addTime - System.currentTimeMillis();
        }
        
        if (timeLeft > 0) {
            return "You're temporarily banned for " + DurationFormatUtils.formatDurationWords(blockDur, true, true) + " due to authentication failure.\n" +
                   "Time to unban : " + DurationFormatUtils.formatDurationWords(timeLeft, true, true) + " remaining.";
        } else {
            return "You're banned due to authentication failure.\nPlease wait till next server restart.";
        }
    }
    public String getKickMessage(SocketAddress addr) {
        return this.getKickMessage(getAddr(addr));
    }
    
    public boolean checkBlocked(InetAddress addr) {
        if (this.blockedForever.contains(addr)) return true;
        BlockInfo inf = this.blockList.get(addr);
        if (inf == null) return false;
        return (System.currentTimeMillis() - inf.addTime < this.getStageTime(inf.blockStage));
    }
    public boolean checkBlocked(SocketAddress addr) {
        return this.checkBlocked(getAddr(addr));
    }
    
    public void block(InetAddress addr) {
        int stage = 0;
        BlockInfo inf = this.blockList.get(addr);
        if (inf != null) stage = inf.blockStage + 1;
        if (this.getStageTime(stage) == -1) { // block forever
            this.blockedForever.add(addr);
            this.clearBlock(addr);
        } else {
            this.blockList.put(addr, new BlockInfo(stage));
        }
    }
    public void block(SocketAddress addr) {
        this.block(getAddr(addr));
    }
    
    public void blockForever(InetAddress addr) {
        this.blockedForever.add(addr);
        this.clearBlock(addr);
    }
    public void blockForever(SocketAddress addr) {
        this.blockForever(getAddr(addr));
    }
    
    public void clearBlock(InetAddress addr) {
        this.blockList.remove(addr);
    }
    public void clearBlock(SocketAddress addr) {
        this.clearBlock(getAddr(addr));
    }
    
    private class BlockInfo {
        public long addTime;
        public int blockStage;
        
        BlockInfo(int stage) {
            this.addTime = System.currentTimeMillis();
            this.blockStage = stage;
        }
    }
}
