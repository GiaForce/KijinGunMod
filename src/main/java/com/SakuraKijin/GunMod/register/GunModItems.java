package com.SakuraKijin.GunMod.register;

import com.SakuraKijin.GunMod.item.*;
import com.SakuraKijin.GunMod.main.GunMod;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(GunMod.MOD_ID)
public class GunModItems {

    public static final NewDiamond DIAMOND = new NewDiamond();

    public static final ItemKiginGun KIGIN_GUN = new ItemKiginGun();
    public static final ItemKijinGunBarrel KIJIN_GUN_BARREL = new ItemKijinGunBarrel();
    public static final ItemKijinGunBody KIJIN_GUN_BODY = new ItemKijinGunBody();
    public static final ItemKijinGunStock KIJIN_GUN_STOCK = new ItemKijinGunStock();
    public static final ItemKijinAmmo KIJIN_AMMO = new ItemKijinAmmo();
    public static final ItemKijinGunPowder KIJIN_POWDER = new ItemKijinGunPowder();
    public static final ItemSakuraIngot SAKURA_INGOT = new ItemSakuraIngot();

    @Mod.EventBusSubscriber(modid = GunMod.MOD_ID,bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class Register{

        @SubscribeEvent
        public static void gunModItems(final RegistryEvent.Register<Item> event){
            final Item[] items = {
                    DIAMOND,
                    KIGIN_GUN,
                    KIJIN_GUN_BARREL,
                    KIJIN_GUN_BODY,
                    KIJIN_GUN_STOCK,
                    KIJIN_AMMO,
                    KIJIN_POWDER,
                    SAKURA_INGOT
            };
            event.getRegistry().registerAll(items);
        }
    }
}
