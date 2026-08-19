package com.starfish_studios.hamsters.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

public final class CagePanelBlock extends Block implements SimpleWaterloggedBlock {
    public static final MapCodec<CagePanelBlock> CODEC = simpleCodec(CagePanelBlock::new);
    public static final EnumProperty<CageType> TYPE = EnumProperty.create("type", CageType.class);
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    public CagePanelBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(TYPE, CageType.NONE).setValue(FACING, Direction.NORTH).setValue(BlockStateProperties.WATERLOGGED, false));
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TYPE, FACING, BlockStateProperties.WATERLOGGED);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(BlockStateProperties.WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).isSource());
        return state.setValue(TYPE, typeFor(context.getLevel(), context.getClickedPos(), state));
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, net.minecraft.util.RandomSource random) {
        if (direction == Direction.UP || direction == Direction.DOWN) {
            return state.setValue(TYPE, typeFor(level, pos, state));
        }
        return super.updateShape(state, level, ticks, pos, direction, neighborPos, neighborState, random);
    }

    private CageType typeFor(LevelReader level, BlockPos pos, BlockState state) {
        boolean above = matches(level.getBlockState(pos.above()), state);
        boolean below = matches(level.getBlockState(pos.below()), state);
        if (above && below) return CageType.MIDDLE;
        if (above) return CageType.BOTTOM;
        if (below) return CageType.TOP;
        return CageType.NONE;
    }

    private boolean matches(BlockState other, BlockState state) {
        return other.is(this) && other.hasProperty(FACING) && other.getValue(FACING) == state.getValue(FACING);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case SOUTH -> box(0, 0, 0, 16, 16, 1);
            case EAST -> box(0, 0, 0, 1, 16, 16);
            case WEST -> box(15, 0, 0, 16, 16, 16);
            default -> box(0, 0, 15, 16, 16, 16);
        };
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case SOUTH -> box(0, 0, 0, 16, 24, 1);
            case EAST -> box(0, 0, 0, 1, 24, 16);
            case WEST -> box(15, 0, 0, 16, 24, 16);
            default -> box(0, 0, 15, 16, 24, 16);
        };
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
