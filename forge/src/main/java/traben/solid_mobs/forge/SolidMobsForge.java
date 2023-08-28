package traben.solid_mobs.forge;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import traben.solid_mobs.SolidMobsMain;
import traben.solid_mobs.config.SolidMobsCommands;

@Mod("solid_mobs")
public class SolidMobsForge {
    public SolidMobsForge() {

        ForgePacketHandler.init();
        SolidMobsMain.init();
        MinecraftForge.EVENT_BUS.register(SolidMobsForge.class);
//        if (FMLEnvironment.dist != Dist.CLIENT) {
//            //server
//
//        }
    }

    @SubscribeEvent
    public static void onRegisterCommandEvent(RegisterCommandsEvent event){
        System.out.println("[Solid Mobs] commands registered");
        SolidMobsCommands.registerCommands(event.getDispatcher(),null,null);
    }



}
