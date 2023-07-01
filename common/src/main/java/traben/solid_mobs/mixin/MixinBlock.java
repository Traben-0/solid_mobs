package traben.solid_mobs.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

import static traben.solid_mobs.solidMobsMain.solidMobsConfigData;

@Mixin(Block.class)
public abstract class MixinBlock {


    @Shadow public abstract BlockState getDefaultState();

    @Inject(method = "onLandedUpon", cancellable = true, at = @At("HEAD"))
    private void etf$fallDamageRedirect(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance, CallbackInfo ci) {
        if(solidMobsConfigData.canUseMod(world)) {
            try {
                boolean cancel = false;
                if (state == null)
                    state = this.getDefaultState();
                if (state.isOf(Blocks.AIR) || state.isOf(Blocks.CAVE_AIR) || state.isOf(Blocks.VOID_AIR)) {
                    if (solidMobsConfigData.fallDamageSharedWithLandedOnMob || solidMobsConfigData.bouncySlimes) {
                        //vanilla assumes fall is always into block find and entity to halve fall damage to
                        try {
                            List<Entity> fellOnEntities = world.getOtherEntities(entity, entity.getBoundingBox().offset(0, -0.5/*entity.getBoundingBox().getYLength()*/, 0));
                            if (!fellOnEntities.isEmpty()) {
                                for (Entity cushion : fellOnEntities) {
                                    //                            for (Entity cushion :
//                                    fellOnEntities) {
                                    if ((cushion.getType().equals(EntityType.SLIME) || cushion.getType().equals(EntityType.MAGMA_CUBE))
                                            && solidMobsConfigData.bouncySlimes
                                            && !entity.bypassesLandingEffects()) {
                                        //bounceUp(entity);
                                        cancel = true;

                                    } else if (solidMobsConfigData.fallDamageSharedWithLandedOnMob) {
                                        //just apply to first found no need to be picky

                                        //get damage source incase of possible AI need to retaliate or flee damage source
                                        DamageSource source;

                                        if (entity instanceof PlayerEntity plyr) {
                                            source = DamageSource.player(plyr);
                                            entity.handleFallDamage(fallDistance, 1.0F - solidMobsConfigData.getFallAbsorbAmount(),  DamageSource.FALL);
                                        } else if (entity instanceof LivingEntity alive) {
                                            source = DamageSource.mob(alive);
                                            entity.handleFallDamage(fallDistance, 1.0F - solidMobsConfigData.getFallAbsorbAmount(),  DamageSource.FALL);
                                        } else {
                                            source = DamageSource.FALL;
                                        }
                                        if(cushion instanceof LivingEntity)
                                            cushion.handleFallDamage(fallDistance, solidMobsConfigData.getFallAbsorbAmount(), source);
                                        cancel = true;
                                    }

                                }
                            }
                        } catch (Exception e) {
                            //
                        }
                    }
                }
                if (cancel) {
                    ci.cancel();
                }
            }catch(Exception | NoSuchFieldError ignored){}
        }
    }

    @Inject(method = "onEntityLand", cancellable = true, at = @At("HEAD"))
    private void etf$bounceRedirect(BlockView world, Entity entity, CallbackInfo ci) {
        if(solidMobsConfigData.canUseMod(entity.getWorld())) {
            if (this.getDefaultState().isOf(Blocks.AIR) || this.getDefaultState().isOf(Blocks.CAVE_AIR) || this.getDefaultState().isOf(Blocks.VOID_AIR)) {
                if (solidMobsConfigData.bouncySlimes) {
                    //vanilla assumes fall is always into block find and entity to halve fall damage to
                    try {
                        List<Entity> fellOnEntities = entity.getWorld().getOtherEntities(entity, entity.getBoundingBox().offset(0, -0.5/*entity.getBoundingBox().getYLength()*/, 0));
                        if (!fellOnEntities.isEmpty()) {
                            for (Entity cushion : fellOnEntities) {
                                //                            for (Entity cushion :
//                                    fellOnEntities) {
                                if ((cushion.getType().equals(EntityType.SLIME) || cushion.getType().equals(EntityType.MAGMA_CUBE))
                                        && !entity.bypassesLandingEffects()) {
                                    bounceUp(entity);
                                    ci.cancel();
                                    break;
                                }
                            }
                        }
                    }catch(Exception e){
                        //
                    }
                }
            }
        }
    }


    //copy of vanilla slimeblock bounce code modified to only change velocity vertically
    private void bounceUp(Entity entity) {
        Vec3d vec3d = entity.getVelocity();
        if (vec3d.y < 0.0D) {
            double d = entity instanceof LivingEntity ? 1.0D : 0.8D;
            entity.setVelocity(vec3d.x,  Math.max(-vec3d.y,vec3d.y) * d, vec3d.z);
        }

    }

}
