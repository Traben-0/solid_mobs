package traben.solid_mobs.forge;

import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class SolidMobsCrossPlatformHelperImpl {

    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
}
