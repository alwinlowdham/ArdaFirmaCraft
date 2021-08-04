package com.alwinlowdham.afc.client.event;

import com.alwinlowdham.afc.AFCMod;
import com.alwinlowdham.afc.common.item.AFCWeaponItem;
import com.alwinlowdham.afc.init.AFCNetwork;
import com.alwinlowdham.afc.networking.toserver.PlayerHitTargetPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = {Dist.CLIENT})
public class ClientInputEventHandler {

    // DONE EXCEPT BLOCK/PARRY
    // CLEANED
    @SubscribeEvent(priority = EventPriority.HIGHEST, receiveCanceled = true)
    public static void onClickInput(InputEvent.ClickInputEvent event) {
        if (event.isAttack()) {
            checkAttack(false, event);
        } else if (event.isUseItem()) {
            checkAttack(true, event);
        } else if (event.isPickBlock()) {
            // check for block/parry else return
            // (if weapon or shield in offhand) or (both hands not holding weapons and offhand not crippled/severed):
            // block with offhand
            // blocking with offhand is useful because its cooldown is separate from mainhand
            // else:
            // block with mainhand
        }
    }

    // DONE
    // CLEANED
    private static void checkAttack(boolean thrust, InputEvent.ClickInputEvent event) {
        final Minecraft mc = Minecraft.getInstance();

        final ClientPlayerEntity clientPlayer = mc.player;
        if (clientPlayer == null || mc.gameMode == null || mc.level == null) return;

        if (thrust && !(clientPlayer.getMainHandItem().getItem() instanceof AFCWeaponItem)) {
            return;
        }
        event.setCanceled(true);
        event.setSwingHand(false);

        // setting rightClickDelay to -1 here prevents you from thrusting by holding down right click
        ObfuscationReflectionHelper.setPrivateValue(Minecraft.class, mc, -1, "field_71467_ac");

        final int missTime = ObfuscationReflectionHelper.getPrivateValue(Minecraft.class, mc, "field_71429_W");
        if (missTime <= 0) {

            if (mc.hitResult == null) {
                AFCMod.LOGGER.error("Null returned as 'hitResult', this shouldn't happen!");
                if (mc.gameMode.hasMissTime()) {
                    ObfuscationReflectionHelper.setPrivateValue(Minecraft.class, mc, 10, "field_71429_W");
                }

            } else if (!clientPlayer.isHandsBusy()) {
                switch (mc.hitResult.getType()) {

                    case ENTITY:
                        final EntityRayTraceResult entityraytraceresult = (EntityRayTraceResult) mc.hitResult;
                        System.out.println("X: " + entityraytraceresult.getLocation().x());
                        System.out.println("Y: " + entityraytraceresult.getLocation().y());
                        System.out.println("Z: " + entityraytraceresult.getLocation().z());
                        final Entity target = entityraytraceresult.getEntity();
                        AFCNetwork.sendPacketToServer(new PlayerHitTargetPacket(thrust, target.getId()));
                        clientPlayer.resetAttackStrengthTicker();
                        break;

                    case BLOCK:
                        if (mc.gameMode.isDestroying()) { // this likely causing block reappearing bug
                            event.setCanceled(false);
                            event.setSwingHand(true);
                            return;
                        }
                        final BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult) mc.hitResult;
                        final BlockPos blockpos = blockraytraceresult.getBlockPos();
                        if (!mc.level.isEmptyBlock(blockpos)) {
                            mc.gameMode.startDestroyBlock(blockpos, blockraytraceresult.getDirection());
                            break;
                        }

                    case MISS:
                        if (mc.gameMode.hasMissTime()) {
                            ObfuscationReflectionHelper.setPrivateValue(Minecraft.class, mc, 10, "field_71429_W");
                        }
                        clientPlayer.resetAttackStrengthTicker();
                        net.minecraftforge.common.ForgeHooks.onEmptyLeftClick(clientPlayer);
                }
                clientPlayer.swing(Hand.MAIN_HAND);
            }
        }
    }
}
