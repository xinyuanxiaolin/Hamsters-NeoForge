package com.starfish_studios.hamsters.event;

import com.starfish_studios.hamsters.Hamsters;
import com.starfish_studios.hamsters.registry.HamsterEntities;
import com.starfish_studios.hamsters.client.HamsterRenderer;
import com.starfish_studios.hamsters.client.HamsterBallRenderer;
import com.starfish_studios.hamsters.client.HamsterWheelRenderer;
import com.starfish_studios.hamsters.registry.HamsterBlockEntities;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.minecraft.client.renderer.entity.NoopRenderer;

@EventBusSubscriber(modid = Hamsters.MOD_ID, value = Dist.CLIENT)
public final class HamsterClientEvents {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(HamsterEntities.HAMSTER.get(), HamsterRenderer::new);
        event.registerEntityRenderer(HamsterEntities.HAMSTER_BALL.get(), HamsterBallRenderer::new);
        event.registerEntityRenderer(HamsterEntities.SEAT.get(), NoopRenderer::new);
        event.registerBlockEntityRenderer(HamsterBlockEntities.HAMSTER_WHEEL.get(), HamsterWheelRenderer::new);
    }
}
