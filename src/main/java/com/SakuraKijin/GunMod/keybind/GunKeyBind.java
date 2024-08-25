package com.SakuraKijin.GunMod.keybind;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.awt.event.KeyEvent;

@OnlyIn(Dist.CLIENT)
public class GunKeyBind {

    public static KeyBinding[] gunKey;

    public static void register(final FMLClientSetupEvent event){
        gunKey = new KeyBinding[1];
        gunKey[0] = create("scope" , KeyEvent.VK_Y);

        for (int a = 0; a < gunKey.length; a++){
            ClientRegistry.registerKeyBinding(gunKey[a]);
        }
    }

    private static KeyBinding create(String name, int key){
        return new KeyBinding("key.gunmod." + name , key , "key.category.gunmod");

    }

}
