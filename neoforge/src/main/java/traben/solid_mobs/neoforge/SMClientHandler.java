package traben.solid_mobs.neoforge;

import net.minecraft.text.Text;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import traben.solid_mobs.SMData;
import traben.solid_mobs.SolidMobsMain;
import traben.solid_mobs.client.SolidMobsClient;

import static traben.solid_mobs.SolidMobsMain.solidMobsConfigData;

public class SMClientHandler {

    private static final SMClientHandler INSTANCE = new SMClientHandler();

    public static SMClientHandler getInstance() {
        return INSTANCE;
    }

    public void handleData(final SMData data) {
//        // Do something with the data, on the network thread
//        blah(data.name());
        if (!(data instanceof final SMData smData)) {
            System.out.println(Text.of("solid mobs networking failed. "+ data.getClass().getName()));
            return;
        }
        // Do something with the data, on the main thread
        try {
                    if(smData.isValid()) {
                        solidMobsConfigData = smData.delegate;

                        SolidMobsMain.resetExemptions();
                        SolidMobsClient.haveServerConfig = true;
                        System.out.println("[Solid mobs] - Server Config data received and synced");
                    }else{
                        System.out.println("[Solid mobs] - Server Config data received and failed to sync\n solids mobs will be disabled");
                        //disabling happens automatically with server config not being properly received
                    }
                } catch (Exception e) {
                    // Handle exception
                System.out.println(Text.of("solid mobs networking failed. "+ e.getMessage()));
//                    return null;
                }
    }
}
