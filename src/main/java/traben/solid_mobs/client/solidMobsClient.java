package traben.solid_mobs.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import traben.solid_mobs.solidMobsMain;
import traben.solid_mobs.config.Config;

import static traben.solid_mobs.solidMobsMain.EXEMPT_ENTITIES;
import static traben.solid_mobs.solidMobsMain.solidMobsConfigData;

@net.fabricmc.api.Environment(net.fabricmc.api.EnvType.CLIENT)
public class solidMobsClient implements ClientModInitializer {

    public static boolean haveServerConfig = false;
    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(solidMobsMain.serverConfigPacketID, (client, handler, buf, responseSender) -> {
            //create server config
            System.out.println("[Solid mobs] - Server Config data received and synced");
            solidMobsConfigData = new Config();
            //PRESERVE WRITE ORDER IN READ
            /////////////////////////////////////////
            solidMobsConfigData.allowGroundItemCollisions = buf.readBoolean();
            solidMobsConfigData.allowPlayerCollisions = buf.readBoolean();
            solidMobsConfigData.allowPetCollisions = buf.readBoolean();
            solidMobsConfigData.bouncySlimes = buf.readBoolean();
            solidMobsConfigData.fallDamageHalvedWithLandedOnMob = buf.readBoolean();
            solidMobsConfigData.setFallAbsorbAmount(buf.readFloat());
            solidMobsConfigData.allowPaintingAndItemFrameCollisions = buf.readBoolean();
            ///////////////////////////////////////////////
            EXEMPT_ENTITIES.clear();
            solidMobsMain.setupExemptions();
            haveServerConfig = true;
        });
    }
}
