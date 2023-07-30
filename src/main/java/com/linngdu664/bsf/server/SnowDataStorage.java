package com.linngdu664.bsf.server;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class SnowDataStorage extends SavedData {
    private HashMap<String, UUID> map = new HashMap<>();

    public SnowDataStorage() {
    }

    public SnowDataStorage(@NotNull CompoundTag root) {// load
        if (root.contains("bsf")) {
            ListTag list = root.getList("bsf", 10);
            CompoundTag current;
            for (Tag nbt : list) {
                current = (CompoundTag) nbt;
                //BlockPos:SnowballEntityId
                this.map.put(current.getString("BPos"), current.getUUID("SEId"));
            }
        }
    }

    @Override
    public CompoundTag save(@NotNull CompoundTag root) {
        ListTag list = new ListTag();
        for (String key : map.keySet()) {
            UUID val = map.get(key);
            CompoundTag c = new CompoundTag();
            c.putString("BPos", key);
            c.putUUID("SEId", val);
            list.add(c);
        }
        root.put("bsf", list);
        return root;
    }

    public HashMap<String, UUID> getMap() {
        return this.map;
    }

    public static String posToString(BlockPos pos) {
        return String.valueOf(pos.getX()) + ',' + pos.getY() + ',' + pos.getZ();
    }
}
