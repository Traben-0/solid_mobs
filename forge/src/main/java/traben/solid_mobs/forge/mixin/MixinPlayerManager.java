package traben.solid_mobs.forge.mixin;


import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ConnectedClientData;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import traben.solid_mobs.SolidMobsCrossPlatformHelper;

@Mixin(PlayerManager.class)
public abstract class MixinPlayerManager {

    //try send a packet with config data to client

    @Inject(method = "onPlayerConnect", at = @At("TAIL"))
    public void etf$sendPacketFromServer(ClientConnection connection, ServerPlayerEntity player, ConnectedClientData clientData, CallbackInfo ci) {
        //if (world.isClient()) return;

        SolidMobsCrossPlatformHelper.sendConfigToClient(player);

    }




}
