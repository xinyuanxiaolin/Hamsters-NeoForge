package com.starfish_studios.hamsters.registry;

import com.starfish_studios.hamsters.Hamsters;
import com.starfish_studios.hamsters.entity.Hamster;
import com.starfish_studios.hamsters.entity.HamsterBall;
import com.starfish_studios.hamsters.entity.SeatEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class HamsterEntities {
    public static final DeferredRegister.Entities ENTITIES = DeferredRegister.createEntities(Hamsters.MOD_ID);
    public static final DeferredHolder<EntityType<?>, EntityType<Hamster>> HAMSTER = ENTITIES.registerEntityType(
            "hamster", Hamster::new, MobCategory.CREATURE,
            builder -> builder.sized(0.45F, 0.45F).clientTrackingRange(8));
    public static final DeferredHolder<EntityType<?>, EntityType<HamsterBall>> HAMSTER_BALL = ENTITIES.registerEntityType(
            "hamster_ball", HamsterBall::new, MobCategory.MISC,
            builder -> builder.sized(0.9F, 0.9F).clientTrackingRange(10).updateInterval(1));
    public static final DeferredHolder<EntityType<?>, EntityType<SeatEntity>> SEAT = ENTITIES.registerEntityType(
            "seat", SeatEntity::new, MobCategory.MISC,
            builder -> builder.sized(0.1F, 0.1F).clientTrackingRange(6).updateInterval(1).noSave().noSummon());

    private HamsterEntities() {
    }
}
