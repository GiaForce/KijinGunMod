package com.SakuraKijin.GunMod.register;

import com.SakuraKijin.GunMod.block.BlockMinecraftBedRock;
import com.SakuraKijin.GunMod.main.GunMod;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(GunMod.MOD_ID)
public class GunModBlocks {

    public static final BlockMinecraftBedRock BED_ROCK = new BlockMinecraftBedRock();

    @Mod.EventBusSubscriber(modid = GunMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Register{

        @SubscribeEvent
        public static void registerBlocks(final RegistryEvent.Register<Block> event){
            final Block[] blocks = {
                    BED_ROCK
            };
            event.getRegistry().registerAll(blocks);
        }

        @SubscribeEvent
        public static void registerBlockItems(final RegistryEvent.Register<Item> event){
            final BlockItem[] blockItems = {
                    new BlockItem(BED_ROCK,new Item.Properties().isImmuneToFire().rarity(GunMod.KIJIN).group(ItemGroup.MATERIALS))
            };
            for (BlockItem items : blockItems){
                Block block = items.getBlock();
                ResourceLocation registryName = block.getRegistryName();
                event.getRegistry().register(items.setRegistryName(registryName));
            }
        }
    }
}
