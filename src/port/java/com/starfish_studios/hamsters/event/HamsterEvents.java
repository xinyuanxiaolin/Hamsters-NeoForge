package com.starfish_studios.hamsters.event;

import com.starfish_studios.hamsters.Hamsters;
import com.starfish_studios.hamsters.registry.HamsterEntities;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.rabbit.Rabbit;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

@EventBusSubscriber(modid = Hamsters.MOD_ID)
public final class HamsterEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(HamsterEntities.HAMSTER.get(), Rabbit.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(HamsterEntities.HAMSTER.get(), SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Rabbit::checkRabbitSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }
}
