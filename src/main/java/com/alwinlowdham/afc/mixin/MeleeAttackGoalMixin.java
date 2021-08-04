package com.alwinlowdham.afc.mixin;

import com.alwinlowdham.afc.util.combat.CombatHelper;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

// DONE
// CLEANED
@Mixin(MeleeAttackGoal.class)
public abstract class MeleeAttackGoalMixin {

    @Shadow
    @Final
    protected CreatureEntity mob;

    @Overwrite
    protected double getAttackReachSqr(LivingEntity target) {
        double d0 = CombatHelper.getTotalReach(this.mob);
        d0 += 0.5 * target.getBbWidth();
        return d0 * d0;
    }
}
