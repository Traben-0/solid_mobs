package traben.solid_mobs.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import traben.solid_mobs.SolidMobsMain;
import traben.solid_mobs.client.solidMobsClient;


@Mixin(MinecraftClient.class)
public abstract class MixinMinecraftClient {

    @Inject(method = "disconnect(Lnet/minecraft/client/gui/screen/Screen;)V", at = @At("TAIL"))
    private void etf$injected(Screen screen, CallbackInfo ci) {
        //reset to remove possible server config data
        solidMobsClient.haveServerConfig = false;
        SolidMobsMain.sm$loadConfig();
        SolidMobsMain.lastPushTime.clear();
    }
}
