package com.linngdu664.bsf.block;

import com.linngdu664.bsf.Main;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockRegister {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MODID);
    public static final RegistryObject<SmartSnowBlock> SMART_SNOW_BLOCK = BLOCKS.register("smart_snow_block", SmartSnowBlock::new);
    public static final RegistryObject<LooseSnowBlock> LOOSE_SNOW_BLOCK = BLOCKS.register("loose_snow_block", LooseSnowBlock::new);
    public static final RegistryObject<SnowTrapBlock> SNOW_TRAP_BLOCK = BLOCKS.register("snow_trap_block", SnowTrapBlock::new);
}
