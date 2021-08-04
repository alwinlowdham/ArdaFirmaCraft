package com.alwinlowdham.afc.init;

import com.alwinlowdham.afc.Ref;
import com.alwinlowdham.afc.common.item.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

// DONE
// CLEANED
public class AFCItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Ref.MOD_ID);
    public static final RegistryObject<ArmingSwordItem> STONE_ARMING_SWORD_BROAD = ITEMS.register("stone_arming_sword_broad", ()
                    -> new ArmingSwordItem( // called chivalric in book
                    AFCItemTier.STONE,
                    new String[]{"1hSword"}, 1, false, 0.75F,
                    +2.0F, 7, 'c', 0,
                    +0.0F, 7, 'p', 0,
                    7, 2,
                    true, false, false, 0.5F,
                    0, 0, 0,
                    0, 0, false,
                    false, false,
                    false, false,
                    new Item.Properties().tab(ItemGroup.TAB_COMBAT)
            )
    );

    // Add a new Arming Sword called Arming Sword (Spatulate)
    public static final RegistryObject<ArmingSwordItem> STONE_ARMING_SWORD_TAPERED = ITEMS.register("stone_arming_sword_tapered", ()
                    -> new ArmingSwordItem( // called late in book
                    AFCItemTier.STONE,
                    new String[]{"1hSword"}, 1, false, 0.75F,
                    +0.0F, 7, 'c', 0,
                    +2.0F, 7, 'p', 0,
                    7, 2,
                    true, false, false, 0.5F,
                    0, 0, 0,
                    0, 0, false,
                    false, false,
                    false, false,
                    new Item.Properties().tab(ItemGroup.TAB_COMBAT)
            )
    );
    public static final RegistryObject<HammerItem> STONE_HAMMER_WAR = ITEMS.register("stone_hammer_war", ()
                    -> new HammerItem(
                    AFCItemTier.STONE,
                    new String[]{"1hBlunt"}, 1, false, 0.5F,
                    +2.0F, 7, 'b', 1,
                    -2.0F, 7, 'b', 0,
                    8, 0,
                    false, false, false, 0.0F,
                    0, 1, 1,
                    0, 0, false,
                    false, false,
                    false, false,
                    new Item.Properties().tab(ItemGroup.TAB_COMBAT)
            )
    );
    public static final RegistryObject<HatchetItem> STONE_HATCHET_WAR = ITEMS.register("stone_hatchet_war", ()
                    -> new HatchetItem( // called hatchet in book
                    AFCItemTier.STONE,
                    new String[]{"1hBlunt"}, 1, false, 0.5F,
                    +2.0F, 7, 'c', 1,
                    -2.0F, 7, 'b', 0,
                    8, 0,
                    false, false, false, 0.0F,
                    0, 1, 0,
                    0, 0, false,
                    true, false,
                    false, false,
                    new Item.Properties().tab(ItemGroup.TAB_COMBAT)
            )
    );
    public static final RegistryObject<KnifeItem> STONE_KNIFE = ITEMS.register("stone_knife", ()
                    -> new KnifeItem( // called small in book
                    AFCItemTier.STONE,
                    new String[]{"1hDagger"}, 1, false, 0.25F,
                    -2.0F, 6, 'c', 0,
                    -1.0F, 6, 'p', 0,
                    9, 0,
                    true, true, false, 0.0F,
                    0, 0, 0,
                    1, 0, false,
                    false, false,
                    false, true,
                    new Item.Properties().tab(ItemGroup.TAB_COMBAT)
            )
    );
    public static final RegistryObject<KnifeItem> STONE_KNIFE_WAR = ITEMS.register("stone_knife_war", ()
                    -> new KnifeItem( // called large in book
                    AFCItemTier.STONE,
                    new String[]{"1hDagger"}, 1, false, 0.5F,
                    -1.0F, 6, 'c', 0,
                    +0.0F, 6, 'p', 0,
                    9, 0,
                    true, true, false, 0.0F,
                    0, 0, 0,
                    1, 0, false,
                    false, false,
                    false, false,
                    new Item.Properties().tab(ItemGroup.TAB_COMBAT)
            )
    );
    public static final RegistryObject<PickItem> STONE_PICK_WAR = ITEMS.register("stone_pick_war", ()
                    -> new PickItem(
                    AFCItemTier.STONE,
                    new String[]{"1hBlunt"}, 1, false, 0.5F,
                    +2.0F, 7, 'p', 2,
                    -2.0F, 7, 'b', 0,
                    8, 0,
                    false, false, false, 0.0F,
                    0, 0, 0,
                    0, 0, false,
                    true, false,
                    false, false,
                    new Item.Properties().tab(ItemGroup.TAB_COMBAT)
            )
    );

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
