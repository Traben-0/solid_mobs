package traben.solid_mobs.fabric;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import traben.solid_mobs.SolidMobsMain;

import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.Arrays;

import static traben.solid_mobs.SolidMobsMain.solidMobsConfigData;

@SuppressWarnings("unused")
public class SolidMobsCrossPlatformHelperImpl {

    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }

    public static void sendConfigToClient(ServerPlayerEntity player){

        PacketByteBuf buf = PacketByteBufs.create();
        System.out.println("[Solid Mobs] - Sending server config to ["+player.getName().getString()+"]");
        //PRESERVE WRITE ORDER IN READ
        /////////////////////////////////////////
        buf.writeBoolean(solidMobsConfigData.allowNonSavingEntityCollisions);
        buf.writeBoolean(solidMobsConfigData.platformMode);
        buf.writeBoolean(solidMobsConfigData.allowItemCollisions);
        buf.writeBoolean(solidMobsConfigData.allowPlayerCollisions);
        buf.writeBoolean(solidMobsConfigData.allowPetCollisions);
        buf.writeBoolean(solidMobsConfigData.bouncySlimes);
        buf.writeBoolean(solidMobsConfigData.fallDamageSharedWithLandedOnMob);
        buf.writeFloat(solidMobsConfigData.getFallAbsorbAmount());
        //buf.writeBoolean(solidMobsConfigData.allowPaintingAndItemFrameCollisions);
        buf.writeBoolean(solidMobsConfigData.allowInvisibleCollisions);
        buf.writeBoolean(solidMobsConfigData.allowShovingMobs);
        buf.writeInt(solidMobsConfigData.shoveAgainTimeInTicks);
        buf.writeBoolean(solidMobsConfigData.allowVillagerCollisions);
        ///////////////////////////////////////////////
        buf.writeInt(Arrays.toString(solidMobsConfigData.entityCollisionBlacklist).length());
        buf.writeCharSequence(Arrays.toString(solidMobsConfigData.entityCollisionBlacklist), Charset.defaultCharset());

        ///////////////////////////////////////////////////
        ServerPlayNetworking.send(player, SolidMobsMain.serverConfigPacketID, buf);
    }


}
