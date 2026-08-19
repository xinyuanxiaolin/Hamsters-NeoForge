package com.starfish_studios.hamsters.client;

import com.geckolib.model.DefaultedBlockGeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import com.starfish_studios.hamsters.block.entity.HamsterWheelBlockEntity;
import net.minecraft.resources.Identifier;

public final class HamsterWheelModel extends DefaultedBlockGeoModel<HamsterWheelBlockEntity> {
    public HamsterWheelModel() {
        super(Identifier.fromNamespaceAndPath("hamsters", "hamster_wheel"));
    }

    @Override
    public Identifier getModelResource(GeoRenderState state) {
        return Identifier.fromNamespaceAndPath("hamsters", "block/hamster_wheel");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState state) {
        return Identifier.fromNamespaceAndPath("hamsters", "textures/block/hamster_wheel.png");
    }

    @Override
    public Identifier getAnimationResource(HamsterWheelBlockEntity animatable) {
        return Identifier.fromNamespaceAndPath("hamsters", "block/hamster_wheel");
    }
}
