package com.starfish_studios.hamsters.entity;

import com.geckolib.animatable.GeoEntity;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.util.GeckoLibUtil;
import com.starfish_studios.hamsters.item.HamsterItem;
import com.starfish_studios.hamsters.registry.HamsterContent;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

public final class HamsterBall extends Entity implements GeoEntity {
    private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(HamsterBall.class, EntityDataSerializers.INT);
    private final AnimatableInstanceCache animationCache = GeckoLibUtil.createInstanceCache(this);

    public HamsterBall(EntityType<? extends HamsterBall> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(COLOR, DyeColor.WHITE.getId());
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        setColor(DyeColor.byId(input.getIntOr("Color", DyeColor.WHITE.getId())));
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        output.putInt("Color", getColor().getId());
    }

    public DyeColor getColor() {
        return DyeColor.byId(this.entityData.get(COLOR));
    }

    public void setColor(DyeColor color) {
        this.entityData.set(COLOR, color.getId());
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 movement = getDeltaMovement();
        if (!isNoGravity()) {
            movement = movement.add(0.0D, -0.08D, 0.0D);
        }
        if (getFirstPassenger() instanceof Hamster hamster && onGround() && tickCount % 30 < 20) {
            Vec3 direction = Vec3.directionFromRotation(0.0F, hamster.getYRot()).scale(0.035D);
            movement = movement.add(direction.x, 0.0D, direction.z);
        }
        setDeltaMovement(movement);
        move(MoverType.SELF, movement);
        setDeltaMovement(getDeltaMovement().multiply(onGround() ? 0.86D : 0.98D, 0.98D, onGround() ? 0.86D : 0.98D));
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand, Vec3 location) {
        ItemStack stack = player.getItemInHand(hand);
        if (getFirstPassenger() == null && stack.is(HamsterContent.HAMSTER.get())) {
            if (!level().isClientSide() && level() instanceof ServerLevel serverLevel) {
                Hamster hamster = com.starfish_studios.hamsters.registry.HamsterEntities.HAMSTER.get().create(serverLevel, net.minecraft.world.entity.EntitySpawnReason.SPAWN_ITEM_USE);
                if (hamster != null) {
                    HamsterItem.applyData(stack, hamster);
                    hamster.setPos(position());
                    serverLevel.addFreshEntity(hamster);
                    hamster.startRiding(this, true, true);
                    if (!player.getAbilities().instabuild) stack.shrink(1);
                }
            }
            return InteractionResult.SUCCESS;
        }
        if (getFirstPassenger() instanceof Hamster hamster && stack.isEmpty()) {
            if (!level().isClientSide()) hamster.stopRiding();
            return InteractionResult.SUCCESS;
        }
        if (player.isShiftKeyDown() && stack.isEmpty()) {
            if (!level().isClientSide()) breakBall();
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    private void breakBall() {
        ejectPassengers();
        Block.popResource(level(), blockPosition(), new ItemStack(HamsterContent.getBallItem(getColor())));
        discard();
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        breakBall();
        return true;
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return getPassengers().isEmpty() && passenger instanceof Hamster;
    }

    @Override
    public Vec3 getPassengerRidingPosition(Entity passenger) {
        return position().add(0.0D, 0.05D, 0.0D);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animationCache;
    }
}
