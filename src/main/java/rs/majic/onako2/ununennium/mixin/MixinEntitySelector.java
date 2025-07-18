package rs.majic.onako2.ununennium.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rs.majic.onako2.ununennium.CacheEntry;

import java.util.List;
import java.util.function.Predicate;

import static rs.majic.onako2.ununennium.Ununennium.cache;

@Mixin(EntitySelector.class)
public abstract class MixinEntitySelector {
    @Shadow
    protected abstract <T extends Entity> List<T> sortAndLimit(Vec3 pos, List<T> entities);

    @Inject(method = "findEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/commands/arguments/selector/EntitySelector;isWorldLimited()Z", shift = At.Shift.BEFORE), cancellable = true)
    public void getCache(CommandSourceStack source, CallbackInfoReturnable<List<? extends Entity>> cir, @Local Vec3 vec3, @Local Predicate<Entity> predicate) {
        // create a potential cache hit and continue execution if isn't the case
        CacheEntry potHit = new CacheEntry(source, source.getLevel(), vec3, predicate);
        List<Entity> entityList = cache.get(potHit);
        if (entityList != null) {
            boolean isHit = entityList.stream().allMatch(predicate);

            if (isHit) {
                // LOGGER.info("CACHE HIT {}, {}", potHit.toString(), entityList);
                cir.setReturnValue(this.sortAndLimit(vec3, entityList));
            } else {
                cache.remove(potHit);
            }
        }
    }

    @Inject(method = "findEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/commands/arguments/selector/EntitySelector;sortAndLimit(Lnet/minecraft/world/phys/Vec3;Ljava/util/List;)Ljava/util/List;"))
    public void setCache(CommandSourceStack source, CallbackInfoReturnable<List<? extends Entity>> cir, @Local List<Entity> list, @Local Vec3 vec3, @Local Predicate<Entity> predicate) {
        // this will only be executed if there is no cache because it is executed after getCache
        CacheEntry entry = new CacheEntry(source, source.getLevel(), vec3, predicate);
        cache.put(entry, list);
    }
}
