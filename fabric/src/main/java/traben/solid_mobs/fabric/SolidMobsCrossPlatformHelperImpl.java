package traben.solid_mobs.fabric;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import traben.solid_mobs.SolidMobsMain;

import java.nio.file.Path;

@SuppressWarnings("unused")
public class SolidMobsCrossPlatformHelperImpl {

    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }

    public static void sendConfigToClient(ServerPlayerEntity player){

        PacketByteBuf buf = PacketByteBufs.create();

        SolidMobsMain.solidMobsSolidMobsConfigData.encodeToByteBuffer(buf);
        System.out.println("[Solid Mobs] - Sending server config to ["+player.getName().getString()+"]");
        ServerPlayNetworking.send(player, SolidMobsMain.SERVER_CONFIG_PACKET_ID, buf);
    }


}
