package com.alwinlowdham.afc.interfaces;

import net.minecraft.util.DamageSource;

public interface IBlood {
    int getTicksPerBeat();
    boolean bleed(float amount);
}
