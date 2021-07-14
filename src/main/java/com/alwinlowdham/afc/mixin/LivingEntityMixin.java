package com.alwinlowdham.afc.mixin;

import com.alwinlowdham.afc.interfaces.IBlood;

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


@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements IBlood {

    public LivingEntityMixin(EntityType<?> p_i48580_1_, World p_i48580_2_) {
        super(p_i48580_1_, p_i48580_2_);
    }
    
    // restingBPM = 50: heart rate of elite athlete
    // beatsPerMinute = restingBPM, when at rest
    // beatsPerSecond = beatsPerMinute/60
    // beatsPerTick = beatsPerSecond/20
    // ticksPerBeat = 1/beatsPerTick
    @Unique private int ticksPerBeat = 24;

    @Override
    public int getTicksPerBeat() {
        return this.ticksPerBeat;
    }

    @Shadow abstract public boolean isDeadOrDying();
    @Shadow abstract public boolean isSleeping();
    @Shadow abstract public void stopSleeping();
    @Shadow protected int noActionTime;
    @Shadow abstract public void setHealth(float amount);
    @Shadow abstract public float getHealth();
    @Shadow abstract protected boolean checkTotemDeathProtection(DamageSource p_190628_1_);
    @Shadow @Nullable abstract protected SoundEvent getDeathSound();
    @Shadow abstract protected float getSoundVolume();
    @Shadow abstract protected float getVoicePitch();
    @Shadow abstract public void die(DamageSource p_70645_1_);
    @Shadow protected DamageSource lastDamageSource;
    @Shadow protected long lastDamageStamp;

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
