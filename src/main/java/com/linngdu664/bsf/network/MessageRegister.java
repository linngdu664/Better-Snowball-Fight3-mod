package com.linngdu664.bsf.network;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MessageRegister {
    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        Network.addNetworkMessage(AmmoRotateRight.class, AmmoRotateRight::buffer, AmmoRotateRight::new, AmmoRotateRight::handler);
        Network.addNetworkMessage(ForwardConeParticlesSender.class, ForwardConeParticlesSender::encoder, ForwardConeParticlesSender::decoder, ForwardConeParticlesSender::messageConsumer);
        Network.addNetworkMessage(AmmoTypeSendToServer.class, AmmoTypeSendToServer::encoder, AmmoTypeSendToServer::decoder, AmmoTypeSendToServer::messageConsumer);
    }
}
