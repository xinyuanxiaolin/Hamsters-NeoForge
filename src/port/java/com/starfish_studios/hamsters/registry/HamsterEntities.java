package com.starfish_studios.hamsters.registry;

import com.starfish_studios.hamsters.Hamsters;
import com.starfish_studios.hamsters.entity.Hamster;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class HamsterEntities {
    public static final DeferredRegister.Entities ENTITIES = DeferredRegister.createEntities(Hamsters.MOD_ID);
    public static final DeferredHolder<EntityType<?>, EntityType<Hamster>> HAMSTER = ENTITIES.registerEntityType(
            "hamster", Hamster::new, MobCategory.CREATURE,
            builder -> builder.sized(0.45F, 0.45F).clientTrackingRange(8));

    private HamsterEntities() {
    }
}
