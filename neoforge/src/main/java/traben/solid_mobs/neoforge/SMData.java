package traben.solid_mobs.neoforge;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import traben.solid_mobs.config.SolidMobsConfig;

import static traben.solid_mobs.SolidMobsMain.solidMobsConfigData;

public class SMData implements CustomPayload {

    public static final Identifier id = new Identifier("solid_mobs:sync");


    public SMData() {
        delegate = solidMobsConfigData;
    }

    public SMData(SolidMobsConfig delegate) {
        this.delegate = delegate;
    }

    public static SMData read(final PacketByteBuf buffer){
        SMData packet;
        if (FMLEnvironment.dist == Dist.CLIENT) {
            try {
                System.out.println("[Solid mobs] - Server Config packet received");
                packet = new SMData(new SolidMobsConfig(buffer));
            }catch(Exception e){
                System.out.println("[Solid mobs] - Server Config packet decoding failed because:\n"+e);
                e.printStackTrace();
                packet = new SMData(null);
            }
        }else{
            System.out.println("[Solid mobs] - received on server?????");
            packet = new SMData(null);
        }
        return packet;
    }

    public boolean isValid(){return delegate != null;}

    public final SolidMobsConfig delegate;

    @Override
    public void write(PacketByteBuf buf) {
        if(delegate != null)
            delegate.encodeToByteBuffer(buf);
    }

    @Override
    public Identifier id() {
        return id;
    }
}
