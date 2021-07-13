package com.alwinlowdham.afc.client;

import com.alwinlowdham.afc.common.item.AFCWeaponItem;
import com.alwinlowdham.afc.init.AFCNetwork;
import com.alwinlowdham.afc.interfaces.IReach;
import com.alwinlowdham.afc.networking.AddThrustPacket;
import com.alwinlowdham.afc.networking.RemoveThrustPacket;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.common.ForgeMod;

// added target bounding box's width to player's reach like in vanilla MeleeAttackGoal
// that may be too much, since it's a diameter (probably) and we want a radius: so adding half
// may also need to add target bb width in GameRendererMixin ???
// DON'T try adding target box height if attacking top of bounding box: I can silently simulate ability to duck
public class Attack {

    // DONE-ish
    public static void doReachAttack(boolean thrust, Entity target) {
        Minecraft mc = Minecraft.getInstance();
        ClientPlayerEntity clientPlayer = mc.player;

        if (clientPlayer == null || mc.gameMode == null || target == null) return;

        ItemStack stack = clientPlayer.getMainHandItem();
        if (!(stack.getItem() instanceof IReach)) return;

        double distanceSquared = clientPlayer.distanceToSqr(target);
        System.out.println("Distance Squared: " + distanceSquared);

        IReach reachItem = (IReach) clientPlayer.getMainHandItem().getItem();
        double totalReach = reachItem.getReach() + clientPlayer.getAttribute(ForgeMod.REACH_DISTANCE.get()).getBaseValue();
        totalReach += 0.5*target.getBbWidth();
        double totalReachSquared = totalReach * totalReach;
        System.out.println("Total Reach Squared: " + totalReachSquared);

        if (totalReachSquared < distanceSquared) return;

        // Preliminary thoughts:

        // Attacking more than 1.425 meters inside of your total reach may
        // carry a penalty to attack speed and/or attack damage, depending on
        // the length of the weapon's head and its sweet spot/percussion point.

        // If you attack too far inside your total reach even more, you may not be
        // able to thrust and may only be able to swing with the head, half, or butt.

        AFCWeaponItem weaponItem = (AFCWeaponItem) stack.getItem();

        if (thrust) {
            AFCNetwork.sendPacketToServer(new AddThrustPacket(Item.getId(weaponItem)));
        }

        mc.gameMode.attack(clientPlayer, target);

        if (thrust) {
            AFCNetwork.sendPacketToServer(new RemoveThrustPacket(Item.getId(weaponItem)));
        }
    }
}
