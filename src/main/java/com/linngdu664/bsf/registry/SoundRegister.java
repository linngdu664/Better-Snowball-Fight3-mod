package com.linngdu664.bsf.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SoundRegister {
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

    private static RegistryObject<SoundEvent> build(String id, float range) {
        return SOUNDS.register(id, () -> SoundEvent.createFixedRangeEvent(new ResourceLocation("bsf", id), range));
    }

    private static RegistryObject<SoundEvent> build(String id) {
        return SOUNDS.register(id, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation("bsf", id)));
    }

    private static RegistryObject<SoundEvent>[] buildMeme() {
        RegistryObject<SoundEvent>[] memes = new RegistryObject[25];
        for (int i = 0; i < 25; i++) {
            memes[i] = build(String.format("memesound%02d", i + 1));
        }
        return memes;
    }
}
