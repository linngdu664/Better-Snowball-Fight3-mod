package com.linngdu664.bsf.item;

import com.linngdu664.bsf.Main;
import com.linngdu664.bsf.item.block.SmartSnowBlockItem;
import com.linngdu664.bsf.item.misc.*;
import com.linngdu664.bsf.item.snowball.CompactedSnowballSetItem;
import com.linngdu664.bsf.item.snowball.force.*;
import com.linngdu664.bsf.item.snowball.normal.*;
import com.linngdu664.bsf.item.snowball.special.*;
import com.linngdu664.bsf.item.snowball.tracking.*;
import com.linngdu664.bsf.item.tank.EmptySnowballTankItem;
import com.linngdu664.bsf.item.tank.force.*;
import com.linngdu664.bsf.item.tank.normal.*;
import com.linngdu664.bsf.item.tank.special.*;
import com.linngdu664.bsf.item.tank.tracking.*;
import com.linngdu664.bsf.item.tool.*;
import com.linngdu664.bsf.item.weapon.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
public class ItemRegister {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);

    public static final RegistryObject<Item> SMOOTH_SNOWBALL = ITEMS.register("smooth_snowball", SmoothSnowballItem::new);
    public static final RegistryObject<Item> COMPACTED_SNOWBALL = ITEMS.register("compacted_snowball", CompactedSnowballItem::new);
    public static final RegistryObject<Item> COMPACTED_SNOWBALL_SET = ITEMS.register("compacted_snowball_set", CompactedSnowballSetItem::new);
    public static final RegistryObject<Item> STONE_SNOWBALL = ITEMS.register("stone_snowball", StoneSnowballItem::new);
    public static final RegistryObject<Item> GLASS_SNOWBALL = ITEMS.register("glass_snowball", GlassSnowballItem::new);
    public static final RegistryObject<Item> ICE_SNOWBALL = ITEMS.register("ice_snowball", IceSnowballItem::new);
    public static final RegistryObject<Item> IRON_SNOWBALL = ITEMS.register("iron_snowball", IronSnowballItem::new);
    public static final RegistryObject<Item> GOLD_SNOWBALL = ITEMS.register("gold_snowball", GoldSnowballItem::new);
    public static final RegistryObject<Item> OBSIDIAN_SNOWBALL = ITEMS.register("obsidian_snowball", ObsidianSnowballItem::new);
    public static final RegistryObject<Item> EXPLOSIVE_SNOWBALL = ITEMS.register("explosive_snowball", ExplosiveSnowballItem::new);
    public static final RegistryObject<Item> SPECTRAL_SNOWBALL = ITEMS.register("spectral_snowball", SpectralSnowballItem::new);
    public static final RegistryObject<Item> FROZEN_SNOWBALL = ITEMS.register("frozen_snowball", FrozenSnowballItem::new);
    public static final RegistryObject<Item> POWDER_SNOWBALL = ITEMS.register("powder_snowball", PowderSnowballItem::new);
    public static final RegistryObject<Item> LIGHT_MONSTER_TRACKING_SNOWBALL = ITEMS.register("light_monster_tracking_snowball", LightMonsterTrackingSnowballItem::new);
    public static final RegistryObject<Item> HEAVY_MONSTER_TRACKING_SNOWBALL = ITEMS.register("heavy_monster_tracking_snowball", HeavyMonsterTrackingSnowballItem::new);
    public static final RegistryObject<Item> EXPLOSIVE_MONSTER_TRACKING_SNOWBALL = ITEMS.register("explosive_monster_tracking_snowball", ExplosiveMonsterTrackingSnowballItem::new);
    public static final RegistryObject<Item> LIGHT_PLAYER_TRACKING_SNOWBALL = ITEMS.register("light_player_tracking_snowball", LightPlayerTrackingSnowballItem::new);
    public static final RegistryObject<Item> HEAVY_PLAYER_TRACKING_SNOWBALL = ITEMS.register("heavy_player_tracking_snowball", HeavyPlayerTrackingSnowballItem::new);
    public static final RegistryObject<Item> EXPLOSIVE_PLAYER_TRACKING_SNOWBALL = ITEMS.register("explosive_player_tracking_snowball", ExplosivePlayerTrackingSnowballItem::new);
    public static final RegistryObject<Item> MONSTER_GRAVITY_SNOWBALL = ITEMS.register("gravity_snowball_to_monster", MonsterGravitySnowballItem::new);
    public static final RegistryObject<Item> PROJECTILE_GRAVITY_SNOWBALL = ITEMS.register("gravity_snowball_to_projectile", ProjectileGravitySnowballItem::new);
    public static final RegistryObject<Item> MONSTER_REPULSION_SNOWBALL = ITEMS.register("repulsion_snowball_to_monster", MonsterRepulsionSnowballItem::new);
    public static final RegistryObject<Item> PROJECTILE_REPULSION_SNOWBALL = ITEMS.register("repulsion_snowball_to_projectile", ProjectileRepulsionSnowballItem::new);
    public static final RegistryObject<Item> BLACK_HOLE_SNOWBALL = ITEMS.register("black_hole_snowball", BlackHoleSnowballItem::new);
    public static final RegistryObject<Item> SUBSPACE_SNOWBALL = ITEMS.register("subspace_snowball", SubspaceSnowballItem::new);
    public static final RegistryObject<Item> THRUST_SNOWBALL = ITEMS.register("thrust_snowball", ThrustSnowballItem::new);
    public static final RegistryObject<Item> ENDER_SNOWBALL = ITEMS.register("ender_snowball", EnderSnowballItem::new);
    public static final RegistryObject<Item> EXPANSION_SNOWBALL = ITEMS.register("expansion_snowball", ExpansionSnowballItem::new);
    public static final RegistryObject<Item> CRITICAL_FROZEN_SNOWBALL = ITEMS.register("critical_frozen_snowball", CriticalFrozenSnowballItem::new);
    public static final RegistryObject<Item> IMPULSE_SNOWBALL = ITEMS.register("impulse_snowball", ImpulseSnowballItem::new);
    public static final RegistryObject<Item> CHERRY_BLOSSOM_SNOWBALL = ITEMS.register("cherry_blossom_snowball", CherryBlossomSnowballItem::new);
    public static final RegistryObject<Item> GHOST_SNOWBALL = ITEMS.register("ghost_snowball", GhostSnowballItem::new);


    public static final RegistryObject<Item> EMPTY_SNOWBALL_STORAGE_TANK = ITEMS.register("empty_snowball_tank", EmptySnowballTankItem::new);
    public static final RegistryObject<Item> COMPACTED_SNOWBALL_TANK = ITEMS.register("compacted_snowball_tank", CompactedSnowballTank::new);
    public static final RegistryObject<Item> STONE_SNOWBALL_TANK = ITEMS.register("stone_snowball_tank", StoneSnowballTank::new);
    public static final RegistryObject<Item> GLASS_SNOWBALL_TANK = ITEMS.register("glass_snowball_tank", GlassSnowballTank::new);
    public static final RegistryObject<Item> ICE_SNOWBALL_TANK = ITEMS.register("ice_snowball_tank", IceSnowballTank::new);
    public static final RegistryObject<Item> IRON_SNOWBALL_TANK = ITEMS.register("iron_snowball_tank", IronSnowballTank::new);
    public static final RegistryObject<Item> GOLD_SNOWBALL_TANK = ITEMS.register("gold_snowball_tank", GoldSnowballTank::new);
    public static final RegistryObject<Item> OBSIDIAN_SNOWBALL_TANK = ITEMS.register("obsidian_snowball_tank", ObsidianSnowballTank::new);
    public static final RegistryObject<Item> EXPLOSIVE_SNOWBALL_TANK = ITEMS.register("explosive_snowball_tank", ExplosiveSnowballTank::new);
    public static final RegistryObject<Item> SPECTRAL_SNOWBALL_TANK = ITEMS.register("spectral_snowball_tank", SpectralSnowballTank::new);
    public static final RegistryObject<Item> FROZEN_SNOWBALL_TANK = ITEMS.register("frozen_snowball_tank", FrozenSnowballTank::new);
    public static final RegistryObject<Item> POWDER_SNOWBALL_TANK = ITEMS.register("powder_snowball_tank", PowderSnowballTank::new);
    public static final RegistryObject<Item> LIGHT_MONSTER_TRACKING_SNOWBALL_TANK = ITEMS.register("light_monster_tracking_snowball_tank", LightMonsterTrackingSnowballTank::new);
    public static final RegistryObject<Item> HEAVY_MONSTER_TRACKING_SNOWBALL_TANK = ITEMS.register("heavy_monster_tracking_snowball_tank", HeavyMonsterTrackingSnowballTank::new);
    public static final RegistryObject<Item> EXPLOSIVE_MONSTER_TRACKING_SNOWBALL_TANK = ITEMS.register("explosive_monster_tracking_snowball_tank", ExplosiveMonsterTrackingSnowballTank::new);
    public static final RegistryObject<Item> LIGHT_PLAYER_TRACKING_SNOWBALL_TANK = ITEMS.register("light_player_tracking_snowball_tank", LightPlayerTrackingSnowballTank::new);
    public static final RegistryObject<Item> HEAVY_PLAYER_TRACKING_SNOWBALL_TANK = ITEMS.register("heavy_player_tracking_snowball_tank", HeavyPlayerTrackingSnowballTank::new);
    public static final RegistryObject<Item> EXPLOSIVE_PLAYER_TRACKING_SNOWBALL_TANK = ITEMS.register("explosive_player_tracking_snowball_tank", ExplosivePlayerTrackingSnowballTank::new);
    public static final RegistryObject<Item> MONSTER_GRAVITY_SNOWBALL_TANK = ITEMS.register("gravity_snowball_to_monster_tank", MonsterGravitySnowballTank::new);
    public static final RegistryObject<Item> PROJECTILE_GRAVITY_SNOWBALL_TANK = ITEMS.register("gravity_snowball_to_projectile_tank", ProjectileGravitySnowballTank::new);
    public static final RegistryObject<Item> MONSTER_REPULSION_SNOWBALL_TANK = ITEMS.register("repulsion_snowball_to_monster_tank", MonsterRepulsionSnowballTank::new);
    public static final RegistryObject<Item> PROJECTILE_REPULSION_SNOWBALL_TANK = ITEMS.register("repulsion_snowball_to_projectile_tank", ProjectileRepulsionSnowballTank::new);
    public static final RegistryObject<Item> BLACK_HOLE_SNOWBALL_TANK = ITEMS.register("black_hole_snowball_tank", BlackHoleSnowballTank::new);
    public static final RegistryObject<Item> SUBSPACE_SNOWBALL_TANK = ITEMS.register("subspace_snowball_tank", SubspaceSnowballTank::new);
    public static final RegistryObject<Item> THRUST_SNOWBALL_TANK = ITEMS.register("thrust_snowball_tank", ThrustSnowballTank::new);
    public static final RegistryObject<Item> ENDER_SNOWBALL_TANK = ITEMS.register("ender_snowball_tank", EnderSnowballTank::new);
    public static final RegistryObject<Item> EXPANSION_SNOWBALL_TANK = ITEMS.register("expansion_snowball_tank", ExpansionSnowballTank::new);
    public static final RegistryObject<Item> CRITICAL_FROZEN_SNOWBALL_TANK = ITEMS.register("critical_frozen_snowball_tank", CriticalFrozenSnowballTank::new);
    public static final RegistryObject<Item> IMPULSE_SNOWBALL_TANK = ITEMS.register("impulse_snowball_tank", ImpulseSnowballTank::new);
    public static final RegistryObject<Item> CHERRY_BLOSSOM_SNOWBALL_TANK = ITEMS.register("cherry_blossom_snowball_tank", CherryBlossomSnowballTank::new);
    public static final RegistryObject<Item> GHOST_SNOWBALL_TANK = ITEMS.register("ghost_snowball_tank", GhostSnowballTank::new);



    public static final RegistryObject<Item> WOOD_SNOWBALL_CLAMP = ITEMS.register("wood_snowball_clamp", () -> new SnowballClampItem(Tiers.WOOD));
    public static final RegistryObject<Item> STONE_SNOWBALL_CLAMP = ITEMS.register("stone_snowball_clamp", () -> new SnowballClampItem(Tiers.STONE));
    public static final RegistryObject<Item> IRON_SNOWBALL_CLAMP = ITEMS.register("iron_snowball_clamp", () -> new SnowballClampItem(Tiers.IRON));
    public static final RegistryObject<Item> GOLD_SNOWBALL_CLAMP = ITEMS.register("gold_snowball_clamp", () -> new SnowballClampItem(Tiers.GOLD));
    public static final RegistryObject<Item> DIAMOND_SNOWBALL_CLAMP = ITEMS.register("diamond_snowball_clamp", () -> new SnowballClampItem(Tiers.DIAMOND));
    public static final RegistryObject<Item> NETHERITE_SNOWBALL_CLAMP = ITEMS.register("netherite_snowball_clamp", () -> new SnowballClampItem(Tiers.NETHERITE));
    public static final RegistryObject<Item> SNOWBALL_CANNON = ITEMS.register("snowball_cannon", SnowballCannonItem::new);
    public static final RegistryObject<Item> POWERFUL_SNOWBALL_CANNON = ITEMS.register("powerful_snowball_cannon", PowerfulSnowballCannonItem::new);
    public static final RegistryObject<Item> FREEZING_SNOWBALL_CANNON = ITEMS.register("freezing_snowball_cannon", FreezingSnowballCannonItem::new);
    public static final RegistryObject<Item> SNOWBALL_MACHINE_GUN = ITEMS.register("snowball_machine_gun", SnowballMachineGunItem::new);
    public static final RegistryObject<Item> SNOWBALL_SHOTGUN = ITEMS.register("snowball_shotgun", SnowballShotgunItem::new);
    public static final RegistryObject<Item> GLOVE = ITEMS.register("glove", GloveItem::new);
    public static final RegistryObject<Item> REPULSIVE_FIELD_GENERATOR = ITEMS.register("repulsive_field_generator", RepulsiveFieldGeneratorItem::new);




    public static final RegistryObject<Item> ICE_SKATES_ITEM = ITEMS.register("ice_skates", IceSkatesItem::new);
    public static final RegistryObject<Item> SNOW_FALL_BOOTS = ITEMS.register("snow_fall_boots", SnowFallBootsItem::new);
    public static final RegistryObject<Item> SNOW_BLOCK_BLENDER = ITEMS.register("snow_block_blender", SnowBlockBlenderItem::new);
    public static final RegistryObject<Item> EMPTY_BASIN = ITEMS.register("empty_basin", EmptyBasinItem::new);
    public static final RegistryObject<Item> BASIN_OF_SNOW = ITEMS.register("basin_of_snow", BasinOfSnowItem::new);
    public static final RegistryObject<Item> BASIN_OF_POWDER_SNOW = ITEMS.register("basin_of_powder_snow", BasinOfPowderSnowItem::new);
    public static final RegistryObject<Item> SNOW_GOLEM_MODE_TWEAKER = ITEMS.register("snow_golem_mode_tweaker", SnowGolemModeTweakerItem::new);
    public static final RegistryObject<Item> CREATE_SNOW_GOLEM_TOOL = ITEMS.register("creative_snow_golem_tool", CreativeSnowGolemToolItem::new);
    public static final RegistryObject<Item> TARGET_LOCATOR = ITEMS.register("target_locator", TargetLocatorItem::new);
    public static final RegistryObject<Item> SNOW_TRAP_SETTER = ITEMS.register("snow_trap_setter", SnowTrapSetterItem::new);
    public static final RegistryObject<Item> SCULK_SNOWBALL_LAUNCHER = ITEMS.register("sculk_snowball_launcher", SculkSnowballLauncherItem::new);




    public static final RegistryObject<Item> POPSICLE = ITEMS.register("popsicle", PopsicleItem::new);
    public static final RegistryObject<Item> MILK_POPSICLE = ITEMS.register("milk_popsicle", MilkPopsicleItem::new);
    public static final RegistryObject<Item> VODKA = ITEMS.register("vodka", VodkaItem::new);




    public static final RegistryObject<Item> SMART_SNOW_BLOCK = ITEMS.register("smart_snow_block", SmartSnowBlockItem::new);





    public static final RegistryObject<Item> SUPER_POWER_CORE = ITEMS.register("super_power_core", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SUPER_FROZEN_CORE = ITEMS.register("super_frozen_core", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> TRACKING_CORE = ITEMS.register("tracking_core", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> UNSTABLE_CORE = ITEMS.register("unstable_core", UnstableCoreItem::new);
    public static final RegistryObject<Item> REPULSION_CORE = ITEMS.register("repulsion_core", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> GRAVITY_CORE = ITEMS.register("gravity_core", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> SNOWBALL_CANNON_UPGRADE_SMITHING_TEMPLATE = ITEMS.register("snowball_cannon_upgrade_smithing_template",SnowballCannonUpgradeSmithingTemplateItem::new);
    public static final RegistryObject<Item> SNOWMAN_IN_HAND = ITEMS.register("snowman_in_hand",SnowmanInHandItem::new);






    public static final RegistryObject<Item> GPS_SNOWBALL = ITEMS.register("gps_snowball", () -> new Item(new Item.Properties()));    //This item does not need to be added to the group
    public static final RegistryObject<Item> SCULK_SNOWBALL = ITEMS.register("sculk_snowball", () -> new Item(new Item.Properties()));
}
