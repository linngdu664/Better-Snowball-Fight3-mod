package com.linngdu664.bsf.item.tool;

import com.linngdu664.bsf.entity.BSFSnowGolemEntity;
import com.linngdu664.bsf.registry.EntityRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CreativeSnowGolemToolItem extends Item {
    public CreativeSnowGolemToolItem() {
        super(new Properties().rarity(Rarity.EPIC).stacksTo(1));
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        if (!pContext.getLevel().isClientSide) {
            ItemStack itemStack = pContext.getItemInHand();
            CompoundTag tag = itemStack.getOrCreateTag();
            ItemStack ammo = ItemStack.of(tag.getCompound("Ammo"));
            ItemStack weapon = ItemStack.of(tag.getCompound("Weapon"));
            boolean useLocator = tag.getBoolean("UseLocator");
            boolean enhance = tag.getBoolean("Enhance");
            byte status = tag.getByte("Status");
            byte style = tag.getByte("Style");
            Level level = pContext.getLevel();
            BSFSnowGolemEntity snowGolem = EntityRegister.BSF_SNOW_GOLEM.get().create(level);
            snowGolem.setTame(true);
            snowGolem.setOwnerUUID(pContext.getPlayer().getUUID());
            snowGolem.setOrderedToSit(status == 0);
            snowGolem.setStatus(status);
            snowGolem.setUseLocator(useLocator);
            snowGolem.setAmmo(ammo);
            snowGolem.setWeapon(weapon);
            snowGolem.setEnhance(enhance);
            snowGolem.setStyle(style);
            BlockPos blockPos = pContext.getClickedPos();
            snowGolem.moveTo(blockPos.getX() + 0.5D, blockPos.getY() + 1, blockPos.getZ() + 0.5D, 0.0F, 0.0F);
            level.addFreshEntity(snowGolem);
            if (tag.contains("ID")) {
                snowGolem.setTarget((LivingEntity) level.getEntity(tag.getInt("ID")));
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("creative_snow_golem_tool.tooltip", null, new Object[0])).withStyle(ChatFormatting.BLUE));
        pTooltipComponents.add(MutableComponent.create(new TranslatableContents("creative_snow_golem_tool1.tooltip", null, new Object[0])).withStyle(ChatFormatting.BLUE));
    }
}
