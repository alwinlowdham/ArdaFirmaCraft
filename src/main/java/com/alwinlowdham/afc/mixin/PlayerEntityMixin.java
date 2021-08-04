package com.alwinlowdham.afc.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

// DONE
// CLEANED
@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Overwrite
    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(Attributes.ATTACK_DAMAGE, 0.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.1D)
                .add(Attributes.ATTACK_SPEED, 2.0D)
                .add(Attributes.LUCK)
                .add(net.minecraftforge.common.ForgeMod.REACH_DISTANCE.get(), 1.5D);
    }
}

