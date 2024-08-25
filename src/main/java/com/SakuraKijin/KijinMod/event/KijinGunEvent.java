package com.SakuraKijin.KijinMod.event;

import com.SakuraKijin.KijinMod.main.KijinMod;
import com.SakuraKijin.KijinMod.regi.KijinModItems;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ComputeFovModifierEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = KijinMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class KijinGunEvent {


    @SubscribeEvent
    public static void scopeMode(ComputeFovModifierEvent event){
        ItemStack stack = event.getPlayer().getMainHandItem();
        if (stack.getItem() == KijinModItems.KIJIN_GUN.get() && stack.getTag().getInt("scope") == 1){
            event.setNewFovModifier(0.3F);
        }
        if (event.getNewFovModifier() == 0.3F){
            Minecraft.getInstance().options.sensitivity().set(0.05D);
        }else {
            Minecraft.getInstance().options.sensitivity().set(0.5D);
        }
    }

    @SubscribeEvent
    public static void renderOverlay(RenderGuiOverlayEvent.Pre event){
        Player player = Minecraft.getInstance().player;
        ItemStack stack = player.getMainHandItem();

        int posX = event.getWindow().getGuiScaledWidth();
        int posY = event.getWindow().getGuiScaledHeight();

        VanillaGuiOverlay[] overlays = {
                VanillaGuiOverlay.AIR_LEVEL,
                VanillaGuiOverlay.ARMOR_LEVEL,
                VanillaGuiOverlay.FOOD_LEVEL,
                VanillaGuiOverlay.HOTBAR,
                VanillaGuiOverlay.PLAYER_HEALTH,
                VanillaGuiOverlay.EXPERIENCE_BAR,
                VanillaGuiOverlay.CROSSHAIR,
        };


        if (stack.getItem() == KijinModItems.KIJIN_GUN.get() && stack.getTag().getInt("scope") == 1 && Minecraft.getInstance().options.getCameraType().isFirstPerson()){

            for (VanillaGuiOverlay overlay : overlays){
                if (overlay.type().equals(event.getOverlay())){
                    event.setCanceled(true);
                }
            }

            RenderSystem.enableBlend();

            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0,new ResourceLocation(KijinMod.MOD_ID,"textures/misc/kijin_gun_scope.png"));
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder bufferBuilder = tesselator.getBuilder();
            bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

            bufferBuilder.vertex(0,posY,-90.0D).uv(0.0F,1.0F).endVertex();
            bufferBuilder.vertex(posX,posY,-90.0D).uv(1.0F,1.0F).endVertex();
            bufferBuilder.vertex(posX,0,-90.0D).uv(1.0F,0.0F).endVertex();
            bufferBuilder.vertex(0,0,-90.0D).uv(0.0F,0.0F).endVertex();
            tesselator.end();
        }
    }

    @SubscribeEvent
    public static void itemInFrame(RenderHandEvent event){
        Player player = Minecraft.getInstance().player;
        ItemStack stack = player.getMainHandItem();
        if (stack.getItem() == KijinModItems.KIJIN_GUN.get() && stack.getTag().getInt("scope") == 1){
            event.setCanceled(true);

        }
    }
}
