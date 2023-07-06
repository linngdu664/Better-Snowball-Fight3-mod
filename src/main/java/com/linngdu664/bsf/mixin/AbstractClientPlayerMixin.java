package com.linngdu664.bsf.mixin;

import com.linngdu664.bsf.item.ItemRegister;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin extends Player {
    public final ClientLevel clientLevel;

    public AbstractClientPlayerMixin(ClientLevel pClientLevel, GameProfile pGameProfile) {
        super(pClientLevel, pClientLevel.getSharedSpawnPos(), pClientLevel.getSharedSpawnAngle(), pGameProfile);
        this.clientLevel = pClientLevel;
    }

    @Inject(method = "getFieldOfViewModifier", at = @At("HEAD"), cancellable = true)
    private void getFieldOfViewModifier(CallbackInfoReturnable<Float> cir) {
        ItemStack itemstack = this.getUseItem();
        float f = 1.0F;
        if (itemstack.is(ItemRegister.SNOWBALL_CANNON.get()) || itemstack.is(ItemRegister.FREEZING_SNOWBALL_CANNON.get()) || itemstack.is(ItemRegister.POWERFUL_SNOWBALL_CANNON.get())) {
            if (this.getAbilities().flying) {
                f *= 1.1F;
            }
            f *= ((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED) / this.getAbilities().getWalkingSpeed() + 1.0F) / 2.0F;
            if (this.getAbilities().getWalkingSpeed() == 0.0F || Float.isNaN(f) || Float.isInfinite(f)) {
                f = 1.0F;
            }
            if (this.isUsingItem()) {
                int i = this.getTicksUsingItem();
                float f1 = (float) i / 20.0F;
                if (f1 > 1.0F) {
                    f1 = 1.0F;
                } else {
                    f1 *= f1;
                }
                if (itemstack.is(ItemRegister.POWERFUL_SNOWBALL_CANNON.get())) {
                    f *= 1.0F - f1 * 0.5F;
                } else {
                    f *= 1.0F - f1 * 0.3F;
                }
            }
            cir.setReturnValue(net.minecraftforge.client.ForgeHooksClient.getFieldOfViewModifier(this, f));
        }
    }
}
