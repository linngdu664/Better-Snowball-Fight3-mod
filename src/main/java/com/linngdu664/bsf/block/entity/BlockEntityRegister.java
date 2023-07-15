package com.linngdu664.bsf.block.entity;


import com.linngdu664.bsf.Main;
import com.linngdu664.bsf.block.BlockRegister;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class BlockEntityRegister {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Main.MODID);
    public static final RegistryObject<BlockEntityType<LooseSnowBlockEntity>> LOOSE_SNOW_BLOCK_ENTITY = BLOCK_ENTITIES.register("loose_snow_block_entity", () -> BlockEntityType.Builder.of(LooseSnowBlockEntity::new, BlockRegister.LOOSE_SNOW_BLOCK.get()).build(null));
    public static final RegistryObject<BlockEntityType<CriticalSnowEntity>> CRITICAL_SNOW_ENTITY = BLOCK_ENTITIES.register("critical_snow_entity", () -> BlockEntityType.Builder.of(CriticalSnowEntity::new, BlockRegister.CRITICAL_SNOW.get()).build(null));
}
