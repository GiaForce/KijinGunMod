package com.SakuraKijin.GunMod.entity;

import com.SakuraKijin.GunMod.main.GunMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.BiFunction;

public class GunModEntityTypes {

    public static DeferredRegister<EntityType<?>> ENTITY_REGISTER = DeferredRegister.create(ForgeRegistries.ENTITIES, GunMod.MOD_ID);

    public static RegistryObject<EntityType<KijinAmmoEntity>> KIJIN_AMMO = create("kijin_ammo", KijinAmmoEntity::new);

    public static <T extends Entity> RegistryObject<EntityType<T>> create(String id, BiFunction<EntityType<T>, World, T> function){
        EntityType<T> type = EntityType.Builder.create(function::apply, EntityClassification.MISC).size(0.2F,0.2F).trackingRange(200).immuneToFire().setShouldReceiveVelocityUpdates(true).func_233608_b_(20).build(id);
        return ENTITY_REGISTER.register(id,()->type);
    }
}
