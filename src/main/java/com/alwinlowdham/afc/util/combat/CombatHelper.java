package com.alwinlowdham.afc.util.combat;

import com.alwinlowdham.afc.common.item.AFCWeaponItem;
import com.alwinlowdham.afc.common.item.interfaces.IReach;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraftforge.common.ForgeMod;

import java.util.Objects;

// DONE
// CLEANED
public class CombatHelper {

    public static double getTotalReach(LivingEntity livingEntity) {
        if (livingEntity instanceof PlayerEntity || livingEntity instanceof MobEntity) {
            final float reach;
            final Item item = livingEntity.getMainHandItem().getItem();
            if (item.getItem() instanceof IReach) {
                reach = ((IReach) item).getReach();
            } else reach = 0.0F;
            return reach + Objects.requireNonNull(livingEntity.getAttribute(ForgeMod.REACH_DISTANCE.get())).getBaseValue();
        } else throw new IllegalArgumentException();
    }

    public static AFCWeaponItem getWeapon(Item item) {
        final AFCWeaponItem weapon;
        if (item instanceof AFCWeaponItem) {
            weapon = (AFCWeaponItem) item;
        } else weapon = null;
        return weapon;
    }
}
