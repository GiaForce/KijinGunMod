package com.SakuraKijin.KijinMod.regi;

import com.SakuraKijin.KijinMod.main.KijinMod;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;

public class KijinModTags {

    public static class Blocks{

        public static final TagKey<Block> NEEDS_KIJIN_TOOL = tag("needs_kijin_tool");

        private static final TagKey<Block> tag(String name){
            return BlockTags.create(new ResourceLocation(KijinMod.MOD_ID, name));
        }
    }

    public static class Items{

        private static final TagKey<Item> tag(String name){
            return ItemTags.create(new ResourceLocation(KijinMod.MOD_ID, name));
        }
    }
}
