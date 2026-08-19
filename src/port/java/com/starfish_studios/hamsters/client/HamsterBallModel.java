package com.starfish_studios.hamsters.client;

import com.geckolib.constant.DataTickets;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.model.DefaultedEntityGeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import com.starfish_studios.hamsters.entity.HamsterBall;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.DyeColor;

public final class HamsterBallModel extends DefaultedEntityGeoModel<HamsterBall> {
    public static final DataTicket<DyeColor> COLOR = DataTickets.create("hamster_ball_color", DyeColor.class);

    public HamsterBallModel() {
        super(Identifier.fromNamespaceAndPath("hamsters", "hamster_ball"));
    }

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath("hamsters", "entity/hamster_ball");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        DyeColor color = renderState.getOrDefaultGeckolibData(COLOR, DyeColor.WHITE);
        return Identifier.fromNamespaceAndPath("hamsters", "textures/entity/hamster_ball/" + color.getName() + ".png");
    }

    @Override
    public Identifier getAnimationResource(HamsterBall animatable) {
        return Identifier.fromNamespaceAndPath("hamsters", "entity/hamster_ball");
    }
}
