package traben.solid_mobs.fabriclike;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import traben.solid_mobs.SolidMobsMain;
import traben.solid_mobs.client.SolidMobsClient;
import traben.solid_mobs.config.SolidMobsConfig;

public class fabricLikeClient {

    public static void init(){
            ClientPlayNetworking.registerGlobalReceiver(SolidMobsMain.SERVER_CONFIG_PACKET_ID, (client, handler, buf, responseSender) -> {
                //create server config
                try {
                    SolidMobsMain.solidMobsConfigData = new SolidMobsConfig(buf);
                    SolidMobsMain.resetExemptions();
                    SolidMobsClient.haveServerConfig = true;
                    System.out.println("[Solid mobs] - Server Config data received and synced");
                }catch (Exception e){
                    System.out.println("[Solid mobs] - Server Config data received and failed to sync");
                }
            });
        }

}
