package com.starfish_studios.hamsters.event;

import com.starfish_studios.hamsters.Hamsters;
import com.starfish_studios.hamsters.registry.HamsterEntities;
import net.minecraft.client.renderer.entity.RabbitRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = Hamsters.MOD_ID, value = Dist.CLIENT)
public final class HamsterClientEvents {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(HamsterEntities.HAMSTER.get(), RabbitRenderer::new);
    }
}
