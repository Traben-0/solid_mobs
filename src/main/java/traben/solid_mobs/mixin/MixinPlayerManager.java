package traben.solid_mobs.mixin;

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

import static traben.solid_mobs.solidMobsMain.solidMobsConfigData;

@Mixin(PlayerManager.class)
public abstract class MixinPlayerManager {

    //try send a packet with config data to client

    @Inject(method = "onPlayerConnect", at = @At("TAIL"))
    public void etf$sendPacketFromServer(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        //if (world.isClient()) return;

        PacketByteBuf buf = PacketByteBufs.create();
        System.out.println("[Solid Mobs] - Sending server config to client.");
        //PRESERVE WRITE ORDER IN READ
        /////////////////////////////////////////
        buf.writeBoolean(solidMobsConfigData.allowGroundItemCollisions);
        buf.writeBoolean(solidMobsConfigData.allowPlayerCollisions);
        buf.writeBoolean(solidMobsConfigData.allowPetCollisions);
        buf.writeBoolean(solidMobsConfigData.bouncySlimes);
        buf.writeBoolean(solidMobsConfigData.fallDamageHalvedWithLandedOnMob);
        buf.writeFloat(solidMobsConfigData.getFallAbsorbAmount());
        buf.writeBoolean(solidMobsConfigData.allowPaintingAndItemFrameCollisions);
        ///////////////////////////////////////////////

        ServerPlayNetworking.send(player, solidMobsMain.serverConfigPacketID, buf);


    }




}
