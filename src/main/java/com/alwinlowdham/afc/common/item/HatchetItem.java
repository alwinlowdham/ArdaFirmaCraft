package com.alwinlowdham.afc.common.item;

import net.minecraft.item.IItemTier;

public class HatchetItem extends AFCWeaponItem {

    public HatchetItem(IItemTier tier, String[] type, int hands, boolean handOff, float reach, float swingDamageIn, int swingTN, char swingDamageType, int swingAP, float thrustDamageIn, int thrustTN, char thrustDamageType, int thrustAP, int defenseTN, int guardAV, boolean balanced, boolean light, boolean heavy, float weight, int bloody, int shock, int crushing, int draw, int spatulateTip, boolean forwardSwept, boolean hook, boolean fluidThrusts, boolean tightGrip, boolean thinBlade, Properties properties) {
        super(tier, hands, type, handOff, reach, swingDamageIn, swingTN, swingDamageType, swingAP, thrustDamageIn, thrustTN, thrustDamageType, thrustAP, defenseTN, guardAV, balanced, light, heavy, weight, bloody, shock, crushing, draw, spatulateTip, forwardSwept, hook, fluidThrusts, tightGrip, thinBlade, properties);
    }
}
