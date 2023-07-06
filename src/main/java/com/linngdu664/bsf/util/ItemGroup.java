package com.linngdu664.bsf.util;

import com.linngdu664.bsf.item.ItemRegister;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemGroup {
    public static final CreativeModeTab MAIN = new CreativeModeTab("bsf_group") {
        @Override
        public ItemStack getIconItem() {
            return new ItemStack(ItemRegister.EXPLOSIVE_SNOWBALL.get());
        }
    };
}
