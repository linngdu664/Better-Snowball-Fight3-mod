package com.linngdu664.bsf.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundRegister {
    public static final int MEME_SOUND_AMOUNT = 64;
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, "bsf");
    public static final RegistryObject<SoundEvent> SNOWBALL_CANNON_SHOOT = build("snowball_cannon_shoot");
    public static final RegistryObject<SoundEvent> SNOWBALL_MACHINE_GUN_SHOOT = build("snowball_machine_gun_shoot");
    public static final RegistryObject<SoundEvent> SHOTGUN_FIRE_1 = build("shotgun_fire1");
    public static final RegistryObject<SoundEvent> SHOTGUN_FIRE_2 = build("shotgun_fire2");
    public static final RegistryObject<SoundEvent> BLACK_HOLE_START = build("black_hole_start", 32);
    public static final RegistryObject<SoundEvent> POWDER_SNOWBALL = build("powder_snowball");
    public static final RegistryObject<SoundEvent> FIELD_PUSH = build("field_push");
    public static final RegistryObject<SoundEvent> FIELD_SNOWBALL_STOP = build("field_snowball_stop");
    public static final RegistryObject<SoundEvent> FIELD_START = build("field_start");
    public static final RegistryObject<SoundEvent> SUBSPACE_SNOWBALL_CUT = build("subspace_snowball_cut");
    public static final RegistryObject<SoundEvent> UNSTABLE_CORE_BREAK = build("unstable_core_break");
    public static final RegistryObject<SoundEvent> MACHINE_GUN_COOLING = build("machine_gun_cooling");
    public static final RegistryObject<SoundEvent>[] MEME = buildMeme();
    public static final RegistryObject<SoundEvent> DUCK = build("duck_sound");
    public static final RegistryObject<SoundEvent> FREEZING = build("freezing");
    public static final RegistryObject<SoundEvent> FORCE_EXECUTOR_START = build("force_executor_start");
    public static final RegistryObject<SoundEvent> BLACK_HOLE_AMBIENCE = build("black_hole_ambience");
    public static final RegistryObject<SoundEvent> COLD_COMPRESSION_JET_ENGINE_STARTUP1 = build("cold_compression_jet_engine_startup1");
    public static final RegistryObject<SoundEvent> COLD_COMPRESSION_JET_ENGINE_STARTUP2 = build("cold_compression_jet_engine_startup2");
    public static final RegistryObject<SoundEvent> COLD_COMPRESSION_JET_ENGINE_STARTUP3 = build("cold_compression_jet_engine_startup3");
    public static final RegistryObject<SoundEvent> COLD_COMPRESSION_JET_ENGINE_STARTUP4 = build("cold_compression_jet_engine_startup4");
    public static final RegistryObject<SoundEvent> COLD_COMPRESSION_JET_ENGINE_STARTUP5 = build("cold_compression_jet_engine_startup5");
    public static final RegistryObject<SoundEvent> VECTOR_INVERSION = build("vector_inversion");
    public static final RegistryObject<SoundEvent> SUBSPACE_SNOWBALL_ATTACK = build("subspace_snowball_attack");
    public static final RegistryObject<SoundEvent> IMPLOSION_SNOWBALL_CANNON = build("implosion_snowball_cannon");

    private static RegistryObject<SoundEvent> build(String id, float range) {
        return SOUNDS.register(id, () -> SoundEvent.createFixedRangeEvent(new ResourceLocation("bsf", id), range));
    }

    private static RegistryObject<SoundEvent> build(String id) {
        return SOUNDS.register(id, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("bsf", id)));
    }

    private static RegistryObject<SoundEvent>[] buildMeme() {
        RegistryObject<SoundEvent>[] memes = new RegistryObject[MEME_SOUND_AMOUNT];
        for (int i = 0; i < 64; i++) {
            memes[i] = build(String.format("memesound%02d", i));
        }
        return memes;
    }
}
