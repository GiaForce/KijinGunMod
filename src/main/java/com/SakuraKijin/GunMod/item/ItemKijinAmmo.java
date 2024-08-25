package com.SakuraKijin.GunMod.item;

import com.SakuraKijin.GunMod.main.GunMod;
import net.minecraft.item.Item;

public class ItemKijinAmmo extends Item {

    public ItemKijinAmmo() {
        super(new Properties().isImmuneToFire().rarity(GunMod.KIJIN).group(GunMod.GUNMOD_TAB));
        this.setRegistryName("kijin_ammo");
    }
}
