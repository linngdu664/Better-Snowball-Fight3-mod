package com.linngdu664.bsf.util;

import com.linngdu664.bsf.Main;
import com.linngdu664.bsf.item.ItemRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeTabRegister {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Main.MODID);
    // Creates a creative tab with the id "bsf:bsf_tab" for the example item, that is placed after the combat tab
    public static final RegistryObject<CreativeModeTab> BSF_TAB = CREATIVE_TABS.register("bsf_tab", () -> net.minecraft.world.item.CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .icon(() -> ItemRegister.EXPLOSIVE_SNOWBALL.get().getDefaultInstance())
            .title(MutableComponent.create(new TranslatableContents("itemGroup.bsf_group", null, new Object[0])))
            .displayItems((parameters, output) -> {
                output.accept(ItemRegister.SMOOTH_SNOWBALL.get());
                output.accept(ItemRegister.COMPACTED_SNOWBALL.get());
                output.accept(ItemRegister.COMPACTED_SNOWBALL_SET.get());
                output.accept(ItemRegister.CHERRY_BLOSSOM_SNOWBALL.get());
                output.accept(ItemRegister.STONE_SNOWBALL.get());
                output.accept(ItemRegister.GLASS_SNOWBALL.get());
                output.accept(ItemRegister.ICE_SNOWBALL.get());
                output.accept(ItemRegister.IRON_SNOWBALL.get());
                output.accept(ItemRegister.GOLD_SNOWBALL.get());
                output.accept(ItemRegister.OBSIDIAN_SNOWBALL.get());
                output.accept(ItemRegister.EXPLOSIVE_SNOWBALL.get());
                output.accept(ItemRegister.SPECTRAL_SNOWBALL.get());
                output.accept(ItemRegister.FROZEN_SNOWBALL.get());
                output.accept(ItemRegister.CRITICAL_FROZEN_SNOWBALL.get());
                output.accept(ItemRegister.POWDER_SNOWBALL.get());
                output.accept(ItemRegister.LIGHT_MONSTER_TRACKING_SNOWBALL.get());
                output.accept(ItemRegister.HEAVY_MONSTER_TRACKING_SNOWBALL.get());
                output.accept(ItemRegister.EXPLOSIVE_MONSTER_TRACKING_SNOWBALL.get());
                output.accept(ItemRegister.LIGHT_PLAYER_TRACKING_SNOWBALL.get());
                output.accept(ItemRegister.HEAVY_PLAYER_TRACKING_SNOWBALL.get());
                output.accept(ItemRegister.EXPLOSIVE_PLAYER_TRACKING_SNOWBALL.get());
                output.accept(ItemRegister.MONSTER_GRAVITY_SNOWBALL.get());
                output.accept(ItemRegister.PROJECTILE_GRAVITY_SNOWBALL.get());
                output.accept(ItemRegister.MONSTER_REPULSION_SNOWBALL.get());
                output.accept(ItemRegister.PROJECTILE_REPULSION_SNOWBALL.get());
                output.accept(ItemRegister.BLACK_HOLE_SNOWBALL.get());
                output.accept(ItemRegister.SUBSPACE_SNOWBALL.get());
                output.accept(ItemRegister.THRUST_SNOWBALL.get());
                output.accept(ItemRegister.ENDER_SNOWBALL.get());
                output.accept(ItemRegister.EXPANSION_SNOWBALL.get());
                output.accept(ItemRegister.IMPULSE_SNOWBALL.get());
                output.accept(ItemRegister.GHOST_SNOWBALL.get());


                output.accept(ItemRegister.EMPTY_SNOWBALL_STORAGE_TANK.get());
                output.accept(ItemRegister.COMPACTED_SNOWBALL_TANK.get());
                output.accept(ItemRegister.CHERRY_BLOSSOM_SNOWBALL_TANK.get());
                output.accept(ItemRegister.STONE_SNOWBALL_TANK.get());
                output.accept(ItemRegister.GLASS_SNOWBALL_TANK.get());
                output.accept(ItemRegister.ICE_SNOWBALL_TANK.get());
                output.accept(ItemRegister.IRON_SNOWBALL_TANK.get());
                output.accept(ItemRegister.GOLD_SNOWBALL_TANK.get());
                output.accept(ItemRegister.OBSIDIAN_SNOWBALL_TANK.get());
                output.accept(ItemRegister.EXPLOSIVE_SNOWBALL_TANK.get());
                output.accept(ItemRegister.SPECTRAL_SNOWBALL_TANK.get());
                output.accept(ItemRegister.FROZEN_SNOWBALL_TANK.get());
                output.accept(ItemRegister.CRITICAL_FROZEN_SNOWBALL_TANK.get());
                output.accept(ItemRegister.POWDER_SNOWBALL_TANK.get());
                output.accept(ItemRegister.LIGHT_MONSTER_TRACKING_SNOWBALL_TANK.get());
                output.accept(ItemRegister.HEAVY_MONSTER_TRACKING_SNOWBALL_TANK.get());
                output.accept(ItemRegister.EXPLOSIVE_MONSTER_TRACKING_SNOWBALL_TANK.get());
                output.accept(ItemRegister.LIGHT_PLAYER_TRACKING_SNOWBALL_TANK.get());
                output.accept(ItemRegister.HEAVY_PLAYER_TRACKING_SNOWBALL_TANK.get());
                output.accept(ItemRegister.EXPLOSIVE_PLAYER_TRACKING_SNOWBALL_TANK.get());
                output.accept(ItemRegister.MONSTER_GRAVITY_SNOWBALL_TANK.get());
                output.accept(ItemRegister.PROJECTILE_GRAVITY_SNOWBALL_TANK.get());
                output.accept(ItemRegister.MONSTER_REPULSION_SNOWBALL_TANK.get());
                output.accept(ItemRegister.PROJECTILE_REPULSION_SNOWBALL_TANK.get());
                output.accept(ItemRegister.BLACK_HOLE_SNOWBALL_TANK.get());
                output.accept(ItemRegister.SUBSPACE_SNOWBALL_TANK.get());
                output.accept(ItemRegister.THRUST_SNOWBALL_TANK.get());
                output.accept(ItemRegister.ENDER_SNOWBALL_TANK.get());
                output.accept(ItemRegister.EXPANSION_SNOWBALL_TANK.get());
                output.accept(ItemRegister.IMPULSE_SNOWBALL_TANK.get());
                output.accept(ItemRegister.GHOST_SNOWBALL_TANK.get());


                output.accept(ItemRegister.WOOD_SNOWBALL_CLAMP.get());
                output.accept(ItemRegister.STONE_SNOWBALL_CLAMP.get());
                output.accept(ItemRegister.IRON_SNOWBALL_CLAMP.get());
                output.accept(ItemRegister.GOLD_SNOWBALL_CLAMP.get());
                output.accept(ItemRegister.DIAMOND_SNOWBALL_CLAMP.get());
                output.accept(ItemRegister.NETHERITE_SNOWBALL_CLAMP.get());
                output.accept(ItemRegister.SNOWBALL_CANNON.get());
                output.accept(ItemRegister.POWERFUL_SNOWBALL_CANNON.get());
                output.accept(ItemRegister.FREEZING_SNOWBALL_CANNON.get());
                output.accept(ItemRegister.SNOWBALL_MACHINE_GUN.get());
                output.accept(ItemRegister.SNOWBALL_SHOTGUN.get());
                output.accept(ItemRegister.SNOWMAN_IN_HAND.get());
                output.accept(ItemRegister.GLOVE.get());
                output.accept(ItemRegister.REPULSIVE_FIELD_GENERATOR.get());
                output.accept(ItemRegister.ICE_SKATES_ITEM.get());
                output.accept(ItemRegister.SNOW_FALL_BOOTS.get());
                output.accept(ItemRegister.SNOW_BLOCK_BLENDER.get());
                output.accept(ItemRegister.EMPTY_BASIN.get());
                output.accept(ItemRegister.BASIN_OF_SNOW.get());
                output.accept(ItemRegister.BASIN_OF_POWDER_SNOW.get());
                output.accept(ItemRegister.SNOW_GOLEM_MODE_TWEAKER.get());
                output.accept(ItemRegister.CREATE_SNOW_GOLEM_TOOL.get());
                output.accept(ItemRegister.TARGET_LOCATOR.get());
                output.accept(ItemRegister.SNOW_TRAP_SETTER.get());
                output.accept(ItemRegister.SCULK_SNOWBALL_LAUNCHER.get());


                output.accept(ItemRegister.POPSICLE.get());
                output.accept(ItemRegister.MILK_POPSICLE.get());
                output.accept(ItemRegister.VODKA.get());
                output.accept(ItemRegister.SMART_SNOW_BLOCK.get());
                output.accept(ItemRegister.SUPER_POWER_CORE.get());
                output.accept(ItemRegister.SUPER_FROZEN_CORE.get());
                output.accept(ItemRegister.TRACKING_CORE.get());
                output.accept(ItemRegister.UNSTABLE_CORE.get());
                output.accept(ItemRegister.REPULSION_CORE.get());
                output.accept(ItemRegister.GRAVITY_CORE.get());
                output.accept(ItemRegister.SNOWBALL_CANNON_UPGRADE_SMITHING_TEMPLATE.get());
            }).build());
}
