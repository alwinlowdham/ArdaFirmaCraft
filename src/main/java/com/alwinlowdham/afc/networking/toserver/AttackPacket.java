package com.alwinlowdham.afc.networking.toserver;

import com.alwinlowdham.afc.common.item.AFCWeaponItem;
import com.alwinlowdham.afc.init.AFCEffects;
import com.alwinlowdham.afc.interfaces.IReach;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class AttackPacket {

    private final boolean thrust;
    private final int entityID;

    public AttackPacket(boolean thrust, int entityID) {
        this.thrust = thrust;
        this.entityID = entityID;
    }

    public static void encode(AttackPacket pkt, PacketBuffer buf) {
        buf.writeBoolean(pkt.thrust);
        buf.writeInt(pkt.entityID);
    }

    public static AttackPacket decode(PacketBuffer buf) {
        return new AttackPacket(buf.readBoolean(), buf.readInt());
    }

    public static class Handler {

        public static void handle(AttackPacket msg, Supplier<NetworkEvent.Context> ctx) {
            if (msg == null) return;

            ctx.get().enqueueWork(() -> {
                ServerPlayerEntity serverPlayer = ctx.get().getSender();
                if (serverPlayer == null) return;

                Entity target = serverPlayer.getLevel().getEntity(msg.entityID);
                if (target == null) return;

                double distanceSquared = serverPlayer.distanceToSqr(target);
                System.out.println("Distance Squared: " + distanceSquared);

                float reach = 0.0F;
                Item item = serverPlayer.getMainHandItem().getItem();
                if (item.getItem() instanceof IReach) {
                    reach = ((IReach) item).getReach();
                }
                double totalReach = reach + serverPlayer.getAttribute(net.minecraftforge.common.ForgeMod.REACH_DISTANCE.get()).getBaseValue();
                totalReach += 0.5*target.getBbWidth();

                double totalReachSquared = totalReach * totalReach;
                System.out.println("Total Reach Squared: " + totalReachSquared);

                // if (totalReachSquared < distanceSquared) return; <- this does actually not need to be here

                // Preliminary thoughts:

                // Attacking more than 1.425 meters inside of your total reach may
                // carry a penalty to attack speed and/or attack damage, depending on
                // the length of the weapon's head and its sweet spot/percussion point.

                // If you attack too far inside your total reach even more, you may not be
                // able to thrust and may only be able to swing with the head, half, or butt.

                AFCWeaponItem weaponItem = null;
                if (item instanceof AFCWeaponItem) {
                    weaponItem = (AFCWeaponItem) item;
                }

                if (msg.thrust && weaponItem != null) {
                    serverPlayer.getAttributes().removeAttributeModifiers(weaponItem.getSwingModifiers());
                    serverPlayer.getAttributes().addTransientAttributeModifiers(weaponItem.getThrustModifiers());
                }

                // mc.gamemode.attack(target); <- for you Nano, this would go here
                /*Begin experimental*/
                if (target instanceof LivingEntity) {
                    ((LivingEntity) target).addEffect(new EffectInstance(AFCEffects.BLEED_SPURTING.get(), 200));
                }
                if (msg.thrust) {
                    serverPlayer.addEffect(new EffectInstance(AFCEffects.BLEED_TRICKLING.get(), 8000));
                }
                else {
                    serverPlayer.addEffect(new EffectInstance(AFCEffects.BLEED_OOZING.get(), 8000));
                }
                /*End experimental*/

                if (msg.thrust && weaponItem != null) {
                    serverPlayer.getAttributes().removeAttributeModifiers(weaponItem.getThrustModifiers());
                    serverPlayer.getAttributes().addTransientAttributeModifiers(weaponItem.getSwingModifiers());
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
}