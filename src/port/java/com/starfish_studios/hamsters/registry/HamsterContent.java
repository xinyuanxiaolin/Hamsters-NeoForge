package com.starfish_studios.hamsters.registry;

import com.starfish_studios.hamsters.Hamsters;
import com.starfish_studios.hamsters.item.HamsterBallItem;
import com.starfish_studios.hamsters.item.HamsterItem;
import com.starfish_studios.hamsters.item.SeedMixItem;
import com.starfish_studios.hamsters.item.ChocolateHamsterItem;
import com.starfish_studios.hamsters.block.CagePanelBlock;
import com.starfish_studios.hamsters.block.HamsterBottleBlock;
import com.starfish_studios.hamsters.block.HamsterBowlBlock;
import com.starfish_studios.hamsters.block.HamsterWheelBlock;
import com.starfish_studios.hamsters.block.TunnelBlock;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class HamsterContent {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Hamsters.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Hamsters.MOD_ID);
    public static final Map<String, DeferredBlock<? extends Block>> REGISTERED_BLOCKS = new LinkedHashMap<>();
    public static final Map<DyeColor, DeferredItem<Item>> HAMSTER_BALLS = new LinkedHashMap<>();
    public static final DeferredItem<Item> HAMSTER = ITEMS.registerItem(
            "hamster", properties -> new HamsterItem(properties.stacksTo(1)));
    public static final DeferredItem<Item> HAMSTER_BALL = ITEMS.registerItem(
            "hamster_ball", properties -> new HamsterBallItem(DyeColor.WHITE, properties));
    public static final DeferredItem<SpawnEggItem> HAMSTER_SPAWN_EGG = ITEMS.registerItem(
            "hamster_spawn_egg", properties -> new SpawnEggItem(properties.spawnEgg(HamsterEntities.HAMSTER.get())));

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
            "chocolate_hamster",
            "seed_mix"
    );

    static {
        for (String id : BLOCK_IDS) {
            DeferredBlock<? extends Block> block;
            if (id.endsWith("cage_panel") || id.equals("cage_panel")) {
                block = BLOCKS.registerBlock(id, CagePanelBlock::new, properties -> properties.strength(0.4F).sound(SoundType.METAL).noOcclusion());
            } else if (id.endsWith("hamster_bottle")) {
                block = BLOCKS.registerBlock(id, HamsterBottleBlock::new, properties -> properties.strength(0.6F).sound(SoundType.GLASS).noOcclusion());
            } else if (id.endsWith("hamster_bowl")) {
                block = BLOCKS.registerBlock(id, HamsterBowlBlock::new, properties -> properties.strength(0.6F).sound(SoundType.STONE).noOcclusion());
            } else if (id.equals("hamster_wheel")) {
                block = BLOCKS.registerBlock(id, HamsterWheelBlock::new, properties -> properties.strength(1.0F).sound(SoundType.WOOD).noOcclusion());
            } else {
                block = BLOCKS.registerBlock(id, TunnelBlock::new, properties -> properties.strength(0.5F).sound(SoundType.GLASS).noOcclusion());
            }
            REGISTERED_BLOCKS.put(id, block);
            ITEMS.registerSimpleBlockItem(block);
        }
        ITEMS.registerItem("chocolate_hamster", ChocolateHamsterItem::new);
        ITEMS.registerItem("seed_mix", SeedMixItem::new);
        for (DyeColor color : DyeColor.values()) {
            String id = color.getName() + "_hamster_ball";
            HAMSTER_BALLS.put(color, ITEMS.registerItem(id, properties -> new HamsterBallItem(color, properties)));
        }
    }

    private HamsterContent() {
    }

    public static Item getBallItem(DyeColor color) {
        return HAMSTER_BALLS.getOrDefault(color, HAMSTER_BALLS.get(DyeColor.WHITE)).get();
    }
}
