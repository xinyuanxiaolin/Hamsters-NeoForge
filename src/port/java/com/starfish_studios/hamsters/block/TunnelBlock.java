package com.starfish_studios.hamsters.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jspecify.annotations.Nullable;

import java.util.EnumMap;
import java.util.Map;

public final class TunnelBlock extends Block {
    public static final MapCodec<TunnelBlock> CODEC = simpleCodec(TunnelBlock::new);
    public static final BooleanProperty NORTH = BooleanProperty.create("north");
    public static final BooleanProperty EAST = BooleanProperty.create("east");
    public static final BooleanProperty SOUTH = BooleanProperty.create("south");
    public static final BooleanProperty WEST = BooleanProperty.create("west");
    public static final BooleanProperty UP = BooleanProperty.create("up");
    public static final BooleanProperty DOWN = BooleanProperty.create("down");
    private static final Map<Direction, BooleanProperty> CONNECTIONS = new EnumMap<>(Direction.class);

    static {
        CONNECTIONS.put(Direction.NORTH, NORTH);
        CONNECTIONS.put(Direction.EAST, EAST);
        CONNECTIONS.put(Direction.SOUTH, SOUTH);
        CONNECTIONS.put(Direction.WEST, WEST);
        CONNECTIONS.put(Direction.UP, UP);
        CONNECTIONS.put(Direction.DOWN, DOWN);
    }

    public TunnelBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false)
                .setValue(WEST, false).setValue(UP, false).setValue(DOWN, false));
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = defaultBlockState();
        for (Direction direction : Direction.values()) {
            state = state.setValue(CONNECTIONS.get(direction), connects(context.getLevel().getBlockState(context.getClickedPos().relative(direction))));
        }
        return state;
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess ticks, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, net.minecraft.util.RandomSource random) {
        return state.setValue(CONNECTIONS.get(direction), connects(neighborState));
    }

    private boolean connects(BlockState state) {
        return state.getBlock() instanceof TunnelBlock;
    }

}
