package traben.solid_mobs.forge;

import net.minecraftforge.fml.common.Mod;
import traben.solid_mobs.solidMobsMain;

@Mod("solid_mobs")
public class SolidMobsForge {
    public SolidMobsForge() {

        ForgePacketHandler.init();

        solidMobsMain.init();
//        if (FMLEnvironment.dist == Dist.CLIENT) {
//
//        } else {
//
//        }
    }
}
