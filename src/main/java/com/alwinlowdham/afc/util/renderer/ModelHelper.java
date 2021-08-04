package com.alwinlowdham.afc.util.renderer;

import com.alwinlowdham.afc.client.renderer.entity.layers.AFCBipedModelRenderer;
import com.alwinlowdham.afc.client.renderer.entity.layers.AFCModelRenderer;
import com.alwinlowdham.afc.client.renderer.entity.model.AFCBipedModel;
import com.alwinlowdham.afc.client.renderer.entity.model.AFCModel;
import com.alwinlowdham.afc.util.math.OrientedBB;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.List;
import java.util.Map;

// DONE
// CLEANED
public class ModelHelper {

    public static void addPlayerLayer(PlayerRenderer renderer) {
        final List<LayerRenderer<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>>> layers = ObfuscationReflectionHelper.getPrivateValue(LivingRenderer.class, renderer, "field_177097_h");
        if (layers != null) layers.add(new AFCBipedModelRenderer<>(renderer));
        else {
            System.out.println("No layers found.");
            throw new UnsupportedOperationException();
        }
    }

    public static <T extends LivingEntity> Map<OrientedBB, ModelRenderer> getPartMap(T target) {
        final Map<OrientedBB, ModelRenderer> partMap;
        AFCModel<T> targetModel = getTargetModel(target);
        if (targetModel instanceof AFCBipedModel) partMap = BipedPartsHelper.getBipedPartMap(target, (AFCBipedModel<T>) targetModel);
        else throw new UnsupportedOperationException();
        return partMap;
    }

    public static <T extends LivingEntity, M extends EntityModel<T>> AFCModel<T> getTargetModel(T target) {
        final LivingRenderer<T, M> renderer = (LivingRenderer<T, M>) Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(target);
        final List<LayerRenderer<T, M>> layers = ObfuscationReflectionHelper.getPrivateValue(LivingRenderer.class, renderer, "field_177097_h");
        final AFCModelRenderer<T, M> modelLayer = getModelLayer(layers);
        return modelLayer.getModel();
    }

    public static <T extends LivingEntity, M extends EntityModel<T>> AFCModelRenderer<T, M> getModelLayer(List<LayerRenderer<T, M>> layers) {
        if (layers != null) {
            for (LayerRenderer<T, M> layer : layers) {
                if (layer instanceof AFCModelRenderer) return (AFCModelRenderer<T, M>) layer;
            }
        } else {
            System.out.println("No layers found.");
            throw new UnsupportedOperationException();
        }
        System.out.println("No model layer found.");
        throw new UnsupportedOperationException();
    }
}
