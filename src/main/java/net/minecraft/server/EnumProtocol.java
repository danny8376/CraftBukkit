package net.minecraft.server;

import java.util.Iterator;
import java.util.Map;

import net.minecraft.util.com.google.common.collect.BiMap;
import net.minecraft.util.com.google.common.collect.HashBiMap;
import net.minecraft.util.com.google.common.collect.Iterables;
import net.minecraft.util.com.google.common.collect.Maps;
import net.minecraft.util.gnu.trove.map.TIntObjectMap;
import net.minecraft.util.gnu.trove.map.hash.TIntObjectHashMap;
import org.apache.logging.log4j.LogManager;

public enum EnumProtocol {

    HANDSHAKING("HANDSHAKING", 0, -1) {
        {
            a(0, PacketHandshakingInSetProtocol.class);
        }
    },
    PLAY("PLAY", 1, 0) {
        {
            b(0, PacketPlayOutKeepAlive.class);
            b(1, PacketPlayOutLogin.class);
            b(2, PacketPlayOutChat.class);
            b(3, PacketPlayOutUpdateTime.class);
            b(4, PacketPlayOutEntityEquipment.class);
            b(5, PacketPlayOutSpawnPosition.class);
            b(6, PacketPlayOutUpdateHealth.class);
            b(7, PacketPlayOutRespawn.class);
            b(8, PacketPlayOutPosition.class);
            b(9, PacketPlayOutHeldItemSlot.class);
            b(10, PacketPlayOutBed.class);
            b(11, PacketPlayOutAnimation.class);
            b(12, PacketPlayOutNamedEntitySpawn.class);
            b(13, PacketPlayOutCollect.class);
            b(14, PacketPlayOutSpawnEntity.class);
            b(15, PacketPlayOutSpawnEntityLiving.class);
            b(16, PacketPlayOutSpawnEntityPainting.class);
            b(17, PacketPlayOutSpawnEntityExperienceOrb.class);
            b(18, PacketPlayOutEntityVelocity.class);
            b(19, PacketPlayOutEntityDestroy.class);
            b(20, PacketPlayOutEntity.class);
            b(21, PacketPlayOutRelEntityMove.class);
            b(22, PacketPlayOutEntityLook.class);
            b(23, PacketPlayOutRelEntityMoveLook.class);
            b(24, PacketPlayOutEntityTeleport.class);
            b(25, PacketPlayOutEntityHeadRotation.class);
            b(26, PacketPlayOutEntityStatus.class);
            b(27, PacketPlayOutAttachEntity.class);
            b(28, PacketPlayOutEntityMetadata.class);
            b(29, PacketPlayOutEntityEffect.class);
            b(30, PacketPlayOutRemoveEntityEffect.class);
            b(31, PacketPlayOutExperience.class);
            b(32, PacketPlayOutUpdateAttributes.class);
            b(33, PacketPlayOutMapChunk.class);
            b(34, PacketPlayOutMultiBlockChange.class);
            b(35, PacketPlayOutBlockChange.class);
            b(36, PacketPlayOutBlockAction.class);
            b(37, PacketPlayOutBlockBreakAnimation.class);
            b(38, PacketPlayOutMapChunkBulk.class);
            b(39, PacketPlayOutExplosion.class);
            b(40, PacketPlayOutWorldEvent.class);
            b(41, PacketPlayOutNamedSoundEffect.class);
            b(42, PacketPlayOutWorldParticles.class);
            b(43, PacketPlayOutGameStateChange.class);
            b(44, PacketPlayOutSpawnEntityWeather.class);
            b(45, PacketPlayOutOpenWindow.class);
            b(46, PacketPlayOutCloseWindow.class);
            b(47, PacketPlayOutSetSlot.class);
            b(48, PacketPlayOutWindowItems.class);
            b(49, PacketPlayOutCraftProgressBar.class);
            b(50, PacketPlayOutTransaction.class);
            b(51, PacketPlayOutUpdateSign.class);
            b(52, PacketPlayOutMap.class);
            b(53, PacketPlayOutTileEntityData.class);
            b(54, PacketPlayOutOpenSignEditor.class);
            b(55, PacketPlayOutStatistic.class);
            b(56, PacketPlayOutPlayerInfo.class);
            b(57, PacketPlayOutAbilities.class);
            b(58, PacketPlayOutTabComplete.class);
            b(59, PacketPlayOutScoreboardObjective.class);
            b(60, PacketPlayOutScoreboardScore.class);
            b(61, PacketPlayOutScoreboardDisplayObjective.class);
            b(62, PacketPlayOutScoreboardTeam.class);
            b(63, PacketPlayOutCustomPayload.class);
            b(64, PacketPlayOutKickDisconnect.class);

            a(0, PacketPlayInKeepAlive.class);
            a(1, PacketPlayInChat.class);
            a(2, PacketPlayInUseEntity.class);
            a(3, PacketPlayInFlying.class);
            a(4, PacketPlayInPosition.class);
            a(5, PacketPlayInLook.class);
            a(6, PacketPlayInPositionLook.class);
            a(7, PacketPlayInBlockDig.class);
            a(8, PacketPlayInBlockPlace.class);
            a(9, PacketPlayInHeldItemSlot.class);
            a(10, PacketPlayInArmAnimation.class);
            a(11, PacketPlayInEntityAction.class);
            a(12, PacketPlayInSteerVehicle.class);
            a(13, PacketPlayInCloseWindow.class);
            a(14, PacketPlayInWindowClick.class);
            a(15, PacketPlayInTransaction.class);
            a(16, PacketPlayInSetCreativeSlot.class);
            a(17, PacketPlayInEnchantItem.class);
            a(18, PacketPlayInUpdateSign.class);
            a(19, PacketPlayInAbilities.class);
            a(20, PacketPlayInTabComplete.class);
            a(21, PacketPlayInSettings.class);
            a(22, PacketPlayInClientCommand.class);
            a(23, PacketPlayInCustomPayload.class);
        }
    },
    STATUS("STATUS", 2, 1) {
        {
            b(0, PacketStatusOutServerInfo.class);
            b(1, PacketStatusOutPong.class);

            a(0, PacketStatusInStart.class);
            a(1, PacketStatusInPing.class);
        }
    },
    LOGIN("LOGIN", 3, 2) {
        {
            b(0, PacketLoginOutDisconnect.class);
            b(1, PacketLoginOutEncryptionBegin.class);
            b(2, PacketLoginOutSuccess.class);

            a(0, PacketLoginInStart.class);
            a(1, PacketLoginInEncryptionBegin.class);
            a(112, PacketLoginInRealIP.class);
        }
    };


    private static final TIntObjectMap e = new TIntObjectHashMap();
    private static final Map f = Maps.newHashMap();
    private final int g;
    private final BiMap h;
    private final BiMap i;

    private EnumProtocol(String s, int i, int j) {
        this.h = HashBiMap.create();
        this.i = HashBiMap.create();
        this.g = j;
    }

    protected EnumProtocol a(int i, Class oclass) {
        String s;

        if (this.h.containsKey(Integer.valueOf(i))) {
            s = "Serverbound packet ID " + i + " is already assigned to " + this.h.get(Integer.valueOf(i)) + "; cannot re-assign to " + oclass;
            LogManager.getLogger().fatal(s);
            throw new IllegalArgumentException(s);
        } else if (this.h.containsValue(oclass)) {
            s = "Serverbound packet " + oclass + " is already assigned to ID " + this.h.inverse().get(oclass) + "; cannot re-assign to " + i;
            LogManager.getLogger().fatal(s);
            throw new IllegalArgumentException(s);
        } else {
            this.h.put(Integer.valueOf(i), oclass);
            return this;
        }
    }

    protected EnumProtocol b(int i, Class oclass) {
        String s;

        if (this.i.containsKey(Integer.valueOf(i))) {
            s = "Clientbound packet ID " + i + " is already assigned to " + this.i.get(Integer.valueOf(i)) + "; cannot re-assign to " + oclass;
            LogManager.getLogger().fatal(s);
            throw new IllegalArgumentException(s);
        } else if (this.i.containsValue(oclass)) {
            s = "Clientbound packet " + oclass + " is already assigned to ID " + this.i.inverse().get(oclass) + "; cannot re-assign to " + i;
            LogManager.getLogger().fatal(s);
            throw new IllegalArgumentException(s);
        } else {
            this.i.put(Integer.valueOf(i), oclass);
            return this;
        }
    }

    public BiMap a() {
        return this.h;
    }

    public BiMap b() {
        return this.i;
    }

    public BiMap a(boolean flag) {
        return flag ? this.b() : this.a();
    }

    public BiMap b(boolean flag) {
        return flag ? this.a() : this.b();
    }

    public int c() {
        return this.g;
    }

    public static EnumProtocol a(int i) {
        return (EnumProtocol) e.get(i);
    }

    public static EnumProtocol a(Packet packet) {
        return (EnumProtocol) f.get(packet.getClass());
    }

    static {
        EnumProtocol[] aenumprotocol = values();
        int i = aenumprotocol.length;

        for (int j = 0; j < i; ++j) {
            EnumProtocol enumprotocol = aenumprotocol[j];

            e.put(enumprotocol.c(), enumprotocol);
            Iterator iterator = Iterables.concat(enumprotocol.b().values(), enumprotocol.a().values()).iterator();

            while (iterator.hasNext()) {
                Class oclass = (Class) iterator.next();

                if (f.containsKey(oclass) && f.get(oclass) != enumprotocol) {
                    throw new Error("Packet " + oclass + " is already assigned to protocol " + f.get(oclass) + " - can\'t reassign to " + enumprotocol);
                }

                f.put(oclass, enumprotocol);
            }
        }
    }
}