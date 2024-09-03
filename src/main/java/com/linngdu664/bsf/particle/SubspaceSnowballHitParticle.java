package com.linngdu664.bsf.particle;

import com.linngdu664.bsf.particle.util.ParticleUtil;
import com.linngdu664.bsf.util.BSFCommonUtil;
import com.linngdu664.bsf.util.PolarPos2d;
import com.linngdu664.bsf.util.Vec2d;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class SubspaceSnowballHitParticle extends TextureSheetParticle {
    private final PolarPos2d polarPos2d;
    private double speed;
    private final double radius;
    private final SpriteSet sprites;
    private final double pDY;
    private final double p0Y;

    protected SubspaceSnowballHitParticle(ClientLevel pLevel, double pX, double pY, double pZ, double p0X, double p0Y, double p0Z, float r, float g, float b,double speed, SpriteSet pSprites) {
        super(pLevel, p0X, p0Y, p0Z);
        this.polarPos2d = new PolarPos2d(new Vec2d(p0X, p0Z), new Vec2d(pX, pZ));
        this.hasPhysics = false;
        this.gravity = 0F;
        this.friction = 0.9F;
        this.sprites = pSprites;
        this.speed = speed;
        this.pDY = pY-p0Y;
        this.p0Y=p0Y;
        this.radius = this.polarPos2d.getRadius();
        this.rCol = r;
        this.gCol = g;
        this.bCol = b;
        this.quadSize = 0.4F * (this.random.nextFloat() * this.random.nextFloat() * 1.0F + 4.5F);
        this.lifetime = 7;
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

            double r = BSFCommonUtil.quadraticFunction(this.age, this.lifetime, this.radius);
            double dy = BSFCommonUtil.quadraticFunction(this.age, this.lifetime, this.pDY);
            polarPos2d.setRadius(r);
            polarPos2d.rotateLength(speed);
            Vec2d vec2d = polarPos2d.getP();
            this.setPos(vec2d.x,p0Y+dy,vec2d.y);
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

    public static double quadraticFunction(double x, double maxX, double maxY) {
        return 4*x*maxY*(maxX-x)/(maxX*maxX);
    }

    /**
     * pXSpeed, pZSpeed and pYSpeed are the x, y and z coordinates of the rotation point.
     */

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;

        public Provider(SpriteSet pSprite) {
            this.sprite = pSprite;
        }

        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            RandomSource random = pLevel.getRandom();
            Color color = ParticleUtil.hsvColor(random.nextInt(360),BSFCommonUtil.randIntWithInfer(random,40,100),BSFCommonUtil.randIntWithInfer(random,80,100));
            return new SubspaceSnowballHitParticle(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed, (float) color.getRed() /255,(float) color.getGreen() /255,(float) color.getBlue() /255,BSFCommonUtil.randDoubleWithInfer(random,1,5), this.sprite);
        }
    }

}

