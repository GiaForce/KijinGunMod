package com.SakuraKijin.GunMod.entity;

import com.SakuraKijin.GunMod.register.GunModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class KijinAmmoEntity extends AbstractArrowEntity {

    private ItemStack shootStack = new ItemStack(GunModItems.KIJIN_AMMO);
    private SoundEvent hitSound = SoundEvents.ENTITY_IRON_GOLEM_STEP;
    private double damage = 1.0D;
    private int knockbackStrength;
    private List<Entity> hitEntities;

    public KijinAmmoEntity(EntityType<? extends KijinAmmoEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public KijinAmmoEntity(World world, LivingEntity living, ItemStack stack) {
        super(GunModEntityTypes.KIJIN_AMMO.get(), living, world);
        this.shootStack = stack;
    }

    protected void onEntityHit(EntityRayTraceResult p_213868_1_) {
        Entity entity = p_213868_1_.getEntity();
        Minecraft.getInstance().world.addParticle(ParticleTypes.FLASH, entity.getPosX(), entity.getPosY(), entity.getPosZ(), 0, 0, 0);

        if (entity.getType() == EntityType.ENDER_DRAGON) {
            this.setDamage(getDamage() * 2);
        }

        float f = (float) this.getMotion().length();
        int i = MathHelper.ceil(MathHelper.clamp((double) f * this.damage, 0.0D, 2.147483647E9D));

        Entity entity1 = this.func_234616_v_();
        DamageSource damagesource;
        if (entity1 == null) {
            damagesource = DamageSource.causeArrowDamage(this, this);
        } else {
            damagesource = DamageSource.causeArrowDamage(this, entity1);
            if (entity1 instanceof LivingEntity) {
                ((LivingEntity) entity1).setLastAttackedEntity(entity);
            }
        }

        int k = entity.getFireTimer();

        if (entity.attackEntityFrom(damagesource, (float) i)) {

            if (entity instanceof LivingEntity) {
                LivingEntity livingentity = (LivingEntity) entity;
                if (!this.world.isRemote && this.getPierceLevel() <= 0) {
                    livingentity.setArrowCountInEntity(livingentity.getArrowCountInEntity() + 1);
                }

                if (this.knockbackStrength > 0) {
                    Vector3d vector3d = this.getMotion().mul(1.0D, 0.0D, 1.0D).normalize().scale((double) this.knockbackStrength * 0.6D);
                    if (vector3d.lengthSquared() > 0.0D) {
                        livingentity.addVelocity(vector3d.x, 0.1D, vector3d.z);
                    }
                }

                if (!this.world.isRemote && entity1 instanceof LivingEntity) {
                    EnchantmentHelper.applyThornEnchantments(livingentity, entity1);
                    EnchantmentHelper.applyArthropodEnchantments((LivingEntity) entity1, livingentity);
                }

                this.arrowHit(livingentity);
                if (entity1 != null && livingentity != entity1 && livingentity instanceof PlayerEntity && entity1 instanceof ServerPlayerEntity && !this.isSilent()) {
                    ((ServerPlayerEntity) entity1).connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241770_g_, 0.0F));
                }

                if (!entity.isAlive() && this.hitEntities != null) {
                    this.hitEntities.add(livingentity);
                }

            }

            this.playSound(this.hitSound, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
            if (this.getPierceLevel() <= 0) {
                this.remove();
            }
        } else {
            entity.forceFireTicks(k);
            this.setMotion(this.getMotion().scale(-0.1D));
            this.rotationYaw += 180.0F;
            this.prevRotationYaw += 180.0F;
            if (!this.world.isRemote && this.getMotion().lengthSquared() < 1.0E-7D) {
                if (this.pickupStatus == AbstractArrowEntity.PickupStatus.ALLOWED) {
                    this.entityDropItem(this.getArrowStack(), 0.1F);
                }

                this.remove();
            }
        }

    }

    protected void arrowHit(LivingEntity living) {
        PlayerEntity player = Minecraft.getInstance().player;
        Vector3d v3 = player.getLook(1);

        if (living.getType() == EntityType.ENDERMAN) {
            living.attemptTeleport(player.getPosX() + v3.x * 3, player.getPosY(), player.getPosZ() + v3.z * 3, true);
            living.attackEntityFrom(DamageSource.causePlayerDamage(player), 40);
        }

    }

    @Override
    protected void func_230299_a_(BlockRayTraceResult p_230299_1_) {
        BlockState state = this.world.getBlockState(p_230299_1_.getPos());
        state.onProjectileCollision(this.world, state, p_230299_1_, this);
        Vector3d vector3d = p_230299_1_.getHitVec().subtract(this.getPosX(), this.getPosY(), this.getPosZ());
        this.setMotion(vector3d);
        Vector3d vector3d1 = vector3d.normalize().scale((double) 0.05F);
        this.setRawPosition(this.getPosX() - vector3d1.x, this.getPosY() - vector3d1.y, this.getPosZ() - vector3d1.z);
        this.playSound(hitSound, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
        this.inGround = true;
        this.arrowShake = 0;
        this.setPierceLevel((byte) 0);
        if (!(p_230299_1_.getPos().getY() == 0) && !(state.getBlock() == Blocks.SPAWNER)) {
            if (!world.isRemote) {
                world.destroyBlock(p_230299_1_.getPos(), true);
            }
        }
        this.remove();
    }

    @Override
    public void setDamage(double damageIn) {
        this.damage = damageIn;
    }

    @Override
    public double getDamage() {
        return this.damage;
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected ItemStack getArrowStack() {
        return shootStack;
    }
}
