package com.linngdu664.bsf.util;

import com.linngdu664.bsf.Main;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BSFConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec.BooleanValue EXPLOSIVE_DESTROY = BUILDER
            .comment("Whether explosive snowballs can destroy blocks. Default value: true")
            .define("explosiveDestroy", true);
    public static final ForgeConfigSpec.BooleanValue BLACK_HOLE_DESTROY = BUILDER
            .comment("Whether black hole snowballs can destroy blocks. Default value: true")
            .define("blackHoleDestroy", true);
    public static final ForgeConfigSpec.BooleanValue BLACK_HOLE_DROP = BUILDER
            .comment("Whether to drop when black hole snowballs destroy blocks. Set to false to improve performance. Default value: true")
            .define("blackHoleDrop", true);
    public static final ForgeConfigSpec.IntValue RECONSTRUCT_SNOWBALL_CAPACITY = BUILDER
            .comment("The capacity of reconstruct snowball. Default value: 500.")
            .defineInRange("reconstruct_snowball_capacity", 500, 0, 1100);
    public static final ForgeConfigSpec.IntValue ICICLE_SNOWBALL_CAPACITY = BUILDER
            .comment("The capacity of icicle snowball. Default value: 2147483647.")
            .defineInRange("icicle_snowball_capacity", 2147483647, 0, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.IntValue EXPANSION_SNOWBALL_DURATION = BUILDER
            .comment("The life span of expansion snowball in tick. Default value: 80.")
            .defineInRange("expansion_snowball_duration", 80, 0, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.IntValue RECONSTRUCT_SNOWBALL_DURATION = BUILDER
            .comment("The life span of reconstruct snowball in tick. Default value: 80.")
            .defineInRange("reconstruct_snowball_duration", 80, 0, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.IntValue ICICLE_SNOWBALL_DURATION = BUILDER
            .comment("The life span of icicle snowball in tick. Default value: 80.")
            .defineInRange("icicle_snowball_duration", 80, 0, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.BooleanValue ENABLE_FRIENDLY_FIRE = BUILDER
            .comment("Default value: false.")
            .define("enable_friendly_fire", false);
    //    public static final ForgeConfigSpec.ConfigValue<String> MAGIC_NUMBER_INTRODUCTION = BUILDER
//            .comment("What you want the introduction message to be for the magic number")
//            .define("magicNumberIntroduction", "The magic number is... ");
    // a list of strings that are treated as resource locations for items
//    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS = BUILDER
//            .comment("A list of items to log on common setup.")
//            .defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), BSFConfig::validateItemName);
    public static final ForgeConfigSpec SPEC = BUILDER.build();
//    public static boolean explosiveDestroy;
//    public static boolean blackHoleDestroy;
//    public static boolean blackHoleDrop;
//    public static int reconstructSnowballCapacity;
//    public static int icicleSnowballCapacity;
//    public static int expansionSnowballDuration;
//    public static int reconstructSnowballDuration;
//    public static int icicleSnowballDuration;
//    public static boolean enableFriendlyFire;
//
//    public static double screenshakeIntensity = 1.0;
//    public static String magicNumberIntroduction;
//    public static Set<Item> items;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
//        explosiveDestroy = EXPLOSIVE_DESTROY.get();
//        blackHoleDestroy = BLACK_HOLE_DESTROY.get();
//        blackHoleDrop = BLACK_HOLE_DROP.get();
//        reconstructSnowballCapacity = RECONSTRUCT_SNOWBALL_CAPACITY.get();
//        icicleSnowballCapacity = ICICLE_SNOWBALL_CAPACITY.get();
//        expansionSnowballDuration = EXPANSION_SNOWBALL_DURATION.get();
//        reconstructSnowballDuration = RECONSTRUCT_SNOWBALL_DURATION.get();
//        icicleSnowballDuration = ICICLE_SNOWBALL_DURATION.get();
//        enableFriendlyFire = ENABLE_FRIENDLY_FIRE.get();

//        screenshakeIntensity = SCREENSHAKE_INTENSITY.get();
//        magicNumberIntroduction = MAGIC_NUMBER_INTRODUCTION.get();
//
//         convert the list of strings into a set of items
//        items = ITEM_STRINGS.get().stream()
//                .map(itemName -> ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)))
//                .collect(Collectors.toSet());
    }
}
