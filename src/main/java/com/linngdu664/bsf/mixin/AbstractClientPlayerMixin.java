package com.linngdu664.bsf.mixin;

import com.linngdu664.bsf.item.weapon.SnowballCannonItem;
import com.linngdu664.bsf.registry.ItemRegister;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Deprecated
@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin extends Player {
    public AbstractClientPlayerMixin(ClientLevel pClientLevel, GameProfile pGameProfile) {
        super(pClientLevel, pClientLevel.getSharedSpawnPos(), pClientLevel.getSharedSpawnAngle(), pGameProfile);
    }

    @Inject(method = "getFieldOfViewModifier", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/AbstractClientPlayer;getUseItem()Lnet/minecraft/world/item/ItemStack;"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
    private void injectedBeforeInvokeGetUseItemGetFieldOfViewModifier(CallbackInfoReturnable<Float> cir, float f) {
        ItemStack itemstack = this.getUseItem();
        if (this.isUsingItem() && itemstack.getItem() instanceof SnowballCannonItem) {
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
            cir.setReturnValue(net.minecraftforge.client.ForgeHooksClient.getFieldOfViewModifier(this, f));
        }
    }
}
