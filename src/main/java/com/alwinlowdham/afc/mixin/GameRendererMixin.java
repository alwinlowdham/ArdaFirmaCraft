package com.alwinlowdham.afc.mixin;

import com.alwinlowdham.afc.util.combat.CombatHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

// DONE
// CLEANED
@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Shadow
    private Minecraft minecraft;

    @Overwrite
    public void pick(float p_78473_1_) {
        final Entity cameraEntity = this.minecraft.getCameraEntity();

        if (cameraEntity != null) {
            if (this.minecraft.level != null) {

                this.minecraft.getProfiler().push("pick");
                this.minecraft.crosshairPickEntity = null;

                final double d0 = CombatHelper.getTotalReach(this.minecraft.player);
                this.minecraft.hitResult = cameraEntity.pick(d0, p_78473_1_, false);
                final Vector3d start = cameraEntity.getEyePosition(p_78473_1_);

                boolean flag = true; // temp?
                double d1 = d0;
                d1 = d1 * d1;

                if (this.minecraft.hitResult != null) {
                    d1 = this.minecraft.hitResult.getLocation().distanceToSqr(start);
                }

                final Vector3d viewVec = cameraEntity.getViewVector(1.0F);
                final Vector3d end = start.add(viewVec.x * d0, viewVec.y * d0, viewVec.z * d0);
                final AxisAlignedBB aabb = cameraEntity.getBoundingBox().expandTowards(viewVec.scale(d0)).inflate(1.0D, 1.0D, 1.0D);
                final EntityRayTraceResult entityraytraceresult = ProjectileHelper.getEntityHitResult(cameraEntity, start, end, aabb, (p_215312_0_) -> !p_215312_0_.isSpectator() && p_215312_0_.isPickable(), d1);

                if (entityraytraceresult != null) {
                    final Entity entityResult = entityraytraceresult.getEntity();
                    final Vector3d location = entityraytraceresult.getLocation();
                    final double d2 = start.distanceToSqr(location);

                    if (flag && d2 > (d0 * d0)) {
                        this.minecraft.hitResult = BlockRayTraceResult.miss(location, Direction.getNearest(viewVec.x, viewVec.y, viewVec.z), new BlockPos(location));
                    } else if (d2 < d1 || this.minecraft.hitResult == null) {
                        this.minecraft.hitResult = entityraytraceresult;

                        if (entityResult instanceof LivingEntity || entityResult instanceof ItemFrameEntity) {
                            this.minecraft.crosshairPickEntity = entityResult;
                        }
                    }
                }
                this.minecraft.getProfiler().pop();
            }
        }
    }
}
