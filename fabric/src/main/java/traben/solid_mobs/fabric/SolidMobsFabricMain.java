package traben.solid_mobs.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import traben.solid_mobs.config.SolidMobsCommands;
import traben.solid_mobs.fabriclike.fabricLikeMain;

public class SolidMobsFabricMain implements ModInitializer {
    @Override
    public void onInitialize() {
//        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(CommandManager.literal("toggleDisableLastEntityCollision")
//                .requires(source -> source.hasPermissionLevel(4))
//                .executes(SolidMobsLastCollisionCommand::run)
//                ));

        CommandRegistrationCallback.EVENT.register(SolidMobsCommands::registerCommands);
        fabricLikeMain.init();
    }



}
