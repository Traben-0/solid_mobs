package traben.solid_mobs.fabriclike;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import traben.solid_mobs.SolidMobsMain;
import traben.solid_mobs.client.SolidMobsClient;
import traben.solid_mobs.config.Config;

import java.nio.charset.Charset;
import java.util.HashSet;

import static traben.solid_mobs.SolidMobsMain.EXEMPT_ENTITIES;
import static traben.solid_mobs.SolidMobsMain.solidMobsConfigData;

public class fabricLikeClient {

    public static void init(){
            ClientPlayNetworking.registerGlobalReceiver(SolidMobsMain.SERVER_CONFIG_PACKET_ID, (client, handler, buf, responseSender) -> {
                //create server config
                System.out.println("[Solid mobs] - Server Config data received and synced");
                solidMobsConfigData = new Config();
                //PRESERVE WRITE ORDER IN READ
                /////////////////////////////////////////
                solidMobsConfigData.allowNonSavingEntityCollisions = buf.readBoolean();
                solidMobsConfigData.platformMode = buf.readBoolean();
                solidMobsConfigData.allowItemCollisions = buf.readBoolean();
                solidMobsConfigData.allowPlayerCollisions = buf.readBoolean();
                solidMobsConfigData.allowPetCollisions = buf.readBoolean();
                solidMobsConfigData.bouncySlimes = buf.readBoolean();
                solidMobsConfigData.fallDamageSharedWithLandedOnMob = buf.readBoolean();
                solidMobsConfigData.setFallAbsorbAmount(buf.readFloat());
                //solidMobsConfigData.allowPaintingAndItemFrameCollisions = buf.readBoolean();
                solidMobsConfigData.allowInvisibleCollisions = buf.readBoolean();
                solidMobsConfigData.allowShovingMobs = buf.readBoolean();
                solidMobsConfigData.shoveAgainTimeInTicks = buf.readInt();
                solidMobsConfigData.allowVillagerCollisions = buf.readBoolean();
                ///////////////////////////////////////////////
                int length = buf.readInt();
                String read = (String) buf.readCharSequence(length, Charset.defaultCharset());
                //remove bracketing
                read = read.replaceFirst("^\\[", "").replaceFirst("]$", "");
                //split array string
                solidMobsConfigData.entityCollisionBlacklist = read.split(", ");
                //////////////////////////////////////////////////////
                EXEMPT_ENTITIES = new HashSet<>();
                SolidMobsMain.resetExemptions();
                SolidMobsClient.haveServerConfig = true;
            });
        }

}
