package com.alwinlowdham.afc.mixin;

import com.alwinlowdham.afc.interfaces.IReach;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.item.Item;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MeleeAttackGoal.class)
public abstract class MeleeAttackGoalMixin {

    @Shadow @Final protected CreatureEntity mob;

    @Overwrite
    protected double getAttackReachSqr(LivingEntity target) {
        float reach = 0.0F;
        Item item = target.getMainHandItem().getItem();
        if (item.getItem() instanceof IReach) {
            reach = ((IReach) item).getReach();
        }

        double totalReach = reach + this.mob.getAttribute(net.minecraftforge.common.ForgeMod.REACH_DISTANCE.get()).getBaseValue();
        totalReach += 0.5*target.getBbWidth();
        return (totalReach * totalReach);
    }
}
