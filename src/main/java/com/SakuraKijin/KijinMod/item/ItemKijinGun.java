package com.SakuraKijin.KijinMod.item;

import com.SakuraKijin.KijinMod.entity.KijinAmmoEntity;
import com.SakuraKijin.KijinMod.main.KijinKeyBind;
import com.SakuraKijin.KijinMod.main.KijinMod;
import com.SakuraKijin.KijinMod.regi.KijinModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.function.Predicate;

public class ItemKijinGun extends ProjectileWeaponItem {

    public static final Predicate<ItemStack> AMMO = (stack) -> stack.getItem() == KijinModItems.KIJIN_AMMO.get();

    public ItemKijinGun() {
        super(new Properties().tab(KijinMod.MOD_TAB).fireResistant().durability(666666));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int itemSlot, boolean isSelected) {
        Player player = (Player) entity;

        if (player.getItemBySlot(EquipmentSlot.MAINHAND).getItem() == KijinModItems.KIJIN_GUN.get()) {
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 20 * 60 * 5, 0, false, false));

            if (!world.isClientSide && KijinKeyBind.kijinKey[0].consumeClick()){
                scopeMode(stack);
                world.playSound(null,player.getX(), player.getY(), player.getZ(),SoundEvents.ITEM_FRAME_BREAK,SoundSource.PLAYERS,1.0F,1.0F);
            }
        } else {
            player.removeEffect(MobEffects.NIGHT_VISION);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        Vec3 v3 = player.getLookAngle();
        ItemStack itemStack = player.getProjectile(stack);

        if (!world.isClientSide && itemStack.getItem() == KijinModItems.KIJIN_AMMO.get()) {
            ItemStack ammo = new ItemStack(Items.AIR);
            AbstractArrow ammoEntity = new KijinAmmoEntity(world, player, ammo);
            ammoEntity.shoot(v3.x, v3.y, v3.z, 45F, 0F);
            world.addFreshEntity(ammoEntity);
            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.DRAGON_FIREBALL_EXPLODE, SoundSource.PLAYERS, 1.0F, 1.0F);

            player.startUsingItem(hand);
            if (!player.isCreative()) {
                itemStack.shrink(1);
            }
            stack.hurtAndBreak(1, player, (b) -> {
                b.broadcastBreakEvent(player.getUsedItemHand());
            });

            return InteractionResultHolder.consume(stack);
        }
        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FLINTANDSTEEL_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
        return InteractionResultHolder.fail(stack);
    }

    public void scopeMode(ItemStack stack) {
        if (stack.getTag() == null) {
            stack.setTag(new CompoundTag());
        }
        stack.getTag().putInt("scope", this.modeInt(stack) == 0 ? 1 : 0);
    }

    public int modeInt(ItemStack stack) {
        if (stack.getTag() == null) {
            return 0;
        }
        return stack.getTag().getInt("scope");
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return AMMO;
    }

    @Override
    public int getDefaultProjectileRange() {
        return 0;
    }
}
