package traben.solid_mobs.quilt;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import traben.solid_mobs.fabriclike.fabricLikeClient;

public class SolidMobsQuiltClient implements ClientModInitializer {


    @Override
    public void onInitializeClient(ModContainer mod) {
        fabricLikeClient.init();
    }
}
