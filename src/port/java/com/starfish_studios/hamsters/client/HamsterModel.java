package com.starfish_studios.hamsters.client;

import com.geckolib.model.DefaultedEntityGeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import com.starfish_studios.hamsters.entity.Hamster;
import net.minecraft.resources.Identifier;

public final class HamsterModel extends DefaultedEntityGeoModel<Hamster> {
    private static final Identifier MODEL = Identifier.fromNamespaceAndPath("hamsters", "entity/hamster");
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("hamsters", "textures/entity/hamster/cream.png");
    private static final Identifier ANIMATION = Identifier.fromNamespaceAndPath("hamsters", "entity/hamster");

    public HamsterModel() {
        super(Identifier.fromNamespaceAndPath("hamsters", "hamster"));
    }

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return MODEL;
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return TEXTURE;
    }

    @Override
    public Identifier getAnimationResource(Hamster animatable) {
        return ANIMATION;
    }
}