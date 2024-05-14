package com.linngdu664.bsf.particle;

import com.linngdu664.bsf.util.BSFCommonUtil;
import com.linngdu664.bsf.util.PolarPos2d;
import com.linngdu664.bsf.util.Vec2d;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class VectorInversionParticle extends TextureSheetParticle {
    private final PolarPos2d polarPos2d;
    private double speed;
    private final SpriteSet sprites;

    protected VectorInversionParticle(ClientLevel pLevel, double pX, double pY, double pZ, double p0X, double p0Z, double speed, float r, float g, float b, SpriteSet pSprites) {
        super(pLevel, pX, pY, pZ);
        this.polarPos2d = new PolarPos2d(new Vec2d(p0X, p0Z), new Vec2d(pX, pZ));
        this.hasPhysics = false;
        this.gravity = 0F;
        this.friction = 0.9F;
        this.sprites = pSprites;
        this.speed = speed;
        this.rCol = r;
        this.gCol = g;
        this.bCol = b;
        this.quadSize = 0.4F * (this.random.nextFloat() * this.random.nextFloat() * 1.0F + 4.5F);
        this.lifetime = 40;
        this.setSpriteFromAge(pSprites);
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            Vec2d vec2d = polarPos2d.rotateLength(speed);
            this.move(vec2d.x, 0, vec2d.y);
            if (this.speedUpWhenYMotionIsBlocked && this.y == this.yo) {
                this.speed *= 1.1D;
            }
            this.speed *= this.friction;
            if (this.onGround) {
                this.speed *= 0.7;
            }
        }
        this.setSpriteFromAge(this.sprites);
        scale(0.92f);
    }

    /**
     * pXSpeed and pYSpeed are the x and z coordinates of the rotation point.
     * pZSpeed is the rotation speed, positive numbers rotate counterclockwise, and complex numbers rotate clockwise.
     */

    @OnlyIn(Dist.CLIENT)
    public static class ProviderRed implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public ProviderRed(SpriteSet pSprite) {
            this.sprite = pSprite;
        }

        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            float f = BSFCommonUtil.random.nextFloat() * 0.6F + 0.4F;
            return new VectorInversionParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, f * 0.9F, f * 0.3F, f * 0.3F, this.sprite);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class ProviderPurple implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public ProviderPurple(SpriteSet pSprite) {
            this.sprite = pSprite;
        }

        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            float f = BSFCommonUtil.random.nextFloat() * 0.6F + 0.4F;
            return new VectorInversionParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, f * 0.9F, f * 0.3F, f, this.sprite);
        }
    }
}
