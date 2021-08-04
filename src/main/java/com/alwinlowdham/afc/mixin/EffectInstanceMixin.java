package com.alwinlowdham.afc.mixin;

import com.alwinlowdham.afc.common.effect.AFCEffect;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// DONE
// CLEANED
@Mixin(EffectInstance.class)
public abstract class EffectInstanceMixin {

    @Shadow
    @Final
    private Effect effect;

    @Inject(method = "tick(Lnet/minecraft/entity/LivingEntity;Ljava/lang/Runnable;)Z", at = @At("HEAD"), remap = false)
    private void afcTick(LivingEntity p_76455_1_, Runnable p_76455_2_, CallbackInfoReturnable<Boolean> ci) {
        if (this.effect instanceof AFCEffect) {
            ((AFCEffect) this.effect).livingTarget = p_76455_1_;
        }
    }
}
