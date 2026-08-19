package com.starfish_studios.hamsters.client;

import com.geckolib.model.DefaultedEntityGeoModel;
import com.geckolib.constant.DataTickets;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.renderer.base.GeoRenderState;
import com.starfish_studios.hamsters.entity.Hamster;
import net.minecraft.resources.Identifier;

public final class HamsterModel extends DefaultedEntityGeoModel<Hamster> {
    private static final Identifier MODEL = Identifier.fromNamespaceAndPath("hamsters", "entity/hamster");
    public static final DataTicket<Hamster.Variant> VARIANT = DataTickets.create("hamster_variant", Hamster.Variant.class);
    public static final DataTicket<Hamster.Marking> MARKING = DataTickets.create("hamster_marking", Hamster.Marking.class);
    public static final DataTicket<Boolean> TAMED = DataTickets.create("hamster_tamed", Boolean.class);
    public static final DataTicket<Boolean> SQUISHED = DataTickets.create("hamster_squished", Boolean.class);
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
        Hamster.Variant variant = renderState.getOrDefaultGeckolibData(VARIANT, Hamster.Variant.CREAM);
        return Identifier.fromNamespaceAndPath("hamsters", "textures/entity/hamster/" + variant.getName() + ".png");
    }

    @Override
    public Identifier getAnimationResource(Hamster animatable) {
        return ANIMATION;
    }
}
