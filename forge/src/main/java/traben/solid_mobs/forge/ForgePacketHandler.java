package traben.solid_mobs.forge;

import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import traben.solid_mobs.solidMobsMain;

public class ForgePacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            solidMobsMain.serverConfigPacketID,
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );
    //public static ForgePacket packet = new ForgePacket();
    public static void init(){
        INSTANCE.registerMessage(0,ForgePacket.class ,ForgePacket::encoder ,ForgePacket::decoder  , ForgePacket::messageConsumer);
    }


}
