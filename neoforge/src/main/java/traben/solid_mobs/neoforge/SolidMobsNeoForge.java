package traben.solid_mobs.neoforge;


import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import traben.solid_mobs.SolidMobsMain;
import traben.solid_mobs.config.SolidMobsCommands;

@Mod("solid_mobs")
public class SolidMobsNeoForge {
    public SolidMobsNeoForge() {

        traben.solid_mobs.neoforge.ForgePacketHandler.init();
        SolidMobsMain.init();
        NeoForge.EVENT_BUS.register(SolidMobsNeoForge.class);
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
