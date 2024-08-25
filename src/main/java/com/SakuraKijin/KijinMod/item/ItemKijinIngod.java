package com.SakuraKijin.KijinMod.item;

import com.SakuraKijin.KijinMod.main.KijinMod;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemKijinIngod extends Item {

    public ItemKijinIngod() {
        super(new Properties().tab(KijinMod.MOD_TAB).fireResistant());
    }
}
