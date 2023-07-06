package com.linngdu664.bsf.network;

import com.linngdu664.bsf.item.snowball.AbstractBSFSnowballItem;
import com.linngdu664.bsf.item.tank.AbstractSnowballTankItem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkEvent;

import java.util.Vector;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class AmmoRotateRight {
    final int type, pressedMs;

    public AmmoRotateRight(int type, int pressedMs) {
        this.type = type;
        this.pressedMs = pressedMs;
    }

    public AmmoRotateRight(FriendlyByteBuf buffer) {
        this.type = buffer.readInt();
        this.pressedMs = buffer.readInt();
    }

    public static void buffer(AmmoRotateRight message, FriendlyByteBuf buffer) {
        buffer.writeInt(message.type);
        buffer.writeInt(message.pressedMs);
    }

    public static void handler(AmmoRotateRight message, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> pressAction(context.getSender(), message.type, message.pressedMs));
        context.setPacketHandled(true);
    }

    @SuppressWarnings("deprecation")
    public static void pressAction(Player player, int type, int pressedMs) {
        Level level = player.level();
        if (!level.hasChunkAt(player.blockPosition()))
            return;
        if (type == 0) {
            if (!level.isClientSide) {
                execute(player);
                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.DISPENSER_DISPENSE, SoundSource.PLAYERS, 1.0F, 1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
            }
        }
    }

    @SubscribeEvent
    public static void registerMessage(FMLCommonSetupEvent event) {
        Network.addNetworkMessage(AmmoRotateRight.class, AmmoRotateRight::buffer, AmmoRotateRight::new, AmmoRotateRight::handler);
    }

    private static void execute(Player player) {
        Inventory inventory = player.getInventory();
        ItemStack itemStack;
        Vector<Integer> list = new Vector<>();
        boolean haveTank = false;
        for (int j = 0; j < inventory.getContainerSize(); j++) {
            itemStack = inventory.getItem(j);
            if (itemStack.getItem() instanceof AbstractSnowballTankItem tank && (tank.getSnowball().canBeLaunchedByNormalWeapon() || tank.getSnowball().canBeLaunchedByMachineGun()) && !listHaveItem(inventory, list, itemStack)) {
                list.add(j);
                haveTank = true;
            }
        }
        if (!haveTank) {
            for (int i = 0; i < inventory.getContainerSize(); i++) {
                itemStack = inventory.getItem(i);
                if (itemStack.getItem() instanceof AbstractBSFSnowballItem snowball && snowball.canBeLaunchedByNormalWeapon() && !listHaveItem(inventory, list, itemStack)) {
                    list.add(i);
                }
            }
        }
        for (int i = 0; i < list.size() - 1; i++) {
            exchangeItem(inventory, list.get(i), list.get(i + 1));
            int c = list.get(i);
            list.set(i, list.get(i + 1));
            list.set(i + 1, c);
        }
        player.displayClientMessage(MutableComponent.create(new TranslatableContents("current_ammunition.tip", null, new Object[0])).append(MutableComponent.create(new TranslatableContents("item.bsf." + inventory.getItem(list.get(list.size() - 1)).getItem(), null, new Object[0]))), false);
    }

    private static void exchangeItem(Inventory inventory, int a, int b) {
        ItemStack ia = inventory.getItem(a);
        ItemStack ib = inventory.getItem(b);
        if (!ia.isEmpty() && !ib.isEmpty()) {
            inventory.setItem(a, ib);
            inventory.setItem(b, ia);
        }
    }

    private static boolean listHaveItem(Inventory inventory, Vector<Integer> list, ItemStack itemStack) {
        for (Integer integer : list) {
            if (contrastItem(inventory, integer, itemStack)) {
                return true;
            }
        }
        return false;
    }

    private static boolean contrastItem(Inventory inventory, int a, ItemStack ib) {
        ItemStack ia = inventory.getItem(a);
        if (!ia.isEmpty() && !ib.isEmpty()) {
            return ia.getItem().getDescriptionId().equals(ib.getItem().getDescriptionId());
        }
        return false;
    }
}
