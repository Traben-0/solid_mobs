package traben.solid_mobs.forge;


import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.SimpleChannel;
import traben.solid_mobs.SolidMobsMain;

public class ForgePacketHandler {
    private static final int PROTOCOL_VERSION = 3;

    public static final SimpleChannel INSTANCE = ChannelBuilder
            .named(SolidMobsMain.SERVER_CONFIG_PACKET_ID)
            .networkProtocolVersion(PROTOCOL_VERSION)
            .simpleChannel();

//            ChannelBuilder.newSimpleChannel(
//            SolidMobsMain.SERVER_CONFIG_PACKET_ID,
//            () -> PROTOCOL_VERSION,
//            PROTOCOL_VERSION::equals,
//            PROTOCOL_VERSION::equals
//    );
    //public static ForgePacket packet = new ForgePacket();
    public static void init(){

        INSTANCE.messageBuilder(SolidMobsSolidMobsConfigPacket.class)
                .encoder(SolidMobsSolidMobsConfigPacket::encoder)
                .decoder(SolidMobsSolidMobsConfigPacket::decoder)
                .consumerMainThread(SolidMobsSolidMobsConfigPacket::messageConsumer)
                .add();

       // INSTANCE.(0, SolidMobsSolidMobsConfigPacket.class , SolidMobsSolidMobsConfigPacket::encoder , SolidMobsSolidMobsConfigPacket::decoder  , SolidMobsSolidMobsConfigPacket::messageConsumer);
    }


}
