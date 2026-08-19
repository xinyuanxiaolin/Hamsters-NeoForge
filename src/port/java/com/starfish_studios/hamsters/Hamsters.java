package com.starfish_studios.hamsters;

import com.starfish_studios.hamsters.registry.HamsterContent;
import com.starfish_studios.hamsters.registry.HamsterEntities;
import com.starfish_studios.hamsters.registry.HamsterCreativeTabs;
import com.starfish_studios.hamsters.registry.HamsterSounds;
import com.starfish_studios.hamsters.registry.HamsterBlockEntities;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.config.ModConfig;
import org.slf4j.Logger;
import com.starfish_studios.hamsters.compat.CreateCompat;

@Mod(Hamsters.MOD_ID)
public final class Hamsters {
    public static final String MOD_ID = "hamsters";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Hamsters(IEventBus modEventBus, ModContainer modContainer) {
        HamsterContent.BLOCKS.register(modEventBus);
        HamsterContent.ITEMS.register(modEventBus);
        HamsterEntities.ENTITIES.register(modEventBus);
        HamsterCreativeTabs.CREATIVE_TABS.register(modEventBus);
        HamsterSounds.SOUNDS.register(modEventBus);
        HamsterBlockEntities.BLOCK_ENTITIES.register(modEventBus);
        modContainer.registerConfig(ModConfig.Type.SERVER, HamsterConfig.SPEC, "hamsters-server.toml");
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            CreateCompat.initialize();
            long registeredItems = BuiltInRegistries.ITEM.keySet().stream()
                    .filter(id -> MOD_ID.equals(id.getNamespace()))
                    .count();
            long registeredBlocks = BuiltInRegistries.BLOCK.keySet().stream()
                    .filter(id -> MOD_ID.equals(id.getNamespace()))
                    .count();
            LOGGER.info("Hamsters 26.2 registry ready: {} items, {} blocks, creative tab hamsters:hamsters", registeredItems, registeredBlocks);
        });
    }
}
