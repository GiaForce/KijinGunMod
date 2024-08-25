package com.SakuraKijin.KijinMod.entity;

import com.SakuraKijin.KijinMod.regi.KijinModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class KijinAmmoEntity extends AbstractArrow {

    private ItemStack ammoItem = new ItemStack(KijinModItems.KIJIN_AMMO.get());
    private SoundEvent hitSound = SoundEvents.IRON_GOLEM_STEP;
    private double baseDamage = 1.0D;
    private int knockback;
    private List<Entity> piercedAndKilledEntities;

    public KijinAmmoEntity(EntityType<? extends KijinAmmoEntity> type, Level world){
        super(type,world);
    }

    public KijinAmmoEntity(Level world, LivingEntity shooter, ItemStack stack){
        super(KijinModEntityTypes.KIJIN_AMMO.get(),shooter,world);
        this.ammoItem = stack;
    }

    @Override
    protected void onHitEntity(EntityHitResult p_36757_) {
        Entity entity = p_36757_.getEntity();

        Minecraft.getInstance().level.addParticle(ParticleTypes.FLASH, entity.getX(), entity.getY(), entity.getZ(), 0, 0, 0);

        if (entity.getType() == EntityType.ENDER_DRAGON) {
            this.setBaseDamage(getBaseDamage() * 2);
        }

        float f = (float)this.getDeltaMovement().length();
        int i = Mth.ceil(Mth.clamp((double)f * this.baseDamage, 0.0D, 2.147483647E9D));

        Entity entity1 = this.getOwner();
        DamageSource damagesource;
        if (entity1 == null) {
            damagesource = DamageSource.arrow(this, this);
        } else {
            damagesource = DamageSource.arrow(this, entity1);
            if (entity1 instanceof LivingEntity) {
                ((LivingEntity)entity1).setLastHurtMob(entity);
            }
        }

        int k = entity.getRemainingFireTicks();

        if (entity.hurt(damagesource, (float)i)) {

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity)entity;
                if (!this.level.isClientSide && this.getPierceLevel() <= 0) {
                    livingentity.setArrowCount(livingentity.getArrowCount() + 1);
                }

                if (this.knockback > 0) {
                    double d0 = Math.max(0.0D, 1.0D - livingentity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                    Vec3 vec3 = this.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale((double)this.knockback * 0.6D * d0);
                    if (vec3.lengthSqr() > 0.0D) {
                        livingentity.push(vec3.x, 0.1D, vec3.z);
                    }
                }

                if (!this.level.isClientSide && entity1 instanceof LivingEntity) {
                    EnchantmentHelper.doPostHurtEffects(livingentity, entity1);
                    EnchantmentHelper.doPostDamageEffects((LivingEntity)entity1, livingentity);
                }

                this.doPostHurtEffects(livingentity);
                if (entity1 != null && livingentity != entity1 && livingentity instanceof Player && entity1 instanceof ServerPlayer && !this.isSilent()) {
                    ((ServerPlayer)entity1).connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
                }

                if (!entity.isAlive() && this.piercedAndKilledEntities != null) {
                    this.piercedAndKilledEntities.add(livingentity);
                }

            }

            this.playSound(hitSound, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            if (this.getPierceLevel() <= 0) {
                this.discard();
            }
        } else {
            entity.setRemainingFireTicks(k);
            this.setDeltaMovement(this.getDeltaMovement().scale(-0.1D));
            this.setYRot(this.getYRot() + 180.0F);
            this.yRotO += 180.0F;
            if (!this.level.isClientSide && this.getDeltaMovement().lengthSqr() < 1.0E-7D) {
                if (this.pickup == AbstractArrow.Pickup.ALLOWED) {
                    this.spawnAtLocation(this.getPickupItem(), 0.1F);
                }

                this.discard();
            }
        }

    }

    @Override
    protected void doPostHurtEffects(LivingEntity living) {
        Player player = Minecraft.getInstance().player;
        Vec3 v3 = player.getLookAngle();

        if (living.getType() == EntityType.ENDERMAN) {
            living.randomTeleport(player.getX() + v3.x * 3, player.getY(), player.getZ() + v3.z * 3, true);
            living.hurt(DamageSource.playerAttack(player), 40);
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        BlockState state = this.level.getBlockState(result.getBlockPos());
        state.onProjectileHit(this.level, state, result, this);
        Vec3 vector3d = result.getLocation().subtract(this.getX(),this.getY(),this.getZ());
        this.setDeltaMovement(vector3d);
        Vec3 vector3d1 = vector3d.normalize().scale((double) 0.05F);
        this.setPosRaw(this.getX() - vector3d1.x, this.getY() - vector3d1.y, this.getZ() - vector3d1.z);
        this.playSound(hitSound, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
        this.inGround = true;
        this.shakeTime = 0;
        this.setPierceLevel((byte) 0);
        if (!(result.getBlockPos().getY() == 0) && !(state.getBlock() == Blocks.SPAWNER)) {
            if (!level.isClientSide) {
                level.destroyBlock(result.getBlockPos(),true);
            }
        }
        this.discard();
    }


    @Override
    public void setBaseDamage(double damage) {
        this.baseDamage = damage;
    }

    @Override
    public double getBaseDamage() {
        return this.baseDamage;
    }

    @Override
    protected ItemStack getPickupItem() {
        return ammoItem;
    }
}
