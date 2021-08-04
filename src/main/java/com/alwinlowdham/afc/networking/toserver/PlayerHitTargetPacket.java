package com.alwinlowdham.afc.networking.toserver;

import com.alwinlowdham.afc.common.item.AFCWeaponItem;
import com.alwinlowdham.afc.init.AFCNetwork;
import com.alwinlowdham.afc.networking.toclient.CheckHitPacket;
import com.alwinlowdham.afc.util.combat.CombatHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

// CLEANED
public class PlayerHitTargetPacket {

    private final boolean thrust;
    private final int targetID;

    public PlayerHitTargetPacket(boolean thrust, int targetID) {
        this.thrust = thrust;
        this.targetID = targetID;
    }

    public static void encode(PlayerHitTargetPacket pkt, PacketBuffer buf) {
        buf.writeBoolean(pkt.thrust);
        buf.writeInt(pkt.targetID);
    }

    public static PlayerHitTargetPacket decode(PacketBuffer buf) {
        return new PlayerHitTargetPacket(buf.readBoolean(), buf.readInt());
    }

    private static void addThrustIfThrust(boolean thrust, AFCWeaponItem weapon, ServerPlayerEntity serverPlayer) {
        if (thrust && weapon != null) {
            serverPlayer.getAttributes().removeAttributeModifiers(weapon.getSwingModifiers());
            serverPlayer.getAttributes().addTransientAttributeModifiers(weapon.getThrustModifiers());
        }
    }

    private static void removeThrustIfThrust(boolean thrust, AFCWeaponItem weapon, ServerPlayerEntity serverPlayer) {
        if (thrust && weapon != null) {
            serverPlayer.getAttributes().removeAttributeModifiers(weapon.getThrustModifiers());
            serverPlayer.getAttributes().addTransientAttributeModifiers(weapon.getSwingModifiers());
        }
    }

    public static class Handler {

        // Preliminary thoughts:

        // Attacking more than 1.425 meters inside of your total reach may
        // carry a penalty to attack speed and/or attack damage, depending on
        // the length of the weapon's head and its sweet spot/percussion point.

        // If you attack too far inside your total reach even more, you may not be
        // able to thrust and may only be able to swing with the head, half, or butt.

        public static void handle(PlayerHitTargetPacket msg, Supplier<NetworkEvent.Context> ctx) {
            if (msg == null) return;

            ctx.get().enqueueWork(() -> {

                final ServerPlayerEntity serverPlayer = ctx.get().getSender();
                if (serverPlayer == null) return;

                final Entity target = serverPlayer.getLevel().getEntity(msg.targetID);
                if (target == null) return;

                final Item item = serverPlayer.getMainHandItem().getItem();
                final AFCWeaponItem weapon = CombatHelper.getWeapon(item);

                addThrustIfThrust(msg.thrust, weapon, serverPlayer);

                // mc.gamemode.attack(target); <- for you Nano, this would go here
                AFCNetwork.sendPacketTo(new CheckHitPacket(serverPlayer.getId(), msg.targetID), serverPlayer);

                removeThrustIfThrust(msg.thrust, weapon, serverPlayer);

            });
            ctx.get().setPacketHandled(true);
        }
    }
}