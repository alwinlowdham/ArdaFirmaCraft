package com.alwinlowdham.afc.client.renderer.entity.layers;

import com.alwinlowdham.afc.client.renderer.entity.model.AFCModel;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;

public abstract class AFCModelRenderer<T extends LivingEntity, M extends EntityModel<T>> extends LayerRenderer<T, M> {

    public AFCModelRenderer(IEntityRenderer<T, M> p_i50926_1_) {
        super(p_i50926_1_);
    }

    public abstract AFCModel<T> getModel();
}
