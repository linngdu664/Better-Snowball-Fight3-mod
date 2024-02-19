package com.linngdu664.bsf.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class BSFTeamSavedData extends SavedData {
    private final HashSet<UUID>[] groupMembers = new HashSet[16];
    private final HashMap<UUID, Integer> groupIdMap = new HashMap<>();

    public BSFTeamSavedData() {
        for (int i = 0; i < 16; i++) {
            groupMembers[i] = new HashSet<>();
        }
    }

    public BSFTeamSavedData(CompoundTag root) {
        this();
        if (root.contains("BSFTeam")) {
            ListTag listTag = (ListTag) root.get("BSFTeam");
            for (Tag tag : listTag) {
                CompoundTag current = (CompoundTag) tag;
                UUID uuid = current.getUUID("UUID");
                int groupId = current.getInt("TeamId");
                groupMembers[groupId].add(uuid);
                groupIdMap.put(uuid, groupId);
            }
        }
    }

    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag pCompoundTag) {
        ListTag listTag = new ListTag();
        for (var e : groupIdMap.entrySet()) {
            CompoundTag compoundTag = new CompoundTag();
            compoundTag.putUUID("UUID", e.getKey());
            compoundTag.putInt("TeamId", e.getValue());
            listTag.add(compoundTag);
        }
        pCompoundTag.put("BSFTeam", listTag);
        return pCompoundTag;
    }

    public int getTeam(UUID uuid) {
        if (groupIdMap.containsKey(uuid)) {
            return groupIdMap.get(uuid);
        }
        return -1;
    }

    public void exitTeam(UUID uuid) {
        int groupId = getTeam(uuid);
        if (groupId != -1) {
            groupIdMap.remove(uuid);
            groupMembers[groupId].remove(uuid);
        }
    }

    public void joinTeam(@NotNull UUID uuid, int groupId) {
        int oldGroupId = getTeam(uuid);
        if (oldGroupId != -1) {
            groupMembers[oldGroupId].remove(uuid);
        }
        groupIdMap.put(uuid, groupId);
        groupMembers[groupId].add(uuid);
    }

    public HashSet<UUID> getMembers(int groupId) {
        if (groupId < 0) {
            return new HashSet<>();
        }
        return groupMembers[groupId];
    }

    public boolean isSameTeam(@Nullable Entity entity1, @Nullable Entity entity2) {
        if (entity1 == null || entity2 == null) {
            return false;
        }
        int id = getTeam(entity1.getUUID());
        return id >= 0 && id == getTeam(entity2.getUUID());
    }
}
