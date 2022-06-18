package traben.solid_mobs.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.VillagerEntity;
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

import static traben.solid_mobs.solidMobsMain.*;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntity extends LivingEntity {


    @Shadow @Final private ItemCooldownManager itemCooldownManager;

    protected MixinPlayerEntity(EntityType<? extends LivingEntity> entityType, World world) {
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
                        sm$pushThis(alive);
                    }
                } else {
                    sm$pushThis(alive);
                }
            }
        }
    }

    private void sm$pushThis(LivingEntity entity){
        if (distanceTo(entity) < 2.5){
            if(world.isClient){
                if (MinecraftClient.getInstance().player != null){
                    MinecraftClient.getInstance().player.swingHand(Hand.MAIN_HAND);
                }
            }else{
                if (entity instanceof VillagerEntity villager) {
                    villager.takeKnockback(0.15d, -(entity.getX() - this.getX()), -(entity.getZ() - this.getZ()));
                    lastPushTime.put(getUuid(), world.getTime() + (solidMobsConfigData.shoveAgainTimeInTicks * 2L));
                    swingHand(Hand.MAIN_HAND);
                } else if (entity instanceof HostileEntity enemy) {
                    enemy.damage(DamageSource.player((PlayerEntity) (Object) this), 0);
                    //enemy.setAttacker((PlayerEntity) (Object)this);
                    //enemy.takeKnockback(0.5d, -(entity.getX() - this.getX()), -(entity.getZ() - this.getZ()));
                    lastPushTime.put(getUuid(), world.getTime() + (solidMobsConfigData.shoveAgainTimeInTicks * 5L));
                    swingHand(Hand.MAIN_HAND);
                } else if (!EXEMPT_ENTITIES.contains(entity.getType())) {
                    entity.takeKnockback(0.3d, -(entity.getX() - this.getX()), -(entity.getZ() - this.getZ()));
                    lastPushTime.put(getUuid(), world.getTime());
                    swingHand(Hand.MAIN_HAND);
                }
            }
        }
    }

}
