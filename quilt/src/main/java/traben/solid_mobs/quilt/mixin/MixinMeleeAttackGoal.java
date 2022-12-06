package traben.solid_mobs.quilt.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static traben.solid_mobs.solidMobsMain.solidMobsConfigData;

@Mixin(MeleeAttackGoal.class)
public abstract class MixinMeleeAttackGoal extends Goal {

    @Final
    @Shadow
    protected
    PathAwareEntity mob;

//    @ModifyVariable(method = "attack(Lnet/minecraft/entity/LivingEntity;D)V", at = @At("STORE"), ordinal = 0, argsOnly = true)
//    private double sm$tryPush(double d) {
//        try {
//            if (solidMobsConfigData.canUseMod(this.mob.getWorld())
//                    && this.mob.getWorld().getOtherEntities(this.mob, this.mob.getBoundingBox().expand(0.1)).contains(this.mob.getTarget())
//            ) {
//                //will allow the hit if within collision distance
//                return Double.MIN_VALUE;//Double.MAX_VALUE;
//                //todo check this flip from max worked in 1.19.2 change
//            }
//        }catch(Exception e){
//            //
//        }
//        return d;
//    }

    @Shadow protected abstract void resetCooldown();

    @Shadow private int cooldown;

    @Inject(method = "attack", at = @At("TAIL"))
    private void sm$forceAttackOnTouch(LivingEntity target, double squaredDistance, CallbackInfo ci){
        //try attack again if mob is in contact with another but not within vanilla attack range
        //e.g. zombie on top of a villager cannot attack with vanilla reach
        if (this.cooldown <= 0) {
            //means we didn't attack and have cooled down
            try {
                if (solidMobsConfigData.canUseMod(this.mob.getWorld())
                        && this.mob.getWorld().getOtherEntities(this.mob, this.mob.getBoundingBox().expand(0.1)).contains(this.mob.getTarget())
                ) {
                    this.resetCooldown();
                    this.mob.swingHand(Hand.MAIN_HAND);
                    this.mob.tryAttack(target);
                }
            }catch(Exception e){
                //
            }
        }
    }
}
