package traben.solid_mobs.fabric;

import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class SolidMobsCrossPlatformHelperImpl {

    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
