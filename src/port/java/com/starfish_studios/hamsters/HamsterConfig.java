package com.starfish_studios.hamsters;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class HamsterConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue HAMSTERS_SQUISH = BUILDER
            .comment("玩家从上方落到仓鼠时是否触发压扁效果")
            .define("hamstersSquish", true);
    public static final ModConfigSpec.BooleanValue JUMP_HURTS_HAMSTERS = BUILDER
            .comment("压扁仓鼠时是否造成伤害")
            .define("jumpHurtsHamsters", false);
    public static final ModConfigSpec.BooleanValue HAMSTERS_BURST = BUILDER
            .comment("脸颊装满后再次受到挤压是否爆裂并掉落种子")
            .define("hamstersBurst", true);
    public static final ModConfigSpec.EnumValue<BurstStyle> BURST_STYLE = BUILDER
            .comment("爆裂表现：CONFETTI 仅粒子，EXPLOSION 产生无方块破坏爆炸")
            .defineEnum("hamsterBurstStyle", BurstStyle.CONFETTI);
    public static final ModConfigSpec.IntValue NATURAL_SPAWN_WEIGHT = BUILDER
            .comment("仓鼠自然生成权重")
            .defineInRange("naturalSpawnWeight", 8, 0, 100);

    public static final ModConfigSpec SPEC = BUILDER.build();

    public enum BurstStyle {
        CONFETTI,
        EXPLOSION
    }

    private HamsterConfig() {
    }
}
