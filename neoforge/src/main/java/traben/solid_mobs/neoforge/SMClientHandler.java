package traben.solid_mobs.neoforge;

import net.minecraft.text.Text;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;
import traben.solid_mobs.SolidMobsMain;
import traben.solid_mobs.client.SolidMobsClient;

import static traben.solid_mobs.SolidMobsMain.solidMobsConfigData;

public class SMClientHandler {

    private static final SMClientHandler INSTANCE = new SMClientHandler();

    public static SMClientHandler getInstance() {
        return INSTANCE;
    }

    public void handleData(final SMData data, final PlayPayloadContext context) {
//        // Do something with the data, on the network thread
//        blah(data.name());

        // Do something with the data, on the main thread
        context.workHandler().submitAsync(() -> {
                    if(data.isValid()) {
                        solidMobsConfigData = data.delegate;

                        SolidMobsMain.resetExemptions();
                        SolidMobsClient.haveServerConfig = true;
                        System.out.println("[Solid mobs] - Server Config data received and synced");
                    }else{
                        System.out.println("[Solid mobs] - Server Config data received and failed to sync\n solids mobs will be disabled");
                        //disabling happens automatically with server config not being properly received
                    }
                })
                .exceptionally(e -> {
                    // Handle exception
                    context.packetHandler().disconnect(Text.of("solid mobs networking failed. "+ e.getMessage()));
                    return null;
                });
    }
}
