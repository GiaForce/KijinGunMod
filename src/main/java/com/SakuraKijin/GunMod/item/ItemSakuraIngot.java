package com.SakuraKijin.GunMod.item;

import com.SakuraKijin.GunMod.main.GunMod;
import net.minecraft.item.Item;

public class ItemSakuraIngot extends Item {
    public ItemSakuraIngot() {
        super(new Properties().isImmuneToFire().group(GunMod.GUNMOD_TAB));
        this.setRegistryName("sakura_ingod");
    }
}
