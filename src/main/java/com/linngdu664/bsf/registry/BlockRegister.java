package com.linngdu664.bsf.registry;

import com.linngdu664.bsf.Main;
import com.linngdu664.bsf.block.CriticalSnow;
import com.linngdu664.bsf.block.LooseSnowBlock;
import com.linngdu664.bsf.block.SmartSnowBlock;
import com.linngdu664.bsf.block.SnowTrap;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegister {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MODID);
    public static final RegistryObject<SmartSnowBlock> SMART_SNOW_BLOCK = BLOCKS.register("smart_snow_block", SmartSnowBlock::new);
    public static final RegistryObject<LooseSnowBlock> LOOSE_SNOW_BLOCK = BLOCKS.register("loose_snow_block", LooseSnowBlock::new);
    public static final RegistryObject<SnowTrap> SNOW_TRAP = BLOCKS.register("snow_trap", SnowTrap::new);
    public static final RegistryObject<CriticalSnow> CRITICAL_SNOW = BLOCKS.register("critical_snow", CriticalSnow::new);
}
