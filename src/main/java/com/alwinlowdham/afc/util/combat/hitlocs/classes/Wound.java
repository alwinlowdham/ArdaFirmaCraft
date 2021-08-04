package com.alwinlowdham.afc.util.combat.hitlocs.classes;

import com.alwinlowdham.afc.util.combat.hitlocs.types.SubType;
import com.alwinlowdham.afc.util.combat.hitlocs.types.Type;
import com.google.common.collect.ImmutableMap;
import mcp.MethodsReturnNonnullByDefault;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

// CLEANED
@MethodsReturnNonnullByDefault
public class Wound {

    private static final Integer TOTAL = Integer.MAX_VALUE;
    // temp
    public static final Map<Character, Map<Integer, Wound>> UH = ImmutableMap.of(
            'c', ImmutableMap.of(
                    1, new Wound(1, 7, 1),
                    2, new Wound(2, 9, 3),
                    3, new Wound(4, 13, 5,
                            new Effect(Type.KNOCKOUT, 4)),
                    4, new Wound(TOTAL, TOTAL, 8, Arrays.asList(
                            new Effect(Type.KNOCKOUT),
                            new Effect(Type.SURGERY, SubType.BRAIN_DAMAGE),
                            new Effect(Type.INTERNAL_BLEEDING))),
                    5, new Wound(TOTAL, TOTAL, 20,
                            new Effect(Type.DEATH))
            ),
            'p', ImmutableMap.of(
                    1, new Wound(0, 4, 0),
                    2, new Wound(1, 6, 3),
                    3, new Wound(2, 10, 5,
                            new Effect(Type.KNOCKOUT, 3)),
                    4, new Wound(TOTAL, TOTAL, 20, Arrays.asList(
                            new Effect(Type.KNOCKOUT),
                            new Effect(Type.SURGERY, SubType.BRAIN_DAMAGE),
                            new Effect(Type.INTERNAL_BLEEDING))),
                    5, new Wound(TOTAL, TOTAL, 20,
                            new Effect(Type.DEATH))
            ),
            'b', ImmutableMap.of(
                    1, new Wound(1, 4, 1),
                    2, new Wound(3, 6, 2,
                            new Effect(Type.KNOCKOUT, 1)),
                    3, new Wound(5, 8, 4,
                            new Effect(Type.KNOCKOUT, 5)),
                    4, new Wound(TOTAL, TOTAL, 6, Arrays.asList(
                            new Effect(Type.KNOCKOUT),
                            new Effect(Type.SURGERY, SubType.BRAIN_DAMAGE),
                            new Effect(Type.INTERNAL_BLEEDING))),
                    5, new Wound(TOTAL, TOTAL, 20,
                            new Effect(Type.DEATH))
            ),
            'u', ImmutableMap.of(
                    1, new Wound(0, 0, 0),
                    2, new Wound(0, 0, 0),
                    3, new Wound(1, 1, 0,
                            new Effect(Type.KNOCKOUT, 1)),
                    4, new Wound(3, 3, 0,
                            new Effect(Type.KNOCKOUT, 3)),
                    5, new Wound(TOTAL, 10, 3, Arrays.asList(
                            new Effect(Type.KNOCKOUT),
                            new Effect(Type.INTERNAL_BLEEDING)))
            )
    );

    public static Map<Character, Wound> LEVEL_0 = ImmutableMap.of(
            'c', new Wound(0, 1, 0),
            'p', new Wound(0, 1, 0),
            'b', new Wound(0, 1, 0),
            'u', new Wound(1, 0, 0)
    );

    private final int stun;
    private final int pain;
    private final int bleed;
    private final List<Effect> effects;

    public Wound(int stun, int pain, int bleed, List<Effect> effects) {
        this.stun = stun;
        this.pain = pain;
        this.bleed = bleed;
        this.effects = effects;
    }

    public Wound(int stun, int pain, int bleed, Effect effect) {
        this.stun = stun;
        this.pain = pain;
        this.bleed = bleed;
        this.effects = Collections.singletonList(effect);
    }

    public Wound(int stun, int pain, int bleed) {
        this.stun = stun;
        this.pain = pain;
        this.bleed = bleed;
        this.effects = Collections.emptyList();
    }

    public float getStun() {
        return this.stun;
    }

    public float getPain() {
        return this.pain;
    }

    public float getBleed() {
        return this.bleed;
    }

    public List<Effect> getEffects() {
        return this.effects;
    }
}
