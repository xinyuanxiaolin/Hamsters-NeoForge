package com.starfish_studios.hamsters.client;

import com.geckolib.renderer.GeoBlockRenderer;
import com.starfish_studios.hamsters.block.entity.HamsterWheelBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public final class HamsterWheelRenderer extends GeoBlockRenderer<HamsterWheelBlockEntity, HamsterWheelRenderState> {
    public HamsterWheelRenderer(BlockEntityRendererProvider.Context context) {
        super(context, new HamsterWheelModel());
    }
}
