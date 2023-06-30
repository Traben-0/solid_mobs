package traben.solid_mobs.config;


import net.minecraft.client.MinecraftClient;
import net.minecraft.world.World;
import traben.solid_mobs.client.solidMobsClient;

public class Config {

   public boolean allowItemCollisions = false;
    public boolean allowPlayerCollisions = true;
    public boolean allowPetCollisions = true;
    public boolean bouncySlimes = true;
    public boolean fallDamageSharedWithLandedOnMob = true;
    private float  fallDamageAmountAbsorbedByLandedOnMob = 0.5F;
   // public boolean allowPaintingAndItemFrameCollisions = false;
    public boolean allowInvisibleCollisions = false;

    public boolean allowShovingMobs = true;
    public int shoveAgainTimeInTicks = 20;
    public boolean allowVillagerCollisions = true;

    public String[] entityCollisionBlacklist = {"list the full ID of entities to disable their collisions e.g. -> \"entity.minecraft.creeper\" separated like the ratsmischief mod's rat example following","entity.ratsmischief.rat"};

    public float getFallAbsorbAmount(){
        if (fallDamageAmountAbsorbedByLandedOnMob > 1) return 1;
        if (fallDamageAmountAbsorbedByLandedOnMob < 0) return 0;
        return fallDamageAmountAbsorbedByLandedOnMob;
    }
    public void setFallAbsorbAmount(float set){
        fallDamageAmountAbsorbedByLandedOnMob = set;
    }

    public boolean canUseMod(World world){
        //only true if this is server, client is in single-player, or if client has received config packet from server
        if(world == null) return false;
        if(world.isClient){
            if(MinecraftClient.getInstance().isInSingleplayer()){
                return true;
            }else{
                return solidMobsClient.haveServerConfig;
            }
        }else{
            return true;
        }

    }
}