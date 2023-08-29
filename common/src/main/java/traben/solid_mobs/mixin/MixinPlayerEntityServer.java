package traben.solid_mobs.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import traben.solid_mobs.SolidMobsMain;

import static traben.solid_mobs.SolidMobsMain.LAST_PUSH_TIME;
import static traben.solid_mobs.SolidMobsMain.solidMobsConfigData;

@Mixin(PlayerEntity.class)
public abstract class MixinPlayerEntityServer extends LivingEntity {



    @Shadow public abstract void remove(RemovalReason reason);

    protected MixinPlayerEntityServer(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }


    @Inject(method = "interact", at = @At("HEAD"),cancellable = true)
    private void sm$tryPushServer(Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (entity == null || entity.isRemoved() || getWorld().isClient()) return;
        if (solidMobsConfigData.canUseMod(this.getWorld())
                && entity instanceof LivingEntity alive
                && solidMobsConfigData.allowShovingMobs

        ) {
            if (isSneaking()) {
                if (LAST_PUSH_TIME.containsKey(getUuid())) {
                    if (LAST_PUSH_TIME.get(getUuid()) + solidMobsConfigData.shoveAgainTimeInTicks < getWorld().getTime()) {
                        if (sm$pushThisServer(alive)) cir.setReturnValue(ActionResult.PASS);

                    }
                } else {
                    if (sm$pushThisServer(alive)) cir.setReturnValue(ActionResult.PASS);
                }
            }
        }
    }


    @Unique
    private boolean sm$pushThisServer(LivingEntity entity) {
        //if (entity == null || entity.isRemoved()) return;
        if (distanceTo(entity) < 2.5) {
//            try {
                if (entity instanceof VillagerEntity villager) {
                    villager.takeKnockback(0.15d, -(entity.getX() - this.getX()), -(entity.getZ() - this.getZ()));
                    LAST_PUSH_TIME.put(getUuid(), getWorld().getTime() + (solidMobsConfigData.shoveAgainTimeInTicks /* * 2L*/));
                    swingHand(Hand.MAIN_HAND);
                    return true;
                } else if (entity instanceof HostileEntity || entity instanceof Angerable) {
                    //noinspection DataFlowIssue
                    entity.damage(entity.getDamageSources().playerAttack((PlayerEntity) ((Object) this)), 0);
                    //enemy.setAttacker((PlayerEntity) (Object)this);
                    //enemy.takeKnockback(0.5d, -(entity.getX() - this.getX()), -(entity.getZ() - this.getZ()));
                    LAST_PUSH_TIME.put(getUuid(), getWorld().getTime() + (solidMobsConfigData.shoveAgainTimeInTicks /* * 5L*/));
                    swingHand(Hand.MAIN_HAND);
                    return true;
                } else if (!SolidMobsMain.isExemptEntity(entity)) {
                    entity.takeKnockback(0.3d, -(entity.getX() - this.getX()), -(entity.getZ() - this.getZ()));
                    LAST_PUSH_TIME.put(getUuid(), getWorld().getTime());
                    swingHand(Hand.MAIN_HAND);
                    return true;
                }
//            } catch (Exception ignored) {
//
//            }
        }
        return false;
    }


}
