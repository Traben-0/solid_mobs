package traben.solid_mobs;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import traben.solid_mobs.config.SolidMobsConfig;

import static traben.solid_mobs.SolidMobsMain.solidMobsConfigData;

public abstract class SMData implements CustomPayload {

    public static final Id<SMData> id = new Id<>(new Identifier("solid_mobs:sync"));



    public SMData() {
        delegate = solidMobsConfigData;
    }

    public SMData(SolidMobsConfig delegate) {
        this.delegate = delegate;
    }


    public boolean isValid(){return delegate != null;}

    public SolidMobsConfig delegate;

//    @Override
    public void write(PacketByteBuf buf) {
        if(delegate != null)
            delegate.encodeToByteBuffer(buf);
    }

//    @Override
//    public Identifier id() {
//        return id;
//    }


    @Override
    public Id<? extends CustomPayload> getId() {
        return id;
    }
}
