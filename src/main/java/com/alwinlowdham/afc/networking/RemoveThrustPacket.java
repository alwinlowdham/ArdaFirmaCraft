package com.alwinlowdham.afc.networking;

import com.alwinlowdham.afc.common.item.AFCWeaponItem;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.network.PacketBuffer;

import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class RemoveThrustPacket {

    private final int itemID;

    public RemoveThrustPacket(int itemID) {
        this.itemID = itemID;
    }

    public static void encode(RemoveThrustPacket pkt, PacketBuffer buf) {
        buf.writeInt(pkt.itemID);
    }

    public static RemoveThrustPacket decode(PacketBuffer buf) {
        return new RemoveThrustPacket(buf.readInt());
    }

    public static class Handler {

        public static void handle(RemoveThrustPacket msg, Supplier<NetworkEvent.Context> ctx) {
            if (msg == null) return;

            ctx.get().enqueueWork(() -> {
                ServerPlayerEntity serverPlayer = ((NetworkEvent.Context) ctx.get()).getSender();
                AFCWeaponItem weaponItem = (AFCWeaponItem) Item.byId(msg.itemID);
                serverPlayer.getAttributes().removeAttributeModifiers(weaponItem.getThrustModifiers());
                serverPlayer.getAttributes().addTransientAttributeModifiers(weaponItem.getSwingModifiers());
            });
            ctx.get().setPacketHandled(true);
        }
    }
}

