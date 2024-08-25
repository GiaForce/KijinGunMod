package com.SakuraKijin.GunMod.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class BlockMinecraftBedRock extends Block {

    public BlockMinecraftBedRock() {
        super(Properties.create(Material.ROCK)
                .setRequiresTool()
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(4)
                .hardnessAndResistance(50F, 99999)
                .setLightLevel(value -> 15)
        );
        this.setRegistryName("minecraft","bedrock");
    }
}
