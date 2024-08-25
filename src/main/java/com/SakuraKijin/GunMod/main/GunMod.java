package com.SakuraKijin.GunMod.main;

import com.SakuraKijin.GunMod.entity.GunModEntityTypes;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(GunMod.MOD_ID)
public class GunMod {

    public static final String MOD_ID = "gunmod";

    public static final ItemGroup GUNMOD_TAB = new GunModTab();

    public static final Rarity KIJIN = Rarity.create("kijin", TextFormatting.RED);

    public GunMod(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        GunModEntityTypes.ENTITY_REGISTER.register(bus);
    }
}
