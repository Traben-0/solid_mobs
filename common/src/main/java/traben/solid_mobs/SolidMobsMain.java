package traben.solid_mobs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unimi.dsi.fastutil.objects.Object2BooleanLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import traben.solid_mobs.client.SolidMobsClient;
import traben.solid_mobs.config.Config;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SolidMobsMain {

    public static Config solidMobsConfigData;

    public static HashSet<String> EXEMPT_ENTITIES = new HashSet<>();

    public static final Identifier SERVER_CONFIG_PACKET_ID = new Identifier("traben_solid_mobs:server_config_packet");

    public static final HashMap<UUID, Long> LAST_PUSH_TIME = new HashMap<>();
    // public static final HashMap<UUID, Long> lastAttackTime = new HashMap<>();


    public static void init() {
        sm$loadConfig();
        //resetExemptions();
    }

    public static void resetExemptions() {
        EXEMPT_CACHE.clear();
        COLLISION_HISTORY.clear();
        EXEMPT_ENTITIES.clear();
        EXEMPT_ENTITIES.add(EntityType.VEX.toString());
        if (!solidMobsConfigData.allowItemCollisions)
            EXEMPT_ENTITIES.add(EntityType.ITEM.toString());
        EXEMPT_ENTITIES.add(EntityType.ARROW.toString());
        EXEMPT_ENTITIES.add(EntityType.SPECTRAL_ARROW.toString());
        EXEMPT_ENTITIES.add(EntityType.AREA_EFFECT_CLOUD.toString());
        EXEMPT_ENTITIES.add(EntityType.EXPERIENCE_ORB.toString());

        if (!solidMobsConfigData.allowVillagerCollisions) {
            EXEMPT_ENTITIES.add(EntityType.VILLAGER.toString());
        }

        if (!solidMobsConfigData.allowPetCollisions) {
            EXEMPT_ENTITIES.add(EntityType.WOLF.toString());
            EXEMPT_ENTITIES.add(EntityType.CAT.toString());
            EXEMPT_ENTITIES.add(EntityType.PARROT.toString());
        }
        EXEMPT_ENTITIES.add(EntityType.ENDER_DRAGON.toString());
        EXEMPT_ENTITIES.add(EntityType.POTION.toString());
        EXEMPT_ENTITIES.add(EntityType.EGG.toString());
        EXEMPT_ENTITIES.add(EntityType.ENDER_PEARL.toString());
        EXEMPT_ENTITIES.add(EntityType.EXPERIENCE_BOTTLE.toString());
        EXEMPT_ENTITIES.add(EntityType.EYE_OF_ENDER.toString());
        EXEMPT_ENTITIES.add(EntityType.LLAMA_SPIT.toString());
        EXEMPT_ENTITIES.add(EntityType.SHULKER_BULLET.toString());
        EXEMPT_ENTITIES.add(EntityType.SPECTRAL_ARROW.toString());
        EXEMPT_ENTITIES.add(EntityType.SNOWBALL.toString());
        EXEMPT_ENTITIES.add(EntityType.TRIDENT.toString());
        EXEMPT_ENTITIES.add(EntityType.WITHER_SKULL.toString());
        //if(!solidMobsConfigData.allowPaintingAndItemFrameCollisions) {
        EXEMPT_ENTITIES.add(EntityType.PAINTING.toString());
        EXEMPT_ENTITIES.add(EntityType.ITEM_FRAME.toString());
        EXEMPT_ENTITIES.add(EntityType.GLOW_ITEM_FRAME.toString());
        //}
        EXEMPT_ENTITIES.add(EntityType.FALLING_BLOCK.toString());

        EXEMPT_ENTITIES.add(EntityType.MARKER.toString());
        EXEMPT_ENTITIES.add(EntityType.BLOCK_DISPLAY.toString());
        EXEMPT_ENTITIES.add(EntityType.ITEM_DISPLAY.toString());
        EXEMPT_ENTITIES.add(EntityType.TEXT_DISPLAY.toString());
        EXEMPT_ENTITIES.add(EntityType.INTERACTION.toString());

        EXEMPT_ENTITIES.addAll(Arrays.asList(solidMobsConfigData.entityCollisionBlacklist));


        //no player types in return list this is handled by player on player collisions
        // players being in this list will cause any collision against the player to fail
        String player = EntityType.PLAYER.toString();
        if (EXEMPT_ENTITIES.contains(player)) EXEMPT_ENTITIES.removeIf((val) -> Objects.equals(val, player));

    }

    public static final Object2BooleanOpenHashMap<EntityType<?>> EXEMPT_CACHE = new Object2BooleanOpenHashMap<>();
    public static boolean isExemptEntity(Entity entity){
        return isExemptType(entity.getType());
    }
    public static boolean isExemptType(EntityType<?> entityType){
        //hashmap faster
        if(EXEMPT_CACHE.containsKey(entityType)){
            return EXEMPT_CACHE.getBoolean(entityType);
        }
        boolean value = EXEMPT_ENTITIES.contains(entityType.toString());
        EXEMPT_CACHE.put(entityType,value);
        return value;
    }

    public static void sm$loadConfig() {
        SolidMobsClient.haveServerConfig = false;
        File config = new File(SolidMobsCrossPlatformHelper.getConfigDirectory().toFile(), "solid_mobs.json");
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
        }
        saveConfig();
        resetExemptions();
    }

    public static void saveConfig() {
        if (!SolidMobsClient.haveServerConfig) {
            File config = new File(SolidMobsCrossPlatformHelper.getConfigDirectory().toFile(), "solid_mobs.json");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            if (!config.getParentFile().exists()) {
                //noinspection ResultOfMethodCallIgnored
                config.getParentFile().mkdirs();
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


    public static final Object2BooleanLinkedOpenHashMap<CollisionEvent> COLLISION_HISTORY = new Object2BooleanLinkedOpenHashMap<>();


    public static void registerCollision(String entity1, String entity2, boolean result) {
        CollisionEvent event = new CollisionEvent(entity1, entity2);
        COLLISION_HISTORY.putAndMoveToLast(event, result);
        if (COLLISION_HISTORY.size() > 64) {
            COLLISION_HISTORY.removeFirstBoolean();
        }
    }

    public record CollisionEvent(String first, String second) {
        @Override
        public String toString() {
            return "[" +
                    first +
                    " -> " + second +
                    "]";
        }

        @Override
        public boolean equals(Object o) {
            //wont occur
            // if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;
            CollisionEvent that = (CollisionEvent) o;
            return (Objects.equals(first, that.first) && Objects.equals(second, that.second))
                    || (Objects.equals(second, that.first) && Objects.equals(first, that.second));
        }

        @Override
        public int hashCode() {
            return first.hashCode() + second.hashCode(); //Objects.hash(first, second);
        }
    }

}
