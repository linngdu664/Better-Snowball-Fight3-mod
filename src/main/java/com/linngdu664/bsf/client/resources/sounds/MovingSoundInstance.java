package com.linngdu664.bsf.client.resources.sounds;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;

import java.util.Objects;

public class MovingSoundInstance extends AbstractTickableSoundInstance {
    private final Entity entity;

    public MovingSoundInstance(Entity entity, SoundEvent soundEvent, boolean looping) {
        super(soundEvent, SoundSource.NEUTRAL, entity.level().getRandom());
        this.entity = entity;
        this.looping = looping;
        this.delay = 0;
        this.volume = 1F;
    }

    @Override
    public void tick() {
        if (entity.isRemoved()) {
            Minecraft.getInstance().getSoundManager().stop(this);
        } else {
            x = entity.getX();
            y = entity.getY();
            z = entity.getZ();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (looping) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MovingSoundInstance that = (MovingSoundInstance) o;
            return Objects.equals(entity, that.entity) && Objects.equals(location, that.location);
        }
        return this == o;
    }

    @Override
    public int hashCode() {
        if (looping) {
            return 31 * Objects.hash(entity) + Objects.hash(location);
        }
        return super.hashCode();
    }
}
