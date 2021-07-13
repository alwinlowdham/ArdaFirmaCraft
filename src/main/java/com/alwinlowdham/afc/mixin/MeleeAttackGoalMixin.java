package com.alwinlowdham.afc.mixin;

import com.alwinlowdham.afc.interfaces.IReach;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.item.Item;

import net.minecraftforge.common.ForgeMod;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MeleeAttackGoal.class)
public abstract class MeleeAttackGoalMixin {

    @Shadow
    @Final
    protected CreatureEntity mob;

    @Overwrite
    protected double getAttackReachSqr(LivingEntity p_179512_1_) {
        float reach = 0.0F;
        Item item = p_179512_1_.getMainHandItem().getItem();
        if (item.getItem() instanceof IReach) {
            reach = ((IReach) item).getReach();
        }

        double totalReach = reach + this.mob.getAttribute(ForgeMod.REACH_DISTANCE.get()).getBaseValue();
        totalReach += 0.5*p_179512_1_.getBbWidth();
        return (totalReach * totalReach);
    }
}
