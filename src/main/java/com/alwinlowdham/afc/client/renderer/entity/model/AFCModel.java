package com.alwinlowdham.afc.client.renderer.entity.model;

import com.alwinlowdham.afc.util.combat.hitlocs.interfaces.IHitLoc;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

import java.util.Map;

public abstract class AFCModel<T extends LivingEntity> extends EntityModel<T> {

    public abstract float getProportion();

    public abstract Map<ModelRenderer, ModelRenderer> getImmediateParentMap();

    public abstract Map<ModelRenderer, ObjectList<ModelRenderer>> getAllParentsMap();

    public abstract Map<ModelRenderer, IHitLoc> getHitLocMap();
}
