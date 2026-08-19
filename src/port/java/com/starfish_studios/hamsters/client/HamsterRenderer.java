package com.starfish_studios.hamsters.client;

import com.geckolib.renderer.GeoEntityRenderer;
import com.starfish_studios.hamsters.entity.Hamster;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public final class HamsterRenderer extends GeoEntityRenderer<Hamster, LivingEntityRenderState> {
    public HamsterRenderer(EntityRendererProvider.Context context) {
        super(context, new HamsterModel());
        withScale(0.8F);
        this.shadowRadius = 0.25F;
    }
}