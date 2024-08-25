package com.SakuraKijin.GunMod.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class NewDiamond extends Item {

    public NewDiamond() {
        super(new Properties().group(ItemGroup.MATERIALS).isImmuneToFire());
        this.setRegistryName("minecraft","diamond");
    }
}
