package traben.solid_mobs.config;



import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.world.World;
import traben.solid_mobs.client.SolidMobsClient;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class SolidMobsConfig {

    public boolean platformMode = false;
    public boolean allowItemCollisions = false;
    public boolean allowNonSavingEntityCollisions = false;
    public boolean allowPlayerCollisions = true;
    public boolean allowPetCollisions = true;
    public boolean fallDamageSharedWithLandedOnMob = true;
    public float fallDamageAmountAbsorbedByLandedOnMob = 0.5F;
    // public boolean allowPaintingAndItemFrameCollisions = false;
    public boolean allowInvisibleCollisions = false;

    public boolean allowShovingMobs = true;
    public int shoveAgainTimeInTicks = 20;
    public boolean allowVillagerCollisions = true;
    public boolean allowBabyCollisions = false;
    public boolean playerOnlyMode = false; //if true, only players can collide with mobs, not other mobs
    public boolean ignoreCollisionsWhenCrouching = false;


    public Set<String> entityCollisionBlacklist = Arrays.stream(new String[]{"entity.ratsmischief.rat"}).collect(Collectors.toSet());
    public Set<String> entityBounceList = Arrays.stream(new String[]{"entity.minecraft.slime", "entity.minecraft.magma_cube"}).collect(Collectors.toSet());


    public float getFallAbsorbAmount() {
        if (fallDamageAmountAbsorbedByLandedOnMob > 1) return 1;
        if (fallDamageAmountAbsorbedByLandedOnMob < 0) return 0;
        return fallDamageAmountAbsorbedByLandedOnMob;
    }

    public void setFallAbsorbAmount(float set) {
        fallDamageAmountAbsorbedByLandedOnMob = set;
    }

    public boolean canUseMod(World world) {
        //only true if this is server, client is in single-player, or if client has received config packet from server
        if (world == null) return false;
        if (world.isClient) {
            if (MinecraftClient.getInstance().isInSingleplayer()) {
                return true;
            } else {
                return SolidMobsClient.haveServerConfig;
            }
        } else {
            return true;
        }

    }

    public static byte CONFIG_START_SYNC = (byte) -69;
    public void encodeToByteBuffer(PacketByteBuf buffer){
        System.out.println("[Solid Mobs] - Encoding server config packet for client.");

        //ALWAYS FIRST BYTEs WRITTEN
        buffer.writeByte(0);//this is required for forge/fabric server byte syncing //forge calls it a discriminator byte
        buffer.writeByte(CONFIG_START_SYNC);//this allows forge/fabric to know where to start with the packet to sync up

        //PRESERVE WRITE ORDER IN READ
        /////////////////////////////////////////
        buffer.writeBoolean(allowNonSavingEntityCollisions);
        buffer.writeBoolean(platformMode);
        buffer.writeBoolean(allowItemCollisions);
        buffer.writeBoolean(allowPlayerCollisions);
        buffer.writeBoolean(allowPetCollisions);
        buffer.writeBoolean(fallDamageSharedWithLandedOnMob);
        buffer.writeFloat(getFallAbsorbAmount());
        //buf.writeBoolean(solidMobsConfigData.allowPaintingAndItemFrameCollisions);
        buffer.writeBoolean(allowInvisibleCollisions);
        buffer.writeBoolean(allowShovingMobs);
        buffer.writeInt(shoveAgainTimeInTicks);
        buffer.writeBoolean(allowVillagerCollisions);
        buffer.writeBoolean(allowBabyCollisions);
        buffer.writeBoolean(playerOnlyMode);
        buffer.writeBoolean(ignoreCollisionsWhenCrouching);
        ///////////////////////////////////////////////
//        buffer.writeInt(((CharSequence)Arrays.toString(entityCollisionBlacklist)).length());
//        buffer.writeCharSequence(Arrays.toString(entityCollisionBlacklist), Charset.defaultCharset());
        buffer.writeString(Arrays.toString(entityCollisionBlacklist.toArray()));
        buffer.writeString(Arrays.toString(entityBounceList.toArray()));
        ///////////////////////////////////////////////////


    }

    @Override
    public String toString() {
        return "SolidMobsConfig{\n" +
                "platformMode=" + platformMode +
                ",\n allowItemCollisions=" + allowItemCollisions +
                ",\n allowNonSavingEntityCollisions=" + allowNonSavingEntityCollisions +
                ",\n allowPlayerCollisions=" + allowPlayerCollisions +
                ",\n allowPetCollisions=" + allowPetCollisions +
                ",\n fallDamageSharedWithLandedOnMob=" + fallDamageSharedWithLandedOnMob +
                ",\n fallDamageAmountAbsorbedByLandedOnMob=" + fallDamageAmountAbsorbedByLandedOnMob +
                ",\n allowInvisibleCollisions=" + allowInvisibleCollisions +
                ",\n allowShovingMobs=" + allowShovingMobs +
                ",\n shoveAgainTimeInTicks=" + shoveAgainTimeInTicks +
                ",\n allowVillagerCollisions=" + allowVillagerCollisions +
                ",\n allowBabyCollisions=" + allowBabyCollisions +
                ",\n playerOnlyMode=" + playerOnlyMode +
                ",\n ignoreCollisionsWhenCrouching=" + ignoreCollisionsWhenCrouching +
                ",\n entityCollisionBlacklist=" + entityCollisionBlacklist +
                ",\n entityBounceList=" + entityBounceList +
                "\n}";
    }

    public SolidMobsConfig(){}
    public SolidMobsConfig(PacketByteBuf buffer){
        /*
         default config packet without start syncing
         * FORGE
         [0, 0, 0, 0, 1, 1, 1, 1, 63, 0, 0, 0, 0, 1, 0, 0, 0, 20, 1, 0, 0, 0, 25, 91, 101, 110, 116, 105, 116, 121, 46, 114, 97, 116, 115, 109, 105, 115, 99, 104, 105, 101, 102, 46, 114, 97, 116, 93, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
         [0, 0, 0, 0, 1, 1, 1, 1, 63, 0, 0, 0, 0, 1, 0, 0, 0, 20, 1, 0, 0, 0, 25, 91, 101, 110, 116, 105, 116, 121, 46, 114, 97, 116, 115, 109, 105, 115, 99, 104, 105, 101, 102, 46, 114, 97, 116, 93]
         * fabric
         [0, 0, 0, 1, 1, 1, 1, 63, 0, 0, 0, 0, 1, 0, 0, 0, 20, 1, 0, 0, 0, 25, 91, 101, 110, 116, 105, 116, 121, 46, 114, 97, 116, 115, 109, 105, 115, 99, 104, 105, 101, 102, 46, 114, 97, 116, 93, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
         [0, 0, 0, 1, 1, 1, 1, 63, 0, 0, 0, 0, 1, 0, 0, 0, 20, 1, 0, 0, 0, 25, 91, 101, 110, 116, 105, 116, 121, 46, 114, 97, 116, 115, 109, 105, 115, 99, 104, 105, 101, 102, 46, 114, 97, 116, 93]
         */


        //forge and fabric add different header bytes to the sent packet, this attempts to sync them up to make the mod compatible with either server/client makeup
        byte findMarker = buffer.readByte();
        while(findMarker != CONFIG_START_SYNC)
            findMarker = buffer.readByte();

        //PRESERVE WRITE ORDER IN READ
        /////////////////////////////////////////
        allowNonSavingEntityCollisions = buffer.readBoolean();
        platformMode = buffer.readBoolean();
        allowItemCollisions = buffer.readBoolean();
        allowPlayerCollisions = buffer.readBoolean();
        allowPetCollisions = buffer.readBoolean();
        fallDamageSharedWithLandedOnMob = buffer.readBoolean();
        setFallAbsorbAmount(buffer.readFloat());
        //solidMobsConfigData.allowPaintingAndItemFrameCollisions = buf.readBoolean();
        allowInvisibleCollisions = buffer.readBoolean();
        allowShovingMobs = buffer.readBoolean();
        shoveAgainTimeInTicks = buffer.readInt();
        allowVillagerCollisions = buffer.readBoolean();
        allowBabyCollisions = buffer.readBoolean();
        playerOnlyMode = buffer.readBoolean();
        ignoreCollisionsWhenCrouching = buffer.readBoolean();
        ///////////////////////////////////////////////
//        int length = buffer.readInt();
//        String read = (String) buffer.readCharSequence(length, Charset.defaultCharset());
        String read = buffer.readString();
        //remove bracketing
        read = read.replaceFirst("^\\[", "").replaceFirst("]$", "");
        //split array string
        entityCollisionBlacklist = Arrays.stream(read.split(", ")).collect(Collectors.toSet());
        read = buffer.readString();
        //remove bracketing
        read = read.replaceFirst("^\\[", "").replaceFirst("]$", "");
        //split array string
        entityBounceList = Arrays.stream(read.split(", ")).collect(Collectors.toSet());
        //////////////////////////////////////////////////////
    }
}
