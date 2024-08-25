package com.SakuraKijin.GunMod.item;

import com.SakuraKijin.GunMod.main.GunMod;
import net.minecraft.item.Item;

public class ItemKijinGunBarrel extends Item {

    public ItemKijinGunBarrel() {
        super(new Properties().rarity(GunMod.KIJIN).group(GunMod.GUNMOD_TAB).isImmuneToFire());
        this.setRegistryName("kijin_gun_barrel");
    }
}
