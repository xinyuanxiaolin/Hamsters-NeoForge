package com.starfish_studios.hamsters.item;

import com.starfish_studios.hamsters.entity.Hamster;
import com.starfish_studios.hamsters.registry.HamsterEntities;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;

public final class HamsterItem extends Item {
    public HamsterItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    public static ItemStack capture(Hamster hamster, Item item) {
        ItemStack stack = new ItemStack(item);
        CustomData.set(DataComponents.CUSTOM_DATA, stack, hamster.savePortableData());
        if (hamster.hasCustomName()) {
            stack.set(DataComponents.CUSTOM_NAME, hamster.getCustomName());
        }
        return stack;
    }

    public static void applyData(ItemStack stack, Hamster hamster) {
        CustomData data = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        hamster.loadPortableData(data.copyTag());
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (!(target instanceof Hamster hamster) || !stack.isEmpty()) {
            return InteractionResult.PASS;
        }
        if (!player.level().isClientSide()) {
            player.setItemInHand(hand, capture(hamster, this));
            hamster.discard();
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (!(context.getLevel() instanceof ServerLevel level)) {
            return InteractionResult.SUCCESS;
        }
        Hamster hamster = HamsterEntities.HAMSTER.get().create(level, EntitySpawnReason.SPAWN_ITEM_USE);
        if (hamster == null) {
            return InteractionResult.FAIL;
        }
        applyData(context.getItemInHand(), hamster);
        hamster.setPos(context.getClickLocation().add(0.0D, 0.1D, 0.0D));
        level.addFreshEntity(hamster);
        if (context.getPlayer() == null || !context.getPlayer().getAbilities().instabuild) {
            context.getItemInHand().shrink(1);
        }
        return InteractionResult.SUCCESS;
    }
}
