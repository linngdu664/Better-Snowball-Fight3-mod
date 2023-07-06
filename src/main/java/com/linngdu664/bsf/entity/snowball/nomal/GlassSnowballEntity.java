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

public class GlassSnowballEntity extends BSFSnowballEntity {
    public GlassSnowballEntity(LivingEntity livingEntity, Level level, LaunchFunc launchFunc) {
        super(livingEntity, level);
        this.setLaunchFrom(launchFunc.getLaunchFrom()).setDamage(3).setBlazeDamage(5);
        launchFunc.launchProperties(this);
        this.setItem(new ItemStack(ItemRegister.GLASS_SNOWBALL.get()));
    }

    //This is only used for dispenser
    public GlassSnowballEntity(Level level, double x, double y, double z) {
        super(level, x, y, z);
        this.setDamage(3).setBlazeDamage(5);
        this.setItem(new ItemStack(ItemRegister.GLASS_SNOWBALL.get()));
    }

    @Override
    public Item getCorrespondingItem() {
        return ItemRegister.GLASS_SNOWBALL.get();
    }

    @Override
    protected void onHit(@NotNull HitResult pResult) {
        super.onHit(pResult);
        if (!level().isClientSide) {
            this.discard();
        }
    }

    public float getPower() {
        return 1.2f;
    }
}
