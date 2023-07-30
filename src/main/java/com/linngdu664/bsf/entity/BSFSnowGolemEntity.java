package com.linngdu664.bsf.entity;

import com.linngdu664.bsf.Main;
import com.linngdu664.bsf.entity.ai.goal.*;
import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.item.snowball.AbstractBSFSnowballItem;
import com.linngdu664.bsf.item.snowball.normal.SmoothSnowballItem;
import com.linngdu664.bsf.item.tank.LargeSnowballTankItem;
import com.linngdu664.bsf.item.tank.SnowballTankItem;
import com.linngdu664.bsf.item.tool.CreativeSnowGolemToolItem;
import com.linngdu664.bsf.item.tool.SnowGolemModeTweakerItem;
import com.linngdu664.bsf.item.tool.SnowballClampItem;
import com.linngdu664.bsf.item.weapon.AbstractBSFWeaponItem;
import com.linngdu664.bsf.item.weapon.SnowballCannonItem;
import com.linngdu664.bsf.item.weapon.SnowballShotgunItem;
import com.linngdu664.bsf.item.weapon.TargetLocatorItem;
import com.linngdu664.bsf.network.ForwardConeParticlesToClient;
import com.linngdu664.bsf.registry.EnchantmentRegister;
import com.linngdu664.bsf.registry.ItemRegister;
import com.linngdu664.bsf.registry.NetworkRegister;
import com.linngdu664.bsf.registry.SoundRegister;
import com.linngdu664.bsf.util.BSFMthUtil;
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
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class BSFSnowGolemEntity extends TamableAnimal implements RangedAttackMob {
    private static final int STYLE_NUM = 9;
    private static final EntityDataAccessor<ItemStack> WEAPON = SynchedEntityData.defineId(BSFSnowGolemEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<ItemStack> AMMO = SynchedEntityData.defineId(BSFSnowGolemEntity.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<Integer> WEAPON_ANG = SynchedEntityData.defineId(BSFSnowGolemEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Byte> STYLE = SynchedEntityData.defineId(BSFSnowGolemEntity.class, EntityDataSerializers.BYTE);
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
    private byte statusFlag;
    private boolean useLocator;
    private boolean enhance;
    private int potionSickness;

    public BSFSnowGolemEntity(EntityType<? extends TamableAnimal> p_21803_, Level p_21804_) {
        super(p_21803_, p_21804_);
    }

    public static AttributeSupplier setAttributes() {
        return TamableAnimal.createLivingAttributes().add(Attributes.MAX_HEALTH, 15.0).add(Attributes.FOLLOW_RANGE, 50.0).add(Attributes.MOVEMENT_SPEED, 0.3).build();
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(WEAPON, ItemStack.EMPTY);
        entityData.define(AMMO, ItemStack.EMPTY);
        entityData.define(WEAPON_ANG, 0);
        entityData.define(STYLE, (byte) (BSFMthUtil.randInt(0, STYLE_NUM)));
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putByte("Status", statusFlag);
        pCompound.putBoolean("UseLocator", useLocator);
        CompoundTag compoundTag = new CompoundTag();
        getWeapon().save(compoundTag);
        pCompound.put("Weapon", compoundTag);
        compoundTag = new CompoundTag();
        getAmmo().save(compoundTag);
        pCompound.put("Ammo", compoundTag);
        pCompound.putInt("WeaponAng", getWeaponAng());
        pCompound.putInt("Style", getStyle());
        pCompound.putBoolean("Enhance", enhance);
        pCompound.putInt("PotionSickness", potionSickness);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        statusFlag = pCompound.getByte("Status");
        useLocator = pCompound.getBoolean("UseLocator");
        setWeapon(ItemStack.of(pCompound.getCompound("Weapon")));
        setAmmo(ItemStack.of(pCompound.getCompound("Ammo")));
        setWeaponAng(pCompound.getInt("WeaponAng"));
        setStyle(pCompound.getByte("Style"));
        enhance = pCompound.getBoolean("Enhance");
        potionSickness = pCompound.getInt("PotionSickness");
    }

    public byte getStatus() {
        return statusFlag;
    }

    public void setStatus(byte status) {
        this.statusFlag = status;
    }

    public boolean isUseLocator() {
        return useLocator;
    }

    public void setUseLocator(boolean useLocator) {
        this.useLocator = useLocator;
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

    public void setEnhance(boolean enhance) {
        this.enhance = enhance;
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

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        goalSelector.addGoal(2, new BSFGolemJumpHighGoal(this));
        goalSelector.addGoal(3, new BSFGolemRangedAttackGoal(this, 1.0, 30, 50.0F));
        goalSelector.addGoal(4, new BSFGolemFollowOwnerGoal(this, 1.0, 8.0F, 3.0F));
        goalSelector.addGoal(5, new BSFGolemRandomStrollGoal(this, 0.8, 1E-5F));
        goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 5.0F));
        targetSelector.addGoal(1, new BSFGolemHurtByTargetGoal(this));
        targetSelector.addGoal(2, new BSFGolemNearestAttackableTargetGoal(this, LivingEntity.class, 1, true, false, (p) -> getOwner() == null ? p instanceof Player player && !player.isCreative() && !player.isSpectator() : p instanceof Enemy));
    }

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player pPlayer, @NotNull InteractionHand pHand) {
        Level level = level();
        if (!level.isClientSide && pPlayer.equals(getOwner())) {
            ItemStack itemStack = pPlayer.getItemInHand(pHand);
            if (itemStack.getItem() instanceof SnowballTankItem && getAmmo().isEmpty()) {
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
                if (potionSickness == 0) {
                    Item item = itemStack.getItem();
                    itemStack.shrink(1);
                    if (item instanceof SmoothSnowballItem) {
                        heal(2);
                        potionSickness = 20;
                        ((ServerLevel) level).sendParticles(ParticleTypes.SNOWFLAKE, this.getX(), this.getEyeY(), this.getZ(), 8, 0, 0, 0, 0.04);
                        playSound(SoundEvents.SNOW_BREAK, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
                    } else if (item instanceof SolidBucketItem) {
                        pPlayer.getInventory().placeItemBackInInventory(new ItemStack(Items.BUCKET, 1), true);
                        heal(8);
                        potionSickness = 100;
                        ((ServerLevel) level).sendParticles(ParticleTypes.SNOWFLAKE, this.getX(), this.getEyeY(), this.getZ(), 24, 0, 0, 0, 0.04);
                        playSound(SoundEvents.SNOW_BREAK, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
                    } else if (item.equals(Items.SNOW_BLOCK)) {
                        heal(5);
                        potionSickness = 60;
                        ((ServerLevel) level).sendParticles(ParticleTypes.SNOWFLAKE, this.getX(), this.getEyeY(), this.getZ(), 16, 0, 0, 0, 0.04);
                        playSound(SoundEvents.SNOW_BREAK, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
                    } else {
                        addEffect(new MobEffectInstance(MobEffects.REGENERATION, 600, 1));
                        potionSickness = 60;
                        ((ServerLevel) level).sendParticles(ParticleTypes.ITEM_SNOWBALL, this.getX(), this.getEyeY(), this.getZ(), 16, 0, 0, 0, 0.04);
                        playSound(SoundEvents.GLASS_BREAK, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
                    }
                } else {
                    pPlayer.displayClientMessage(MutableComponent.create(new TranslatableContents("potionSickness.tip", null, new Object[0])).append(String.valueOf(potionSickness)), false);
                }
            } else if (itemStack.getItem() instanceof SnowGolemModeTweakerItem) {
                CompoundTag tag = itemStack.getOrCreateTag();
                if (tag.getBoolean("UseLocator") != useLocator) {
                    setTarget(null);
                }
                useLocator = tag.getBoolean("UseLocator");
                statusFlag = tag.getByte("Status");
                setOrderedToSit(statusFlag == 0);
                pPlayer.displayClientMessage(MutableComponent.create(new TranslatableContents("import_state.tip", null, new Object[0])), false);
                level.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.DISPENSER_DISPENSE, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
            } else if (itemStack.getItem() instanceof TargetLocatorItem && useLocator) {
                Entity entity = ((ServerLevel) level).getEntity(itemStack.getOrCreateTag().getUUID("UUID"));
                if (entity instanceof LivingEntity livingEntity && entity != this) {
                    pPlayer.displayClientMessage(MutableComponent.create(new TranslatableContents("snow_golem_locator_tip", null, new Object[0])), false);
                    setTarget(livingEntity);
                }
                level.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.DISPENSER_DISPENSE, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
            } else if (itemStack.getItem() instanceof SnowballClampItem snowballClamp) {
                if (snowballClamp.getTier().equals(BSFTiers.EMERALD)) {
                    pPlayer.getInventory().placeItemBackInInventory(ItemRegister.DUCK_SNOWBALL.get().getDefaultInstance(), true);
                } else {
                    pPlayer.getInventory().placeItemBackInInventory(ItemRegister.SMOOTH_SNOWBALL.get().getDefaultInstance(), true);
                }
                itemStack.hurtAndBreak(1, pPlayer, (e) -> e.broadcastBreakEvent(pHand));
            } else if (itemStack.getItem() instanceof SnowballItem) {
                setStyle((byte) ((getStyle() + 1) % STYLE_NUM));
            } else if (itemStack.getItem() instanceof CreativeSnowGolemToolItem) {
                if (pPlayer.isShiftKeyDown()) {
                    CompoundTag tag = itemStack.getOrCreateTag();
                    CompoundTag tag1 = new CompoundTag();
                    getAmmo().save(tag1);
                    tag.put("Ammo", tag1);
                    tag1 = new CompoundTag();
                    getWeapon().save(tag1);
                    tag.put("Weapon", tag1);
                    tag.putBoolean("Enhance", enhance);
                    tag.putBoolean("UseLocator", useLocator);
                    tag.putByte("Status", statusFlag);
                    tag.putByte("Style", getStyle());
                    if (getTarget() != null) {
                        tag.putUUID("UUID", getTarget().getUUID());
                    } else {
                        tag.remove("UUID");
                    }
                    pPlayer.displayClientMessage(MutableComponent.create(new TranslatableContents("copy.tip", null, new Object[0])), false);
                } else {
                    enhance = !enhance;
                    pPlayer.displayClientMessage(MutableComponent.create(new TranslatableContents("golem_enhance.tip", null, new Object[0])).append(String.valueOf(enhance)), false);
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
            for (int i = 0; i < 4; ++i) {
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
        if (!level().isClientSide) {
            if (enhance) {
                heal(1);
            }
            if (potionSickness > 0) {
                potionSickness--;
            }
            if (getWeaponAng() > 0) {
                setWeaponAng(getWeaponAng() - 60);
            }
        }
        super.tick();
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
            if (!enhance) {
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
                NetworkRegister.PACKET_HANDLER.send(PacketDistributor.TRACKING_ENTITY.with(() -> this), new ForwardConeParticlesToClient(getEyePosition(), new Vec3(shootX, shootY, shootZ), 4.5F, aStep, 1.5F, 0.1F));
                playSound(j == 4 ? SoundRegister.SHOTGUN_FIRE_2.get() : SoundRegister.SNOWBALL_CANNON_SHOOT.get(), 1.0F, 1.0F / (getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
                if (getRandom().nextFloat() <= damageChance && !enhance) {
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
