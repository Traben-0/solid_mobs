package traben.solid_mobs.neoforge;


import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import traben.solid_mobs.SMData;
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
    @EventBusSubscriber(modid = "solid_mobs", bus = EventBusSubscriber.Bus.MOD)
    class ModRegister{
        @SubscribeEvent
        public static void onPayloadRegister(RegisterPayloadHandlersEvent event) {
            // final IPayloadRegistrar registrar = event.registrar("solid_mobs");
            PayloadRegistrar registrar = event.registrar("solid_mobs");
            registrar.playToClient(SMData.id, SMDataNeo.CODEC, (a,b) -> SMClientHandler.getInstance().handleData(a));

        }
    }



