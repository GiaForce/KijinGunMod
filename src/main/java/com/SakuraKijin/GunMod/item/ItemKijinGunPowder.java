package com.SakuraKijin.GunMod.item;

import com.SakuraKijin.GunMod.main.GunMod;
import net.minecraft.item.Item;

public class ItemKijinGunPowder extends Item {

    public ItemKijinGunPowder() {
        super(new Properties().group(GunMod.GUNMOD_TAB).rarity(GunMod.KIJIN).isImmuneToFire());
        this.setRegistryName("kijin_powder");
    }
}
