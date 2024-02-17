package com.linngdu664.bsf.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashSet;
import java.util.UUID;
import java.util.function.Supplier;

public class TeamMembersToClient {
    public static HashSet<UUID> staticMembers = null;
    private final HashSet<UUID> members;

    public TeamMembersToClient(HashSet<UUID> members) {
        this.members = members;
    }

    public static void encoder(TeamMembersToClient message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.members.size());
        for (UUID uuid : message.members) {
            buffer.writeUUID(uuid);
        }
    }

    public static TeamMembersToClient decoder(FriendlyByteBuf buffer) {
        int cnt = buffer.readInt();
        HashSet<UUID> hashSet = new HashSet<>();
        for (int i = 0; i < cnt; i++) {
            hashSet.add(buffer.readUUID());
        }
        return new TeamMembersToClient(hashSet);
    }

    public static void messageConsumer(TeamMembersToClient message, Supplier<NetworkEvent.Context> ctxSupplier) {
        NetworkEvent.Context context = ctxSupplier.get();
        context.enqueueWork(() -> DistExecutor.unsafeCallWhenOn(Dist.CLIENT, () -> () -> handlePacket(message.members)));
        context.setPacketHandled(true);
    }

    @OnlyIn(Dist.CLIENT)
    public static boolean handlePacket(HashSet<UUID> members) {
        staticMembers = members;
        return true;
    }
}
