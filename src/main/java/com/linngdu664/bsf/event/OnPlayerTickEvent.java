package com.linngdu664.bsf.event;

import com.linngdu664.bsf.item.misc.IceSkatesItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.UUID;

public class OnPlayerTickEvent {
    public static final UUID SKATES_SPEED_ID = UUID.fromString("00a3641b-33e0-4022-8d92-1c7b74c380b0");

    private void clearSpeedEffect(Player player) {
        if (player.getAttributes().getInstance(Attributes.MOVEMENT_SPEED).getModifier(SKATES_SPEED_ID) != null) {
            player.getAttributes().getInstance(Attributes.MOVEMENT_SPEED).removeModifier(SKATES_SPEED_ID);
            player.setMaxUpStep(0.6f);
        }
    }

    private void addSpeedGoodEffect(Player player) {
        AttributeModifier skatesSpeed = new AttributeModifier(SKATES_SPEED_ID, "skates_speed", 0.15, AttributeModifier.Operation.ADDITION);
        if (!player.getAttributes().getInstance(Attributes.MOVEMENT_SPEED).hasModifier(skatesSpeed)) {
            player.getAttributes().getInstance(Attributes.MOVEMENT_SPEED).addPermanentModifier(skatesSpeed);
            player.setMaxUpStep(2);
        }
    }

    private void addSpeedBadEffect(Player player) {
        AttributeModifier skatesSpeed = new AttributeModifier(SKATES_SPEED_ID, "skates_speed", -0.25, AttributeModifier.Operation.MULTIPLY_BASE);
        if (!player.getAttributes().getInstance(Attributes.MOVEMENT_SPEED).hasModifier(skatesSpeed)) {
            player.getAttributes().getInstance(Attributes.MOVEMENT_SPEED).addPermanentModifier(skatesSpeed);
            player.setMaxUpStep(0.5f);
        }
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            Player player = event.player;
            ItemStack shoes = player.getItemBySlot(EquipmentSlot.FEET);
            if (!shoes.isEmpty()) {
                if (shoes.getItem() instanceof IceSkatesItem && player.isSprinting() && player.onGround()) {
                    Level level = player.level();
                    BlockPos pos = player.blockPosition().below();
                    if (level.getBlockState(pos).is(BlockTags.ICE)) {
                        level.addParticle(ParticleTypes.SNOWFLAKE, player.getX(), player.getEyeY() - 1.4, player.getZ(), 0, 0, 0);
                        addSpeedGoodEffect(player);
                    } else {
                        addSpeedBadEffect(player);
                    }
                } else {
                    clearSpeedEffect(player);
                }
            }
        }
    }
}
