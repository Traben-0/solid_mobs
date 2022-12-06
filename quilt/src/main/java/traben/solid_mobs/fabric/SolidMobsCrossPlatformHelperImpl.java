package traben.solid_mobs.fabric;

import org.quiltmc.loader.api.QuiltLoader;

import java.nio.file.Path;

public class SolidMobsCrossPlatformHelperImpl {

    public static Path getConfigDirectory() {
        return QuiltLoader.getConfigDir();
    }
}
