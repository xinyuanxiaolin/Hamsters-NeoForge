package com.starfish_studios.hamsters;

import com.starfish_studios.hamsters.registry.HamsterContent;
import com.starfish_studios.hamsters.registry.HamsterEntities;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(Hamsters.MOD_ID)
public final class Hamsters {
    public static final String MOD_ID = "hamsters";

    public Hamsters(IEventBus modEventBus, ModContainer modContainer) {
        HamsterContent.BLOCKS.register(modEventBus);
        HamsterContent.ITEMS.register(modEventBus);
        HamsterEntities.ENTITIES.register(modEventBus);
    }
}
