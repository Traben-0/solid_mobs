package traben.solid_mobs.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import traben.solid_mobs.SolidMobsMain;

import static traben.solid_mobs.SolidMobsMain.solidMobsConfigData;

@Mixin(LivingEntity.class)
public abstract class MixinDismountOnTop extends Entity {

    public MixinDismountOnTop() {super(null, null);}

    @Inject(method = "onDismounted", at = @At("HEAD"), cancellable = true)
    private void sm$onDismounted(final Entity vehicle, final CallbackInfo ci) {
        World world = vehicle.getWorld();
        if (solidMobsConfigData.canUseMod(world)
                && !SolidMobsMain.isExemptEntity(vehicle)
                && !vehicle.isRemoved()) {
            ci.cancel();

            this.requestTeleportAndDismount(
                    vehicle.getX(),
                    vehicle.getBoundingBox().maxY + 0.01,
                    vehicle.getZ());
        }
    }
}
