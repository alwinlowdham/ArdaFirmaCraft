package com.alwinlowdham.afc.util.combat.hitlocs;

import com.alwinlowdham.afc.util.combat.hitlocs.classes.Wound;
import com.alwinlowdham.afc.util.combat.hitlocs.interfaces.IHitLoc;
import mcp.MethodsReturnNonnullByDefault;

import java.util.Map;

// CLEANED
@MethodsReturnNonnullByDefault
public enum BipedHitLoc implements IHitLoc {

    UPPER_HEAD(Wound.UH),
    FACE(Wound.UH),
    LOWER_HEAD(Wound.UH),

    THROAT(Wound.UH),
    // NAPE(Wound.UH),

    CHEST(Wound.UH),
    R_SHOULDER(Wound.UH),
    L_SHOULDER(Wound.UH),
    UPPER_BACK(Wound.UH),

    // R_WING(),
    R_ARM(Wound.UH),
    R_ELBOW(Wound.UH),
    R_FOREARM(Wound.UH),
    R_HAND(Wound.UH),

    // L_WING(),
    L_ARM(Wound.UH),
    L_ELBOW(Wound.UH),
    L_FOREARM(Wound.UH),
    L_HAND(Wound.UH),

    BELLY(Wound.UH),
    // R_SIDE(),
    // L_SIDE(),
    LOWER_BACK(Wound.UH),

    GROIN(Wound.UH),
    R_HIP(Wound.UH),
    L_HIP(Wound.UH),
    // R_BUTTOCK(Wound.UH),
    // L_BUTTOCK(Wound.UH),
    // TAIL(),

    R_THIGH(Wound.UH),
    // R_HAMSTRING(Wound.UH),
    R_KNEE(Wound.UH),
    R_SHIN(Wound.UH),
    // R_CALF(Wound.UH),
    R_FOOT(Wound.UH),

    L_THIGH(Wound.UH),
    // L_HAMSTRING(Wound.UH),
    L_KNEE(Wound.UH),
    L_SHIN(Wound.UH),
    // L_CALF(Wound.UH),
    L_FOOT(Wound.UH);

    private final Map<Character, Map<Integer, Wound>> woundMap;

    BipedHitLoc(Map<Character, Map<Integer, Wound>> woundMap) {
        this.woundMap = woundMap;
    }

    @Override
    public Map<Character, Map<Integer, Wound>> getWoundMap() {
        return this.woundMap;
    }
}
