package com.alwinlowdham.afc.util.combat.hitlocs.classes;

import com.alwinlowdham.afc.util.combat.hitlocs.types.SubType;
import com.alwinlowdham.afc.util.combat.hitlocs.types.Type;

// DONE
// CLEANED
public class Effect {

    private final Type type;
    private final SubType subType;
    private final float chance;

    public Effect(Type type, int RS) {
        this.type = type;
        this.chance = ((float) RS) * 2 / 10;
        this.subType = null;
    }

    public Effect(Type type, int RS, SubType subType) {
        this.type = type;
        this.chance = ((float) RS) * 2 / 10;
        this.subType = subType;
    }

    public Effect(Type type) {
        this.type = type;
        this.chance = 1.0F;
        this.subType = null;
    }

    public Effect(Type type, SubType subType) {
        this.type = type;
        this.chance = 1.0F;
        this.subType = subType;
    }

    public Type getType() {
        return type;
    }

    public SubType getSubType() {
        return subType;
    }

    public float getChance() {
        return chance;
    }
}


