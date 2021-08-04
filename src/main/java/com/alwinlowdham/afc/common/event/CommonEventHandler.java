package com.alwinlowdham.afc.common.event;

import com.alwinlowdham.afc.init.AFCNetwork;
import com.alwinlowdham.afc.networking.toclient.CheckHitPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Map;
import java.util.WeakHashMap;

// DONE
// CLEANED
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonEventHandler {

    private static final Map<PlayerEntity, Pair<Entity, RayTraceResult>> hitList = new WeakHashMap<>();

    @SubscribeEvent(priority = EventPriority.LOWEST)
    //so all other can modify their damage first, and we apply after that
    public static void onLivingHurt(LivingHurtEvent event) {
        final LivingEntity livingEntity = event.getEntityLiving();

        if (livingEntity.level.isClientSide || !(livingEntity instanceof PlayerEntity)) return;
        final PlayerEntity player = (PlayerEntity) livingEntity;

        if (event.getSource().isProjectile()) {
            final Pair<Entity, RayTraceResult> rayTraceResult = hitList.remove(player);
            if (rayTraceResult != null) {
                final Entity projectile = rayTraceResult.getLeft();
                AFCNetwork.sendPacketTo(new CheckHitPacket(projectile.getId(), player.getId()), (ServerPlayerEntity) player);
            }
        }

        event.setCanceled(true);
        hitList.remove(player);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onProjectileImpact(ProjectileImpactEvent event) {
        final RayTraceResult result = event.getRayTraceResult();

        if (result.getType() != RayTraceResult.Type.ENTITY)
            return;

        final Entity entity = ((EntityRayTraceResult) result).getEntity();
        if (!entity.level.isClientSide && entity instanceof PlayerEntity) {
            hitList.put((PlayerEntity) entity, Pair.of(event.getEntity(), event.getRayTraceResult()));
        }
    }
}
