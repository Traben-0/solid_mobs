package traben.solid_mobs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import traben.solid_mobs.config.Config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

public class solidMobsMain implements ModInitializer {

    public static Config solidMobsConfigData;

    public static final HashSet<EntityType<?>> EXEMPT_ENTITIES = new HashSet<EntityType<?>>();

    public static final Identifier serverConfigPacketID = new Identifier("traben_solid_mobs:server_config_packet");

    @Override
    public void onInitialize() {
        sm$loadConfig();
        setupExemptions();
    }

    public static void setupExemptions(){
        EXEMPT_ENTITIES.add(EntityType.VEX);
        if(!solidMobsConfigData.allowGroundItemCollisions)
            EXEMPT_ENTITIES.add(EntityType.ITEM);
        EXEMPT_ENTITIES.add(EntityType.ARROW);
        EXEMPT_ENTITIES.add(EntityType.SPECTRAL_ARROW);
        EXEMPT_ENTITIES.add(EntityType.AREA_EFFECT_CLOUD);
        EXEMPT_ENTITIES.add(EntityType.EXPERIENCE_ORB);

        if(!solidMobsConfigData.allowPetCollisions) {
            EXEMPT_ENTITIES.add(EntityType.WOLF);
            EXEMPT_ENTITIES.add(EntityType.CAT);
            EXEMPT_ENTITIES.add(EntityType.PARROT);
        }
        EXEMPT_ENTITIES.add(EntityType.ENDER_DRAGON);
        EXEMPT_ENTITIES.add(EntityType.POTION);
        EXEMPT_ENTITIES.add(EntityType.EGG);
        EXEMPT_ENTITIES.add(EntityType.ENDER_PEARL);
        EXEMPT_ENTITIES.add(EntityType.EXPERIENCE_BOTTLE);
        EXEMPT_ENTITIES.add(EntityType.EYE_OF_ENDER);
        EXEMPT_ENTITIES.add(EntityType.LLAMA_SPIT);
        EXEMPT_ENTITIES.add(EntityType.SHULKER_BULLET);
        EXEMPT_ENTITIES.add(EntityType.SPECTRAL_ARROW);
        EXEMPT_ENTITIES.add(EntityType.SNOWBALL);
        EXEMPT_ENTITIES.add(EntityType.TRIDENT);
        EXEMPT_ENTITIES.add(EntityType.WITHER_SKULL);
        if(!solidMobsConfigData.allowPaintingAndItemFrameCollisions) {
            EXEMPT_ENTITIES.add(EntityType.PAINTING);
            EXEMPT_ENTITIES.add(EntityType.ITEM_FRAME);
            EXEMPT_ENTITIES.add(EntityType.GLOW_ITEM_FRAME);
        }
    }

    public static void sm$loadConfig() {
        File config = new File(FabricLoader.getInstance().getConfigDir().toFile(), "solid_mobs.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if (config.exists()) {
            try {
                FileReader fileReader = new FileReader(config);
                solidMobsConfigData = gson.fromJson(fileReader, Config.class);
                fileReader.close();
                //saveConfig();
            } catch (IOException e) {
                //ETFUtils.logMessage("Config could not be loaded, using defaults", false);
            }
        } else {
            solidMobsConfigData = new Config();
            saveConfig();
        }
    }
    public static void saveConfig() {
        File config = new File(FabricLoader.getInstance().getConfigDir().toFile(), "solid_mobs.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        if (!config.getParentFile().exists()) {
            //noinspection ResultOfMethodCallIgnored
            config.getParentFile().mkdir();
        }
        try {
            FileWriter fileWriter = new FileWriter(config);
            fileWriter.write(gson.toJson(solidMobsConfigData));
            fileWriter.close();
        } catch (IOException e) {
            //logError("Config file could not be saved", false);
        }
    }




}
