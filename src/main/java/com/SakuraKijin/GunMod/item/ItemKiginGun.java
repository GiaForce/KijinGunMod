package com.SakuraKijin.GunMod.item;

import com.SakuraKijin.GunMod.keybind.GunKeyBind;
import com.SakuraKijin.GunMod.entity.KijinAmmoEntity;
import com.SakuraKijin.GunMod.main.GunMod;
import com.SakuraKijin.GunMod.register.GunModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import java.util.function.Predicate;

public class ItemKiginGun extends ShootableItem {

    public static final Predicate<ItemStack> AMMO = (stack) -> stack.getItem() == GunModItems.KIJIN_AMMO;

    public ItemKiginGun() {
        super(new Properties().group(GunMod.GUNMOD_TAB).rarity(GunMod.KIJIN).isImmuneToFire().maxDamage(60000));
        this.setRegistryName("kijin_gun");
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        PlayerEntity player = (PlayerEntity) entity;

        if (player.getItemStackFromSlot(EquipmentSlotType.MAINHAND).getItem() == GunModItems.KIGIN_GUN) {

            if (!world.isRemote && GunKeyBind.gunKey[0].isPressed()){
                scopeMode(stack);
                player.sendStatusMessage(new StringTextComponent("ScopeMode Changed:" + this.modeInt(stack)),false);//Part.4でこの行消す
            }

            player.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, 20 * 60 * 5, 0, false, false));

        } else {
            player.removeActivePotionEffect(Effects.NIGHT_VISION);
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        Vector3d v3 = player.getLook(1F);
        ItemStack itemstack = player.findAmmo(stack);

        if (!world.isRemote && itemstack.getItem()==GunModItems.KIJIN_AMMO) {
            ItemStack ammo = new ItemStack(Items.AIR);
            KijinAmmoEntity ammoEntity = new KijinAmmoEntity(world, player, ammo);
            ammoEntity.shoot(v3.x, v3.y, v3.z, 45, 0);
            world.addEntity(ammoEntity);
            world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_DRAGON_FIREBALL_EXPLODE, SoundCategory.PLAYERS, 1F, 1F);

            player.setActiveHand(hand);
            if (!player.abilities.isCreativeMode) {
                itemstack.shrink(1);
            }
            stack.damageItem(1, player, (b) -> {
                b.sendBreakAnimation(player.getActiveHand());
            });

            return ActionResult.resultConsume(stack);
        }
        world.playSound(null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.PLAYERS, 1F, 1.0F);
        return ActionResult.resultFail(stack);
    }

    public void scopeMode(ItemStack stack) {
        if (stack.getTag() == null) {
            stack.setTag(new CompoundNBT());
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
    public Predicate<ItemStack> getInventoryAmmoPredicate() {
        return AMMO;
    }

    @Override
    public int func_230305_d_() {
        return 0;
    }
}
