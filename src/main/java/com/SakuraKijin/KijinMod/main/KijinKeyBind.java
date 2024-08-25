package com.SakuraKijin.KijinMod.main;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.event.KeyEvent;

@Mod.EventBusSubscriber(modid = KijinMod.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class KijinKeyBind {

    public static KeyMapping[] kijinKey;

    @SubscribeEvent
    public static void register(final RegisterKeyMappingsEvent event){
        kijinKey = new KeyMapping[1];

        kijinKey[0] = create("mode_change", KeyEvent.VK_V);

        for (int a = 0; a < kijinKey.length; a++){
            event.register(kijinKey[a]);
        }
    }

    private static KeyMapping create(String name, int key){
       return new KeyMapping("key.kijinmod." + name , key , "key.category.kijinmod");
    }
}
