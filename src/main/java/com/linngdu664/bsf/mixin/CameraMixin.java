package com.linngdu664.bsf.mixin;

import com.linngdu664.bsf.client.screenshake.ScreenshakeHandler;
import com.linngdu664.bsf.event.ClientForgeEvents;
import com.linngdu664.bsf.util.BSFConfig;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public class CameraMixin {
    @Inject(method = {"setup"}, at = {@At("RETURN")})
    private void bsf$Screenshake(BlockGetter area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
        if (BSFConfig.screenshakeIntensity > 0.0) {
            ScreenshakeHandler.cameraTick((Camera)(Object) this, ClientForgeEvents.BSF_RANDOM_SOURCE);
        }
    }
}
