package traben.solid_mobs.neoforge;



import net.neoforged.neoforge.network.NetworkRegistry;
import net.neoforged.neoforge.network.simple.SimpleChannel;
import traben.solid_mobs.SolidMobsMain;

public class ForgePacketHandler {
//    private static final int PROTOCOL_VERSION = 3;

    private static final String version = "4";

    public static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder
            .named(SolidMobsMain.SERVER_CONFIG_PACKET_ID)
            .networkProtocolVersion(()->version)
            .serverAcceptedVersions(version::equals)
            .clientAcceptedVersions(version::equals)
            .simpleChannel();

//            ChannelBuilder.newSimpleChannel(
//            SolidMobsMain.SERVER_CONFIG_PACKET_ID,
//            () -> PROTOCOL_VERSION,
//            PROTOCOL_VERSION::equals,
//            PROTOCOL_VERSION::equals
//    );
    //public static ForgePacket packet = new ForgePacket();
    public static void init(){

        INSTANCE.messageBuilder(SolidMobsSolidMobsConfigPacket.class,0)
                .encoder(SolidMobsSolidMobsConfigPacket::encoder)
                .decoder(SolidMobsSolidMobsConfigPacket::decoder)
                .consumerMainThread(new SolidMobsSolidMobsConfigPacket.SolidMobsConsumer())
                .add();

       // INSTANCE.(0, SolidMobsSolidMobsConfigPacket.class , SolidMobsSolidMobsConfigPacket::encoder , SolidMobsSolidMobsConfigPacket::decoder  , SolidMobsSolidMobsConfigPacket::messageConsumer);
    }


}
