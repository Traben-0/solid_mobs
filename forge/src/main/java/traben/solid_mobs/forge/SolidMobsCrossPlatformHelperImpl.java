package traben.solid_mobs.forge;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.network.PacketDistributor;

import java.nio.file.Path;

@SuppressWarnings("unused")
public class SolidMobsCrossPlatformHelperImpl {

    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }

    public static void sendConfigToClient(ServerPlayerEntity player){
        //PacketByteBuf buf = PacketByteBufs.create();
        ForgePacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(()-> player),new ForgePacket());
        System.out.println("[Solid Mobs] - Sending server config to ["+player.getName().getString()+"]");
        // ServerPlayNetworking.send(player, solidMobsMain.serverConfigPacketID, buf);
    }
}
