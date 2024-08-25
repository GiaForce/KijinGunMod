package com.SakuraKijin.KijinMod.regi;

import com.SakuraKijin.KijinMod.item.ItemKijinGun;
import com.SakuraKijin.KijinMod.item.ItemKijinIngod;
import com.SakuraKijin.KijinMod.item.ItemkijinAmmo;
import com.SakuraKijin.KijinMod.item.tool.ToolKijinPickaxe;
import com.SakuraKijin.KijinMod.main.KijinMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.*;

import java.util.function.Supplier;

public class KijinModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, KijinMod.MOD_ID);

    public static final RegistryObject<ItemKijinIngod> KIJIN_INGOD = itemRegister("kijin_ingod", ItemKijinIngod::new);
    public static final RegistryObject<ItemkijinAmmo> KIJIN_AMMO = itemRegister("kijin_ammo",ItemkijinAmmo::new);

    public static final RegistryObject<ToolKijinPickaxe> KIJIN_PICKAXE = itemRegister("kijin_pickaxe", ToolKijinPickaxe::new);
    public static final RegistryObject<ItemKijinGun> KIJIN_GUN = itemRegister("kijin_gun", ItemKijinGun::new);

    private static <T extends Item> RegistryObject<T> itemRegister(String key, Supplier<T> supplier){
     return ITEMS.register(key,supplier);
    }
}
