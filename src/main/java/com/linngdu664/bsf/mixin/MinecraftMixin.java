package com.linngdu664.bsf.mixin;

import com.linngdu664.bsf.item.tool.TeamLinkerItem;
import com.linngdu664.bsf.network.TeamMembersToClient;
import com.mojang.blaze3d.platform.WindowEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.util.thread.ReentrantBlockableEventLoop;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.OwnableEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin extends ReentrantBlockableEventLoop<Runnable> implements WindowEventHandler, net.minecraftforge.client.extensions.IForgeMinecraft {
    public MinecraftMixin(String pName) {
        super(pName);
    }

    @Inject(method = "shouldEntityAppearGlowing", at = @At(value = "HEAD"), cancellable = true)
    private void shouldEntityAppearGlowing(Entity pEntity, CallbackInfoReturnable<Boolean> cir) {
        if (TeamLinkerItem.shouldShowHighlight && (TeamMembersToClient.staticMembers.contains(pEntity.getUUID()) || pEntity instanceof OwnableEntity ownable && TeamMembersToClient.staticMembers.contains(ownable.getOwnerUUID()))) {
            cir.setReturnValue(true);
        }
    }
}
