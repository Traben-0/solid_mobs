package traben.solid_mobs.forge;

import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import traben.solid_mobs.SolidMobsMain;

public class ForgePacketHandler {
    private static final String PROTOCOL_VERSION = "2";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            SolidMobsMain.SERVER_CONFIG_PACKET_ID,
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );
    //public static ForgePacket packet = new ForgePacket();
    public static void init(){
        INSTANCE.registerMessage(0, SolidMobsSolidMobsConfigPacket.class , SolidMobsSolidMobsConfigPacket::encoder , SolidMobsSolidMobsConfigPacket::decoder  , SolidMobsSolidMobsConfigPacket::messageConsumer);
    }


}
