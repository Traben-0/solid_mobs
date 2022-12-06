package traben.solid_mobs.fabriclike;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import traben.solid_mobs.client.solidMobsClient;
import traben.solid_mobs.config.Config;
import traben.solid_mobs.solidMobsMain;

import java.nio.charset.Charset;
import java.util.HashSet;

import static traben.solid_mobs.solidMobsMain.EXEMPT_ENTITIES;
import static traben.solid_mobs.solidMobsMain.solidMobsConfigData;

public class fabricLikeClient {

    public static void init(){
            ClientPlayNetworking.registerGlobalReceiver(solidMobsMain.serverConfigPacketID, (client, handler, buf, responseSender) -> {
                //create server config
                System.out.println("[Solid mobs] - Server Config data received and synced");
                solidMobsConfigData = new Config();
                //PRESERVE WRITE ORDER IN READ
                /////////////////////////////////////////
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
                EXEMPT_ENTITIES = new HashSet<String>();
                solidMobsMain.resetExemptions();
                solidMobsClient.haveServerConfig = true;
            });
        }

}
