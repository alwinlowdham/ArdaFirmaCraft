package com.alwinlowdham.afc.util.combat.hitlocs.interfaces;

import com.alwinlowdham.afc.util.combat.hitlocs.classes.Wound;

import java.util.Map;

// DONE
// CLEANED
public interface IHitLoc {

    Integer TOTAL = Integer.MAX_VALUE;

    Map<Character, Map<Integer, Wound>> getWoundMap();
}
