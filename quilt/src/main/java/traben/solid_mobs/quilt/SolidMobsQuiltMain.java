package traben.solid_mobs.quilt;

import traben.solid_mobs.fabriclike.fabricLikeMain;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

public class SolidMobsQuiltMain implements ModInitializer {
    @Override
    public void onInitialize(ModContainer mod) {
        fabricLikeMain.init();
    }
}
