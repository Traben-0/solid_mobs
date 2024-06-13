package traben.solid_mobs.neoforge;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import traben.solid_mobs.SMData;
import traben.solid_mobs.config.SolidMobsConfig;


public class SMDataNeo extends SMData {

    public static final PacketCodec<RegistryByteBuf, SMData> CODEC = new PacketCodec<RegistryByteBuf, SMData>() {
        @Override
        public SMData decode(final RegistryByteBuf buf) {
            var data = SMDataNeo.read(buf);
            if (data.isValid()) {
                return data;
            }
            return null;
        }

        @Override
        public void encode(final RegistryByteBuf buf, final SMData value) {
            value.write(buf);
        }
    };

    public SMDataNeo() {
        super();
    }

    public SMDataNeo(SolidMobsConfig delegate) {
        super(delegate);
    }

    public static SMData read(final PacketByteBuf buffer){
        SMDataNeo packet;
        if (FMLEnvironment.dist == Dist.CLIENT) {
            try {
                System.out.println("[Solid mobs] - Server Config packet received");
                packet = new SMDataNeo(new SolidMobsConfig(buffer));
            }catch(Exception e){
                System.out.println("[Solid mobs] - Server Config packet decoding failed because:\n"+e);
                e.printStackTrace();
                packet = new SMDataNeo(null);
            }
        }else{
            System.out.println("[Solid mobs] - received on server?????");
            packet = new SMDataNeo(null);
        }
        return packet;
    }
}
