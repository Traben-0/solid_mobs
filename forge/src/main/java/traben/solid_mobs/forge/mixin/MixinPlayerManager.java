package traben.solid_mobs.forge.mixin;


import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraftforge.network.PacketDistributor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import traben.solid_mobs.forge.ForgePacket;
import traben.solid_mobs.forge.ForgePacketHandler;

@Mixin(PlayerManager.class)
public abstract class MixinPlayerManager {

    //try send a packet with config data to client

    @Inject(method = "onPlayerConnect", at = @At("TAIL"))
    public void etf$sendPacketFromServer(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        //if (world.isClient()) return;

        //PacketByteBuf buf = PacketByteBufs.create();
        ForgePacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(()-> player),new ForgePacket());
        System.out.println("[Solid Mobs] - Sending server config to ["+player.getName().getString()+"]");
       // ServerPlayNetworking.send(player, solidMobsMain.serverConfigPacketID, buf);


    }




}
