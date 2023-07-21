package com.linngdu664.bsf.event;

import com.linngdu664.bsf.Main;
import com.linngdu664.bsf.item.ItemRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LoadLootTableEvent {
    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        if (event.getName().equals(new ResourceLocation("minecraft:chests/shipwreck_treasure")) || event.getName().equals(new ResourceLocation("minecraft:chests/igloo_chest"))) {
            LootTable lootTable = event.getTable();
            lootTable.addPool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1.0F))
                    .setBonusRolls(ConstantValue.exactly(0.0F))
                    .add(LootItem.lootTableItem(ItemRegister.SNOWBALL_CANNON_UPGRADE_SMITHING_TEMPLATE.get()))
                    .build());
            event.setTable(lootTable);
        } else if (event.getName().equals(new ResourceLocation("minecraft:chests/pillager_outpost"))) {
            LootTable lootTable = event.getTable();
            lootTable.addPool(LootPool.lootPool()
                    .setRolls(BinomialDistributionGenerator.binomial(2, 0.4F))
                    .setBonusRolls(ConstantValue.exactly(0.0F))
                    .add(LootItem.lootTableItem(ItemRegister.SNOWBALL_CANNON_UPGRADE_SMITHING_TEMPLATE.get()))
                    .build());
            event.setTable(lootTable);
        }
    }
}
