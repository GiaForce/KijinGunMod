package com.SakuraKijin.KijinMod.main;

import com.SakuraKijin.KijinMod.regi.KijinModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

public class KijinModTab extends CreativeModeTab {

    public KijinModTab() {
        super("kijinmod");
    }


    @Override
    public ItemStack makeIcon() {
        return new ItemStack(KijinModItems.KIJIN_PICKAXE.get());
    }
}
