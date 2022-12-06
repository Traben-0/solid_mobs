package traben.solid_mobs;

import dev.architectury.injectables.annotations.ExpectPlatform;

import java.nio.file.Path;

public class SolidMobsCrossPlatformHelper {

    @ExpectPlatform
    public static Path getConfigDirectory() {
        return Path.of("");
    }
}
