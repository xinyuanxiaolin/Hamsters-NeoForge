package com.starfish_studios.hamsters.block;

import net.minecraft.util.StringRepresentable;

public enum CageType implements StringRepresentable {
    NONE("none"), TOP("top"), MIDDLE("middle"), BOTTOM("bottom");

    private final String name;

    CageType(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}
