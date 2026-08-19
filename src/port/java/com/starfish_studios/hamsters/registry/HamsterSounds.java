package com.starfish_studios.hamsters.registry;

import com.starfish_studios.hamsters.Hamsters;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class HamsterSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(Registries.SOUND_EVENT, Hamsters.MOD_ID);
    public static final DeferredHolder<SoundEvent, SoundEvent> AMBIENT = register("entity.hamster.ambient");
    public static final DeferredHolder<SoundEvent, SoundEvent> HURT = register("entity.hamster.hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> DEATH = register("entity.hamster.death");
    public static final DeferredHolder<SoundEvent, SoundEvent> SLEEP = register("entity.hamster.sleep");
    public static final DeferredHolder<SoundEvent, SoundEvent> EAT = register("entity.hamster.eat");
    public static final DeferredHolder<SoundEvent, SoundEvent> EXPLODE = register("entity.hamster.explode");
    public static final DeferredHolder<SoundEvent, SoundEvent> SQUISH = register("entity.hamster.squish");
    public static final DeferredHolder<SoundEvent, SoundEvent> UNSQUISH = register("entity.hamster.unsquish");
    public static final DeferredHolder<SoundEvent, SoundEvent> PICK_UP = register("entity.hamster.pick_up");
    public static final DeferredHolder<SoundEvent, SoundEvent> PLACE = register("entity.hamster.place");

    private static DeferredHolder<SoundEvent, SoundEvent> register(String id) {
        return SOUNDS.register(id, () -> SoundEvent.createVariableRangeEvent(Identifier.fromNamespaceAndPath(Hamsters.MOD_ID, id)));
    }

    private HamsterSounds() {
    }
}
