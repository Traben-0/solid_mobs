package traben.solid_mobs.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import traben.solid_mobs.client.solidMobsClient;
import traben.solid_mobs.solidMobsMain;


@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient {

    @Inject(method = "disconnect(Lnet/minecraft/client/gui/screen/Screen;)V", at = @At("TAIL"))
    private void etf$injected(Screen screen, CallbackInfo ci) {
        //reset to remove possible server config data
        solidMobsClient.haveServerConfig = false;
        solidMobsMain.sm$loadConfig();
        solidMobsMain.lastPushTime.clear();
    }
}
