package traben.solid_mobs.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static traben.solid_mobs.SolidMobsMain.LAST_PUSH_TIME;
import static traben.solid_mobs.SolidMobsMain.solidMobsConfigData;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntityClient extends LivingEntity {


    protected MixinPlayerEntityClient(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }


    @Inject(method = "interact", at = @At("HEAD"))
    private void sm$tryPushClient(Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (solidMobsConfigData.canUseMod(this.getWorld())
                && entity instanceof LivingEntity alive
                && solidMobsConfigData.allowShovingMobs

        ) {
            if (isSneaking()) {
                if (LAST_PUSH_TIME.containsKey(getUuid())) {
                    if (LAST_PUSH_TIME.get(getUuid()) + solidMobsConfigData.shoveAgainTimeInTicks < getWorld().getTime()) {
                        sm$pushThisClient(alive);
                    }
                } else {
                    sm$pushThisClient(alive);
                }
            }
        }
    }


    @Unique
    private void sm$pushThisClient(LivingEntity entity) {
        if (distanceTo(entity) < 2.5) {
            if (MinecraftClient.getInstance().player != null) {
                MinecraftClient.getInstance().player.swingHand(Hand.MAIN_HAND);
            }
        }
    }

}
