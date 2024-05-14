package com.linngdu664.bsf.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;

// Good modders copy, great ones steal.
public class BigLongTimeSnowflake extends TextureSheetParticle {
    private final SpriteSet sprites;

    protected BigLongTimeSnowflake(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, SpriteSet pSprites) {
        super(pLevel, pX, pY, pZ);
        this.gravity = 0;
        this.friction = 1.0F;
        this.sprites = pSprites;
        this.xd = pXSpeed + (Math.random() * 2.0D - 1.0D) * (double) 0.05F;
        this.yd = pYSpeed + (Math.random() * 2.0D - 1.0D) * (double) 0.05F;
        this.zd = pZSpeed + (Math.random() * 2.0D - 1.0D) * (double) 0.05F;
        this.quadSize = 0.8F * (this.random.nextFloat() * this.random.nextFloat() + 1.0F);
        this.lifetime = (int) (60.0F * this.random.nextFloat() + 140.0F);
        this.setSpriteFromAge(pSprites);
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteFromAge(this.sprites);
        this.xd *= 0.95F;
        this.yd *= 0.9F;
        this.zd *= 0.95F;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet pSprites) {
            this.sprites = pSprites;
        }

        public Particle createParticle(@NotNull SimpleParticleType pType, @NotNull ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            BigLongTimeSnowflake bigLongTimeSnowflake = new BigLongTimeSnowflake(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, this.sprites);
            bigLongTimeSnowflake.setColor(0.923F, 0.964F, 0.999F);
            return bigLongTimeSnowflake;
        }
    }
}
