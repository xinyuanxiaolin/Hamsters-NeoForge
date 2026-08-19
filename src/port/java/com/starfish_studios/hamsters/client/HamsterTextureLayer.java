package com.starfish_studios.hamsters.client;

import com.geckolib.renderer.base.GeoRenderer;
import com.geckolib.renderer.layer.builtin.TextureLayerGeoLayer;
import com.starfish_studios.hamsters.entity.Hamster;
import net.minecraft.resources.Identifier;

public final class HamsterTextureLayer extends TextureLayerGeoLayer<Hamster, Void, HamsterRenderState> {
    private final boolean collar;

    public HamsterTextureLayer(GeoRenderer<Hamster, Void, HamsterRenderState> renderer, boolean collar) {
        super(renderer, Identifier.fromNamespaceAndPath("hamsters", "textures/entity/hamster/blank.png"));
        this.collar = collar;
    }

    @Override
    protected Identifier getTextureResource(HamsterRenderState renderState) {
        if (collar) {
            boolean tamed = renderState.getOrDefaultGeckolibData(HamsterModel.TAMED, false);
            return Identifier.fromNamespaceAndPath("hamsters", "textures/entity/hamster/" + (tamed ? "collar" : "blank") + ".png");
        }
        Hamster.Marking marking = renderState.getOrDefaultGeckolibData(HamsterModel.MARKING, Hamster.Marking.BLANK);
        return Identifier.fromNamespaceAndPath("hamsters", "textures/entity/hamster/" + marking.getName() + ".png");
    }
}