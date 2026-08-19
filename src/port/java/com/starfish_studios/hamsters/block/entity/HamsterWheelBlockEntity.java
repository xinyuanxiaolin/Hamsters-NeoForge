package com.starfish_studios.hamsters.block.entity;

import com.geckolib.animatable.GeoBlockEntity;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.RawAnimation;
import com.geckolib.animation.object.PlayState;
import com.geckolib.util.GeckoLibUtil;
import com.starfish_studios.hamsters.entity.SeatEntity;
import com.starfish_studios.hamsters.registry.HamsterBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public final class HamsterWheelBlockEntity extends BlockEntity implements GeoBlockEntity {
    private static final RawAnimation SPIN = RawAnimation.begin().thenLoop("animation.sf_hba.hamster_wheel.spin");
    private final AnimatableInstanceCache animationCache = GeckoLibUtil.createInstanceCache(this);

    public HamsterWheelBlockEntity(BlockPos pos, BlockState state) {
        super(HamsterBlockEntities.HAMSTER_WHEEL.get(), pos, state);
    }

    public boolean isOccupied() {
        return level != null && !level.getEntitiesOfClass(SeatEntity.class, new AABB(worldPosition)).isEmpty();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<HamsterWheelBlockEntity>("wheel", 2,
                test -> isOccupied() ? test.setAndContinue(SPIN) : PlayState.STOP));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return animationCache;
    }
}
