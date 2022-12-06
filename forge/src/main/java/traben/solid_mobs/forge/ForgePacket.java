package traben.solid_mobs.forge;

import net.minecraft.network.PacketByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkEvent;
import traben.solid_mobs.client.solidMobsClient;
import traben.solid_mobs.config.Config;
import traben.solid_mobs.solidMobsMain;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Supplier;

import static traben.solid_mobs.solidMobsMain.EXEMPT_ENTITIES;
import static traben.solid_mobs.solidMobsMain.solidMobsConfigData;


public class ForgePacket 
{
    // A class MessagePacket
    public void encoder(PacketByteBuf buffer) {
        // Write to buffer

        System.out.println("[Solid Mobs] - Encoding server config packet for client.");
        //PRESERVE WRITE ORDER IN READ
        /////////////////////////////////////////
        buffer.writeBoolean(solidMobsConfigData.allowItemCollisions);
        buffer.writeBoolean(solidMobsConfigData.allowPlayerCollisions);
        buffer.writeBoolean(solidMobsConfigData.allowPetCollisions);
        buffer.writeBoolean(solidMobsConfigData.bouncySlimes);
        buffer.writeBoolean(solidMobsConfigData.fallDamageSharedWithLandedOnMob);
        buffer.writeFloat(solidMobsConfigData.getFallAbsorbAmount());
        //buf.writeBoolean(solidMobsConfigData.allowPaintingAndItemFrameCollisions);
        buffer.writeBoolean(solidMobsConfigData.allowInvisibleCollisions);
        buffer.writeBoolean(solidMobsConfigData.allowShovingMobs);
        buffer.writeInt(solidMobsConfigData.shoveAgainTimeInTicks);
        buffer.writeBoolean(solidMobsConfigData.allowVillagerCollisions);
        ///////////////////////////////////////////////
        buffer.writeInt(((CharSequence)Arrays.toString(solidMobsConfigData.entityCollisionBlacklist)).length());
        buffer.writeCharSequence(Arrays.toString(solidMobsConfigData.entityCollisionBlacklist), Charset.defaultCharset());
        ///////////////////////////////////////////////////
    }

    public static ForgePacket decoder(PacketByteBuf buffer) {
        // Create packet from buffer data
        //create server config
        ForgePacket packet = new ForgePacket();
        try {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                //todo setup packet listening however it works on forge

                System.out.println("[Solid mobs] - Server Config packet received");
                //PRESERVE WRITE ORDER IN READ
                /////////////////////////////////////////
                packet.allowItemCollisions = buffer.readBoolean();
                packet.allowPlayerCollisions = buffer.readBoolean();
                packet.allowPetCollisions = buffer.readBoolean();
                packet.bouncySlimes = buffer.readBoolean();
                packet.fallDamageSharedWithLandedOnMob = buffer.readBoolean();
                packet.setFallAbsorbAmount = buffer.readFloat();
                //solidMobsConfigData.allowPaintingAndItemFrameCollisions = buf.readBoolean();
                packet.allowInvisibleCollisions = buffer.readBoolean();
                packet.allowShovingMobs = buffer.readBoolean();
                packet.shoveAgainTimeInTicks = buffer.readInt();
                packet.allowVillagerCollisions = buffer.readBoolean();
                ///////////////////////////////////////////////
                int length = buffer.readInt();
//                CharSequence seq = ;
//                System.out.println("test "+ seq.length());
//
//                seq.chars().forEachOrdered((i)->{System.out.println("- "+ i);});
//
                String read =  buffer.readCharSequence(length,Charset.defaultCharset()).toString();

                //remove bracketing
                read = read.replaceFirst("^\\[", "").replaceFirst("]$", "");
                //split array string
                packet.entityCollisionBlacklist = read.split(", ");
                ///////////////////////////////////////////////////

                packet.is_valid = true;
            }
        }catch(Exception e){
            System.out.println("[Solid mobs] - Server Config packet decoding failed because:\n"+e);
           // e.printStackTrace();
            packet.is_valid = false;
        }
        return packet;
    }

    ////packet data
    private boolean is_valid;

    private boolean allowItemCollisions;
    private boolean allowPlayerCollisions;
    private boolean allowPetCollisions;
    private boolean bouncySlimes;
    private boolean fallDamageSharedWithLandedOnMob;
    private float setFallAbsorbAmount;
    private boolean allowInvisibleCollisions;
    private boolean allowShovingMobs;
    private int shoveAgainTimeInTicks;
    private boolean allowVillagerCollisions;

    public String[] entityCollisionBlacklist;



    public void messageConsumer(Supplier<NetworkEvent.Context> ctx) {
        // Handle message
        if(is_valid) {

            solidMobsConfigData = new Config();
            /////////////////////////////////////////
            solidMobsConfigData.allowItemCollisions = this.allowItemCollisions;
            solidMobsConfigData.allowPlayerCollisions = this.allowPlayerCollisions;
            solidMobsConfigData.allowPetCollisions = this.allowPetCollisions;
            solidMobsConfigData.bouncySlimes = this.bouncySlimes;
            solidMobsConfigData.fallDamageSharedWithLandedOnMob = this.fallDamageSharedWithLandedOnMob;
            solidMobsConfigData.setFallAbsorbAmount(this.setFallAbsorbAmount);
            //solidMobsConfigData.allowPaintingAndItemFrameCollisions = buf.readBoolean();
            solidMobsConfigData.allowInvisibleCollisions = this.allowInvisibleCollisions;
            solidMobsConfigData.allowShovingMobs = this.allowShovingMobs;
            solidMobsConfigData.shoveAgainTimeInTicks = this.shoveAgainTimeInTicks;
            solidMobsConfigData.allowVillagerCollisions = this.allowVillagerCollisions;
            ///////////////////////////////////////////////
            solidMobsConfigData.entityCollisionBlacklist = this.entityCollisionBlacklist;
            ///////////////////////////////////////////////
            EXEMPT_ENTITIES = new HashSet<String>();
            solidMobsMain.resetExemptions();
            solidMobsClient.haveServerConfig = true;
            System.out.println("[Solid mobs] - Server Config data received and synced");
        }else{
            System.out.println("[Solid mobs] - Server Config data received and failed to sync\n solids mobs will be disabled");
            //disabling happens automatically with server config not being properly received
        }
        ctx.get().setPacketHandled(true);
    }
}
