package com.linngdu664.bsf.item.misc;

import com.linngdu664.bsf.client.model.SnowFallBootsModel;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class SnowFallBootsItem extends ArmorItem {
    public SnowFallBootsItem() {
        super(new ArmorMaterial() {
            @Override
            public int getDurabilityForType(@NotNull Type p_266807_) {
                return 810;
            }

            @Override
            public int getDefenseForType(@NotNull Type p_267168_) {
                return 1;
            }

            @Override
            public int getEnchantmentValue() {
                return 17;
            }

            @Override
            public @NotNull SoundEvent getEquipSound() {
                return SoundEvents.ARMOR_EQUIP_LEATHER;
            }

            @Override
            public @NotNull Ingredient getRepairIngredient() {
                return Ingredient.EMPTY;
            }

            @Override
            public @NotNull String getName() {
                return "snow_fall_boots";
            }

            @Override
            public float getToughness() {
                return 0;
            }

            @Override
            public float getKnockbackResistance() {
                return 0;
            }
        }, Type.BOOTS, new Properties().rarity(Rarity.UNCOMMON).stacksTo(1));
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "bsf:textures/models/armor/snow_fall_boots.png";
    }

    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                HumanoidModel armorModel = new HumanoidModel(new ModelPart(Collections.emptyList(), Map.of(
                        "left_leg", new SnowFallBootsModel(Minecraft.getInstance().getEntityModels().bakeLayer(SnowFallBootsModel.LAYER_LOCATION)).bone,
                        "right_leg", new SnowFallBootsModel(Minecraft.getInstance().getEntityModels().bakeLayer(SnowFallBootsModel.LAYER_LOCATION)).bone,
                        "head", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                        "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                        "body", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                        "right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                        "left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()))));
                armorModel.crouching = livingEntity.isShiftKeyDown();
                armorModel.riding = original.riding;
                armorModel.young = livingEntity.isBaby();
                return armorModel;
            }
        });
    }

    @Override
    public boolean isValidRepairItem(@NotNull ItemStack pStack, ItemStack pRepairCandidate) {
        return pRepairCandidate.is(Items.LEATHER_BOOTS);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("snow_fall_boots1.tooltip", null, new Object[0])).withStyle(ChatFormatting.BLUE));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("snow_fall_boots.tooltip", null, new Object[0])).withStyle(ChatFormatting.DARK_AQUA));
    }
}
