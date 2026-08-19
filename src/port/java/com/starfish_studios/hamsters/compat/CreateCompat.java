package com.starfish_studios.hamsters.compat;

import com.mojang.logging.LogUtils;
import net.neoforged.fml.ModList;
import org.slf4j.Logger;

public final class CreateCompat {
    private static final Logger LOGGER = LogUtils.getLogger();

    public static void initialize() {
        if (ModList.get().isLoaded("create")) {
            LOGGER.info("Create detected: hamster wheel automation bridge enabled through redstone/comparator output");
        }
    }

    private CreateCompat() {
    }
}
