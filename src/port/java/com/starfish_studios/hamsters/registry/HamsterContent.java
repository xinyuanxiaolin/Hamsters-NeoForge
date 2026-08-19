package com.starfish_studios.hamsters.registry;

import com.starfish_studios.hamsters.Hamsters;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class HamsterContent {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Hamsters.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Hamsters.MOD_ID);
    public static final Map<String, DeferredBlock<Block>> REGISTERED_BLOCKS = new LinkedHashMap<>();

    private static final List<String> BLOCK_IDS = List.of(
            "black_cage_panel",
            "black_hamster_bottle",
            "black_hamster_bowl",
            "blue_cage_panel",
            "blue_hamster_bottle",
            "blue_hamster_bowl",
            "brown_cage_panel",
            "brown_hamster_bottle",
            "brown_hamster_bowl",
            "cage_panel",
            "cyan_cage_panel",
            "cyan_hamster_bottle",
            "cyan_hamster_bowl",
            "gray_cage_panel",
            "gray_hamster_bottle",
            "gray_hamster_bowl",
            "green_cage_panel",
            "green_hamster_bottle",
            "green_hamster_bowl",
            "hamster_wheel",
            "light_blue_cage_panel",
            "light_blue_hamster_bottle",
            "light_blue_hamster_bowl",
            "light_gray_cage_panel",
            "light_gray_hamster_bottle",
            "light_gray_hamster_bowl",
            "lime_cage_panel",
            "lime_hamster_bottle",
            "lime_hamster_bowl",
            "magenta_cage_panel",
            "magenta_hamster_bottle",
            "magenta_hamster_bowl",
            "orange_cage_panel",
            "orange_hamster_bottle",
            "orange_hamster_bowl",
            "pink_cage_panel",
            "pink_hamster_bottle",
            "pink_hamster_bowl",
            "purple_cage_panel",
            "purple_hamster_bottle",
            "purple_hamster_bowl",
            "red_cage_panel",
            "red_hamster_bottle",
            "red_hamster_bowl",
            "tunnel",
            "tunnel2",
            "white_cage_panel",
            "white_hamster_bottle",
            "white_hamster_bowl",
            "yellow_cage_panel",
            "yellow_hamster_bottle",
            "yellow_hamster_bowl"
    );

    private static final List<String> ITEM_IDS = List.of(
            "black_hamster_ball",
            "blue_hamster_ball",
            "brown_hamster_ball",
            "chocolate_hamster",
            "cyan_hamster_ball",
            "gray_hamster_ball",
            "green_hamster_ball",
            "hamster",
            "hamster_ball",
            "light_blue_hamster_ball",
            "light_gray_hamster_ball",
            "lime_hamster_ball",
            "magenta_hamster_ball",
            "orange_hamster_ball",
            "pink_hamster_ball",
            "purple_hamster_ball",
            "red_hamster_ball",
            "seed_mix",
            "white_hamster_ball",
            "yellow_hamster_ball"
    );

    static {
        for (String id : BLOCK_IDS) {
            DeferredBlock<Block> block = BLOCKS.registerSimpleBlock(id, properties -> properties
                    .strength(id.contains("cage_panel") ? 0.4F : 1.0F)
                    .sound(id.contains("cage_panel") ? SoundType.METAL : SoundType.WOOD)
                    .noOcclusion());
            REGISTERED_BLOCKS.put(id, block);
            ITEMS.registerSimpleBlockItem(block);
        }
        for (String id : ITEM_IDS) {
            ITEMS.registerSimpleItem(id, properties -> properties.stacksTo(id.equals("hamster") || id.equals("chocolate_hamster") ? 1 : 64));
        }
    }

    private HamsterContent() {
    }
}
