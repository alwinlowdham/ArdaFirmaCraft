package com.alwinlowdham.afc.mixin;

import com.alwinlowdham.afc.common.entity.interfaces.IBlood;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import javax.annotation.Nullable;

// DONE
// CLEANED
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements IBlood {

    @Unique
    private final int ticksPerBeat = 24;
    @Override
    public int getTicksPerBeat() {
        return this.ticksPerBeat;
    }

    @Shadow
    protected int noActionTime;
    @Shadow
    protected DamageSource lastDamageSource;
    // DO NOT MAKE FINAL! Will need to be changed depending on entity.
    @Shadow
    protected long lastDamageStamp;
    // restingBPM = 50: heart rate of elite athlete
    // beatsPerMinute = restingBPM, when at rest
    // beatsPerSecond = beatsPerMinute/60
    // beatsPerTick = beatsPerSecond/20
    // ticksPerBeat = 1/beatsPerTick
    @Shadow
    public abstract boolean isDeadOrDying();
    @Shadow
    public abstract boolean isSleeping();
    @Shadow
    public abstract void stopSleeping();
    @Shadow
    public abstract float getHealth();
    @Shadow
    public abstract void setHealth(float amount);
    @Shadow
    protected abstract boolean checkTotemDeathProtection(DamageSource p_190628_1_);
    @Shadow
    @Nullable
    protected abstract SoundEvent getDeathSound();
    @Shadow
    protected abstract float getSoundVolume();
    @Shadow
    protected abstract float getVoicePitch();
    @Shadow
    protected abstract void die(DamageSource p_70645_1_);

    public LivingEntityMixin(EntityType<?> p_i48580_1_, World p_i48580_2_) {
        super(p_i48580_1_, p_i48580_2_);
    }

    @Override
    public boolean bleed(float amount) {
        final DamageSource DAMAGE_SOURCE = DamageSource.WITHER;

        if (this.isInvulnerableTo(DAMAGE_SOURCE)) {
            return false;
        } else if (this.level.isClientSide) {
            return false;
        } else if (this.isDeadOrDying()) {
            return false;
        } else {
            if (this.isSleeping() && !this.level.isClientSide) {
                this.stopSleeping();
            }

            this.noActionTime = 0; // ???
            this.setHealth(this.getHealth() - amount);

            byte b0 = 2;
            this.level.broadcastEntityEvent(this, b0);

            if (this.isDeadOrDying()) {
                if (!this.checkTotemDeathProtection(DAMAGE_SOURCE)) {
                    SoundEvent soundevent = this.getDeathSound();
                    if (soundevent != null) {
                        this.playSound(soundevent, this.getSoundVolume(), this.getVoicePitch());
                    }

                    this.die(DAMAGE_SOURCE);
                }
            }

            this.lastDamageSource = DAMAGE_SOURCE;
            this.lastDamageStamp = this.level.getGameTime();

            return true;
        }
    }
}
