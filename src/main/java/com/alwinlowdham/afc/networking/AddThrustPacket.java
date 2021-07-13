package com.alwinlowdham.afc.networking;

import com.alwinlowdham.afc.common.item.AFCWeaponItem;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.network.PacketBuffer;

import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class AddThrustPacket {

    private final int itemID;

    public AddThrustPacket(int itemID) {
        this.itemID = itemID;
    }

    public static void encode(AddThrustPacket pkt, PacketBuffer buf) {
        buf.writeInt(pkt.itemID);
    }

    public static AddThrustPacket decode(PacketBuffer buf) {
        return new AddThrustPacket(buf.readInt());
    }

    public static class Handler {

        public static void handle(AddThrustPacket msg, Supplier<NetworkEvent.Context> ctx) {
            if (msg == null) return;

            ctx.get().enqueueWork(() -> {
                ServerPlayerEntity serverPlayer = ((NetworkEvent.Context) ctx.get()).getSender();
                AFCWeaponItem weaponItem = (AFCWeaponItem) Item.byId(msg.itemID);
                serverPlayer.getAttributes().removeAttributeModifiers(weaponItem.getSwingModifiers());
                serverPlayer.getAttributes().addTransientAttributeModifiers(weaponItem.getThrustModifiers());
            });
            ctx.get().setPacketHandled(true);
        }
    }
}

