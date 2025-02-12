package traben.solid_mobs.fabriclike;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import traben.solid_mobs.SolidMobsMain;

public class fabricLikeMain {
    public static void init() {
        PayloadTypeRegistry.playS2C().register(SMDataFab.id, SMDataFab.CODEC);
        SolidMobsMain.init();
    }
}
