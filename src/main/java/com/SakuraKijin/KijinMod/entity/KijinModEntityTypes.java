package com.SakuraKijin.KijinMod.entity;

import com.SakuraKijin.KijinMod.main.KijinMod;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.BiFunction;

public class KijinModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, KijinMod.MOD_ID);

    public static final RegistryObject<EntityType<KijinAmmoEntity>> KIJIN_AMMO = shootEntityType("kijin_ammo", KijinAmmoEntity::new,0.2F,0.2F,200,20);

    private static <T extends Entity> RegistryObject<EntityType<T>> shootEntityType(String id, BiFunction<EntityType<T>, Level, T> function, float w, float h, int cTR, int uI){
        return ENTITIES.register(id,()-> EntityType.Builder.<T>of(function::apply,MobCategory.MISC).sized(w,h).fireImmune().clientTrackingRange(cTR).updateInterval(uI).build(id));
    }
}
