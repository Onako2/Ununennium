package rs.majic.onako2.ununennium.mixin;

import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static rs.majic.onako2.ununennium.Ununennium.cache;

@Mixin(MinecraftServer.class)
public abstract class MixinMinecraftServer {
    @Inject(method = "tickServer", at = @At("TAIL"))
    public void tickServer(CallbackInfo ci) {
        cache.clear();
    }
}
