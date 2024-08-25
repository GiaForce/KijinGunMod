package com.SakuraKijin.KijinMod.regi;

import com.SakuraKijin.KijinMod.block.BlockKijinBlock;
import com.SakuraKijin.KijinMod.main.KijinMod;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class KijinModBlocks {
    public static class Blocks {
        public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, KijinMod.MOD_ID);

        public static final RegistryObject<BlockKijinBlock> KIJIN_BLOCK = blockRegister("kijin_block", BlockKijinBlock::new);

        private static <T extends Block> RegistryObject<T> blockRegister(String name, Supplier<T> supplier) {
            return BLOCKS.register(name, supplier);
        }
    }

    public static class BlockItems {

        public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, KijinMod.MOD_ID);

        public static final RegistryObject<BlockItem> KIJIN_BLOCK = BLOCK_ITEMS.register("kijin_block", ()->new BlockItem(Blocks.KIJIN_BLOCK.get(), new Item.Properties().tab(KijinMod.MOD_TAB).fireResistant()));

    }
}
