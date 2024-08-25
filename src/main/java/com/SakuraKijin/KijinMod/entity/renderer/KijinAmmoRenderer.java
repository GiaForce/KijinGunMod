package com.SakuraKijin.KijinMod.entity.renderer;

import com.SakuraKijin.KijinMod.entity.KijinAmmoEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class KijinAmmoRenderer <T extends KijinAmmoEntity>extends EntityRenderer<T> {

    public KijinAmmoRenderer(EntityRendererProvider.Context p_174008_) {
        super(p_174008_);
    }

    @Override
    public ResourceLocation getTextureLocation(KijinAmmoEntity p_114482_) {
        return null;
    }
}
