package com.alwinlowdham.afc.mixin;

import com.alwinlowdham.afc.interfaces.IReach;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.item.Item;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Shadow private Minecraft minecraft;

    // DONE?
    @Overwrite
    public void pick(float p_78473_1_) {
        Entity entity = this.minecraft.getCameraEntity();
        if (entity != null) {
            if (this.minecraft.level != null) {
                this.minecraft.getProfiler().push("pick");
                this.minecraft.crosshairPickEntity = null;
                float reach = 0.0F;
                Item item = this.minecraft.player.getMainHandItem().getItem();
                if (item.getItem() instanceof IReach) {
                    reach = ((IReach) item).getReach();
                }
                double totalReach = reach + this.minecraft.player.getAttribute(net.minecraftforge.common.ForgeMod.REACH_DISTANCE.get()).getBaseValue();
                double d0 = totalReach;
                this.minecraft.hitResult = entity.pick(d0, p_78473_1_, false);
                Vector3d vector3d = entity.getEyePosition(p_78473_1_);
                boolean flag = false;
                double d1 = d0;
                if (this.minecraft.gameMode.hasFarPickRange()) {
                    d1 = 6.0D;
                    d0 = d1;
                } else {
                    if (d0 > reach) {
                        flag = true;
                    }

                    d0 = d0;
                }

                d1 = d1 * d1;
                if (this.minecraft.hitResult != null) {
                    d1 = this.minecraft.hitResult.getLocation().distanceToSqr(vector3d);
                }

                Vector3d vector3d1 = entity.getViewVector(1.0F);
                Vector3d vector3d2 = vector3d.add(vector3d1.x * d0, vector3d1.y * d0, vector3d1.z * d0);
                float f = 1.0F;
                AxisAlignedBB axisalignedbb = entity.getBoundingBox().expandTowards(vector3d1.scale(d0)).inflate(1.0D, 1.0D, 1.0D);
                EntityRayTraceResult entityraytraceresult = ProjectileHelper.getEntityHitResult(entity, vector3d, vector3d2, axisalignedbb, (p_215312_0_) -> {
                    return !p_215312_0_.isSpectator() && p_215312_0_.isPickable();
                }, d1);
                if (entityraytraceresult != null) {

                    /*Begin experimental*/

                    /*End experimental*/

                    Entity entity1 = entityraytraceresult.getEntity();
                    Vector3d vector3d3 = entityraytraceresult.getLocation();
                    double d2 = vector3d.distanceToSqr(vector3d3);
                    if (flag && d2 > (totalReach * totalReach)) { // try d0 * d0 ??? see the original algorithm!
                        this.minecraft.hitResult = BlockRayTraceResult.miss(vector3d3, Direction.getNearest(vector3d1.x, vector3d1.y, vector3d1.z), new BlockPos(vector3d3));
                    } else if (d2 < d1 || this.minecraft.hitResult == null) {
                        this.minecraft.hitResult = entityraytraceresult;
                        if (entity1 instanceof LivingEntity || entity1 instanceof ItemFrameEntity) {
                            this.minecraft.crosshairPickEntity = entity1;
                        }
                    }
                }
                this.minecraft.getProfiler().pop();
            }
        }
    }
}
