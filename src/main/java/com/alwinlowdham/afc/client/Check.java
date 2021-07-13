package com.alwinlowdham.afc.client;

import com.alwinlowdham.afc.AFCMod;
import com.alwinlowdham.afc.interfaces.IReach;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;

import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class Check {

    // DONE?
    public static void checkAttack(boolean thrust, InputEvent.ClickInputEvent event) {
        Minecraft mc = Minecraft.getInstance();
        ClientPlayerEntity clientPlayer = mc.player;
        if (clientPlayer == null || mc.gameMode == null || mc.level == null) return;

        ItemStack stack = clientPlayer.getMainHandItem();
        if (!(stack.getItem() instanceof IReach)) return;
        IReach iri = (IReach) stack.getItem();

        event.setCanceled(true);
        event.setSwingHand(false);

        // setting it to negative one here prevents you from thrusting by holding down right click
        ObfuscationReflectionHelper.setPrivateValue(Minecraft.class, mc, -1, "field_71467_ac");

        int missTime = ObfuscationReflectionHelper.getPrivateValue(Minecraft.class, mc, "field_71429_W");
        if (missTime <= 0) {
            if (mc.hitResult == null) {
                AFCMod.LOGGER.error("Null returned as 'hitResult', this shouldn't happen!");
                if (mc.gameMode.hasMissTime()) {
                    ObfuscationReflectionHelper.setPrivateValue(Minecraft.class, mc, 10, "field_71429_W");
                }
            } else {
                switch (mc.hitResult.getType())
                {
                    case ENTITY:
                        Attack.doReachAttack(thrust, ((EntityRayTraceResult) mc.hitResult).getEntity());
                        break;

                    case BLOCK:
                        if (mc.gameMode.isDestroying()) {
                            event.setCanceled(false);
                            event.setSwingHand(true);
                            return;
                        }
                        BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult) mc.hitResult;
                        BlockPos blockpos = blockraytraceresult.getBlockPos();
                        if (!mc.level.isEmptyBlock(blockpos)) {
                            mc.gameMode.startDestroyBlock(blockpos, blockraytraceresult.getDirection());
                            break;
                        }

                    case MISS:
                        if (mc.gameMode.hasMissTime()) {
                            ObfuscationReflectionHelper.setPrivateValue(Minecraft.class, mc, 10, "field_71429_W");
                            net.minecraftforge.common.ForgeHooks.onEmptyLeftClick(clientPlayer);
                        }
                        clientPlayer.resetAttackStrengthTicker();
                }

                mc.player.swing(Hand.MAIN_HAND);
            }
        }
    }
}
