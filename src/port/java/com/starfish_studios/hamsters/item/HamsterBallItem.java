package com.starfish_studios.hamsters.item;

import com.starfish_studios.hamsters.entity.HamsterBall;
import com.starfish_studios.hamsters.registry.HamsterEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;

public final class HamsterBallItem extends Item {
    private final DyeColor color;

    public HamsterBallItem(DyeColor color, Properties properties) {
        super(properties.stacksTo(1));
        this.color = color;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (!(context.getLevel() instanceof ServerLevel level)) {
            return InteractionResult.SUCCESS;
        }
        HamsterBall ball = HamsterEntities.HAMSTER_BALL.get().create(level, EntitySpawnReason.SPAWN_ITEM_USE);
        if (ball == null) {
            return InteractionResult.FAIL;
        }
        ball.setColor(color);
        ball.setPos(context.getClickLocation().add(0.0D, 0.45D, 0.0D));
        level.addFreshEntity(ball);
        if (context.getPlayer() == null || !context.getPlayer().getAbilities().instabuild) {
            context.getItemInHand().shrink(1);
        }
        return InteractionResult.SUCCESS;
    }
}
