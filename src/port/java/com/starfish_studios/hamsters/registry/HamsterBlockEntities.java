package com.starfish_studios.hamsters.registry;

import com.starfish_studios.hamsters.Hamsters;
import com.starfish_studios.hamsters.block.entity.HamsterWheelBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class HamsterBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Hamsters.MOD_ID);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<HamsterWheelBlockEntity>> HAMSTER_WHEEL = BLOCK_ENTITIES.register(
            "hamster_wheel", () -> new BlockEntityType<>(HamsterWheelBlockEntity::new, HamsterContent.REGISTERED_BLOCKS.get("hamster_wheel").get()));

    private HamsterBlockEntities() {
    }
}
