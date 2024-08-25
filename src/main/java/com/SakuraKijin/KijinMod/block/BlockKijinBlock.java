package com.SakuraKijin.KijinMod.block;

import com.SakuraKijin.KijinMod.main.KijinMod;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;

public class BlockKijinBlock extends Block {

    public BlockKijinBlock() {
        super(Properties.of(Material.METAL)
                .requiresCorrectToolForDrops()
                .sound(SoundType.METAL)
                .strength(3F,1500F)
                .noOcclusion()
        );
    }
    public Item.Properties properties(){
        return new Item.Properties().tab(KijinMod.MOD_TAB).fireResistant();
    }
}
