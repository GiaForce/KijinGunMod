package com.SakuraKijin.KijinMod.item.tool;

import com.SakuraKijin.KijinMod.main.KijinMod;
import com.SakuraKijin.KijinMod.regi.KijinModItems;
import com.SakuraKijin.KijinMod.regi.KijinModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class KijinModTiers {

    public static final ForgeTier KIJIN = new ForgeTier(5,6000,15F,6F,100, KijinModTags.Blocks.NEEDS_KIJIN_TOOL,()-> Ingredient.of(KijinModItems.KIJIN_INGOD.get()));

    static {
        TierSortingRegistry.registerTier(KIJIN, new ResourceLocation(KijinMod.MOD_ID,"kijin"), List.of(TierSortingRegistry.getName(Tiers.NETHERITE)),List.of());
    }
}
