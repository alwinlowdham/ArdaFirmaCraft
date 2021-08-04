package com.alwinlowdham.afc.init;

import com.alwinlowdham.afc.Ref;
import com.alwinlowdham.afc.networking.toclient.CheckHitPacket;
import com.alwinlowdham.afc.networking.toserver.PlayerHitTargetPacket;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

// DONE
// CLEANED
public class AFCNetwork {

    protected static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(Ref.MOD_ID, "network"))
            .clientAcceptedVersions(PROTOCOL_VERSION::equals)
            .serverAcceptedVersions(PROTOCOL_VERSION::equals)
            .networkProtocolVersion(() -> PROTOCOL_VERSION)
            .simpleChannel();
    protected static int nextPacketID = 0;

    public static int getNextPacketID() {
        return nextPacketID++;
    }

    public static void init() {
        INSTANCE.registerMessage(getNextPacketID(), PlayerHitTargetPacket.class, PlayerHitTargetPacket::encode, PlayerHitTargetPacket::decode, PlayerHitTargetPacket.Handler::handle);
        INSTANCE.registerMessage(getNextPacketID(), CheckHitPacket.class, CheckHitPacket::encode, CheckHitPacket::decode, CheckHitPacket.Handler::handle);
    }

    public static void sendPacketTo(Object packet, ServerPlayerEntity player) {
        if (!(player instanceof FakePlayer))
            INSTANCE.sendTo(packet, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendPacketToServer(Object packet) {
        INSTANCE.sendToServer(packet);
    }
}
