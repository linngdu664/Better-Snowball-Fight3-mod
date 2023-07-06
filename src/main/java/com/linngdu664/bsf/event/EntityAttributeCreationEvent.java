package com.linngdu664.bsf.event;

import com.linngdu664.bsf.Main;
import com.linngdu664.bsf.entity.BSFSnowGolemEntity;
import com.linngdu664.bsf.entity.EntityRegister;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EntityAttributeCreationEvent {
    @SubscribeEvent
    public static void createEntityAttribute(net.minecraftforge.event.entity.EntityAttributeCreationEvent event) {
        event.put(EntityRegister.BSF_SNOW_GOLEM.get(), BSFSnowGolemEntity.setAttributes());
    }
}
