package com.linngdu664.bsf.config;

import com.linngdu664.bsf.Main;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ServerConfig extends BSFConfig {
    public static ConfigValueHolder<Boolean> EXPLOSIVE_DESTROY = new ConfigValueHolder(Main.MODID, "server/explosiveDestroy", (builder) ->
            builder.comment("Whether explosive snowballs can destroy blocks. Default value: true")
                    .define("explosiveDestroy", true));
    public static ConfigValueHolder<Boolean> BLACK_HOLE_DESTROY = new ConfigValueHolder(Main.MODID, "server/blackHoleDestroy", (builder) ->
            builder.comment("Whether black hole snowballs can destroy blocks. Default value: true")
                    .define("blackHoleDestroy", true));
    public static ConfigValueHolder<Boolean> BLACK_HOLE_DROP = new ConfigValueHolder(Main.MODID, "server/blackHoleDrop", (builder) ->
            builder.comment("Whether to drop when black hole snowballs destroy blocks. Set to false to improve performance. Default value: true")
                    .define("blackHoleDrop", true));
    public static ConfigValueHolder<Integer> RECONSTRUCT_SNOWBALL_CAPACITY = new ConfigValueHolder(Main.MODID, "server/reconstructSnowballCapacity", (builder) ->
            builder.comment("The capacity of reconstruct snowball. Default value: 500.")
                    .defineInRange("reconstructSnowballCapacity", 500, 0, 1100));
    public static ConfigValueHolder<Integer> ICICLE_SNOWBALL_CAPACITY = new ConfigValueHolder(Main.MODID, "server/icicleSnowballCapacity", (builder) ->
            builder.comment("The capacity of icicle snowball. Default value: 2147483647.")
                    .defineInRange("icicleSnowballCapacity", 2147483647, 0, Integer.MAX_VALUE));
    public static ConfigValueHolder<Integer> EXPANSION_SNOWBALL_DURATION = new ConfigValueHolder(Main.MODID, "server/expansionSnowballDuration", (builder) ->
            builder.comment("The life span of expansion snowball in tick. Default value: 80.")
                    .defineInRange("expansionSnowballDuration", 80, 0, Integer.MAX_VALUE));
    public static ConfigValueHolder<Integer> RECONSTRUCT_SNOWBALL_DURATION = new ConfigValueHolder(Main.MODID, "server/reconstructSnowballDuration", (builder) ->
            builder.comment("The life span of reconstruct snowball in tick. Default value: 80.")
                    .defineInRange("reconstructSnowballDuration", 80, 0, Integer.MAX_VALUE));
    public static ConfigValueHolder<Integer> ICICLE_SNOWBALL_DURATION = new ConfigValueHolder(Main.MODID, "server/icicleSnowballDuration", (builder) ->
            builder.comment("The life span of icicle snowball in tick. Default value: 80.")
                    .defineInRange("icicleSnowballDuration", 80, 0, Integer.MAX_VALUE));
    public static ConfigValueHolder<Boolean> ENABLE_FRIENDLY_FIRE = new ConfigValueHolder(Main.MODID, "server/enableFriendlyFire", (builder) ->
            builder.comment("Default value: false.")
                    .define("enableFriendlyFire", false));

    public static final ServerConfig INSTANCE;
    public static final ForgeConfigSpec SPEC;

    public ServerConfig(ForgeConfigSpec.Builder builder) {
        super(Main.MODID, "server", builder);
    }

    static {
        Pair<ServerConfig, ForgeConfigSpec> specPair = (new ForgeConfigSpec.Builder()).configure(ServerConfig::new);
        SPEC = specPair.getRight();
        INSTANCE = specPair.getLeft();
    }

}
