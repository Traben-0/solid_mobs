package traben.solid_mobs.quilt.mixin;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import traben.solid_mobs.solidMobsMain;

import java.nio.charset.Charset;
import java.util.Arrays;

import static traben.solid_mobs.solidMobsMain.solidMobsConfigData;

@Mixin(PlayerManager.class)
public abstract class MixinPlayerManager {

    //try send a packet with config data to client

    @Inject(method = "onPlayerConnect", at = @At("TAIL"))
    public void etf$sendPacketFromServer(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        //if (world.isClient()) return;

        PacketByteBuf buf = PacketByteBufs.create();
        System.out.println("[Solid Mobs] - Sending server config to ["+player.getName().getString()+"]");
        //PRESERVE WRITE ORDER IN READ
        /////////////////////////////////////////
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

        ServerPlayNetworking.send(player, solidMobsMain.serverConfigPacketID, buf);


    }




}
