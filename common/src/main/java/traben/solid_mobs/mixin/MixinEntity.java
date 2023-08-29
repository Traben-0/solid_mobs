package traben.solid_mobs.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import traben.solid_mobs.SolidMobsMain;

import java.util.List;

import static traben.solid_mobs.SolidMobsMain.solidMobsConfigData;

@Mixin(Entity.class)
public abstract class MixinEntity {

    @Shadow
    public abstract EntityType<?> getType();

    @Shadow
    public abstract boolean isInvisible();

    @Shadow
    public abstract World getEntityWorld();

    @Shadow
    private Box boundingBox;

    @Shadow
    public abstract Vec3d getVelocity();

    @Shadow
    public abstract double getY();

    @Shadow
    public abstract Vec3d getPos();

    @Shadow
    public abstract float getHeight();

    @Shadow
    public abstract World getWorld();

    @Shadow
    public abstract boolean isAlive();

    @Shadow
    public abstract boolean isLiving();


    @Shadow
    public abstract boolean isSneaking();


    @Shadow
    public abstract boolean isPlayer();

    @Inject(method = "isCollidable", cancellable = true, at = @At("RETURN"))
    private void sm$collisionOverride(CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue() && solidMobsConfigData.canUseMod(this.getWorld())) {
            if (this.isLiving()) {
                //System.out.println(getType().toString());
                //14:58:14.077
                //[STDOUT]: entity.minecraft.cow
                //14:58:14.078
                //[STDOUT]: entity.minecraft.chicken
                //14:58:14.078
                //[STDOUT]: entity.minecraft.sheep
                //14:58:14.080
                //[STDOUT]: entity.minecraft.vex
                boolean returnValue;

                //ignore if in exemption list
                //lets ignore invis stuff, could be problematic with some builds
                if (SolidMobsMain.isExemptType(getType())) {
                    //System.out.println("true for "+getName().getString());
                    returnValue = false;
                } else {//return false if invisible
                    if (!solidMobsConfigData.allowNonSavingEntityCollisions && !getType().isSaveable() && !getType().equals(EntityType.PLAYER)) {
                        returnValue = false;
                    } else if (solidMobsConfigData.allowInvisibleCollisions) {
                        returnValue = isAlive();
                    } else {
                        returnValue = isAlive() && !isInvisible();
                    }
                    //returnValue = isAlive() && !(!solidMobsConfigData.allowInvisibleCollisions && isInvisible());
                }
                cir.setReturnValue(returnValue);
            }
        }
    }

    @Inject(method = "collidesWith", cancellable = true, at = @At("RETURN"))
    private void sm$collisionOverride(Entity other, CallbackInfoReturnable<Boolean> cir) {
        if (solidMobsConfigData.canUseMod(this.getWorld())) {
            boolean collides = cir.getReturnValue();
            EntityType<?> thisType = getType();
            if (SolidMobsMain.isExemptType(thisType)) { // || EXEMPT_ENTITIES.contains(other.getType().toString())) {
                collides = false;
            } else if (isPlayer() && other.isPlayer() && !solidMobsConfigData.allowPlayerCollisions) {
                //only affect player on player collisions we still need other things to collide with players so PLAYER cannot be in exempt list
                collides = false;
            }
            if (solidMobsConfigData.platformMode && collides) {
                if (isSneaking()) {
                    SolidMobsMain.registerCollision(thisType.toString(), other.getType().toString(), false);
                    cir.setReturnValue(false);
                } else {
                    collides = getY() + 0.01 >= other.getY() + other.getBoundingBox().getYLength();
                    SolidMobsMain.registerCollision(thisType.toString(), other.getType().toString(), collides);
                    cir.setReturnValue(collides);
                }
            } else {
                SolidMobsMain.registerCollision(thisType.toString(), other.getType().toString(), collides);
                cir.setReturnValue(collides);
            }
        }
    }


    @Inject(method = "tick", at = @At("TAIL"))
    private void sm$moveWalkingRider(CallbackInfo ci) {
        if (solidMobsConfigData.canUseMod(this.getWorld()) && this.isLiving()) {
            if (!SolidMobsMain.isExemptType(getType())) {

                if ((!getType().equals(EntityType.SLIME) && !getType().equals(EntityType.MAGMA_CUBE)) || !solidMobsConfigData.bouncySlimes) {//move with mob
                    try {
                        @SuppressWarnings("DataFlowIssue") List<Entity> colliders = getEntityWorld().getOtherEntities(((Entity) (Object) this), boundingBox.expand(-0.03, 0.1, -0.03));
                        if (!colliders.isEmpty()) {
                            //remove any vanilla passengers from this check
//                List<Entity> vanillaRiders = getPassengerList();
//                for (Entity rider:
//                     vanillaRiders) {
//                    colliders.remove(rider);
//                }
                            for (Entity possibleStandingMob : colliders) {
                                if (possibleStandingMob.isLiving()
                                        && possibleStandingMob.getY() >= this.getY() + this.getHeight() - 0.06
                                    //&& this.collidesWith(possibleStandingMob)
                                ) {
                                    //apply movement to this mob
                                    if (possibleStandingMob.isSneaking()) {
                                        if (!solidMobsConfigData.platformMode) {
                                            //if sneaking snap player to centre of mob
                                            // prevents falling off cause sneaking
                                            double modifyY;
                                            if (getType().equals(EntityType.GHAST)) {
                                                modifyY = 0.08;
                                            } else {
                                                modifyY = 1.0 / 16;
                                            }
                                            possibleStandingMob.setPosition(
                                                    this.getPos().getX(),
                                                    this.getPos().getY() + getHeight() + modifyY,
                                                    this.getPos().getZ());
                                        }
                                    } else {//not sneaking
                                        possibleStandingMob.addVelocity(
                                                this.getVelocity().getX() * 0.833333333333,
                                                this.getVelocity().getY() * 0.833333333333,
                                                this.getVelocity().getZ() * 0.833333333333);
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        //
                    }
                }// else {
//                List<Entity> colliders = getEntityWorld().getOtherEntities(((Entity) (Object) this), boundingBox.expand(-0.03, 1, -0.03));
//                if (!colliders.isEmpty()) {
//                    for (Entity possibleBouncingMob :
//                            colliders) {
//                        if (possibleBouncingMob.getY() >= this.getY() + this.getHeight()
//                            && !possibleBouncingMob.isSneaking()
//                        ) {
//                            bounceUp(possibleBouncingMob);
//                        }
//                    }
//                }
//                }
            }
        }
    }


}
