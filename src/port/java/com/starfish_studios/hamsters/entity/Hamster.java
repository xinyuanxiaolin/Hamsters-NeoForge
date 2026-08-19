package com.starfish_studios.hamsters.entity;

import com.geckolib.animatable.GeoEntity;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.RawAnimation;
import com.geckolib.util.GeckoLibUtil;
import com.starfish_studios.hamsters.registry.HamsterEntities;
import com.starfish_studios.hamsters.registry.HamsterContent;
import com.starfish_studios.hamsters.block.HamsterBottleBlock;
import com.starfish_studios.hamsters.block.HamsterBowlBlock;
import com.starfish_studios.hamsters.block.HamsterWheelBlock;
import com.starfish_studios.hamsters.entity.SeatEntity;
import com.starfish_studios.hamsters.HamsterConfig;
import com.starfish_studios.hamsters.registry.HamsterSounds;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

import java.util.Arrays;
import java.util.Comparator;

public class Hamster extends TamableAnimal implements GeoEntity {
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.defineId(Hamster.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> MARKING = SynchedEntityData.defineId(Hamster.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> COLLAR_COLOR = SynchedEntityData.defineId(Hamster.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> CHEEK_LEVEL = SynchedEntityData.defineId(Hamster.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SLEEP_COOLDOWN = SynchedEntityData.defineId(Hamster.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> SLEEPING = SynchedEntityData.defineId(Hamster.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DRINKING_COOLDOWN = SynchedEntityData.defineId(Hamster.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> SQUISHED_TICKS = SynchedEntityData.defineId(Hamster.class, EntityDataSerializers.INT);
    private static final RawAnimation IDLE = RawAnimation.begin().thenLoop("animation.sf_hba.hamster.idle");
    private static final RawAnimation WALK = RawAnimation.begin().thenLoop("animation.sf_hba.hamster.walk");
    private final AnimatableInstanceCache animationCache = GeckoLibUtil.createInstanceCache(this);

    public Hamster(EntityType<? extends Hamster> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.28D)
                .add(Attributes.FOLLOW_RANGE, 16.0D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(VARIANT, Variant.CREAM.id);
        builder.define(MARKING, Marking.BLANK.id);
        builder.define(COLLAR_COLOR, DyeColor.RED.getId());
        builder.define(CHEEK_LEVEL, 0);
        builder.define(SLEEP_COOLDOWN, 200);
        builder.define(SLEEPING, false);
        builder.define(DRINKING_COOLDOWN, 0);
        builder.define(SQUISHED_TICKS, 0);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("Variant", getVariant().id);
        output.putInt("Marking", getMarking().id);
        output.putInt("CollarColor", getCollarColor().getId());
        output.putInt("CheekLevel", getCheekLevel());
        output.putInt("SleepCooldown", getSleepCooldown());
        output.putBoolean("Sleeping", isSleeping());
        output.putInt("DrinkingCooldown", getDrinkingCooldown());
        output.putInt("SquishedTicks", getSquishedTicks());
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        setVariant(Variant.byId(input.getIntOr("Variant", Variant.CREAM.id)));
        setMarking(Marking.byId(input.getIntOr("Marking", Marking.BLANK.id)));
        setCollarColor(DyeColor.byId(input.getIntOr("CollarColor", DyeColor.RED.getId())));
        setCheekLevel(input.getIntOr("CheekLevel", 0));
        setSleepCooldown(input.getIntOr("SleepCooldown", 200));
        setSleeping(input.getBooleanOr("Sleeping", false));
        setDrinkingCooldown(input.getIntOr("DrinkingCooldown", 0));
        setSquishedTicks(input.getIntOr("SquishedTicks", 0));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new PanicGoal(this, 1.4D));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.15D, this::isFood, false));
        this.goalSelector.addGoal(5, new FacilityGoal(this, Facility.BOWL));
        this.goalSelector.addGoal(6, new FacilityGoal(this, Facility.BOTTLE));
        this.goalSelector.addGoal(7, new FacilityGoal(this, Facility.WHEEL));
        this.goalSelector.addGoal(8, new FollowOwnerGoal(this, 1.1D, 6.0F, 2.0F));
        this.goalSelector.addGoal(9, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(11, new RandomLookAroundGoal(this));
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.WHEAT_SEEDS) || stack.is(Items.MELON_SEEDS)
                || stack.is(Items.PUMPKIN_SEEDS) || stack.is(Items.BEETROOT_SEEDS);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.isShiftKeyDown() && stack.isEmpty() && (this.isOwnedBy(player) || !this.isTame())) {
            if (!this.level().isClientSide()) {
                player.setItemInHand(hand, com.starfish_studios.hamsters.item.HamsterItem.capture(this, HamsterContent.HAMSTER.get()));
                discard();
            }
            return InteractionResult.SUCCESS;
        }
        if (this.isTame() && this.isOwnedBy(player) && stack.getItem() instanceof DyeItem) {
            DyeColor color = getDyeColor(stack);
            if (color != getCollarColor()) {
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
                if (!this.level().isClientSide()) {
                    setCollarColor(color);
                }
                return InteractionResult.SUCCESS;
            }
        }
        if (!this.isTame() && isFood(stack)) {
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
            if (!this.level().isClientSide()) {
                if (this.random.nextInt(3) == 0) {
                    this.tame(player);
                    this.setOrderedToSit(true);
                    this.level().broadcastEntityEvent(this, (byte)7);
                } else {
                    this.level().broadcastEntityEvent(this, (byte)6);
                }
            }
            return InteractionResult.SUCCESS;
        }
        if (this.isTame() && this.isOwnedBy(player) && !isFood(stack)) {
            if (!this.level().isClientSide()) {
                this.setOrderedToSit(!this.isOrderedToSit());
            }
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(player, hand);
    }

    private static DyeColor getDyeColor(ItemStack stack) {
        for (DyeColor color : DyeColor.values()) {
            if (stack.is(Items.DYE.pick(color))) {
                return color;
            }
        }
        return DyeColor.RED;
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner) {
        Hamster child = HamsterEntities.HAMSTER.get().create(level, net.minecraft.world.entity.EntitySpawnReason.BREEDING);
        if (child != null && partner instanceof Hamster other) {
            child.setVariant(this.random.nextBoolean() ? getVariant() : other.getVariant());
            child.setMarking(this.random.nextBoolean() ? getMarking() : other.getMarking());
        }
        return child;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            if (getSleepCooldown() > 0) {
                setSleepCooldown(getSleepCooldown() - 1);
            }
            if (getDrinkingCooldown() > 0) {
                setDrinkingCooldown(getDrinkingCooldown() - 1);
            }
            if (getSquishedTicks() > 0) {
                setSquishedTicks(getSquishedTicks() - 1);
                if (getSquishedTicks() == 0) playSound(HamsterSounds.UNSQUISH.get(), 0.7F, 1.0F);
            }
            collectNearbySeed();
            checkForSquish();
            if (isPassenger() && getVehicle() instanceof SeatEntity && getCheekLevel() > 0 && tickCount % 100 == 0) {
                setCheekLevel(getCheekLevel() - 1);
            }
            if (isSleeping() && (this.isInWater() || this.getTarget() != null || this.isPassenger())) {
                setSleeping(false);
            }
            long dayTime = this.level().getOverworldClockTime() % 24000L;
            if (!isSleeping() && isTame() && !isOrderedToSit() && !isPassenger() && getSleepCooldown() == 0
                    && dayTime >= 1000L && dayTime < 11000L && getDeltaMovement().horizontalDistanceSqr() < 0.0004D) {
                setSleeping(true);
                getNavigation().stop();
            }
            if (isSleeping() && (dayTime >= 12000L || dayTime < 1000L)) {
                setSleeping(false);
            }
        }
    }

    public Variant getVariant() {
        return Variant.byId(this.entityData.get(VARIANT));
    }

    public void setVariant(Variant variant) {
        this.entityData.set(VARIANT, variant.id);
    }

    public Marking getMarking() {
        return Marking.byId(this.entityData.get(MARKING));
    }

    public void setMarking(Marking marking) {
        this.entityData.set(MARKING, marking.id);
    }

    public DyeColor getCollarColor() {
        return DyeColor.byId(this.entityData.get(COLLAR_COLOR));
    }

    public void setCollarColor(DyeColor color) {
        this.entityData.set(COLLAR_COLOR, color.getId());
    }

    public int getCheekLevel() {
        return this.entityData.get(CHEEK_LEVEL);
    }

    public void setCheekLevel(int level) {
        this.entityData.set(CHEEK_LEVEL, Math.max(0, Math.min(3, level)));
    }

    public int getSleepCooldown() {
        return this.entityData.get(SLEEP_COOLDOWN);
    }

    public void setSleepCooldown(int ticks) {
        this.entityData.set(SLEEP_COOLDOWN, Math.max(0, ticks));
    }

    public boolean isSleeping() {
        return this.entityData.get(SLEEPING);
    }

    public void setSleeping(boolean sleeping) {
        this.entityData.set(SLEEPING, sleeping);
        if (!sleeping && getSleepCooldown() == 0) {
            setSleepCooldown(200);
        }
    }

    public int getDrinkingCooldown() {
        return entityData.get(DRINKING_COOLDOWN);
    }

    public void setDrinkingCooldown(int ticks) {
        entityData.set(DRINKING_COOLDOWN, Math.max(0, ticks));
    }

    public int getSquishedTicks() {
        return entityData.get(SQUISHED_TICKS);
    }

    public void setSquishedTicks(int ticks) {
        entityData.set(SQUISHED_TICKS, Math.max(0, ticks));
    }

    private void collectNearbySeed() {
        if (getCheekLevel() >= 3 || tickCount % 10 != 0 || isSleeping() || isPassenger()) return;
        ItemEntity itemEntity = level().getEntitiesOfClass(ItemEntity.class, getBoundingBox().inflate(1.25D), item -> HamsterBowlBlock.isSeed(item.getItem())).stream().findFirst().orElse(null);
        if (itemEntity == null) return;
        ItemStack stack = itemEntity.getItem();
        stack.shrink(1);
        if (stack.isEmpty()) itemEntity.discard(); else itemEntity.setItem(stack);
        setCheekLevel(getCheekLevel() + 1);
        playSound(HamsterSounds.EAT.get(), 0.7F, 1.1F);
    }

    private void checkForSquish() {
        if (!HamsterConfig.HAMSTERS_SQUISH.get() || getSquishedTicks() > 0 || tickCount % 2 != 0) return;
        Player player = level().getEntitiesOfClass(Player.class, getBoundingBox().inflate(0.2D, 0.5D, 0.2D), candidate ->
                candidate.getY() > getY() + 0.2D && candidate.getDeltaMovement().y < -0.05D).stream().findFirst().orElse(null);
        if (player == null) return;
        if (getCheekLevel() >= 3 && HamsterConfig.HAMSTERS_BURST.get()) {
            burst();
            return;
        }
        setSquishedTicks(80);
        playSound(HamsterSounds.SQUISH.get(), 0.8F, 1.0F);
        if (HamsterConfig.JUMP_HURTS_HAMSTERS.get() && level() instanceof ServerLevel serverLevel) {
            hurtServer(serverLevel, damageSources().playerAttack(player), 2.0F);
        }
    }

    private void burst() {
        if (!(level() instanceof ServerLevel serverLevel)) return;
        playSound(HamsterSounds.EXPLODE.get(), 1.0F, 1.0F);
        for (int i = 0; i < getCheekLevel(); i++) {
            spawnAtLocation(serverLevel, Items.WHEAT_SEEDS);
        }
        if (HamsterConfig.BURST_STYLE.get() == HamsterConfig.BurstStyle.EXPLOSION) {
            level().explode(this, getX(), getY(), getZ(), 1.0F, Level.ExplosionInteraction.NONE);
        } else {
            serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, getX(), getY() + 0.2D, getZ(), 18, 0.35D, 0.25D, 0.35D, 0.05D);
        }
        discard();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return isSleeping() ? HamsterSounds.SLEEP.get() : HamsterSounds.AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(net.minecraft.world.damagesource.DamageSource source) {
        return HamsterSounds.HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return HamsterSounds.DEATH.get();
    }

    private enum Facility {
        BOWL, BOTTLE, WHEEL
    }

    private final class FacilityGoal extends MoveToBlockGoal {
        private final Facility facility;

        private FacilityGoal(Hamster hamster, Facility facility) {
            super(hamster, 1.05D, 10, 3);
            this.facility = facility;
        }

        @Override
        public boolean canUse() {
            if (!Hamster.this.isTame() || Hamster.this.isOrderedToSit() || Hamster.this.isPassenger() || Hamster.this.isSleeping()) return false;
            if (facility == Facility.BOWL && Hamster.this.getCheekLevel() >= 3) return false;
            if (facility == Facility.BOTTLE && Hamster.this.getDrinkingCooldown() > 0) return false;
            return super.canUse();
        }

        @Override
        protected boolean isValidTarget(LevelReader level, BlockPos pos) {
            BlockState state = level.getBlockState(pos);
            return switch (facility) {
                case BOWL -> state.getBlock() instanceof HamsterBowlBlock && state.getValue(HamsterBowlBlock.SEEDS) > 0;
                case BOTTLE -> state.getBlock() instanceof HamsterBottleBlock && state.getValue(HamsterBottleBlock.WATER) > 0;
                case WHEEL -> state.getBlock() instanceof HamsterWheelBlock;
            };
        }

        @Override
        public void tick() {
            super.tick();
            if (!isReachedTarget() || Hamster.this.level().isClientSide()) return;
            switch (facility) {
                case BOWL -> {
                    if (HamsterBowlBlock.consume(Hamster.this.level(), blockPos)) {
                        Hamster.this.setCheekLevel(Hamster.this.getCheekLevel() + 1);
                        Hamster.this.setSleepCooldown(200);
                    }
                }
                case BOTTLE -> {
                    if (HamsterBottleBlock.drink(Hamster.this.level(), blockPos)) {
                        Hamster.this.setDrinkingCooldown(1200);
                        Hamster.this.setSleepCooldown(200);
                    }
                }
                case WHEEL -> {
                    if (Hamster.this.level() instanceof ServerLevel serverLevel) {
                        SeatEntity seat = HamsterEntities.SEAT.get().create(serverLevel, net.minecraft.world.entity.EntitySpawnReason.TRIGGERED);
                        if (seat != null) {
                            seat.moveToWheel(blockPos);
                            serverLevel.addFreshEntity(seat);
                            Hamster.this.startRiding(seat, true, true);
                        }
                    }
                }
            }
            stop();
        }
    }

    public CompoundTag savePortableData() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("Variant", getVariant().id);
        tag.putInt("Marking", getMarking().id);
        tag.putInt("CollarColor", getCollarColor().getId());
        tag.putInt("CheekLevel", getCheekLevel());
        tag.putBoolean("Baby", isBaby());
        if (hasCustomName()) {
            tag.putString("CustomName", getCustomName().getString());
        }
        return tag;
    }

    public void loadPortableData(CompoundTag tag) {
        setVariant(Variant.byId(tag.getIntOr("Variant", Variant.CREAM.id)));
        setMarking(Marking.byId(tag.getIntOr("Marking", Marking.BLANK.id)));
        setCollarColor(DyeColor.byId(tag.getIntOr("CollarColor", DyeColor.RED.getId())));
        setCheekLevel(tag.getIntOr("CheekLevel", 0));
        if (tag.getBooleanOr("Baby", false)) {
            setBaby(true);
        }
        tag.getString("CustomName").ifPresent(name -> setCustomName(net.minecraft.network.chat.Component.literal(name)));
    }

    public enum Variant {
        BLACK(0, "black"), CHAMPAGNE(1, "champagne"), CHOCOLATE(2, "chocolate"),
        CREAM(3, "cream"), DOVE(4, "dove"), SILVER_DOVE(5, "silver_dove"), WHITE(6, "white");

        private static final Variant[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(value -> value.id)).toArray(Variant[]::new);
        private final int id;
        private final String name;

        Variant(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static Variant byId(int id) {
            return id >= 0 && id < BY_ID.length ? BY_ID[id] : CREAM;
        }
    }

    public enum Marking {
        BLANK(0, "blank"), BANDED(1, "banded"), ROAN(2, "roan"), SPOTTED(3, "spotted"), WHITEBELLY(4, "whitebelly");

        private static final Marking[] BY_ID = Arrays.stream(values()).sorted(Comparator.comparingInt(value -> value.id)).toArray(Marking[]::new);
        private final int id;
        private final String name;

        Marking(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public static Marking byId(int id) {
            return id >= 0 && id < BY_ID.length ? BY_ID[id] : BLANK;
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<Hamster>("movement", 4,
                test -> test.setAndContinue(test.isMoving() ? WALK : IDLE)));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.animationCache;
    }
}
