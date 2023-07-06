package com.linngdu664.bsf.util;

import com.linngdu664.bsf.Main;
import com.linngdu664.bsf.item.ItemRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeTabRegister {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Main.MODID);
    // Creates a creative tab with the id "examplemod:example_tab" for the example item, that is placed after the combat tab
    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = CREATIVE_TABS.register("example_tab", () -> net.minecraft.world.item.CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ItemRegister.EXPLOSIVE_SNOWBALL.get().getDefaultInstance())
            .title(MutableComponent.create(new TranslatableContents("itemGroup.bsf_group", null, new Object[0])))
            .displayItems((parameters, output) -> {
                output.accept(ItemRegister.SMOOTH_SNOWBALL.get());   // Add the example item to the tab. For your own tabs, this method is preferred over the event
            }).build());
}
