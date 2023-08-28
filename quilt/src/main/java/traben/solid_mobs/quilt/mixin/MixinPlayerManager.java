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
import traben.solid_mobs.SolidMobsMain;

import java.nio.charset.Charset;
import java.util.Arrays;

import static traben.solid_mobs.SolidMobsMain.solidMobsSolidMobsConfigData;

@Mixin(PlayerManager.class)
public abstract class MixinPlayerManager {

    //try send a packet with config data to client

    @Inject(method = "onPlayerConnect", at = @At("TAIL"))
    public void etf$sendPacketFromServer(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        //if (world.isClient()) return;


        //todo deprecated now?
        PacketByteBuf buf = PacketByteBufs.create();
        System.out.println("[Solid Mobs] - Sending server config to ["+player.getName().getString()+"]");
        //PRESERVE WRITE ORDER IN READ
        /////////////////////////////////////////
        buf.writeBoolean(solidMobsSolidMobsConfigData.allowItemCollisions);
        buf.writeBoolean(solidMobsSolidMobsConfigData.allowPlayerCollisions);
        buf.writeBoolean(solidMobsSolidMobsConfigData.allowPetCollisions);
        buf.writeBoolean(solidMobsSolidMobsConfigData.bouncySlimes);
        buf.writeBoolean(solidMobsSolidMobsConfigData.fallDamageSharedWithLandedOnMob);
        buf.writeFloat(solidMobsSolidMobsConfigData.getFallAbsorbAmount());
        //buf.writeBoolean(solidMobsConfigData.allowPaintingAndItemFrameCollisions);
        buf.writeBoolean(solidMobsSolidMobsConfigData.allowInvisibleCollisions);
        buf.writeBoolean(solidMobsSolidMobsConfigData.allowShovingMobs);
        buf.writeInt(solidMobsSolidMobsConfigData.shoveAgainTimeInTicks);
        buf.writeBoolean(solidMobsSolidMobsConfigData.allowVillagerCollisions);
        ///////////////////////////////////////////////
        buf.writeInt(Arrays.toString(solidMobsSolidMobsConfigData.entityCollisionBlacklist).length());
        buf.writeCharSequence(Arrays.toString(solidMobsSolidMobsConfigData.entityCollisionBlacklist), Charset.defaultCharset());

        ///////////////////////////////////////////////////

        ServerPlayNetworking.send(player, SolidMobsMain.SERVER_CONFIG_PACKET_ID, buf);


    }




}
