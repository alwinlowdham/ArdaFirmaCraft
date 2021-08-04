package com.alwinlowdham.afc.common.effect;

import com.alwinlowdham.afc.common.entity.interfaces.IBlood;
import com.alwinlowdham.afc.init.AFCEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

// Arterial bleeding fires every heartbeat. Need heart rate field for entities.
// Remember some animals like dogs have irregular heartbeats.
// Venous and capillary bleeding fires every tick.

// DONE
// CLEANED
public class AFCEffect extends Effect {

    public LivingEntity livingTarget;

    public AFCEffect(EffectType p_i50391_1_, int p_i50391_2_) {
        super(p_i50391_1_, p_i50391_2_);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (!(livingEntity instanceof IBlood)) return;
        if (this == AFCEffects.BLEED_SPURTING.get()) {
            ((IBlood) livingEntity).bleed(0.25F);
        } else if (this == AFCEffects.BLEED_OOZING.get()) {
            ((IBlood) livingEntity).bleed(0.005F);
        } else if (this == AFCEffects.BLEED_TRICKLING.get()) {
            ((IBlood) livingEntity).bleed(0.0025F);
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public void applyInstantenousEffect(@Nullable Entity p_180793_1_, @Nullable Entity p_180793_2_, LivingEntity p_180793_3_, int p_180793_4_, double p_180793_5_) {
        return; // temp
    }

    @Override
    public boolean isDurationEffectTick(int amplifier, int duration) {
        if (this == AFCEffects.BLEED_SPURTING.get()) {
            final int i = ((IBlood) this.livingTarget).getTicksPerBeat();
            if (i > 0) {
                return amplifier % i == 0;
            } else {
                return true;
            }
        } else return this == AFCEffects.BLEED_OOZING.get() || this == AFCEffects.BLEED_TRICKLING.get();
    }
}
