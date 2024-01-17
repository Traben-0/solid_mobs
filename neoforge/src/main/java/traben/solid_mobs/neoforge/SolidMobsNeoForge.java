package traben.solid_mobs.neoforge;


import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoader;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.IModBusEvent;
import net.neoforged.fml.event.lifecycle.ModLifecycleEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.network.event.OnGameConfigurationEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.handlers.ClientPayloadHandler;
import net.neoforged.neoforge.network.handlers.ServerPayloadHandler;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;
import traben.solid_mobs.SolidMobsMain;
import traben.solid_mobs.config.SolidMobsCommands;

@Mod("solid_mobs")
public class SolidMobsNeoForge {
    public SolidMobsNeoForge() {

        SolidMobsMain.init();
        NeoForge.EVENT_BUS.register(SolidMobsNeoForge.class);


    }

    @SubscribeEvent
    public static void onRegisterCommandEvent(RegisterCommandsEvent event) {
        System.out.println("[Solid Mobs] commands registered");
        SolidMobsCommands.registerCommands(event.getDispatcher(), null, null);
    }
}

    @Mod.EventBusSubscriber(modid = "solid_mobs", bus = Mod.EventBusSubscriber.Bus.MOD)
    class ModRegister{
        @SubscribeEvent
        public static void register(RegisterPayloadHandlerEvent event) {
            final IPayloadRegistrar registrar = event.registrar("solid_mobs");

            registrar.play(SMData.id, SMData::read, handler -> handler
                            .client(SMClientHandler.getInstance()::handleData)
                    .server((a,b)-> System.out.println("[Solid Mobs] sent to server ??"))
            );
        }
    }


