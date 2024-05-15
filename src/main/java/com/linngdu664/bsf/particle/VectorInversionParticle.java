package com.linngdu664.bsf.particle;

import com.linngdu664.bsf.util.BSFCommonUtil;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class VectorInversionParticle extends TextureSheetParticle {
    private final SpriteSet sprites;
    private float angle = 0;
    private float speed;
    Vec3 vec31, vec32;

    protected VectorInversionParticle(ClientLevel pLevel, Vec3 center, Vec3 offset, double axisYaw, double axisPitch, double speed, float r, float g, float b, SpriteSet pSprites) {
        super(pLevel, center.x + offset.x, center.y + offset.y, center.z + offset.z);
        this.hasPhysics = false;
        this.gravity = 0F;
        this.friction = 0.9F;
        this.sprites = pSprites;
        this.speed = (float) speed;
        this.rCol = r;
        this.gCol = g;
        this.bCol = b;
        this.quadSize = 0.4F * (this.random.nextFloat() * this.random.nextFloat() * 1.0F + 4.5F);
        this.lifetime = 40;
        this.setSpriteFromAge(pSprites);


        Vec3 axis = BSFCommonUtil.radRotationToVector(1, axisYaw, axisPitch);   // 单位方向向量
        Vec3 vec33 = axis.scale(offset.length() * BSFCommonUtil.vec3AngleCos(offset, axis));
        vec31 = offset.subtract(vec33);
        vec32 = vec31.cross(axis);     // axis为单位向量且垂直于vec31，不需要调整结果长度
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
            Vec3 posDiff = vec31.scale(Mth.cos(angle + speed) - Mth.cos(angle)).add(vec32.scale(Mth.sin(angle + speed) - Mth.sin(angle)));
            this.move(posDiff.x, posDiff.y, posDiff.z);
            angle += speed;
            if (this.speedUpWhenYMotionIsBlocked && this.y == this.yo) {
                this.speed *= 1.1F;
            }
            this.speed *= this.friction;
            if (this.onGround) {
                this.speed *= 0.7F;
            }
        }
        this.setSpriteFromAge(this.sprites);
        scale(0.92f);
    }

    /**
     * (pX pY pZ) is the center of sphere.<br>
     * pXSpeed is axis yaw.<br>
     * pYSpeed is axis pitch.<br>
     * pZSpeed is rotation speed +/-.<br>
     */
    public static class ProviderRed implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public ProviderRed(SpriteSet pSprite) {
            this.sprite = pSprite;
        }

        @Override
        public Particle createParticle(@NotNull SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            RandomSource randomSource = pLevel.getRandom();
            float f = randomSource.nextFloat() * 0.6F + 0.4F;
            double theta = BSFCommonUtil.randDouble(randomSource, 0, 2 * Mth.PI);
            double phi = Math.acos(BSFCommonUtil.randDouble(randomSource, -1, 1));
            return new VectorInversionParticle(pLevel,  new Vec3(pX, pY, pZ), BSFCommonUtil.radRotationToVector(10, theta, phi), pXSpeed, pYSpeed, pZSpeed, f * 0.9F, f * 0.3F, f * 0.3F, this.sprite);
        }
    }

    public static class ProviderPurple implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public ProviderPurple(SpriteSet pSprite) {
            this.sprite = pSprite;
        }

        @Override
        public Particle createParticle(@NotNull SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            RandomSource randomSource = pLevel.getRandom();
            float f = randomSource.nextFloat() * 0.6F + 0.4F;
            double theta = BSFCommonUtil.randDouble(randomSource, 0, 2 * Mth.PI);
            double phi = Math.acos(BSFCommonUtil.randDouble(randomSource, -1, 1));  // todo：统一一下一些东西！这东西俯仰角竟然是[0, pi]而不是[-pi/2, pi/2]
            return new VectorInversionParticle(pLevel, new Vec3(pX, pY, pZ), BSFCommonUtil.radRotationToVector(10, theta, phi), pXSpeed, pYSpeed, pZSpeed, f * 0.9F, f * 0.3F, f, this.sprite);
        }
    }
}
