package net.minecraft.server;

import java.util.Map;
import net.minecraft.util.com.google.common.collect.BiMap;
import net.minecraft.util.com.google.common.collect.HashBiMap;
import net.minecraft.util.com.google.common.collect.Iterables;
import net.minecraft.util.com.google.common.collect.Maps;
import net.minecraft.util.gnu.trove.map.TIntObjectMap;
import net.minecraft.util.gnu.trove.map.hash.TIntObjectHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum EnumProtocol
{
	HANDSHAKING(-1) {
		{
			a(0, PacketHandshakingInSetProtocol.class);
		}
	},
	PLAY(0) {
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
	STATUS(1) {
		{
			b(0, PacketStatusOutServerInfo.class);
			b(1, PacketStatusOutPong.class);

			a(0, PacketStatusInStart.class);
			a(1, PacketStatusInPing.class);
		}
	},
	LOGIN(2) {
		{
			b(0, PacketLoginOutDisconnect.class);
			b(1, PacketLoginOutEncryptionBegin.class);
			b(2, PacketLoginOutSuccess.class);

			a(0, PacketLoginInStart.class);
			a(1, PacketLoginInEncryptionBegin.class);
			a(112, PacketLoginInRealIP.class);
		}
	};

	private static final TIntObjectMap e;
	private static final Map f;
	private final int g;
	private final BiMap h = HashBiMap.create();
	private final BiMap i = HashBiMap.create();

	private EnumProtocol(int paramInt)
	{
		this.g = paramInt;
	}

	protected EnumProtocol a(int paramInt, Class paramClass)
	{
		String str;
		if (this.h.containsKey(Integer.valueOf(paramInt)))
		{
			str = "Serverbound packet ID " + paramInt + " is already assigned to " + this.h.get(Integer.valueOf(paramInt)) + "; cannot re-assign to " + paramClass;
			LogManager.getLogger().fatal(str);
			throw new IllegalArgumentException(str);
		}
		if (this.h.containsValue(paramClass))
		{
			str = "Serverbound packet " + paramClass + " is already assigned to ID " + this.h.inverse().get(paramClass) + "; cannot re-assign to " + paramInt;
			LogManager.getLogger().fatal(str);
			throw new IllegalArgumentException(str);
		}
		this.h.put(Integer.valueOf(paramInt), paramClass);
		return this;
	}

	protected EnumProtocol b(int paramInt, Class paramClass)
	{
		String str;
		if (this.i.containsKey(Integer.valueOf(paramInt)))
		{
			str = "Clientbound packet ID " + paramInt + " is already assigned to " + this.i.get(Integer.valueOf(paramInt)) + "; cannot re-assign to " + paramClass;
			LogManager.getLogger().fatal(str);
			throw new IllegalArgumentException(str);
		}
		if (this.i.containsValue(paramClass))
		{
			str = "Clientbound packet " + paramClass + " is already assigned to ID " + this.i.inverse().get(paramClass) + "; cannot re-assign to " + paramInt;
			LogManager.getLogger().fatal(str);
			throw new IllegalArgumentException(str);
		}
		this.i.put(Integer.valueOf(paramInt), paramClass);
		return this;
	}

	public BiMap a()
	{
		return this.h;
	}

	public BiMap b()
	{
		return this.i;
	}

	public BiMap a(boolean paramBoolean)
	{
		return paramBoolean ? b() : a();
	}

	public BiMap b(boolean paramBoolean)
	{
		return paramBoolean ? a() : b();
	}

	public int c()
	{
		return this.g;
	}

	static
	{
		e = new TIntObjectHashMap();
		f = Maps.newHashMap();
		for (EnumProtocol localEnumProtocol : values())
		{
			e.put(localEnumProtocol.c(), localEnumProtocol);
			for (Object tmpObj : Iterables.concat(localEnumProtocol.b().values(), localEnumProtocol.a().values()))
			{
				Class localClass = (Class)tmpObj;
				if ((f.containsKey(localClass)) && (f.get(localClass) != localEnumProtocol)) {
					throw new Error("Packet " + localClass + " is already assigned to protocol " + f.get(localClass) + " - can't reassign to " + localEnumProtocol);
				}
				f.put(localClass, localEnumProtocol);
			}
		}
	}

	public static EnumProtocol a(int paramInt)
	{
		return (EnumProtocol)e.get(paramInt);
	}

	public static EnumProtocol a(Packet paramPacket)
	{
		return (EnumProtocol)f.get(paramPacket.getClass());
	}
}
