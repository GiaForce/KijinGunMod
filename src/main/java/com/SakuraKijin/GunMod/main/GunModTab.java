package com.SakuraKijin.GunMod.main;

import com.SakuraKijin.GunMod.register.GunModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class GunModTab extends ItemGroup {

    public GunModTab() {
        super("gunmod");
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(GunModItems.KIGIN_GUN);
    }
}
