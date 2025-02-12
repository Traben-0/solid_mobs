package traben.solid_mobs.fabric;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import traben.solid_mobs.SolidMobsMain;
import traben.solid_mobs.fabriclike.SMDataFab;

import java.nio.file.Path;

@SuppressWarnings("unused")
public class SolidMobsCrossPlatformHelperImpl {

    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }

    public static void sendConfigToClient(ServerPlayerEntity player){

        PacketByteBuf buf = PacketByteBufs.create();

        SolidMobsMain.solidMobsConfigData.encodeToByteBuffer(buf);
        System.out.println("[Solid Mobs] - Sending server config to ["+player.getName().getString()+"]");
        ServerPlayNetworking.send(player,   SMDataFab.read(buf));//todo just init and send the object normally in 1.20.6+ ?????
    }


}
