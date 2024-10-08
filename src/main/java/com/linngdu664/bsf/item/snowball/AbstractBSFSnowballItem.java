package com.linngdu664.bsf.item.snowball;

import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.snowball.util.ILaunchAdjustment;
import com.linngdu664.bsf.entity.snowball.util.LaunchFrom;
import com.linngdu664.bsf.item.tank.SnowballTankItem;
import com.linngdu664.bsf.item.weapon.SnowballCannonItem;
import com.linngdu664.bsf.item.weapon.SnowballMachineGunItem;
import com.linngdu664.bsf.item.weapon.SnowballShotgunItem;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class AbstractBSFSnowballItem extends Item {
    public static final int HAND_TYPE_FLAG = 1;
    private final SnowballProperties snowballProperties;
//    private final int id;
//    private final double machineGunRecoil;
//    private final double shotgunPushRank;

    public AbstractBSFSnowballItem(Rarity rarity, SnowballProperties snowballProperties) {
        super(new Properties().stacksTo(16).rarity(rarity));
        this.snowballProperties = snowballProperties;
    }

//    public AbstractBSFSnowballItem(Rarity rarity, int id) {
//        super(new Properties().stacksTo(16).rarity(rarity));
//        this.id = id;
//    }
//
//    public AbstractBSFSnowballItem(Rarity rarity, int id, double machineGunRecoil) {
//        super(new Properties().stacksTo(16).rarity(rarity));
//        this.id = id;
//    }

    public ILaunchAdjustment getLaunchAdjustment(float playerBadEffectRate) {
        return new ILaunchAdjustment() {
            @Override
            public double adjustPunch(double punch) {
                return punch;
            }

            @Override
            public int adjustWeaknessTicks(int weaknessTicks) {
                return weaknessTicks;
            }

            @Override
            public int adjustFrozenTicks(int frozenTicks) {
                return frozenTicks;
            }

            @Override
            public float adjustDamage(float damage) {
                return damage * playerBadEffectRate;
            }

            @Override
            public float adjustBlazeDamage(float blazeDamage) {
                return blazeDamage * playerBadEffectRate;
            }

            @Override
            public LaunchFrom getLaunchFrom() {
                return LaunchFrom.HAND;
            }
        };
    }

    /**
     * Handle the storage of the snowballs.
     *
     * @param pPlayer The player who uses snowball.
     * @return If the method stores snowballs in the tank successfully, it will return true, else return false.
     */
    public boolean storageInTank(Player pPlayer) {
        ItemStack offhand = pPlayer.getOffhandItem();
        ItemStack mainHand = pPlayer.getMainHandItem();
        int count = mainHand.getCount();
        if (offhand.getItem() instanceof SnowballTankItem) {
            CompoundTag compoundTag = offhand.getOrCreateTag();
            String path = ForgeRegistries.ITEMS.getKey(this).getPath();
            int offHandDamage = offhand.getDamageValue();
            int offHandMaxDamage = offhand.getMaxDamage();
            if ((path.equals(compoundTag.getString("Snowball")) && offHandDamage != 0) || offHandDamage == offHandMaxDamage) {
                if (offHandDamage == offHandMaxDamage) {
                    compoundTag.putString("Snowball", path);
                }
                if (offHandDamage < count) {
                    if (!pPlayer.getAbilities().instabuild) {
                        mainHand.shrink(offHandDamage);
                    }
                    offhand.setDamageValue(0);
                } else {
                    if (!pPlayer.getAbilities().instabuild) {
                        mainHand.shrink(count);
                    }
                    offhand.setDamageValue(offHandDamage - count);
                }
                return true;
            }
        }
        return false;
    }

    public InteractionResultHolder<ItemStack> throwOrStorage(Player pPlayer, Level pLevel, InteractionHand pUsedHand, float velocity, int coolDown) {
        ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
        if (!storageInTank(pPlayer)) {
            pLevel.playSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (pLevel.getRandom().nextFloat() * 0.4F + 0.8F));
            if (!pLevel.isClientSide) {
                AbstractBSFSnowballEntity snowballEntity = getCorrespondingEntity(pLevel, pPlayer, getLaunchAdjustment(getSnowballDamageRate(pPlayer)));
                snowballEntity.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, velocity * getSnowballSlowdownRate(pPlayer), 1.0F);
                pLevel.addFreshEntity(snowballEntity);
            }
            if (!pPlayer.getAbilities().instabuild) {
                itemStack.shrink(1);
                if (coolDown != 0) {
                    pPlayer.getCooldowns().addCooldown(this, coolDown);
                }
            }
        }
        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemStack, pLevel.isClientSide());
    }

    // 1.005^(-ticks)
    public float getSnowballSlowdownRate(Player player) {
        return (float) Math.exp(-0.005 * player.getTicksFrozen());
    }

    public float getSnowballDamageRate(Player player) {
        float reDamageRate = 1;
        if (player.hasEffect(MobEffects.WEAKNESS)) {
            reDamageRate -= switch (player.getEffect(MobEffects.WEAKNESS).getAmplifier()) {
                case 0 -> 0.25f;
                case 1 -> 0.5f;
                default -> 0.75f;
            };
        }
        if (player.hasEffect(MobEffects.DAMAGE_BOOST)) {
            if (player.getEffect(MobEffects.DAMAGE_BOOST).getAmplifier() == 0) {
                reDamageRate += 0.15F;
            } else {
                reDamageRate += 0.3F;
            }
        }
        return reDamageRate;
    }

    public void generateWeaponTips(List<Component> pTooltipComponents) {
        int typeFlag = getTypeFlag();
        if ((typeFlag & HAND_TYPE_FLAG) == 0) {
            pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_no_hand.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_RED));
        } else {
            pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_yes_hand.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_GREEN));
        }
        if ((typeFlag & SnowballCannonItem.TYPE_FLAG) == 0) {
            pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_no_cannon.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_RED));
        } else {
            pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_yes_cannon.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_GREEN));
        }
        if ((typeFlag & SnowballMachineGunItem.TYPE_FLAG) == 0) {
            pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_no_machine_gun.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_RED));
        } else {
            pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_yes_machine_gun.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_GREEN));
        }
        if ((typeFlag & SnowballShotgunItem.TYPE_FLAG) == 0) {
            pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_no_shotgun.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_RED));
        } else {
            pTooltipComponents.add(MutableComponent.create(new TranslatableContents("lunch_yes_shotgun.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_GREEN));
        }
    }

    public void addUsageTips(List<Component> pTooltipComponents) {
    }

    public void addLastTips(List<Component> pTooltipComponents) {
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        generateWeaponTips(pTooltipComponents);
        addUsageTips(pTooltipComponents);
        addLastTips(pTooltipComponents);
    }

    /**
     * You must override this fucking method if you want to launch the snowball by weapons.
     *
     * @param level        Level.
     * @param livingEntity The entity who throws/launches the snowball.
     * @return The corresponding entity.
     */
    public AbstractBSFSnowballEntity getCorrespondingEntity(Level level, LivingEntity livingEntity, ILaunchAdjustment launchAdjustment) {
        return null;
    }

    public final int getIdForTank() {
        return snowballProperties.idForTank;
    }

    public final int getTypeFlag() {
        return snowballProperties.allowLaunchTypeFlag;
    }

    public final double getMachineGunRecoil() {
        return snowballProperties.machineGunRecoil;
//        return 0.075;
    }

    public final double getShotgunPushRank() {
        return snowballProperties.shotgunPushRank;
//        return 0.1;
    }

    public static class SnowballProperties {
        int idForTank;
        int allowLaunchTypeFlag;
        double machineGunRecoil;
        double shotgunPushRank;

        public SnowballProperties() {
            idForTank = -1;
            allowLaunchTypeFlag = 0;
            machineGunRecoil = 0.075;
            shotgunPushRank = 0.1;
        }

        public SnowballProperties idForTank(int idForTank) {
            this.idForTank = idForTank;
            return this;
        }

        public SnowballProperties allowLaunchTypeFlag(int allowLaunchTypeFlag) {
            this.allowLaunchTypeFlag = allowLaunchTypeFlag;
            return this;
        }

        public SnowballProperties machineGunRecoil(double machineGunRecoil) {
            this.machineGunRecoil = machineGunRecoil;
            return this;
        }

        public SnowballProperties shotgunPushRank(double shotgunPushRank) {
            this.shotgunPushRank = shotgunPushRank;
            return this;
        }
    }
}
