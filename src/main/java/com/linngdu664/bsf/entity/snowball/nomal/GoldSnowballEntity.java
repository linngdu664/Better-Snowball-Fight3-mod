package com.linngdu664.bsf.entity.snowball.nomal;

import com.linngdu664.bsf.entity.BSFSnowballEntity;
import com.linngdu664.bsf.item.ItemRegister;
import com.linngdu664.bsf.util.LaunchFunc;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class GoldSnowballEntity extends BSFSnowballEntity {
    public GoldSnowballEntity(LivingEntity livingEntity, Level level, LaunchFunc launchFunc) {
        super(livingEntity, level);
        this.setLaunchFrom(launchFunc.getLaunchFrom()).setDamage(5).setBlazeDamage(7);
        launchFunc.launchProperties(this);
        this.setItem(new ItemStack(ItemRegister.GOLD_SNOWBALL.get()));
    }

    //This is only used for dispenser
    public GoldSnowballEntity(Level level, double x, double y, double z) {
        super(level, x, y, z);
        this.setDamage(5).setBlazeDamage(7);
        this.setItem(new ItemStack(ItemRegister.GOLD_SNOWBALL.get()));
    }

    @Override
    public Item getCorrespondingItem() {
        return ItemRegister.GOLD_SNOWBALL.get();
    }

    @Override
    protected void onHit(@NotNull HitResult pResult) {
        super.onHit(pResult);
        if (!level().isClientSide) {
            this.discard();
        }
    }

    public float getPower() {
        return 1.5f;
    }
}
