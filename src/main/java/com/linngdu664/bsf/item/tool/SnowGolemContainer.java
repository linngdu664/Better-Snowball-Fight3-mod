package com.linngdu664.bsf.item.tool;

import com.linngdu664.bsf.entity.BSFSnowGolemEntity;
import com.linngdu664.bsf.registry.EntityRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class SnowGolemContainer extends Item {
    public SnowGolemContainer() {
        super(new Properties().rarity(Rarity.UNCOMMON).stacksTo(1));
    }

    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        ItemStack itemStack = pContext.getItemInHand();
        CompoundTag tag = itemStack.getOrCreateTag();
        if (tag.getBoolean("HasGolem")) {
            if (!level.isClientSide) {
                byte status = tag.getByte("Status");
                BSFSnowGolemEntity snowGolem = EntityRegister.BSF_SNOW_GOLEM.get().create(level);
                snowGolem.setTame(true);
                snowGolem.setOwnerUUID(pContext.getPlayer().getUUID());
                snowGolem.setOrderedToSit(status == 0);
                snowGolem.setStatus(status);
                snowGolem.setUseLocator(tag.getBoolean("UseLocator"));
                snowGolem.setAmmo(ItemStack.of(tag.getCompound("Ammo")));
                snowGolem.setWeapon(ItemStack.of(tag.getCompound("Weapon")));
                snowGolem.setEnhance(tag.getBoolean("Enhance"));
                snowGolem.setStyle(tag.getByte("Style"));
                BlockPos blockPos = pContext.getClickedPos();
                snowGolem.moveTo(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5, 0.0F, 0.0F);
                level.addFreshEntity(snowGolem);
                if (tag.contains("UUID") && ((ServerLevel) level).getEntity(tag.getUUID("UUID")) instanceof LivingEntity livingEntity) {
                    snowGolem.setTarget(livingEntity);
                }
                tag.putBoolean("HasGolem", false);
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}
