package com.SakuraKijin.GunMod.entity.renderer;

import com.SakuraKijin.GunMod.entity.KijinAmmoEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class KijinAmmoRenderer extends EntityRenderer<KijinAmmoEntity> {

    public KijinAmmoRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public ResourceLocation getEntityTexture(KijinAmmoEntity entity) {
        return null;
    }
}
