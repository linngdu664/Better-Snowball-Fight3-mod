package com.linngdu664.bsf.entity;

import com.linngdu664.bsf.enchantment.EnchantmentRegister;
import com.linngdu664.bsf.entity.ai.goal.*;
import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.entity.snowball.util.LaunchFrom;
import com.linngdu664.bsf.item.ItemRegister;
import com.linngdu664.bsf.item.snowball.normal.SmoothSnowballItem;
import com.linngdu664.bsf.item.tank.AbstractSnowballTankItem;
import com.linngdu664.bsf.item.tank.special.PowderSnowballTank;
import com.linngdu664.bsf.item.tool.CreativeSnowGolemToolItem;
import com.linngdu664.bsf.item.tool.SnowGolemModeTweakerItem;
import com.linngdu664.bsf.item.tool.SnowballClampItem;
import com.linngdu664.bsf.item.tool.TargetLocatorItem;
import com.linngdu664.bsf.item.weapon.*;
import com.linngdu664.bsf.particle.ParticleUtil;
import com.linngdu664.bsf.util.BSFMthUtil;
import com.linngdu664.bsf.util.SoundRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class BSFSnowGolemEntity extends TamableAnimal implements RangedAttackMob {
    private static final int styleNum = 9;
    /*
     status flag:
     0: standby
     1: follow
     2: follow & attack
     3: patrol & attack
     4: turret
     */
    private static final EntityDataAccessor<Byte> STATUS_FLAG = SynchedEntityData.defineId(BSFSnowGolemEntity.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Boolean> USE_LOCATOR = SynchedEntityData.defineId(BSFSnowGolemEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<ItemStack> WEAPON = SynchedEntityData.defineId(BSFSnowGolemEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> AMMO = SynchedEntityData.defineId(BSFSnowGolemEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<Integer> WEAPON_ANG = SynchedEntityData.defineId(BSFSnowGolemEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Byte> STYLE = SynchedEntityData.defineId(BSFSnowGolemEntity.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Boolean> ENHANCE = SynchedEntityData.defineId(BSFSnowGolemEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Float> REAL_SIGHT_X = SynchedEntityData.defineId(BSFSnowGolemEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> REAL_SIGHT_Y = SynchedEntityData.defineId(BSFSnowGolemEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> REAL_SIGHT_Z = SynchedEntityData.defineId(BSFSnowGolemEntity.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Integer> POTION_SICKNESS = SynchedEntityData.defineId(BSFSnowGolemEntity.class, EntityDataSerializers.INT);

    public BSFSnowGolemEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
    }

    public static AttributeSupplier setAttributes() {
        return TamableAnimal.createLivingAttributes().add(Attributes.MAX_HEALTH, 15.0).add(Attributes.FOLLOW_RANGE, 50.0).add(Attributes.MOVEMENT_SPEED, 0.3).build();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(STATUS_FLAG, (byte) 0);
        entityData.define(USE_LOCATOR, false);
        entityData.define(WEAPON, ItemStack.EMPTY);
        entityData.define(AMMO, ItemStack.EMPTY);
        entityData.define(WEAPON_ANG, 0);
        entityData.define(STYLE, (byte) (BSFMthUtil.randInt(0, styleNum)));
        entityData.define(ENHANCE, false);
        entityData.define(REAL_SIGHT_X, 1F);
        entityData.define(REAL_SIGHT_Y, 0F);
        entityData.define(REAL_SIGHT_Z, 0F);
        entityData.define(POTION_SICKNESS, 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putByte("Status", getStatus());
        pCompound.putBoolean("UseLocator", isUseLocator());
        CompoundTag compoundTag = new CompoundTag();
        getWeapon().save(compoundTag);
        pCompound.put("Weapon", compoundTag);
        compoundTag = new CompoundTag();
        getAmmo().save(compoundTag);
        pCompound.put("Ammo", compoundTag);
        pCompound.putInt("WeaponAng", getWeaponAng());
        pCompound.putInt("Style", getStyle());
        pCompound.putBoolean("Enhance", getEnhance());
        pCompound.putInt("PotionSickness", getPotionSickness());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        setStatus(pCompound.getByte("Status"));
        setUseLocator(pCompound.getBoolean("UseLocator"));
        setWeapon(ItemStack.of(pCompound.getCompound("Weapon")));
        setAmmo(ItemStack.of(pCompound.getCompound("Ammo")));
        setWeaponAng(pCompound.getInt("WeaponAng"));
        setStyle(pCompound.getByte("Style"));
        setEnhance(pCompound.getBoolean("Enhance"));
        setPotionSickness(pCompound.getInt("PotionSickness"));
    }

    public byte getStatus() {
        return entityData.get(STATUS_FLAG);
    }

    public void setStatus(byte status) {
        entityData.set(STATUS_FLAG, status);
    }

    public boolean isUseLocator() {
        return entityData.get(USE_LOCATOR);
    }

    public void setUseLocator(boolean useLocator) {
        entityData.set(USE_LOCATOR, useLocator);
    }

    public ItemStack getWeapon() {
        return entityData.get(WEAPON);
    }

    public void setWeapon(ItemStack itemStack) {
        entityData.set(WEAPON, itemStack);
    }

    public ItemStack getAmmo() {
        return entityData.get(AMMO);
    }

    public void setAmmo(ItemStack itemStack) {
        entityData.set(AMMO, itemStack);
    }

    public int getWeaponAng() {
        return entityData.get(WEAPON_ANG);
    }

    public void setWeaponAng(int ang) {
        entityData.set(WEAPON_ANG, ang);
    }

    public byte getStyle() {
        return entityData.get(STYLE);
    }

    public void setStyle(byte style) {
        entityData.set(STYLE, style);
    }

    public boolean getEnhance() {
        return entityData.get(ENHANCE);
    }

    public void setEnhance(boolean enhance) {
        entityData.set(ENHANCE, enhance);
    }

    public float getRealSightX() {
        return entityData.get(REAL_SIGHT_X);
    }

    public void setRealSightX(float x) {
        entityData.set(REAL_SIGHT_X, x);
    }

    public float getRealSightY() {
        return entityData.get(REAL_SIGHT_Y);
    }

    public void setRealSightY(float y) {
        entityData.set(REAL_SIGHT_Y, y);
    }

    public float getRealSightZ() {
        return entityData.get(REAL_SIGHT_Z);
    }

    public void setRealSightZ(float z) {
        entityData.set(REAL_SIGHT_Z, z);
    }

    public int getPotionSickness() {
        return entityData.get(POTION_SICKNESS);
    }

    public void setPotionSickness(int i) {
        entityData.set(POTION_SICKNESS, i);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        goalSelector.addGoal(2, new BSFGolemRangedAttackGoal(this, 1.0, 30, 50.0F));
        goalSelector.addGoal(3, new BSFGolemFollowOwnerGoal(this, 1.0, 6.0F, 3.0F));
        goalSelector.addGoal(4, new BSFGolemRandomStrollGoal(this, 0.8, 1E-5F));
        goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 5.0F));
        targetSelector.addGoal(1, new BSFGolemHurtByTargetGoal(this));
        targetSelector.addGoal(2, new BSFGolemNearestAttackableTargetGoal(this, Mob.class, 1, true, false, (p) -> p instanceof Enemy));
    }

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player pPlayer, @NotNull InteractionHand pHand) {
        Level level = level();
        if (!level.isClientSide && pPlayer.equals(getOwner())) {
            ItemStack itemStack = pPlayer.getItemInHand(pHand);
            if (itemStack.getItem() instanceof AbstractSnowballTankItem tank && tank.getSnowball().getTypeFlag() == (SnowballCannonItem.TYPE_FLAG | SnowballShotgunItem.TYPE_FLAG) && !(tank instanceof PowderSnowballTank) && getAmmo().isEmpty()) {
                setAmmo(itemStack.copy());
                if (!pPlayer.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }
            } else if ((itemStack.getItem() instanceof SnowballCannonItem || itemStack.getItem() instanceof SnowballShotgunItem) && getWeapon().isEmpty()) {
                setWeapon(itemStack.copy());
                if (!pPlayer.getAbilities().instabuild) {
                    if (EnchantmentHelper.getTagEnchantmentLevel(EnchantmentRegister.SNOW_GOLEM_EXCLUSIVE.get(), itemStack) > 0) {
                        itemStack.hurtAndBreak(10, pPlayer, (p) -> p.broadcastBreakEvent(p.getUsedItemHand()));
                    } else {
                        itemStack.shrink(1);
                    }
                }
            } else if (itemStack.isEmpty()) {
                if (pPlayer.isShiftKeyDown()) {
                    if (EnchantmentHelper.getTagEnchantmentLevel(EnchantmentRegister.SNOW_GOLEM_EXCLUSIVE.get(), getWeapon()) <= 0) {
                        pPlayer.getInventory().placeItemBackInInventory(getWeapon(), true);
                    }
                    setWeapon(ItemStack.EMPTY);
                } else {
                    pPlayer.getInventory().placeItemBackInInventory(getAmmo(), true);
                    setAmmo(ItemStack.EMPTY);
                }
            } else if (itemStack.getItem() instanceof SmoothSnowballItem || itemStack.getItem() instanceof SolidBucketItem || itemStack.getItem().equals(Items.SNOW_BLOCK) || itemStack.getItem().equals(Items.ICE)) {
                if (getPotionSickness() == 0) {
                    Item item = itemStack.getItem();
                    itemStack.shrink(1);
                    if (item instanceof SmoothSnowballItem) {
                        heal(2);
                        setPotionSickness(20);
                        ((ServerLevel) level).sendParticles(ParticleTypes.SNOWFLAKE, this.getX(), this.getEyeY(), this.getZ(), 8, 0, 0, 0, 0.04);
                        playSound(SoundEvents.SNOW_BREAK, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
                    } else if (item instanceof SolidBucketItem) {
                        pPlayer.getInventory().placeItemBackInInventory(new ItemStack(Items.BUCKET, 1), true);
                        heal(8);
                        setPotionSickness(100);
                        ((ServerLevel) level).sendParticles(ParticleTypes.SNOWFLAKE, this.getX(), this.getEyeY(), this.getZ(), 24, 0, 0, 0, 0.04);
                        playSound(SoundEvents.SNOW_BREAK, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
                    } else if (item.equals(Items.SNOW_BLOCK)) {
                        heal(5);
                        setPotionSickness(60);
                        ((ServerLevel) level).sendParticles(ParticleTypes.SNOWFLAKE, this.getX(), this.getEyeY(), this.getZ(), 16, 0, 0, 0, 0.04);
                        playSound(SoundEvents.SNOW_BREAK, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
                    } else {
                        addEffect(new MobEffectInstance(MobEffects.REGENERATION, 600, 1));
                        setPotionSickness(60);
                        ((ServerLevel) level).sendParticles(ParticleTypes.ITEM_SNOWBALL, this.getX(), this.getEyeY(), this.getZ(), 16, 0, 0, 0, 0.04);
                        playSound(SoundEvents.GLASS_BREAK, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
                    }
                } else {
                    pPlayer.displayClientMessage(MutableComponent.create(new TranslatableContents("potionSickness.tip", null, new Object[0])).append(String.valueOf(getPotionSickness())), false);
                }
            } else if (itemStack.getItem() instanceof SnowGolemModeTweakerItem) {
                CompoundTag tag = itemStack.getOrCreateTag();
                if (tag.getBoolean("UseLocator") != isUseLocator()) {
                    setTarget(null);
                }
                setUseLocator(tag.getBoolean("UseLocator"));
                setStatus(tag.getByte("Status"));
                setOrderedToSit(getStatus() == 0);
                pPlayer.displayClientMessage(MutableComponent.create(new TranslatableContents("import_state.tip", null, new Object[0])), false);
                level.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.DISPENSER_DISPENSE, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
            } else if (itemStack.getItem() instanceof TargetLocatorItem && isUseLocator()) {
                LivingEntity entity = (LivingEntity) level.getEntity(itemStack.getTag().getInt("ID"));
                if (entity != null && entity != this && getOwner() != null) {
                    ((Player) getOwner()).displayClientMessage(MutableComponent.create(new TranslatableContents("snow_golem_locator_tip", null, new Object[0])), false);
                    setTarget(entity);
                }
                level.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.DISPENSER_DISPENSE, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
            } else if (itemStack.getItem() instanceof SnowballClampItem) {
                pPlayer.getInventory().placeItemBackInInventory(new ItemStack(ItemRegister.SMOOTH_SNOWBALL.get(), 1), true);
                itemStack.hurtAndBreak(1, pPlayer, (e) -> e.broadcastBreakEvent(pHand));
            } else if (itemStack.getItem() instanceof SnowballItem) {
                setStyle((byte) ((getStyle() + 1) % styleNum));
            } else if (itemStack.getItem() instanceof CreativeSnowGolemToolItem) {
                if (pPlayer.isShiftKeyDown()) {
                    CompoundTag tag = itemStack.getOrCreateTag();
                    CompoundTag tag1 = new CompoundTag();
                    getAmmo().save(tag1);
                    tag.put("Ammo", tag1);
                    tag1 = new CompoundTag();
                    getWeapon().save(tag1);
                    tag.put("Weapon", tag1);
                    tag.putBoolean("Enhance", getEnhance());
                    tag.putBoolean("UseLocator", isUseLocator());
                    tag.putByte("Status", getStatus());
                    tag.putByte("Style", getStyle());
                    if (getTarget() != null) {
                        tag.putInt("ID", getTarget().getId());
                    } else {
                        tag.remove("ID");
                    }
                    ((Player) getOwner()).displayClientMessage(MutableComponent.create(new TranslatableContents("copy.tip", null, new Object[0])), false);
                } else {
                    setEnhance(!getEnhance());
                    ((Player) getOwner()).displayClientMessage(MutableComponent.create(new TranslatableContents("golem_enhance.tip", null, new Object[0])).append(String.valueOf(getEnhance())), false);
                }
                level.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.DISPENSER_DISPENSE, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        Level level = level();
        if (!level.isClientSide) {
            if (level.getBiome(blockPosition()).is(BiomeTags.SNOW_GOLEM_MELTS)) {
                this.hurt(this.damageSources().onFire(), 1.0F);
            }
            if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(level, this)) {
                return;
            }
            BlockState blockstate = Blocks.SNOW.defaultBlockState();
            for(int i = 0; i < 4; ++i) {
                int j = Mth.floor(getX() + (double) ((float) (i % 2 * 2 - 1) * 0.25F));
                int k = Mth.floor(getY());
                int l = Mth.floor(getZ() + (double) ((float) (i / 2 % 2 * 2 - 1) * 0.25F));
                BlockPos blockPos1 = new BlockPos(j, k, l);
                if (level.isEmptyBlock(blockPos1) && blockstate.canSurvive(level, blockPos1)) {
                    level.setBlockAndUpdate(blockPos1, blockstate);
                    level.gameEvent(GameEvent.BLOCK_PLACE, blockPos1, GameEvent.Context.of(this, blockstate));
                }
            }
        }
    }

    @Override
    public void tick() {
        Level level = level();
        if (getEnhance()) {
            this.heal(1);
        }
        if (getPotionSickness() > 0) {
            setPotionSickness(getPotionSickness() - 1);
        }
        if (getWeaponAng() > 0) {
            if (getWeaponAng() == 360) {
                if (getWeapon().getItem() instanceof SnowballCannonItem || getWeapon().getItem() instanceof FreezingSnowballCannonItem) {
                    ParticleUtil.spawnForwardConeParticles(level, this, new Vec3(getRealSightX(), getRealSightY(), getRealSightZ()), ParticleTypes.SNOWFLAKE, 4.5F, 90, 1.5F, 0.1F);
                } else if (getWeapon().getItem() instanceof SnowballShotgunItem || getWeapon().getItem() instanceof PowerfulSnowballCannonItem) {
                    ParticleUtil.spawnForwardConeParticles(level, this, new Vec3(getRealSightX(), getRealSightY(), getRealSightZ()), ParticleTypes.SNOWFLAKE, 4.5F, 45, 1.5F, 0.1F);
                }
            }
            setWeaponAng(getWeaponAng() - 72);
        }
        super.tick();
    }

    @Override
    public void performRangedAttack(@NotNull LivingEntity pTarget, float pDistanceFactor) {
        Level level = level();
        ItemStack weapon = getWeapon();
        ItemStack ammo = getAmmo();
        if (!weapon.isEmpty()) {
            float damageChance = 1.0F / (1.0F + EnchantmentHelper.getTagEnchantmentLevel(Enchantments.UNBREAKING, weapon));
            float v = 3.0F;
            float accuracy = 1.0F;
            ILaunchAdjustment launchAdjustment = ((AbstractBSFWeaponItem) weapon.getItem()).getLaunchAdjustment(1, ammo.getItem());
            if (launchAdjustment.getLaunchFrom().equals(LaunchFrom.POWERFUL_CANNON)) {
                v = 4.0F;
            } else if (launchAdjustment.getLaunchFrom().equals(LaunchFrom.SHOTGUN)) {
                v = 2.0F;
                accuracy = 10.0F;
            }
            double h = pTarget.getEyeY() - getEyeY();
            double dx = pTarget.getX() - getX();
            double dz = pTarget.getZ() - getZ();
            double x2 = BSFMthUtil.modSqr(dx, dz);
            double d = Math.sqrt(x2 + h * h);
            double x = Math.sqrt(x2);
            double k = 0.015 * x2 / (v * v);    // 0.5 * g / 400.0, g = 12
            double cosTheta = 0.7071067811865475 / d * Math.sqrt(x2 - 2 * k * h + x * Math.sqrt(x2 - 4 * k * k - 4 * k * h));
            double sinTheta;
            dx /= x;
            dz /= x;
            if (cosTheta > 1) {
                sinTheta = 0;
            } else {
                sinTheta = Math.sqrt(1 - cosTheta * cosTheta);
                dx *= cosTheta;
                dz *= cosTheta;
                if (h < -k) {
                    sinTheta = -sinTheta;
                }
            }
            setRealSightX((float) dx);
            setRealSightY((float) sinTheta);
            setRealSightZ((float) dz);
            int j = weapon.getItem() instanceof SnowballShotgunItem ? 4 : 1;
            for (int i = 0; i < j; i++) {
                if (ammo.getItem() instanceof AbstractSnowballTankItem tank) {
                    AbstractBSFSnowballEntity snowball = tank.getSnowball().getCorrespondingEntity(level, this, launchAdjustment);
                    snowball.shoot(dx, sinTheta, dz, v, accuracy);
                    level.addFreshEntity(snowball);
                    if (!getEnhance()) {
                        ammo.setDamageValue(ammo.getDamageValue() + 1);
                        if (ammo.getDamageValue() == 96) {
                            setAmmo(new ItemStack(ItemRegister.EMPTY_SNOWBALL_STORAGE_TANK.get()));
                        }
                    }
                    if (i == 0) {
                        playSound(j == 4 ? SoundRegister.SHOTGUN_FIRE_2.get() : SoundRegister.SNOWBALL_CANNON_SHOOT.get(), 1.0F, 1.0F / (getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
                        if (getRandom().nextFloat() <= damageChance && !getEnhance()) {
                            weapon.setDamageValue(weapon.getDamageValue() + 1);
                            if (weapon.getDamageValue() == 256) {
                                setWeapon(ItemStack.EMPTY);
                                playSound(SoundEvents.ITEM_BREAK, 1.0F, 1.0F / (getRandom().nextFloat() * 0.4F + 0.8F));
                            }
                        }
                    }
                    setWeaponAng(360);
                } else {
                    break;
                }
            }
        }
    }

    @Override
    public void die(@NotNull DamageSource pCause) {
        super.die(pCause);
        if (!getWeapon().isEmpty() && !EnchantmentHelper.hasVanishingCurse(getWeapon()) && EnchantmentHelper.getTagEnchantmentLevel(EnchantmentRegister.SNOW_GOLEM_EXCLUSIVE.get(), getWeapon()) <= 0) {
            spawnAtLocation(getWeapon());
        }
        if (!getAmmo().isEmpty() && !EnchantmentHelper.hasVanishingCurse(getAmmo())) {
            spawnAtLocation(getAmmo());
        }
        spawnAtLocation(new ItemStack(Items.SNOWBALL, BSFMthUtil.randInt(0, 16)));
    }

    @Override
    public boolean isSensitiveToWater() {
        return true;
    }

    @Override
    protected int calculateFallDamage(float pDistance, float pDamageMultiplier) {
        return 0;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SNOW_GOLEM_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource pDamageSource) {
        return SoundEvents.SNOW_GOLEM_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SNOW_GOLEM_DEATH;
    }

    @Override
    public AgeableMob getBreedOffspring(@NotNull ServerLevel p_146743_, @NotNull AgeableMob p_146744_) {
        return null;
    }
}
