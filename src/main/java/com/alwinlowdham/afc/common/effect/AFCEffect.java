package com.alwinlowdham.afc.common.effect;

import com.alwinlowdham.afc.init.AFCEffects;

import com.alwinlowdham.afc.interfaces.IBlood;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

// Arterial bleeding fires every heartbeat. Need heart rate field for entities.
// Remember some animals like dogs have irregular heartbeats.
// Venous and capillary bleeding fires every tick.

import javax.annotation.Nullable;

public class AFCEffect extends Effect {
    public AFCEffect(EffectType p_i50391_1_, int p_i50391_2_) {
        super(p_i50391_1_, p_i50391_2_);
    }

    public LivingEntity target;

    @Override
    public void applyEffectTick(LivingEntity target, int amplifier) {
        if (!(target instanceof IBlood)) return;
        if (this == AFCEffects.BLEED_SPURTING.get()) {
            ((IBlood) target).bleed(0.25F);
        }
        else if (this == AFCEffects.BLEED_OOZING.get()) {
            ((IBlood) target).bleed(0.005F);
        }
        else if (this == AFCEffects.BLEED_TRICKLING.get()) {
            ((IBlood) target).bleed(0.0025F);
        }
    }

    @Override
    public void applyInstantenousEffect(@Nullable Entity p_180793_1_, @Nullable Entity p_180793_2_, LivingEntity p_180793_3_, int p_180793_4_, double p_180793_5_) {
        return; // temp
    }

    @Override
    public boolean isDurationEffectTick(int amplifier, int duration) {
        if (this == AFCEffects.BLEED_SPURTING.get()) {
            int i = ((IBlood)this.target).getTicksPerBeat();
            if (i > 0) {
                return amplifier % i == 0;
            } else {
                return true;
            }
        }
        else if (this == AFCEffects.BLEED_OOZING.get() || this == AFCEffects.BLEED_TRICKLING.get()) {
            return true;
        }
        else {
            return false;
        }
    }
}
