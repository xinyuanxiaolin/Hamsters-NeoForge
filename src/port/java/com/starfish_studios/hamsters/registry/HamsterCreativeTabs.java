package com.starfish_studios.hamsters.registry;

import com.starfish_studios.hamsters.Hamsters;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class HamsterCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Hamsters.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> HAMSTERS = CREATIVE_TABS.register(
            "hamsters",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.hamsters.tab"))
                    .icon(() -> HamsterContent.HAMSTER.get().getDefaultInstance())
                    .displayItems((parameters, output) -> HamsterContent.ITEMS.getEntries()
                            .forEach(item -> output.accept(item.get())))
                    .build());

    private HamsterCreativeTabs() {
    }
}