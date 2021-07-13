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

public class AFCWeaponItem extends TieredItem implements IReach {

    private final Multimap<Attribute, AttributeModifier> swingModifiers;
    private final Multimap<Attribute, AttributeModifier> thrustModifiers;

    private String[] type;
    private int hands;
    private boolean handOff;
    private float reach;

    private float swingDamage;
    private float swingSpeed;
    private char swingDamageType;
    private int swingAP;

    private float thrustDamage;
    private float thrustSpeed;
    private char thrustDamageType;
    private int thrustAP;

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
        this.swingDamage = swingDamageIn + tier.getAttackDamageBonus();
        this.swingSpeed = -(swingTN - 5.0F)/4.0F + (balanced ? 0.0F : -0.25F);
        this.thrustDamage = thrustDamageIn + tier.getAttackDamageBonus();
        this.thrustSpeed = -(thrustTN - 5.0F)/4.0F + (balanced ? 0.0F : -0.25F);
        // algorithim from TN to speed needs to be changed
        // also need to make sure any attack speed changes on thrusts are actually working

        this.reach = reach;

        ImmutableMultimap.Builder<Attribute, AttributeModifier> swingBuilder = ImmutableMultimap.builder();
        swingBuilder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier",
                this.swingDamage, AttributeModifier.Operation.ADDITION));
        swingBuilder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier",
                this.swingSpeed, AttributeModifier.Operation.ADDITION));
        this.swingModifiers = swingBuilder.build();

        ImmutableMultimap.Builder<Attribute, AttributeModifier> thrustBuilder = ImmutableMultimap.builder();
        thrustBuilder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier",
                this.thrustDamage, AttributeModifier.Operation.ADDITION));
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

