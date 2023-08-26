package traben.solid_mobs.forge;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
import traben.solid_mobs.SolidMobsMain;
import traben.solid_mobs.config.SolidMobsCommands;

@Mod("solid_mobs")
public class SolidMobsForge {
    public SolidMobsForge() {

        ForgePacketHandler.init();

        SolidMobsMain.init();
        if (FMLEnvironment.dist != Dist.CLIENT) {
            //server
            //EventBuses.registerModEventBus("solid_mobs", FMLJavaModLoadingContext.get().getModEventBus());
            MinecraftForge.EVENT_BUS.register(this);


        }
    }

    @SubscribeEvent
    public static void onRegisterCommandEvent(RegisterCommandsEvent event){
        SolidMobsCommands.registerCommands(event.getDispatcher(),null,null);
    }



}
