package com.linngdu664.bsf.network;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MessageRegister {
    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        Network.addNetworkMessage(ForwardConeParticlesToClient.class, ForwardConeParticlesToClient::encoder, ForwardConeParticlesToClient::decoder, ForwardConeParticlesToClient::messageConsumer);
        Network.addNetworkMessage(AmmoTypeToServer.class, AmmoTypeToServer::encoder, AmmoTypeToServer::decoder, AmmoTypeToServer::messageConsumer);
    }
}
