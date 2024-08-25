package com.SakuraKijin.KijinMod.regi;

import com.SakuraKijin.KijinMod.entity.KijinModEntityTypes;
import com.SakuraKijin.KijinMod.entity.renderer.KijinAmmoRenderer;
import com.SakuraKijin.KijinMod.main.KijinMod;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.RenderTypeGroup;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = KijinMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event){
        modEntityRenderer();
        blockRenderType();
    }

    private static void modEntityRenderer(){
        EntityRenderers.register(KijinModEntityTypes.KIJIN_AMMO.get(), KijinAmmoRenderer::new);
    }

    private static void blockRenderType(){
        ItemBlockRenderTypes.setRenderLayer(KijinModBlocks.Blocks.KIJIN_BLOCK.get(), RenderType.translucent());
    }
}
