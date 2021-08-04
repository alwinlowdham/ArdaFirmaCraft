package com.alwinlowdham.afc;

import com.alwinlowdham.afc.init.AFCEffects;
import com.alwinlowdham.afc.init.AFCItems;
import com.alwinlowdham.afc.init.AFCNetwork;
import com.alwinlowdham.afc.util.renderer.ModelHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

// Make nights actually realistically dark, varying by moon phase.
// Check out RLCraft!!!

// The thing I need to do right now is graph the reconstructed model in 3d to fix what's currently wrong.
// Worry about reconstructing rotations later. For now just handle position.

// CLEANED
@Mod(Ref.MOD_ID)
public class AFCMod {

    public static final Logger LOGGER = LogManager.getLogger();

    public AFCMod() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onCommonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);

        MinecraftForge.EVENT_BUS.register(this);

        /* register various things here*/
        AFCEffects.init();
        AFCItems.init();
    }

    private void onCommonSetup(final FMLCommonSetupEvent event) {
        // do stuff that needs to be done on commons
        /* register packets and capabilities here */
        AFCNetwork.init();
    }

    private void onClientSetup(final FMLClientSetupEvent event) {
        // do stuff that needs to be done on client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().options);

        Map<String, PlayerRenderer> playerSkinMap = Minecraft.getInstance().getEntityRenderDispatcher().getSkinMap();
        ModelHelper.addPlayerLayer(playerSkinMap.get("default"));
        ModelHelper.addPlayerLayer(playerSkinMap.get("slim"));
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo("examplemod", "helloworld", () -> {
            LOGGER.info("Hello world from AFC");
            return "Hello world";
        });
    }

    private void processIMC(final InterModProcessEvent event) {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream()
                .map(m -> m.getMessageSupplier().get())
                .collect(Collectors.toList()));
    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do stuff that needs to be done on server
    }

    @SubscribeEvent
    public void onSpecialSpawn(LivingSpawnEvent.SpecialSpawn event) {
        final LivingEntity entity = event.getEntityLiving();
        final double bbWidth = entity.getBbWidth();
        final double appendageLength;
        if (entity instanceof ZombieEntity) {
            appendageLength = 1.0D;
            Objects.requireNonNull(entity.getAttribute(ForgeMod.REACH_DISTANCE.get()))
                    .setBaseValue(0.5 * bbWidth + appendageLength);
        }
    }

    @SubscribeEvent
    public void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
    }

    @SubscribeEvent
    public void onEntityAttributeModification(EntityAttributeModificationEvent event) {
    }
}