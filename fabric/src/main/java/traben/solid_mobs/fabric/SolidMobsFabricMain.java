package traben.solid_mobs.fabric;

import traben.solid_mobs.fabriclike.fabricLikeMain;
import net.fabricmc.api.ModInitializer;

public class SolidMobsFabricMain implements ModInitializer {
    @Override
    public void onInitialize() {
        fabricLikeMain.init();
    }
}
