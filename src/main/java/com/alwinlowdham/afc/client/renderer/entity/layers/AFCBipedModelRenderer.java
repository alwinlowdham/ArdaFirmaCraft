package com.alwinlowdham.afc.client.renderer.entity.layers;

import com.alwinlowdham.afc.client.renderer.entity.model.AFCBipedModel;
import com.alwinlowdham.afc.client.renderer.entity.model.AFCModel;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;

import javax.annotation.ParametersAreNonnullByDefault;

// DONE
// CLEANED
public class AFCBipedModelRenderer<T extends LivingEntity, M extends EntityModel<T>> extends AFCModelRenderer<T, M> {

    private static final ResourceLocation RESOURCE = new ResourceLocation("afc", "textures/entity/player.png");
    private final AFCBipedModel<T> model;

    public AFCBipedModelRenderer(IEntityRenderer<T, M> p_i50926_1_) {
        super(p_i50926_1_);
        this.model = new AFCBipedModel<>();
    }

    @Override
    @ParametersAreNonnullByDefault
    public void render(MatrixStack stack, IRenderTypeBuffer buffer, int packedLightIn, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (livingEntity instanceof AbstractClientPlayerEntity) {
            stack.pushPose();
            this.getParentModel().copyPropertiesTo(this.model);
            this.scale(stack);
            stack.translate(0.0D, 0.6501F, 0.0D);
            this.model.setupAnim(livingEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
            IVertexBuilder ivertexbuilder = ItemRenderer.getFoilBufferDirect(buffer, this.model.renderType(RESOURCE), false, false);
            this.model.renderToBuffer(stack, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            stack.popPose();
        }
    }

    private void scale(MatrixStack stack) {
        stack.scale(this.model.getProportion(), this.model.getProportion(), this.model.getProportion());
    }

    @Override
    public AFCModel<T> getModel() {
        return this.model;
    }
}