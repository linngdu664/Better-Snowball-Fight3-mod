package com.linngdu664.bsf.registry;

import com.linngdu664.bsf.Main;
import com.linngdu664.bsf.network.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class NetworkRegister {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(Main.MODID, "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
    private static int messageID = 0;

    public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
        PACKET_HANDLER.registerMessage(messageID++, messageType, encoder, decoder, messageConsumer);
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        NetworkRegister.addNetworkMessage(ForwardConeParticlesToClient.class, ForwardConeParticlesToClient::encoder, ForwardConeParticlesToClient::decoder, ForwardConeParticlesToClient::messageConsumer);
        NetworkRegister.addNetworkMessage(ForwardRaysParticlesToClient.class, ForwardRaysParticlesToClient::encoder, ForwardRaysParticlesToClient::decoder, ForwardRaysParticlesToClient::messageConsumer);
        NetworkRegister.addNetworkMessage(AmmoTypeToServer.class, AmmoTypeToServer::encoder, AmmoTypeToServer::decoder, AmmoTypeToServer::messageConsumer);
        NetworkRegister.addNetworkMessage(TeamMembersToClient.class, TeamMembersToClient::encoder, TeamMembersToClient::decoder, TeamMembersToClient::messageConsumer);
        NetworkRegister.addNetworkMessage(ToggleMovingSoundToClient.class, ToggleMovingSoundToClient::encoder, ToggleMovingSoundToClient::decoder, ToggleMovingSoundToClient::messageConsumer);
        NetworkRegister.addNetworkMessage(SwitchSoundToServer.class, SwitchSoundToServer::encoder, SwitchSoundToServer::decoder, SwitchSoundToServer::messageConsumer);
        NetworkRegister.addNetworkMessage(VectorInversionParticleToClient.class, VectorInversionParticleToClient::encoder, VectorInversionParticleToClient::decoder, VectorInversionParticleToClient::messageConsumer);
        NetworkRegister.addNetworkMessage(SubspaceSnowballParticlesToClient.class, SubspaceSnowballParticlesToClient::encoder, SubspaceSnowballParticlesToClient::decoder, SubspaceSnowballParticlesToClient::messageConsumer);
        NetworkRegister.addNetworkMessage(SubspaceSnowballReleaseTraceParticlesToClient.class, SubspaceSnowballReleaseTraceParticlesToClient::encoder, SubspaceSnowballReleaseTraceParticlesToClient::decoder, SubspaceSnowballReleaseTraceParticlesToClient::messageConsumer);
        NetworkRegister.addNetworkMessage(ImplosionSnowballCannonParticleToClient.class, ImplosionSnowballCannonParticleToClient::encoder, ImplosionSnowballCannonParticleToClient::decoder, ImplosionSnowballCannonParticleToClient::messageConsumer);
        NetworkRegister.addNetworkMessage(SwitchTweakerStatusModeToServer.class, SwitchTweakerStatusModeToServer::encoder, SwitchTweakerStatusModeToServer::decoder, SwitchTweakerStatusModeToServer::messageConsumer);
        NetworkRegister.addNetworkMessage(SwitchTweakerTargetModeToServer.class, SwitchTweakerTargetModeToServer::encoder, SwitchTweakerTargetModeToServer::decoder, SwitchTweakerTargetModeToServer::messageConsumer);
        NetworkRegister.addNetworkMessage(ScreenshakeToClient.class, ScreenshakeToClient::encoder, ScreenshakeToClient::decoder, ScreenshakeToClient::messageConsumer);
    }
}
