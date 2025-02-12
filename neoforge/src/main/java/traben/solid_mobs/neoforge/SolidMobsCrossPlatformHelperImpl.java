package traben.solid_mobs.neoforge;

import net.minecraft.server.network.ServerPlayerConfigurationTask;
import net.minecraft.server.network.ServerPlayerEntity;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.network.PacketDistributor;
import traben.solid_mobs.SMData;

import java.nio.file.Path;

import static traben.solid_mobs.SolidMobsMain.solidMobsConfigData;

@SuppressWarnings("unused")
public class SolidMobsCrossPlatformHelperImpl {

    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }

    public static void sendConfigToClient(ServerPlayerEntity player){
        PacketDistributor.sendToPlayer(player, new SMDataNeo());
//       PacketDistributor.PLAYER.with(player).send(new SMData()
        System.out.println("[Solid Mobs] - Sending server config to ["+player.getName().getString()+"]");
    }
}
