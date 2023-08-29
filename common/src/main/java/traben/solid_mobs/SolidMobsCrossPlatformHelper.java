package traben.solid_mobs;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.server.network.ServerPlayerEntity;

import java.nio.file.Path;

@SuppressWarnings({"EmptyMethod","unused"})
public class SolidMobsCrossPlatformHelper {

    @ExpectPlatform
    public static Path getConfigDirectory() {
        return Path.of("");
    }


    @ExpectPlatform
    public static void sendConfigToClient(ServerPlayerEntity player) {}

}
