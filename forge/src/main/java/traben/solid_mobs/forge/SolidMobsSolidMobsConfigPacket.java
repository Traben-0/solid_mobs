package traben.solid_mobs.forge;

import net.minecraft.network.PacketByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import traben.solid_mobs.SolidMobsMain;
import traben.solid_mobs.client.SolidMobsClient;
import traben.solid_mobs.config.SolidMobsConfig;

import static traben.solid_mobs.SolidMobsMain.solidMobsConfigData;


public class SolidMobsSolidMobsConfigPacket extends SolidMobsConfig
{
    // A class MessagePacket
    public void encoder(PacketByteBuf buffer) {
        // Write to buffer
        solidMobsConfigData.encodeToByteBuffer(buffer);
    }

    SolidMobsSolidMobsConfigPacket(){}
    SolidMobsSolidMobsConfigPacket(PacketByteBuf buffer){super(buffer);}

    public static SolidMobsSolidMobsConfigPacket decoder(PacketByteBuf buffer) {
        // Create packet from buffer data
        //create server config
        SolidMobsSolidMobsConfigPacket packet;
        if (FMLEnvironment.dist == Dist.CLIENT) {
            try {
                System.out.println("[Solid mobs] - Server Config packet received");
                packet = new SolidMobsSolidMobsConfigPacket(buffer);
                packet.is_valid = true;
            }catch(Exception e){
                System.out.println("[Solid mobs] - Server Config packet decoding failed because:\n"+e);
                e.printStackTrace();
                packet = new SolidMobsSolidMobsConfigPacket();
                packet.is_valid = false;
            }
        }else{
            packet = new SolidMobsSolidMobsConfigPacket();
            packet.is_valid = false;
        }
        return packet;
    }

    private boolean is_valid;





    public static void messageConsumer(SolidMobsSolidMobsConfigPacket packet, CustomPayloadEvent.Context ctx) {
        // Handle message
        if(packet.is_valid) {
            solidMobsConfigData = packet;

            SolidMobsMain.resetExemptions();
            SolidMobsClient.haveServerConfig = true;
            System.out.println("[Solid mobs] - Server Config data received and synced");
        }else{
            System.out.println("[Solid mobs] - Server Config data received and failed to sync\n solids mobs will be disabled");
            //disabling happens automatically with server config not being properly received
        }
        ctx.setPacketHandled(true);
        //ctx.get().setPacketHandled(true);
    }
}
