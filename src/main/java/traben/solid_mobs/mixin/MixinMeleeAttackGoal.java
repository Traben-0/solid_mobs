package traben.solid_mobs.mixin;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static traben.solid_mobs.solidMobsMain.solidMobsConfigData;

@Mixin(MeleeAttackGoal.class)
public abstract class MixinMeleeAttackGoal extends Goal {




    @Final
    @Shadow
    protected
    PathAwareEntity mob;



    @ModifyVariable(method = "attack", at = @At("STORE"), ordinal = 1)
    private double sm$tryPush(double d) {
        try {
            if (solidMobsConfigData.canUseMod(this.mob.getWorld())
                    && this.mob.getWorld().getOtherEntities(this.mob, this.mob.getBoundingBox().expand(0.1)).contains(this.mob.getTarget())
            ) {
                //will allow the hit if within collision distance
                return Double.MAX_VALUE;
            }
        }catch(Exception e){
            //
        }
        return d;
    }
}
