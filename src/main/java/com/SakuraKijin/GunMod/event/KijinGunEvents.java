package com.SakuraKijin.GunMod.event;

import com.SakuraKijin.GunMod.main.GunMod;
import com.SakuraKijin.GunMod.register.GunModItems;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.event.brewing.PotionBrewEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = GunMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class KijinGunEvents {

    @SubscribeEvent
    public static void scopeMode(FOVUpdateEvent event){
        Item item = event.getEntity().getHeldItemMainhand().getItem();
        ItemStack stack = event.getEntity().getHeldItemMainhand().getStack();
        if (item == GunModItems.KIGIN_GUN && stack.getTag().getInt("scope") == 1){
            event.setNewfov(0.3F);
        }
        if (event.getNewfov() == 0.3F){
            Minecraft.getInstance().gameSettings.mouseSensitivity = 0.05D;
        }else {
            Minecraft.getInstance().gameSettings.mouseSensitivity = 0.5D;
        }
    }

    @SubscribeEvent
    public static void renderOverlay(RenderGameOverlayEvent event) {
        RenderGameOverlayEvent.ElementType type = event.getType();
        PlayerEntity player = Minecraft.getInstance().player;
        ItemStack stack = player.getItemStackFromSlot(EquipmentSlotType.MAINHAND).getStack();
        Item item = player.getItemStackFromSlot(EquipmentSlotType.MAINHAND).getItem();

        int posX = event.getWindow().getScaledWidth();
        int posY = event.getWindow().getScaledHeight();

        if(item == GunModItems.KIGIN_GUN && stack.getTag().getInt("scope") == 1 && Minecraft.getInstance().gameSettings.getPointOfView().func_243192_a()) {

            switch (type){

                case AIR: event.setCanceled(true);
                case ARMOR: event.setCanceled(true);
                case FOOD: event.setCanceled(true);
                case HEALTH: event.setCanceled(true);
                case EXPERIENCE: event.setCanceled(true);
                case HOTBAR: event.setCanceled(true);
                case CROSSHAIRS: event.setCanceled(true);
            }

            RenderSystem.enableBlend();

            Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation(GunMod.MOD_ID,"textures/misc/kijin_gun_scope.png"));
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
            bufferbuilder.pos(0, posY, -90.0D).tex(0.0F, 1.0F).endVertex();
            bufferbuilder.pos(posX, posY, -90.0D).tex(1.0F, 1.0F).endVertex();
            bufferbuilder.pos(posX, 0.0D, -90.0D).tex(1.0F, 0.0F).endVertex();
            bufferbuilder.pos(0, 0.0D, -90.0D).tex(0.0F, 0.0F).endVertex();
            tessellator.draw();
        }
    }

    @SubscribeEvent
    public static void itemInFrame(RenderHandEvent event){
        PlayerEntity player = Minecraft.getInstance().player;
        ItemStack itemStack = player.getItemStackFromSlot(EquipmentSlotType.MAINHAND).getStack();
        Item item = player.getItemStackFromSlot(EquipmentSlotType.MAINHAND).getItem();
        if (item == GunModItems.KIGIN_GUN && itemStack.getTag().getInt("scope") == 1){
            event.setCanceled(true);
        }
    }
}
