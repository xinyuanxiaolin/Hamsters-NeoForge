package com.starfish_studios.hamsters.client;

import com.geckolib.renderer.GeoEntityRenderer;
import com.starfish_studios.hamsters.entity.Hamster;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import com.geckolib.renderer.base.RenderPassInfo;

public final class HamsterRenderer extends GeoEntityRenderer<Hamster, HamsterRenderState> {
    public HamsterRenderer(EntityRendererProvider.Context context) {
        super(context, new HamsterModel());
        withRenderLayer(new HamsterTextureLayer(this, false));
        withRenderLayer(new HamsterTextureLayer(this, true));
        withScale(0.8F);
        this.shadowRadius = 0.25F;
    }

    @Override
    public HamsterRenderState createRenderState(Hamster hamster, Void relatedObject) {
        return new HamsterRenderState();
    }

    @Override
    public void captureDefaultRenderState(Hamster hamster, Void relatedObject, HamsterRenderState renderState, float partialTick) {
        super.captureDefaultRenderState(hamster, relatedObject, renderState, partialTick);
        renderState.addGeckolibData(HamsterModel.VARIANT, hamster.getVariant());
        renderState.addGeckolibData(HamsterModel.MARKING, hamster.getMarking());
        renderState.addGeckolibData(HamsterModel.TAMED, hamster.isTame());
        renderState.addGeckolibData(HamsterModel.SQUISHED, hamster.getSquishedTicks() > 0);
    }

    @Override
    public void scaleModelForRender(RenderPassInfo<HamsterRenderState> renderPassInfo, float widthScale, float heightScale) {
        if (renderPassInfo.getOrDefaultGeckolibData(HamsterModel.SQUISHED, false)) {
            renderPassInfo.poseStack().scale(1.25F, 0.4F, 1.25F);
        }
        super.scaleModelForRender(renderPassInfo, widthScale, heightScale);
    }
}
