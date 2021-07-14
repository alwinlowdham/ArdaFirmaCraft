package com.alwinlowdham.afc.init;

import com.alwinlowdham.afc.Ref;
import com.alwinlowdham.afc.networking.toserver.AttackPacket;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class AFCNetwork {
    protected static final String PROTOCOL_VERSION = "1";

    protected static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(Ref.MOD_ID, "network"))
            .clientAcceptedVersions("1"::equals)
            .serverAcceptedVersions("1"::equals)
            .networkProtocolVersion(() -> "1")
            .simpleChannel();

    protected static int nextPacketID = 0;

    public static void init(){
        INSTANCE.registerMessage(getNextPacketID(), AttackPacket.class, AttackPacket::encode, AttackPacket::decode, AttackPacket.Handler::handle);
    }

    public static int getNextPacketID(){
        return nextPacketID++;
    }

    public static void sendPacketTo(Object packet, ServerPlayerEntity player){
        if (!(player instanceof FakePlayer))
            INSTANCE.sendTo(packet, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static void sendPacketToServer(Object packet){
        INSTANCE.sendToServer(packet);
    }
}
