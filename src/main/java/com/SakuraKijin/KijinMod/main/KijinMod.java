package com.SakuraKijin.KijinMod.main;

import com.SakuraKijin.KijinMod.entity.KijinModEntityTypes;
import com.SakuraKijin.KijinMod.regi.KijinModBlocks;
import com.SakuraKijin.KijinMod.regi.KijinModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("kijinmod")
public class KijinMod {

    public static final String MOD_ID = "kijinmod";

    public static CreativeModeTab MOD_TAB = new KijinModTab();

    public KijinMod(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        KijinModItems.ITEMS.register(bus);
        KijinModBlocks.Blocks.BLOCKS.register(bus);
        KijinModBlocks.BlockItems.BLOCK_ITEMS.register(bus);
        KijinModEntityTypes.ENTITIES.register(bus);
    }
}
