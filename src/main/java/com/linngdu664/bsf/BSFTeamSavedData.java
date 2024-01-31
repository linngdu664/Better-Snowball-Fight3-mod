package com.linngdu664.bsf;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

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

    public int getGroup(UUID uuid) {
        if (groupIdMap.containsKey(uuid)) {
            return groupIdMap.get(uuid);
        }
        return -1;
    }

    public void exitGroup(UUID uuid) {
        int groupId = getGroup(uuid);
        if (groupId != -1) {
            groupIdMap.remove(uuid);
            groupMembers[groupId].remove(uuid);
        }
    }

    public void joinGroup(UUID uuid, int groupId) {
        int oldGroupId = getGroup(uuid);
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
}
