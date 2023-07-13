package com.linngdu664.bsf.util;

import com.linngdu664.bsf.entity.snowball.AbstractBSFSnowballEntity;
import com.linngdu664.bsf.entity.BSFSnowGolemEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

// I wonder why IDEA warns list.remove.
public class TargetGetter {
    /**
     * Get the nearest available target.
     *
     * @param snowball         The snowball entity.
     * @param t                The class of specific targets.
     * @param trackingRange    Only gets target within the range. See AABB.inflate().
     * @param <T>              Extends entity class.
     * @param angleRestriction Whether only return the target within 60 degrees.
     * @return The target.
     */
    public static <T extends Entity> Entity getTarget(AbstractBSFSnowballEntity snowball, Class<T> t, boolean angleRestriction, double trackingRange) {
        Level level = snowball.level();
        List<T> list = level.getEntitiesOfClass(t, snowball.getBoundingBox().inflate(trackingRange, trackingRange, trackingRange));
        Entity owner = snowball.getOwner();
        list.remove(snowball);
        list.remove(owner);
        if (owner instanceof BSFSnowGolemEntity golem) {
            list.remove(golem.getOwner());
        }
        if (t == BSFSnowGolemEntity.class) {
            ArrayList<Entity> arrayList = new ArrayList<>();
            if (owner instanceof BSFSnowGolemEntity golem) {
                for (T entity : list) {
                    if (((BSFSnowGolemEntity) entity).getTarget() == null || !((BSFSnowGolemEntity) entity).getTarget().equals(golem.getOwner())) {
                        arrayList.add(entity);
                    }
                }
            } else {
                for (T entity : list) {
                    if (((BSFSnowGolemEntity) entity).getTarget() == null || !((BSFSnowGolemEntity) entity).getTarget().equals(owner)) {
                        arrayList.add(entity);
                    }
                }
            }
            for (Entity entity : arrayList) {
                list.remove(entity);
            }
        }
        if (angleRestriction) {
            ArrayList<Entity> arrayList = new ArrayList<>();
            Vec3 velocity = snowball.getDeltaMovement();
            for (T entity : list) {
                Vec3 vec3 = new Vec3(entity.getX() - snowball.getX(), entity.getY() - snowball.getY(), entity.getZ() - snowball.getZ());
                if (BSFMthUtil.vec3AngleCos(vec3, velocity) < 0.5) {
                    arrayList.add(entity);
                }
            }
            for (Entity entity : arrayList) {
                list.remove(entity);
            }
        }
        if (list.isEmpty()) {
            return null;
        }
        Entity entity1 = list.get(0);
        for (T entity : list) {
            if (snowball.distanceToSqr(entity) < snowball.distanceToSqr(entity1)) {
                entity1 = entity;
            }
        }
        return entity1;
    }

    /**
     * Get a list that contains nearby targets.
     *
     * @param entity The center entity.
     * @param t      The class of specific targets.
     * @param range  Only gets target within the range. See AABB.inflate().
     * @param <T>    Extends entity class.
     * @return The target list.
     */
    public static <T extends Entity> List<T> getTargetList(Entity entity, Class<T> t, double range) {
        Level level = entity.level();
        List<T> list = level.getEntitiesOfClass(t, entity.getBoundingBox().inflate(range, range, range));
        list.remove(entity);
        return list;
    }
}
