package traben.solid_mobs.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import traben.solid_mobs.solidMobsMain;
import traben.solid_mobs.config.Config;

import static traben.solid_mobs.solidMobsMain.EXEMPT_ENTITIES;
import static traben.solid_mobs.solidMobsMain.solidMobsConfigData;

@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class solidMobsClient implements ClientModInitializer {

    public static Config solidMobsConfigDataFromServer = null;
    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(solidMobsMain.serverConfigPacketID, (client, handler, buf, responseSender) -> {
            //create server config
            System.out.println("[Solid mobs] - Server Config data received and synced");
            solidMobsConfigDataFromServer = new Config();
            //PRESERVE WRITE ORDER IN READ
            /////////////////////////////////////////
            solidMobsConfigDataFromServer.allowGroundItemCollisions = buf.readBoolean();
            solidMobsConfigDataFromServer.allowPlayerCollisions = buf.readBoolean();
            solidMobsConfigDataFromServer.allowPetCollisions = buf.readBoolean();
            solidMobsConfigDataFromServer.bouncySlimes = buf.readBoolean();
            solidMobsConfigDataFromServer.fallDamageHalvedWithLandedOnMob = buf.readBoolean();
            solidMobsConfigData.setFallAbsorbAmount(buf.readFloat());
            solidMobsConfigDataFromServer.allowPaintingAndItemFrameCollisions = buf.readBoolean();
            ///////////////////////////////////////////////
            EXEMPT_ENTITIES.clear();
            solidMobsMain.setupExemptions();

        });
    }
}
