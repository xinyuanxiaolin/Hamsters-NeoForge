package com.starfish_studios.hamsters.client;

import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.GeoRenderState;
import com.starfish_studios.hamsters.entity.HamsterBall;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;

public final class HamsterBallRenderer extends GeoEntityRenderer<HamsterBall, EntityRenderState> {
    public HamsterBallRenderer(EntityRendererProvider.Context context) {
        super(context, new HamsterBallModel());
        this.shadowRadius = 0.4F;
    }

    @Override
    public void captureDefaultRenderState(HamsterBall ball, Void relatedObject, EntityRenderState renderState, float partialTick) {
        super.captureDefaultRenderState(ball, relatedObject, renderState, partialTick);
        ((GeoRenderState) renderState).addGeckolibData(HamsterBallModel.COLOR, ball.getColor());
    }
}
