package com.SakuraKijin.KijinMod.item.tool;

import com.SakuraKijin.KijinMod.main.KijinKeyBind;
import com.SakuraKijin.KijinMod.main.KijinMod;
import com.SakuraKijin.KijinMod.regi.KijinModItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ToolKijinPickaxe extends PickaxeItem {

    /*p_42962_ = toolの攻撃力補正*/
    /*実際の攻撃力 = 1 + tierの攻撃力 + toolの攻撃力*/
    /*p_42963_ = toolの攻撃速度*/
    /*実際の攻撃速度 = 4 + toolの攻撃速度*/
    public ToolKijinPickaxe() {
        super(KijinModTiers.KIJIN,1,-2.8F,new Properties().tab(KijinMod.MOD_TAB).fireResistant());
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int itemSlot, boolean isSelected) {
        if (entity.tickCount % 200 == 0){
            stack.setDamageValue(stack.getDamageValue()-5);
        }
        Player player = (Player) entity;
        InteractionHand hand = player.getUsedItemHand();
        if (!world.isClientSide && player.getItemBySlot(EquipmentSlot.MAINHAND).getItem() == KijinModItems.KIJIN_PICKAXE.get() && KijinKeyBind.kijinKey[0].consumeClick()){
            player.startUsingItem(hand);
            world.playSound(null,player, SoundEvents.IRON_DOOR_OPEN, SoundSource.PLAYERS,1.0F,1.0F);
            modeChange(stack);
            player.displayClientMessage(Component.literal("Mode:" + modeName(stack)),true);
        }
    }

    public void modeChange(ItemStack stack){
        if (stack.getTag()==null){
            stack.setTag(new CompoundTag());
        }
        stack.getTag().putInt("mode",modeInt(stack) < 1 ? modeInt(stack) + 1 : 0);
    }

    public int modeInt(ItemStack stack){
        if (stack.getTag()==null){
            return 0;
        }
        return stack.getTag().getInt("mode");
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (state.is(BlockTags.MINEABLE_WITH_PICKAXE)) {
            return switch (modeInt(stack)) {
                case 0 -> KijinModTiers.KIJIN.getSpeed();
                case 1 -> KijinModTiers.KIJIN.getSpeed() * 3;
                default -> 0F;
            };
        }
        return 1.0F;
    }

    public String modeName(ItemStack stack){
        return switch (modeInt(stack)){
            case 0 -> "normal";
            case 1 -> "tactical";
            default -> "unknown";
        };
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> list, TooltipFlag tooltip) {
        list.add(Component.translatable("Mode:" + modeName(stack)));
    }
}
