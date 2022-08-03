package traben.solid_mobs.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static traben.solid_mobs.solidMobsMain.lastPushTime;
import static traben.solid_mobs.solidMobsMain.solidMobsConfigData;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntityClient extends LivingEntity {


    @Shadow @Final private ItemCooldownManager itemCooldownManager;

    protected MixinPlayerEntityClient(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }



    @Inject(method = "interact", at = @At("HEAD"))
    private void sm$tryPush(Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (solidMobsConfigData.canUseMod(this.getWorld())
                && entity instanceof LivingEntity alive
                && solidMobsConfigData.allowShovingMobs

        ) {
            if(isSneaking()) {
                if (lastPushTime.containsKey(getUuid())) {
                    if (lastPushTime.get(getUuid()) + solidMobsConfigData.shoveAgainTimeInTicks < world.getTime()) {
                        sm$pushThisClient(alive);
                    }
                } else {
                    sm$pushThisClient(alive);
                }
            }
        }
    }

    
    private void sm$pushThisClient(LivingEntity entity){
        if (distanceTo(entity) < 2.5){
                if (MinecraftClient.getInstance().player != null){
                    MinecraftClient.getInstance().player.swingHand(Hand.MAIN_HAND);
                }
        }
    }

}
