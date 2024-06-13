package traben.solid_mobs.fabriclike;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.CustomPayload;
import traben.solid_mobs.SMData;
import traben.solid_mobs.SolidMobsMain;
import traben.solid_mobs.client.SolidMobsClient;
import traben.solid_mobs.config.SolidMobsConfig;

public class fabricLikeClient {

    public static void init(){

            ClientPlayNetworking.registerGlobalReceiver( SMData.id,
                    (data, context) -> {
                        context.client().execute(() -> {
                            //create server config
                            if (!data.isValid()) {
                                System.out.println("[Solid mobs] - Server Config data received and failed to sync, invalid data");
                                return;
                            }
                            try {
                                SolidMobsMain.solidMobsConfigData = data.delegate;
                                SolidMobsMain.resetExemptions();
                                SolidMobsClient.haveServerConfig = true;
                                System.out.println("[Solid mobs] - Server Config data received and synced");
                            } catch (Exception e) {
                                System.out.println("[Solid mobs] - Server Config data received and failed to sync");
                            }
                        }
                    );
            });
    }

}
