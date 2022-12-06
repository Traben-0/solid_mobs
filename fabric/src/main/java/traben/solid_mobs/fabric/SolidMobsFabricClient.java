package traben.solid_mobs.fabric;

import net.fabricmc.api.ClientModInitializer;
import traben.solid_mobs.fabriclike.fabricLikeClient;

public class SolidMobsFabricClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {
        fabricLikeClient.init();
    }
}
