package com.starfish_studios.hamsters.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.rabbit.Rabbit;
import net.minecraft.world.level.Level;

public class Hamster extends Rabbit {
    public Hamster(EntityType<? extends Rabbit> entityType, Level level) {
        super(entityType, level);
    }
}
