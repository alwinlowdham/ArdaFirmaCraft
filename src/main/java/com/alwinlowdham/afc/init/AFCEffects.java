package com.alwinlowdham.afc.init;

import com.alwinlowdham.afc.Ref;
import com.alwinlowdham.afc.common.effect.AFCEffect;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

// DONE
// CLEANED
public class AFCEffects {

    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, Ref.MOD_ID);
    public static final RegistryObject<AFCEffect> BLEED_OOZING = EFFECTS.register("bleed_oozing", ()
            -> new AFCEffect(EffectType.HARMFUL, 4393481));
    public static final RegistryObject<AFCEffect> BLEED_SPURTING = EFFECTS.register("bleed_spurting", ()
            -> new AFCEffect(EffectType.HARMFUL, 4393481));
    public static final RegistryObject<AFCEffect> BLEED_TRICKLING = EFFECTS.register("bleed_trickling", ()
            -> new AFCEffect(EffectType.HARMFUL, 4393481));

    public static void init() {
        EFFECTS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
