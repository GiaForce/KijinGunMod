package com.SakuraKijin.GunMod.register;

import com.SakuraKijin.GunMod.keybind.GunKeyBind;
import com.SakuraKijin.GunMod.entity.GunModEntityTypes;
import com.SakuraKijin.GunMod.entity.renderer.KijinAmmoRenderer;
import com.SakuraKijin.GunMod.main.GunMod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = GunMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD ,value = Dist.CLIENT)
public class ClientEventBusSubscriber {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event){
        GunKeyBind.register(event);
        renderRegister();
    }

    private static void renderRegister(){
        RenderingRegistry.registerEntityRenderingHandler(GunModEntityTypes.KIJIN_AMMO.get(), KijinAmmoRenderer::new);
    }
}
