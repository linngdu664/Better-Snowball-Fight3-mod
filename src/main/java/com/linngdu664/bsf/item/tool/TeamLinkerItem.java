package com.linngdu664.bsf.item.tool;

import com.linngdu664.bsf.BSFTeamSavedData;
import com.linngdu664.bsf.network.TeamMembersToClient;
import com.linngdu664.bsf.registry.NetworkRegister;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.UUID;

public class TeamLinkerItem extends Item {
    public static boolean shouldShowHighlight = false;  // client only
    private final int teamId;

    public TeamLinkerItem(int teamId) {
        super(new Properties());
        this.teamId = teamId;
    }

    private String getColorNameKeyById(int id) {
        return DyeColor.byId(id).getName() + ".tip";
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        if (pPlayer.isShiftKeyDown()) {
            if (pLevel.isClientSide) {
                shouldShowHighlight = !shouldShowHighlight;
            }
        } else if (!pLevel.isClientSide) {
            BSFTeamSavedData savedData = pPlayer.getServer().overworld().getDataStorage().computeIfAbsent(BSFTeamSavedData::new, BSFTeamSavedData::new, "bsf_team");
            UUID uuid = pPlayer.getUUID();
            String playerName = pPlayer.getName().getString();
            int oldId = savedData.getGroup(uuid);
            String[] newNameParam = new String[]{playerName, MutableComponent.create(new TranslatableContents(getColorNameKeyById(teamId), null, new Object[]{})).getString()};
            String[] oldNameParam = new String[]{playerName, MutableComponent.create(new TranslatableContents(getColorNameKeyById(oldId), null, new Object[]{})).getString()};
            HashSet<UUID> oldMembers = savedData.getMembers(oldId);
            oldMembers.forEach(p -> pLevel.getPlayerByUUID(p).displayClientMessage(MutableComponent.create(new TranslatableContents("leave_team.tip", null, oldNameParam)), false));
            if (oldId == teamId) {
                // 退队
                savedData.exitGroup(uuid);
                oldMembers.forEach(p -> NetworkRegister.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) pLevel.getPlayerByUUID(p)), new TeamMembersToClient(oldMembers)));
                NetworkRegister.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) pPlayer), new TeamMembersToClient(new HashSet<>()));
            } else {
                // 进队
                savedData.joinGroup(uuid, teamId);
                oldMembers.forEach(p -> NetworkRegister.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) pLevel.getPlayerByUUID(p)), new TeamMembersToClient(oldMembers)));
                HashSet<UUID> newMembers = savedData.getMembers(teamId);
                newMembers.forEach(p -> {
                    ServerPlayer serverPlayer = (ServerPlayer) pLevel.getPlayerByUUID(p);
                    serverPlayer.displayClientMessage(MutableComponent.create(new TranslatableContents("join_team.tip", null, newNameParam)), false);
                    NetworkRegister.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new TeamMembersToClient(newMembers));
                });
            }
            savedData.setDirty();
        }
        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.success(itemstack);
    }
}
