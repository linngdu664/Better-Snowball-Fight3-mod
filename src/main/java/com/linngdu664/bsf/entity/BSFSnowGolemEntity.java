package com.linngdu664.bsf.entity;

import com.linngdu664.bsf.Main;
import com.linngdu664.bsf.entity.ai.goal.*;
import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.item.misc.SnowGolemCoreItem;
import com.linngdu664.bsf.item.snowball.AbstractBSFSnowballItem;
import com.linngdu664.bsf.item.tank.LargeSnowballTankItem;
import com.linngdu664.bsf.item.tank.SnowballTankItem;
import com.linngdu664.bsf.item.tool.SnowballClampItem;
import com.linngdu664.bsf.item.weapon.AbstractBSFWeaponItem;
import com.linngdu664.bsf.item.weapon.SnowballCannonItem;
import com.linngdu664.bsf.item.weapon.SnowballShotgunItem;
import com.linngdu664.bsf.network.ForwardConeParticlesToClient;
import com.linngdu664.bsf.particle.util.BSFParticleType;
import com.linngdu664.bsf.registry.*;
import com.linngdu664.bsf.util.BSFCommonUtil;
import com.linngdu664.bsf.util.BSFTiers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.List;

// I call this "shit mountain".
public class BSFSnowGolemEntity extends TamableAnimal implements RangedAttackMob {
    private static final int STYLE_NUM = 9;
    private static final EntityDataAccessor<ItemStack> WEAPON = SynchedEntityData.defineId(BSFSnowGolemEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> AMMO = SynchedEntityData.defineId(BSFSnowGolemEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> CORE = SynchedEntityData.defineId(BSFSnowGolemEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<Integer> WEAPON_ANG = SynchedEntityData.defineId(BSFSnowGolemEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Byte> STYLE = SynchedEntityData.defineId(BSFSnowGolemEntity.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Byte> statusFlag = SynchedEntityData.defineId(BSFSnowGolemEntity.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Byte> locatorFlag = SynchedEntityData.defineId(BSFSnowGolemEntity.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Integer> potionSickness = SynchedEntityData.defineId(BSFSnowGolemEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> enhance = SynchedEntityData.defineId(BSFSnowGolemEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> coreCoolDown = SynchedEntityData.defineId(BSFSnowGolemEntity.class, EntityDataSerializers.INT);
    private float launchVelocity;
    private float launchAccuracy;
    private double shootX;
    private double shootY;
    private double shootZ;
    /*
     status flag:
     0: standby
     1: follow
     2: follow & attack
     3: patrol & attack
     4: turret
     */
//    private byte statusFlag;
//    private byte locatorFlag;
//    private boolean enhance;
//    private int potionSickness;
//    private int coreCoolDown;
    private boolean dropEquipment;

    public BSFSnowGolemEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(WEAPON, ItemStack.EMPTY);
        entityData.define(AMMO, ItemStack.EMPTY);
        entityData.define(CORE, ItemStack.EMPTY);
        entityData.define(WEAPON_ANG, 0);
        entityData.define(STYLE, (byte) (getRandom().nextInt(0, STYLE_NUM)));
        entityData.define(statusFlag, (byte)0);
        entityData.define(locatorFlag, (byte)0);
        entityData.define(potionSickness, 0);
        entityData.define(enhance, false);
        entityData.define(coreCoolDown, 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putByte("Status", getStatus());
        pCompound.putByte("Locator", getLocator());
        CompoundTag compoundTag = new CompoundTag();
        getWeapon().save(compoundTag);
        pCompound.put("Weapon", compoundTag);
        compoundTag = new CompoundTag();
        getAmmo().save(compoundTag);
        pCompound.put("Ammo", compoundTag);
        compoundTag = new CompoundTag();
        getCore().save(compoundTag);
        pCompound.put("Core", compoundTag);
        pCompound.putByte("Style", getStyle());
        pCompound.putBoolean("Enhance", getEnhance());
        pCompound.putInt("PotionSickness", getPotionSickness());
        pCompound.putInt("CoreCoolDown", getCoreCoolDown());
        pCompound.putBoolean("DropEquipment", dropEquipment);
        if (getTarget() != null) {
            pCompound.putUUID("TargetUUID", getTarget().getUUID());
        } else {
            pCompound.remove("TargetUUID");
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        setStatus(pCompound.getByte("Status"));
        setLocator(pCompound.getByte("Locator"));
        setWeapon(ItemStack.of(pCompound.getCompound("Weapon")));
        setAmmo(ItemStack.of(pCompound.getCompound("Ammo")));
        setCore(ItemStack.of(pCompound.getCompound("Core")));
        setWeaponAng(pCompound.getInt("WeaponAng"));
        setStyle(pCompound.getByte("Style"));
        setEnhance(pCompound.getBoolean("Enhance"));
        setPotionSickness(pCompound.getInt("PotionSickness"));
        setCoreCoolDown(pCompound.getInt("CoreCoolDown"));
        dropEquipment = pCompound.getBoolean("DropEquipment");
        if (pCompound.contains("TargetUUID")) {
            setTarget((LivingEntity) ((ServerLevel) level()).getEntity(pCompound.getUUID("TargetUUID")));
        }
    }

    public byte getStatus() {
        return entityData.get(statusFlag);
    }
    public void setStatus(byte status){
        entityData.set(statusFlag,status);
    }

    public byte getLocator() {
        return entityData.get(locatorFlag);
    }
    public void setLocator(byte locator){
        entityData.set(locatorFlag,locator);
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

    public ItemStack getCore() {
        return entityData.get(CORE);
    }

    public void setCore(ItemStack itemStack) {
        entityData.set(CORE, itemStack);
    }

    public byte getStyle() {
        return entityData.get(STYLE);
    }

    public void setStyle(byte style) {
        entityData.set(STYLE, style);
    }

    public void setLaunchVelocity(float launchVelocity) {
        this.launchVelocity = launchVelocity;
    }

    public void setLaunchAccuracy(float launchAccuracy) {
        this.launchAccuracy = launchAccuracy;
    }

    public void setShootX(double shootX) {
        this.shootX = shootX;
    }

    public void setShootY(double shootY) {
        this.shootY = shootY;
    }

    public void setShootZ(double shootZ) {
        this.shootZ = shootZ;
    }

    public int getCoreCoolDown() {
        return entityData.get(coreCoolDown);
    }
    public void setCoreCoolDown(int coolDown) {
        entityData.set(coreCoolDown,coolDown);
    }
    public int getPotionSickness() {
        return entityData.get(potionSickness);
    }
    public void setPotionSickness(int sickness) {
        entityData.set(potionSickness,sickness);
    }
    public boolean getEnhance() {
        return entityData.get(enhance);
    }
    public void setEnhance(boolean enhance0) {
        entityData.set(enhance,enhance0);
    }

    public void setDropEquipment(boolean b) {
        this.dropEquipment = b;
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        goalSelector.addGoal(2, new BSFGolemTargetNearGoal(this));
        goalSelector.addGoal(3, new BSFGolemRangedAttackGoal(this, 1.0, 30, 50.0F));
        goalSelector.addGoal(4, new BSFGolemFollowOwnerGoal(this, 1.0, 8.0F, 3.0F));
        goalSelector.addGoal(5, new BSFGolemRandomStrollGoal(this, 0.8, 1E-5F));
        goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 5.0F));
        targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        targetSelector.addGoal(3, new BSFGolemHurtByTargetGoal(this));
        targetSelector.addGoal(4, new BSFGolemNearestAttackableTargetGoal(this, LivingEntity.class, 1, true, false, (p) -> getOwner() == null ? p instanceof Player player && !player.isCreative() && !player.isSpectator() : p instanceof Enemy));
    }

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player pPlayer, @NotNull InteractionHand pHand) {
        Level level = level();
        if (!level.isClientSide && pPlayer.equals(getOwner())) {
            ItemStack itemStack = pPlayer.getItemInHand(pHand);
            Item item = itemStack.getItem();
            if (item instanceof SnowballTankItem && getAmmo().isEmpty()) {
                setAmmo(itemStack.copy());
                if (!pPlayer.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }
                playSound(SoundEvents.ARMOR_EQUIP_IRON, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
            } else if ((item instanceof SnowballCannonItem || item instanceof SnowballShotgunItem) && getWeapon().isEmpty()) {
                setWeapon(itemStack.copy());
                if (!pPlayer.getAbilities().instabuild) {
                    if (EnchantmentHelper.getTagEnchantmentLevel(EnchantmentRegister.SNOW_GOLEM_EXCLUSIVE.get(), itemStack) > 0) {
                        itemStack.hurtAndBreak(10, pPlayer, (p) -> p.broadcastBreakEvent(p.getUsedItemHand()));
                    } else {
                        itemStack.shrink(1);
                    }
                }
                playSound(SoundEvents.ARMOR_EQUIP_IRON, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
            } else if (itemStack.isEmpty()) {
                if (pPlayer.isShiftKeyDown()) {
                    if (!getWeapon().isEmpty()) {
                        playSound(SoundEvents.DISPENSER_DISPENSE, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
                    }
                    if (EnchantmentHelper.getTagEnchantmentLevel(EnchantmentRegister.SNOW_GOLEM_EXCLUSIVE.get(), getWeapon()) <= 0) {
                        pPlayer.getInventory().placeItemBackInInventory(getWeapon(), true);
                    }
                    setWeapon(ItemStack.EMPTY);
                } else {
                    if (!getAmmo().isEmpty()) {
                        playSound(SoundEvents.DISPENSER_DISPENSE, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
                    }
                    pPlayer.getInventory().placeItemBackInInventory(getAmmo(), true);
                    setAmmo(ItemStack.EMPTY);
                }
            } else if (item.equals(ItemRegister.SMOOTH_SNOWBALL.get()) || item.equals(Items.POWDER_SNOW_BUCKET) || item.equals(Items.SNOW_BLOCK) || item.equals(Items.ICE)) {
                if (getPotionSickness() == 0) {
                    itemStack.shrink(1);
                    if (item.equals(ItemRegister.SMOOTH_SNOWBALL.get())) {
                        heal(2);
                        setPotionSickness(20);
                        ((ServerLevel) level).sendParticles(ParticleTypes.SNOWFLAKE, this.getX(), this.getEyeY(), this.getZ(), 8, 0, 0, 0, 0.04);
                        playSound(SoundEvents.SNOW_BREAK, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
                    } else if (item.equals(Items.POWDER_SNOW_BUCKET)) {
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
                    pPlayer.displayClientMessage(MutableComponent.create(new TranslatableContents("potionSickness.tip", null, new Object[0])).append(String.valueOf(potionSickness)), false);
                }
            } else if (item.equals(ItemRegister.SNOW_GOLEM_MODE_TWEAKER.get())) {
                CompoundTag tag = itemStack.getOrCreateTag();
                if (tag.getByte("Locator") != getLocator()) {
                    setTarget(null);
                }
                setLocator(tag.getByte("Locator"));
                setStatus(tag.getByte("Status"));
                setOrderedToSit(getStatus() == 0);
                pPlayer.displayClientMessage(MutableComponent.create(new TranslatableContents("import_state.tip", null, new Object[0])), false);
                level.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.DISPENSER_DISPENSE, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
            } else if (item.equals(ItemRegister.TARGET_LOCATOR.get()) && getLocator()==1) {
                Entity entity = ((ServerLevel) level).getEntity(itemStack.getOrCreateTag().getUUID("UUID"));
                if (entity instanceof LivingEntity livingEntity && entity != this) {
                    pPlayer.displayClientMessage(MutableComponent.create(new TranslatableContents("snow_golem_locator_tip", null, new Object[0])), false);
                    setTarget(livingEntity);
                }
                level.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.DISPENSER_DISPENSE, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
            } else if (item instanceof SnowballClampItem snowballClamp) {
                if (snowballClamp.getTier().equals(BSFTiers.EMERALD)) {
                    pPlayer.getInventory().placeItemBackInInventory(ItemRegister.DUCK_SNOWBALL.get().getDefaultInstance(), true);
                } else {
                    pPlayer.getInventory().placeItemBackInInventory(ItemRegister.SMOOTH_SNOWBALL.get().getDefaultInstance(), true);
                }
                itemStack.hurtAndBreak(1, pPlayer, (e) -> e.broadcastBreakEvent(pHand));
            } else if (item.equals(Items.SNOWBALL)) {
                setStyle((byte) ((getStyle() + 1) % STYLE_NUM));
            } else if (item.equals(ItemRegister.CREATIVE_SNOW_GOLEM_TOOL.get())) {
                if (pPlayer.isShiftKeyDown()) {
                    addAdditionalSaveData(itemStack.getOrCreateTag());
                    pPlayer.displayClientMessage(MutableComponent.create(new TranslatableContents("copy.tip", null, new Object[0])), false);
                } else {
                    setEnhance(!getEnhance());
                    pPlayer.displayClientMessage(MutableComponent.create(new TranslatableContents("golem_enhance.tip", null, new Object[0])).append(String.valueOf(enhance)), false);
                }
                level.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.DISPENSER_DISPENSE, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
            } else if (item.equals(ItemRegister.SNOW_GOLEM_CONTAINER.get())) {
                CompoundTag tag = itemStack.getOrCreateTag();
                if (!tag.getBoolean("HasGolem")) {
                    addAdditionalSaveData(tag);
                    tag.putBoolean("HasGolem", true);
                    playSound(SoundEvents.SNOW_BREAK);
                    discard();
                }
            } else if (item instanceof SnowGolemCoreItem && getCore().isEmpty()) {
                ItemStack itemStack1 = itemStack.copy();
                itemStack1.setCount(1);
                setCore(itemStack1);
                resetCoreCoolDown();
                if (!pPlayer.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }
                playSound(SoundEvents.ARMOR_EQUIP_IRON, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
            } else if (item.equals(ItemRegister.SNOW_GOLEM_CORE_REMOVER.get())) {
                if (!getCore().isEmpty()) {
                    playSound(SoundEvents.DISPENSER_DISPENSE, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
                }
                pPlayer.getInventory().placeItemBackInInventory(getCore(), true);
                setCore(ItemStack.EMPTY);
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
            BlockState blockState = Blocks.SNOW.defaultBlockState();
            BlockState blockState1 = BlockRegister.CRITICAL_SNOW.get().defaultBlockState();
            for (int i = 0; i < 4; ++i) {
                int j = Mth.floor(getX() + (double) ((float) (i % 2 * 2 - 1) * 0.25F));
                int k = Mth.floor(getY());
                int l = Mth.floor(getZ() + (double) ((float) (i / 2 % 2 * 2 - 1) * 0.25F));
                BlockPos blockPos1 = new BlockPos(j, k, l);
                if (getCore().getItem().equals(ItemRegister.CRITICAL_SNOW_GOLEM_CORE.get())) {
                    if ((level.isEmptyBlock(blockPos1) || level.getBlockState(blockPos1).equals(blockState)) && blockState1.canSurvive(level, blockPos1)) {
                        level.setBlockAndUpdate(blockPos1, blockState1);
                        level.gameEvent(GameEvent.BLOCK_PLACE, blockPos1, GameEvent.Context.of(this, blockState1));
                    }
                } else {
                    if (level.isEmptyBlock(blockPos1) && blockState.canSurvive(level, blockPos1)) {
                        level.setBlockAndUpdate(blockPos1, blockState);
                        level.gameEvent(GameEvent.BLOCK_PLACE, blockPos1, GameEvent.Context.of(this, blockState));
                    }
                }
            }
        }
    }

    @Override
    public void tick() {
        Level level = level();
        if (!level.isClientSide) {
            setTicksFrozen(0);
            if (getEnhance()) {
                heal(1);
            }
            if (getPotionSickness() > 0) {
                setPotionSickness(getPotionSickness()-1);
            }
            if (getWeaponAng() > 0) {
                setWeaponAng(getWeaponAng() - 60);
            }
            Item item = getCore().getItem();
            if (item.equals(ItemRegister.SWIFTNESS_GOLEM_CORE.get())) {
                addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 2, 0));
            }
            if (getCoreCoolDown() > 0) {
                setCoreCoolDown(getCoreCoolDown()-1);
            } else if (getCoreCoolDown() == 0) {
                if (item.equals(ItemRegister.REGENERATION_GOLEM_CORE.get())) {
                    addEffect(new MobEffectInstance(MobEffects.REGENERATION, 2, 2));
                } else if (item.equals(ItemRegister.REPULSIVE_FIELD_GOLEM_CORE.get()) && getTarget() != null) {
                    LivingEntity target = getTarget();
                    List<Projectile> list1 = level.getEntitiesOfClass(Projectile.class, getBoundingBox().inflate(3), p -> !this.equals(p.getOwner()) && BSFCommonUtil.vec3AngleCos(getTarget().getPosition(0).subtract(getPosition(0)), p.getPosition(0).subtract(getPosition(0))) > 0);
                    List<Projectile> list = level.getEntitiesOfClass(Projectile.class, getBoundingBox().inflate(5), p -> !this.equals(p.getOwner()) && BSFCommonUtil.vec3AngleCos(getTarget().getPosition(0).subtract(getPosition(0)), p.getPosition(0).subtract(getPosition(0))) > 0);
                    if (!list1.isEmpty()) {
                        for (Projectile projectile : list) {
                            Vec3 vec3 = projectile.getDeltaMovement();
                            double v2 = vec3.lengthSqr();
                            double sin2Phi = vec3.y * vec3.y / v2;
                            double cosPhi = Math.sqrt(1 - sin2Phi);
                            double theta = Mth.atan2(target.getZ() - getZ(), target.getX() - getX());
                            double sinTheta = Mth.sin((float) theta);
                            double cosTheta = Mth.cos((float) theta);
                            double v = vec3.length();
                            Vec3 vec31 = new Vec3(v * cosTheta * cosPhi, -vec3.y, v * sinTheta * cosPhi);
                            projectile.push(vec31.x - vec3.x, vec31.y - vec3.y, vec31.z - vec3.z);
                            ((ServerLevel) level).sendParticles(ParticleRegister.GENERATOR_PUSH.get(), projectile.getX(), projectile.getY(), projectile.getZ(), 1, 0, 0, 0, 0);
                        }
                        playSound(SoundRegister.FIELD_PUSH.get(), 0.5F, 1.0F / (getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
                        resetCoreCoolDown();
                    }
                }
            }
        }
        super.tick();
    }

    @Override
    public boolean hurt(@NotNull DamageSource pSource, float pAmount) {
        Level level = level();
        if (!level.isClientSide) {
            Item item = getCore().getItem();
            if (item.equals(ItemRegister.REGENERATION_GOLEM_CORE.get())) {
                resetCoreCoolDown();
            } else if (pSource.getDirectEntity() instanceof Projectile && item.equals(ItemRegister.ENDER_TELEPORTATION_GOLEM_CORE.get()) && getCoreCoolDown() == 0 && (getStatus() == 2 || getStatus() == 3)) {
                Vec3 vec3 = getRandomTeleportPos();
                if (vec3 != null) {
                    tpWithParticlesAndResetCD(vec3);
                    return false;
                }
            }
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    public void performRangedAttack(@NotNull LivingEntity pTarget, float pDistanceFactor) {
        Level level = level();
        ItemStack weapon = getWeapon();
        ItemStack ammo = getAmmo();
        CompoundTag compoundTag = ammo.getOrCreateTag();
        AbstractBSFWeaponItem weaponItem = (AbstractBSFWeaponItem) weapon.getItem();
        if (!compoundTag.contains("Snowball") || (((AbstractBSFSnowballItem) ForgeRegistries.ITEMS.getValue(new ResourceLocation(Main.MODID, compoundTag.getString("Snowball")))).getTypeFlag() & weaponItem.getTypeFlag()) == 0) {
            return;
        }
        float damageChance = 1.0F / (1.0F + EnchantmentHelper.getTagEnchantmentLevel(Enchantments.UNBREAKING, weapon));
        ILaunchAdjustment launchAdjustment = weaponItem.getLaunchAdjustment(1, ammo.getItem());
        int j = weapon.getItem() instanceof SnowballShotgunItem ? 4 : 1;
        for (int i = 0; i < j; i++) {
            if (!compoundTag.contains("Snowball")) {
                break;
            }
            AbstractBSFSnowballEntity snowball = ((AbstractBSFSnowballItem) ForgeRegistries.ITEMS.getValue(new ResourceLocation(Main.MODID, compoundTag.getString("Snowball")))).getCorrespondingEntity(level, this, launchAdjustment);
            snowball.shoot(shootX, shootY, shootZ, launchVelocity, launchAccuracy);
            level.addFreshEntity(snowball);
            if (!getEnhance()) {
                ammo.setDamageValue(ammo.getDamageValue() + 1);
                if (ammo.getDamageValue() == ammo.getMaxDamage()) {
                    ItemStack empty;
                    if (ammo.getItem() instanceof LargeSnowballTankItem) {
                        empty = ItemRegister.LARGE_SNOWBALL_TANK.get().getDefaultInstance();
                    } else {
                        empty = ItemRegister.SNOWBALL_TANK.get().getDefaultInstance();
                    }
                    empty.setDamageValue(empty.getMaxDamage());
                    setAmmo(empty);
                }
            }
            if (i == 0) {
                int aStep = 90;
                if (weaponItem.equals(ItemRegister.POWERFUL_SNOWBALL_CANNON.get()) || weaponItem.equals(ItemRegister.SNOWBALL_SHOTGUN.get())) {
                    aStep = 45;
                }
                NetworkRegister.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY.with(() -> this), new ForwardConeParticlesToClient(getEyePosition(), new Vec3(shootX, shootY, shootZ), 4.5F, aStep, 1.5F, 0.1F, BSFParticleType.SNOWFLAKE.ordinal()));
                playSound(j == 4 ? SoundRegister.SHOTGUN_FIRE_2.get() : SoundRegister.SNOWBALL_CANNON_SHOOT.get(), 1.0F, 1.0F / (getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
                if (getRandom().nextFloat() <= damageChance && !getEnhance()) {
                    weapon.setDamageValue(weapon.getDamageValue() + 1);
                    if (weapon.getDamageValue() == 256) {
                        setWeapon(ItemStack.EMPTY);
                        playSound(SoundEvents.ITEM_BREAK, 1.0F, 1.0F / (getRandom().nextFloat() * 0.4F + 0.8F));
                    }
                }
                setWeaponAng(360);
            }
        }
    }

    @Override
    public void die(@NotNull DamageSource pCause) {
        super.die(pCause);
        if (level().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
            if (dropEquipment) {
                if (!EnchantmentHelper.hasVanishingCurse(getWeapon()) && EnchantmentHelper.getTagEnchantmentLevel(EnchantmentRegister.SNOW_GOLEM_EXCLUSIVE.get(), getWeapon()) <= 0) {
                    spawnAtLocation(getWeapon());
                }
                if (!EnchantmentHelper.hasVanishingCurse(getAmmo())) {
                    spawnAtLocation(getAmmo());
                }
                spawnAtLocation(getCore());
            }
            spawnAtLocation(new ItemStack(Items.SNOWBALL, getRandom().nextInt(0, 16)));
        }
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

    @Override
    protected float getBlockSpeedFactor() {
        if (level().getBlockState(new BlockPos(BSFCommonUtil.vec3ToI(getPosition(0)))).getBlock().equals(BlockRegister.CRITICAL_SNOW.get())) {
            return 1.0F;
        }
        return super.getBlockSpeedFactor();
    }

    @Override
    protected float getBlockJumpFactor() {
        if (level().getBlockState(new BlockPos(BSFCommonUtil.vec3ToI(getPosition(0)))).getBlock().equals(BlockRegister.CRITICAL_SNOW.get())) {
            return 1.0F;
        }
        return super.getBlockJumpFactor();
    }

    @Override
    public boolean wantsToAttack(@NotNull LivingEntity pTarget, @NotNull LivingEntity pOwner) {
        return !(pTarget instanceof OwnableEntity ownableEntity && pOwner.equals(ownableEntity.getOwner()));
    }

    public void resetCoreCoolDown() {
        setCoreCoolDown(((SnowGolemCoreItem) getCore().getItem()).getCoolDown());
    }

    public Vec3 getRandomTeleportPos() {
        Level level = level();
        RandomSource randomSource = getRandom();
        float initTheta = (float) BSFCommonUtil.randDouble(randomSource, 0, 2 * Mth.PI);
        double golemX = getX();
        double golemY = getY();
        double golemZ = getZ();
        boolean clockwise = randomSource.nextInt(0, 2) == 0;
        for (int r = 20; r >= 4; r--) {
            float step = 1.0F / r;
            for (float theta = 0; theta < 2 * Mth.PI; theta += step) {
                float theta1 = clockwise ? theta : -theta;
                for (float phi = 0; phi <= Mth.PI * 0.5; phi += step) {
                    int x = Mth.floor(golemX + r * Mth.cos(initTheta + theta1) * Mth.cos(phi));
                    int y = Mth.floor(golemY + r * Mth.sin(phi));
                    int y1 = Mth.floor(golemY - r * Mth.sin(phi));
                    int z = Mth.floor(golemZ + r * Mth.sin(initTheta + theta1) * Mth.cos(phi));
                    BlockPos blockPos = new BlockPos(x, y, z);
                    if (canStandOn(blockPos, level)) {
                        return new Vec3(x, y, z);
                    }
                    blockPos = new BlockPos(x, y1, z);
                    if (canStandOn(blockPos, level)) {
                        return new Vec3(x, y1, z);
                    }
                }
            }
        }
        return null;
    }

    public boolean canStandOn(BlockPos blockPos, Level level) {
        return level.getBlockState(blockPos).getCollisionShape(level, blockPos).isEmpty() &&
                level.getBlockState(blockPos.above()).getCollisionShape(level, blockPos.above()).isEmpty() &&
                level.getBlockState(blockPos.below()).blocksMotion();
    }

    public void tpWithParticlesAndResetCD(Vec3 vec3) {
        AABB aabb = getBoundingBox();
        Vec3 center = aabb.getCenter();
        double x = 0.5 * (aabb.maxX - aabb.minX);
        double y = 0.5 * (aabb.maxY - aabb.minY);
        ((ServerLevel) level()).sendParticles(ParticleTypes.PORTAL, center.x, center.y, center.z, 81, x, y, x, 0);
        teleportTo(vec3.x, vec3.y, vec3.z);
        playSound(SoundEvents.ENDERMAN_TELEPORT);
        resetCoreCoolDown();
    }
}
