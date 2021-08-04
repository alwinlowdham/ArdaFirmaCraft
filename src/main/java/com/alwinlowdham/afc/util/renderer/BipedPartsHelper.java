package com.alwinlowdham.afc.util.renderer;

import com.alwinlowdham.afc.client.renderer.entity.model.AFCBipedModel;
import com.alwinlowdham.afc.util.math.AFCVector3d;
import com.alwinlowdham.afc.util.math.OrientedBB;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.Map;
import java.util.Random;
import java.util.Set;

// CLEANED
public class BipedPartsHelper {

    public static <T extends LivingEntity> Map<OrientedBB, ModelRenderer> getBipedPartMap(T target, AFCBipedModel<T> targetModel) {
        final Map<OrientedBB, ModelRenderer> bipedPartMap = Maps.newHashMap();

        // NEED to consider scaling PROPORTION of model!!!!!
        // make sure to account for entity's HEIGHT in yPos!!!
        // may need to account for rotation of ENTIRE entity!!!
        // may need to consider differences in height caused by different POSES!
        // See: http://greyminecraftcoder.blogspot.com/2015/07/entity-rotations-and-animation.html

        final Random r = new Random();
        Set<ModelRenderer> parts = targetModel.getAllParentsMap().keySet();
        for (ModelRenderer part : parts) {
            System.out.println("Loc: " + targetModel.getHitLocMap().get(part));

            double xOffset = target.getX();
            double yOffset = target.getY();
            double zOffset = target.getZ();

            final ObjectList<ModelRenderer> parents = targetModel.getAllParentsMap().get(part);
            // System.out.println("Parents: " + parents);

            final ModelRenderer firstParent = parents.get(0);
            // System.out.println("First Parent: " + firstParent);

            for (ModelRenderer parent : parents) {
                xOffset -= parent.x/16;
                yOffset -= parent.y/16;
                zOffset += parent.z/16;
            }

            final ModelRenderer immediateParent = targetModel.getImmediateParentMap().get(part);
            // System.out.println("Immediate Parent: " + immediateParent);

            // System.out.println("Offsets");
            // System.out.println(xOffset);
            // System.out.println(yOffset);
            // System.out.println(zOffset);

            final ModelRenderer.ModelBox cube = part.getRandomCube(r);
            final OrientedBB bb = new OrientedBB(new AxisAlignedBB(
                    xOffset - cube.minX/16, yOffset - cube.minY/16, zOffset + cube.minZ/16,
                    xOffset - cube.maxX/16, yOffset - cube.maxY/16, zOffset + cube.maxZ/16),
                    new AFCVector3d(xOffset, yOffset, zOffset));

            bipedPartMap.put(bb, part);
        }

        // if this doesn't work, try polygons

        return bipedPartMap;
    }
}
