package com.alwinlowdham.afc.common.item;

import com.alwinlowdham.afc.interfaces.IReach;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TieredItem;

import java.lang.Math;

public class AFCWeaponItem extends TieredItem implements IReach {

    private final Multimap<Attribute, AttributeModifier> swingModifiers;
    private final Multimap<Attribute, AttributeModifier> thrustModifiers;

    private final String[] type;
    private int hands;
    private final boolean handOff;
    private final float reach;

    private final float swingDamage;
    private final float swingSpeed;
    private final char swingDamageType;
    private final int swingAP;

    private final float thrustDamage;
    private final float thrustSpeed;
    private final char thrustDamageType;
    private final int thrustAP;

    private final int defenseTN;
    private final int guardAV;

    private final boolean balanced;
    private final boolean light;
    private final boolean heavy;
    private final float weight;

    private final int bloody;
    private final int shock;
    private final int crushing;

    public AFCWeaponItem(IItemTier tier,
                     String[] type, int hands, boolean handOff, float reach,
                     float swingDamageIn, int swingTN, char swingDamageType, int swingAP,
                     float thrustDamageIn, int thrustTN, char thrustDamageType, int thrustAP,
                     int defenseTN, int guardAV,
                     boolean balanced, boolean light, boolean heavy, float weight,
                     int bloody, int shock, int crushing,
                     int draw, int spatulateTip, boolean forwardSwept,
                     boolean hook, boolean fluidThrusts,
                     boolean tightGrip, boolean thinBlade,
                     Properties properties) {
        super(tier, properties);

        final float e = (float) Math.E;
        final float pi = (float) Math.PI;
        final float b = balanced ? 0.0F : e/(pi*pi);

        this.type = type;
        this.hands = hands;
        this.handOff = handOff;
        this.reach = reach;

        this.swingDamage = swingDamageIn + tier.getAttackDamageBonus();
        this.swingSpeed = -(swingTN - 6.0F)/e - b;
        this.swingDamageType = swingDamageType;
        this.swingAP = swingAP;

        this.thrustDamage = thrustDamageIn + tier.getAttackDamageBonus();
        this.thrustSpeed = -(thrustTN - 6.0F)/e - b;
        this.thrustDamageType = thrustDamageType;
        this.thrustAP = thrustAP;

        this.defenseTN = defenseTN;
        this.guardAV = guardAV;
        this.balanced = balanced;
        this.light = light;
        this.heavy = heavy;
        this.weight = weight;

        this.bloody = bloody;
        this.shock = shock;
        this.crushing = crushing;

        // algorithm for TN to speed may need to be changed
        // also need to make sure any attack speed changes on thrusts are actually working

        ImmutableMultimap.Builder<Attribute, AttributeModifier> swingBuilder = ImmutableMultimap.builder();
        swingBuilder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier",
                0.0F, AttributeModifier.Operation.ADDITION));
        swingBuilder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier",
                this.swingSpeed, AttributeModifier.Operation.ADDITION));
        this.swingModifiers = swingBuilder.build();

        ImmutableMultimap.Builder<Attribute, AttributeModifier> thrustBuilder = ImmutableMultimap.builder();
        thrustBuilder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier",
                0.0F, AttributeModifier.Operation.ADDITION));
        thrustBuilder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier",
                this.thrustSpeed, AttributeModifier.Operation.ADDITION));
        this.thrustModifiers = thrustBuilder.build();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        return slot == EquipmentSlotType.MAINHAND ? this.swingModifiers : super.getAttributeModifiers(slot, stack);
    }


    @Override
    public float getReach() {
        return this.reach;
    }

    public Multimap<Attribute, AttributeModifier> getSwingModifiers() {
        return this.swingModifiers;
    }

    public Multimap<Attribute, AttributeModifier> getThrustModifiers() {
        return this.thrustModifiers;
    }
}

