package traben.solid_mobs.neoforge;

import net.minecraft.server.network.ServerPlayerEntity;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.network.PacketDistributor;

import java.nio.file.Path;

@SuppressWarnings("unused")
public class SolidMobsCrossPlatformHelperImpl {

    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }

    public static void sendConfigToClient(ServerPlayerEntity player){
        //PacketByteBuf buf = PacketByteBufs.create();
        ForgePacketHandler.INSTANCE.send(
                PacketDistributor.PLAYER.with(()->player),
                new SolidMobsSolidMobsConfigPacket()
        );
//        ForgePacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(()-> player),new SolidMobsSolidMobsConfigPacket());
        System.out.println("[Solid Mobs] - Sending server config to ["+player.getName().getString()+"]");
        // ServerPlayNetworking.send(player, solidMobsMain.serverConfigPacketID, buf);
    }
}
