package com.linngdu664.bsf.entity;

import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public interface Forceable {
    default void forceEffect(Entity self, List<? extends Entity> list, double constForceRangeSqr, double GM) {
        for (Entity entity : list) {
            Vec3 rVec = new Vec3(self.getX() - entity.getX(), self.getY() - (entity.getEyeY() + entity.getY()) * 0.5, self.getZ() - entity.getZ());
            double r2 = rVec.lengthSqr();
            double ir2 = Mth.invSqrt(r2);
            double a;
            if (r2 > constForceRangeSqr) {
                a = GM / r2;
            } else if (r2 > 0.25) {         // default 0.25
                a = GM / constForceRangeSqr;
            } else {
                a = 0;
            }
            entity.push(a * rVec.x * ir2, a * rVec.y * ir2, a * rVec.z * ir2);
            //Tell client that player should move because client handles player's movement.
            if (entity instanceof ServerPlayer player) {
                player.connection.send(new ClientboundSetEntityMotionPacket(entity));
            }
        }
    }
}
