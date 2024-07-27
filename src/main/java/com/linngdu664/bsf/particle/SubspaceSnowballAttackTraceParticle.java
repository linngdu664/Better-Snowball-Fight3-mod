package com.linngdu664.bsf.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;

public class SubspaceSnowballAttackTraceParticle extends TextureSheetParticle {
    private final SpriteSet sprites;

    protected SubspaceSnowballAttackTraceParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, SpriteSet pSprites) {
        super(pLevel, pX, pY, pZ);
        this.gravity = 0;
        this.friction = 1.0F;
        this.sprites = pSprites;
        this.xd = pXSpeed;
        this.yd = pYSpeed;
        this.zd = pZSpeed;
        this.quadSize = 1f;
        this.lifetime = 60;
        this.setSpriteFromAge(pSprites);
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        super.tick();
//        this.xd *= 0.95F;
//        this.yd *= 0.9F;
//        this.zd *= 0.95F;
        this.bCol*=0.95f;
        this.quadSize*=0.95f;

    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet pSprites) {
            this.sprites = pSprites;
        }

        public Particle createParticle(@NotNull SimpleParticleType pType, @NotNull ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            SubspaceSnowballAttackTraceParticle subspaceSnowballAttackTraceParticle = new SubspaceSnowballAttackTraceParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, this.sprites);
            subspaceSnowballAttackTraceParticle.setColor(0.8F, 0, 0.9F);
            return subspaceSnowballAttackTraceParticle;
        }
    }
}
