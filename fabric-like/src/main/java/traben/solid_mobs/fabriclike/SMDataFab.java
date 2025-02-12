package traben.solid_mobs.fabriclike;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import traben.solid_mobs.SMData;
import traben.solid_mobs.config.SolidMobsConfig;


public class SMDataFab extends SMData {

    public static final PacketCodec<RegistryByteBuf, SMData> CODEC = new PacketCodec<RegistryByteBuf, SMData>() {
        @Override
        public SMData decode(final RegistryByteBuf buf) {
            var data = SMDataFab.read(buf);
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

    public SMDataFab() {
        super();
    }

    public SMDataFab(SolidMobsConfig delegate) {
        super(delegate);
    }

    public static SMData read(final PacketByteBuf buffer){
        SMDataFab packet;
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            try {
                System.out.println("[Solid mobs] - Server Config packet received");
                packet = new SMDataFab(new SolidMobsConfig(buffer));
            }catch(Exception e){
                System.out.println("[Solid mobs] - Server Config packet decoding failed because:\n"+e);
                e.printStackTrace();
                packet = new SMDataFab(null);
            }
        }else{
            System.out.println("[Solid mobs] - received on server?????");
            packet = new SMDataFab(null);
        }
        return packet;
    }
}
