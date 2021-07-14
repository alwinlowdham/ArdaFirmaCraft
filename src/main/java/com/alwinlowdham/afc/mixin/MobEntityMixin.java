package com.alwinlowdham.afc.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin {

    @Overwrite
    public static AttributeModifierMap.MutableAttribute createMobAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.FOLLOW_RANGE, 16.0D)
                .add(Attributes.ATTACK_KNOCKBACK)
                .add(net.minecraftforge.common.ForgeMod.REACH_DISTANCE.get());
    }
}

