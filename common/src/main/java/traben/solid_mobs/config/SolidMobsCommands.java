package traben.solid_mobs.config;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandExceptionType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import traben.solid_mobs.SolidMobsCrossPlatformHelper;
import traben.solid_mobs.SolidMobsMain;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

@SuppressWarnings("SameReturnValue")
public class SolidMobsCommands {


    private static final CommandExceptionType exception = new CommandExceptionType() {
    };

    private static CommandSyntaxException commandException(String str) {
        return new CommandSyntaxException(exception, () -> str);
    }

    public static int listAdd(CommandContext<ServerCommandSource> context, Set<String> list, String name) throws CommandSyntaxException {
        try {
            String add = context.getArgument("text", String.class);
            list.add(add);
            SolidMobsMain.saveConfig();
            SolidMobsMain.resetExemptions();
            context.getSource().getServer().getPlayerManager().getPlayerList().forEach(SolidMobsCrossPlatformHelper::sendConfigToClient);
            sendCommandFeedback(context, (" [" + add + "] has been added to the " + name));
            return 1;
        } catch (Exception e) {
            throw commandException(e.getMessage());
        }
    }

    public static int listRemove(CommandContext<ServerCommandSource> context, Set<String> list, String name) throws CommandSyntaxException {
        try {
            String remove = context.getArgument("text", String.class);
            list.remove(remove);
            SolidMobsMain.saveConfig();
            SolidMobsMain.resetExemptions();
            context.getSource().getServer().getPlayerManager().getPlayerList().forEach(SolidMobsCrossPlatformHelper::sendConfigToClient);
            sendCommandFeedback(context, (" [" + remove + "] has been removed from the " + name));
            return 1;
        } catch (Exception e) {
            throw commandException(e.getMessage());
        }
    }

    public static int listClear(CommandContext<ServerCommandSource> context, Set<String> list, String name) {
        Object held = new HashSet<>(list);
        list.clear();
        SolidMobsMain.saveConfig();
        SolidMobsMain.resetExemptions();
        context.getSource().getServer().getPlayerManager().getPlayerList().forEach(SolidMobsCrossPlatformHelper::sendConfigToClient);
        sendCommandFeedback(context, ("The "+name+" has been cleared, it was: " + held));
        return 1;
    }




    public static int recentCollisionsQueryAll(CommandContext<ServerCommandSource> context) {
        StringBuilder builder = new StringBuilder();
        SolidMobsMain.COLLISION_HISTORY.forEach((key, val) -> builder.append(key.toString()).append("\n - mod triggered a collision: ").append((val ? "yes.\n" : "no.\n")));
        sendCommandFeedback(context, ("All recent interactions:\n" + builder));
        return 1;
    }

    public static int recentCollisionsQueryPlayers(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        try {
            String player = EntityType.PLAYER.toString();
            StringBuilder builder = new StringBuilder();
            SolidMobsMain.COLLISION_HISTORY.forEach((collision, val) -> {
                if (player.equals(collision.first()) || player.equals(collision.second()))
                    builder.append(collision).append("\n - mod triggered a collision: ").append((val ? "yes.\n" : "no.\n"));
            });
            sendCommandFeedback(context, ("All recent interactions involving players:\n" + builder));
            return 1;
        } catch (Exception e) {
            throw commandException(e.getMessage());
        }
    }

    public static int recentCollisionsQueryYes(CommandContext<ServerCommandSource> context) {
        return collisionQueryYesNo(context, true);
    }

    public static int recentCollisionsQueryNo(CommandContext<ServerCommandSource> context) {
        return collisionQueryYesNo(context, false);
    }

    private static int collisionQueryYesNo(CommandContext<ServerCommandSource> context, boolean mustCollide) {
        StringBuilder builder = new StringBuilder();
        SolidMobsMain.COLLISION_HISTORY.forEach((key, val) -> {
            if (val == mustCollide)
                builder.append(key.toString()).append("\n - ").append(mustCollide ? "mod triggered a collision" : "mod did not trigger a collision").append("!.\n");
        });
        sendCommandFeedback(context, ("All recent interactions:\n" + builder));
        return 1;
    }

    public static int resetAllSettings(CommandContext<ServerCommandSource> context) {
        SolidMobsMain.solidMobsConfigData = new SolidMobsConfig();
        SolidMobsMain.saveConfig();
        context.getSource().getServer().getPlayerManager().getPlayerList().forEach(SolidMobsCrossPlatformHelper::sendConfigToClient);
        sendCommandFeedback(context, ("Solid Mobs server config reset to defaults."));
        return 1;
    }


    public static int runBooleanConfigCommand(CommandContext<ServerCommandSource> context, Consumer<Boolean> setter) throws CommandSyntaxException {
        try {
            Boolean bool = context.getArgument("set", Boolean.class);
            setter.accept(bool);
            SolidMobsMain.saveConfig();
            context.getSource().getServer().getPlayerManager().getPlayerList().forEach(SolidMobsCrossPlatformHelper::sendConfigToClient);
            returnMessageValue(context,bool, "Setting successfully changed.");
            return 1;
        } catch (IllegalArgumentException e) {
            throw commandException(e.getMessage());
        }
    }


    public static int runFloatConfigCommand(CommandContext<ServerCommandSource> context, Consumer<Float> setter) throws CommandSyntaxException {
        try {
            Float aFloat = context.getArgument("set", Float.class);
            setter.accept(aFloat);
            SolidMobsMain.saveConfig();
            context.getSource().getServer().getPlayerManager().getPlayerList().forEach(SolidMobsCrossPlatformHelper::sendConfigToClient);
            returnMessageValue(context,aFloat, "Setting successfully changed.");
            return 1;
        } catch (IllegalArgumentException e) {
            throw commandException(e.getMessage());
        }
    }

    public static int runIntConfigCommand(CommandContext<ServerCommandSource> context, Consumer<Integer> setter) throws CommandSyntaxException {

        try {
            Integer integer = context.getArgument("set", Integer.class);
            setter.accept(integer);
            SolidMobsMain.saveConfig();
            context.getSource().getServer().getPlayerManager().getPlayerList().forEach(SolidMobsCrossPlatformHelper::sendConfigToClient);
            returnMessageValue(context,integer, "Setting successfully changed.");
            return 1;
        } catch (IllegalArgumentException e) {
            throw commandException(e.getMessage());
        }
    }

    public static int returnMessageValue(CommandContext<ServerCommandSource> context, Object val, String info) {
        sendCommandFeedback(context, (info + "\n§a§o - current value: " + val + "§r"));
        return 1;
    }

    public static int printSettings(CommandContext<ServerCommandSource> context) {
        sendCommandFeedback(context, (SolidMobsMain.solidMobsConfigData.toString()));
        return 1;
    }


    private static void sendCommandFeedback(CommandContext<ServerCommandSource> context, String text) {
        String inputCommand = context.getInput();
        context.getSource().sendMessage(Text.of("\n§7§o/" + inputCommand + "§r\n" + text + "\n§7_____________________________"));
    }

    private static int help(CommandContext<ServerCommandSource> context) {
        sendCommandFeedback(context, ("""
                §c§lSolids Mobs, command help:§r
                
                §a§o~listRecentCollisions§r - Displays a list of recent entity collisions the mod has been involved with, and their outcomes.
                
                §a§o~entityBlacklist§r  - Allows modification of the entity blacklist (entities that will not be modified by the mod). Entity names for these commands can be found using "getRecentCollisions".
                
                §a§o~settings <setting>§r  - Allows modification of all server side settings. Must be a server Operator or be in Single-Player. Each setting will explain what is does if you run it without setting a value."""));
        return 1;
    }


    public static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess ignoredRegistryAccess, CommandManager.RegistrationEnvironment ignoredEnvironment) {
        dispatcher.register(CommandManager.literal("solid_mobs")
                .requires(source -> source.hasPermissionLevel(4)
                        || (source.getServer().isSingleplayer() && source.getPlayer() != null && source.getServer().isHost(source.getPlayer().getGameProfile()))
                )
                .executes(SolidMobsCommands::help)
                .then(CommandManager.literal("help").executes(SolidMobsCommands::help))
                .then(CommandManager.literal("listRecentCollisions")
                        .executes(SolidMobsCommands::recentCollisionsQueryAll)
                        .then(CommandManager.literal("all").executes(SolidMobsCommands::recentCollisionsQueryAll))
                        .then(CommandManager.literal("involvingPlayers").executes(SolidMobsCommands::recentCollisionsQueryPlayers))
                        .then(CommandManager.literal("resultCollision").executes(SolidMobsCommands::recentCollisionsQueryYes))
                        .then(CommandManager.literal("resultNoCollision").executes(SolidMobsCommands::recentCollisionsQueryNo))
                )
                .then(entityListCommand("entityBlacklist", SolidMobsMain.solidMobsConfigData.entityCollisionBlacklist, blacklistList()))
                .then(entityListCommand("entityBounceList", SolidMobsMain.solidMobsConfigData.entityBounceList, bounceListList()))
                .then(CommandManager.literal("settings")
                        .executes(SolidMobsCommands::printSettings)
                        .then(CommandManager.literal("platformMode").executes((context ->
                                        returnMessageValue(context, SolidMobsMain.solidMobsConfigData.platformMode,
                                                "If 'true' mobs will only be solid when walking on top of them.\n You can also press crouch to fall through them like platforms.")))
                                .then(CommandManager.argument("set", BoolArgumentType.bool())
                                        .executes((context -> runBooleanConfigCommand(context, (val) -> SolidMobsMain.solidMobsConfigData.platformMode = val))))
                        ).then(CommandManager.literal("playerOnlyMode").executes((context ->
                                        returnMessageValue(context, SolidMobsMain.solidMobsConfigData.playerOnlyMode,
                                                "If 'true' mobs will only be solid when colliding with a player not other mobs.")))
                                .then(CommandManager.argument("set", BoolArgumentType.bool())
                                        .executes((context -> runBooleanConfigCommand(context, (val) -> SolidMobsMain.solidMobsConfigData.playerOnlyMode = val))))
                        )
                        .then(CommandManager.literal("canShoveMobs").executes((context ->
                                        returnMessageValue(context, SolidMobsMain.solidMobsConfigData.allowShovingMobs,
                                                "Sets whether or not you can shove mobs, mobs can be shoved by crouching and right clicking them.\nThis allows you to get un-stuck when crammed in by a mob.\nBeware neutral mobs may get mad after being shoved.\nThe cooldown between shoving attempts can be set with the 'shovingMobsCoolDownTicks' setting")))
                                .then(CommandManager.argument("set", BoolArgumentType.bool())
                                        .executes((context -> runBooleanConfigCommand(context, (val) -> SolidMobsMain.solidMobsConfigData.allowShovingMobs = val))))
                        )
                        .then(CommandManager.literal("ignoreCollisionsWhenCrouching").executes((context ->
                                        returnMessageValue(context, SolidMobsMain.solidMobsConfigData.ignoreCollisionsWhenCrouching,
                                                "Allows you to ignore collisions when crouching. Reverts to vanilla behaviour.")))
                                .then(CommandManager.argument("set", BoolArgumentType.bool())
                                        .executes((context -> runBooleanConfigCommand(context, (val) -> SolidMobsMain.solidMobsConfigData.ignoreCollisionsWhenCrouching = val))))
                        )
                        .then(CommandManager.literal("canItemsCollide").executes((context ->
                                        returnMessageValue(context, SolidMobsMain.solidMobsConfigData.allowItemCollisions,
                                                "Sets whether or not Items on the ground are made solid.")))
                                .then(CommandManager.argument("set", BoolArgumentType.bool())
                                        .executes((context -> runBooleanConfigCommand(context, (val) -> SolidMobsMain.solidMobsConfigData.allowItemCollisions = val))))
                        )
                        .then(CommandManager.literal("canPlayersCollide").executes((context ->
                                        returnMessageValue(context, SolidMobsMain.solidMobsConfigData.allowPlayerCollisions,
                                                "Sets whether or not players can collide with other players.")))
                                .then(CommandManager.argument("set", BoolArgumentType.bool())
                                        .executes((context -> runBooleanConfigCommand(context, (val) -> SolidMobsMain.solidMobsConfigData.allowPlayerCollisions = val))))
                        )
                        .then(CommandManager.literal("canPetsCollide").executes((context ->
                                        returnMessageValue(context, SolidMobsMain.solidMobsConfigData.allowPetCollisions,
                                                "Sets whether or not pet mobs collide. E.G. if you don't want your dog getting in your way.")))
                                .then(CommandManager.argument("set", BoolArgumentType.bool())
                                        .executes((context -> runBooleanConfigCommand(context, (val) -> SolidMobsMain.solidMobsConfigData.allowPetCollisions = val))))
                        )
                        .then(CommandManager.literal("canVillagersCollide").executes((context ->
                                        returnMessageValue(context, SolidMobsMain.solidMobsConfigData.allowVillagerCollisions,
                                                "Sets whether or not Villagers can collide.")))
                                .then(CommandManager.argument("set", BoolArgumentType.bool())
                                        .executes((context -> runBooleanConfigCommand(context, (val) -> SolidMobsMain.solidMobsConfigData.allowVillagerCollisions = val))))
                        )
                        .then(CommandManager.literal("canBabiesCollide").executes((context ->
                                        returnMessageValue(context, SolidMobsMain.solidMobsConfigData.allowBabyCollisions,
                                                "Sets whether or not Baby mobs can collide.")))
                                .then(CommandManager.argument("set", BoolArgumentType.bool())
                                        .executes((context -> runBooleanConfigCommand(context, (val) -> SolidMobsMain.solidMobsConfigData.allowBabyCollisions = val))))
                        )
                        .then(CommandManager.literal("canInvisibleMobsCollide").executes((context ->
                                        returnMessageValue(context, SolidMobsMain.solidMobsConfigData.allowInvisibleCollisions,
                                                "Sets whether or not invisible mobs will still collide.")))
                                .then(CommandManager.argument("set", BoolArgumentType.bool())
                                        .executes((context -> runBooleanConfigCommand(context, (val) -> SolidMobsMain.solidMobsConfigData.allowInvisibleCollisions = val))))
                        )
                        .then(CommandManager.literal("canFallDamageBeSharedWithLandedOnMobs").executes((context ->
                                        returnMessageValue(context, SolidMobsMain.solidMobsConfigData.fallDamageSharedWithLandedOnMob,
                                                "If 'true' then a percentage of fall damage is absorbed by any mob you fall onto.\nThis percentage can be set via the 'fallDamagePercentageSharedWithLandedOnMob' setting.")))
                                .then(CommandManager.argument("set", BoolArgumentType.bool())
                                        .executes((context -> runBooleanConfigCommand(context, (val) -> SolidMobsMain.solidMobsConfigData.fallDamageSharedWithLandedOnMob = val))))
                        )
                        .then(CommandManager.literal("canNonSavingEntitesCollide").executes((context ->
                                        returnMessageValue(context, SolidMobsMain.solidMobsConfigData.allowNonSavingEntityCollisions,
                                                "Sets whether or not Null/Unsaved entities can collide. Typically these might be decorative or logical entities depending on the mod.")))
                                .then(CommandManager.argument("set", BoolArgumentType.bool())
                                        .executes((context -> runBooleanConfigCommand(context, (val) -> SolidMobsMain.solidMobsConfigData.allowNonSavingEntityCollisions = val))))
                        )
                        .then(CommandManager.literal("fallDamagePercentageSharedWithLandedOnMob").executes((context ->
                                        returnMessageValue(context, SolidMobsMain.solidMobsConfigData.fallDamageAmountAbsorbedByLandedOnMob,
                                                "If 'canFallDamageBeSharedWithLandedOnMobs' is enabled, this sets the percentage of fall damage shared with the other mob.\n0.5 = 50/50 split of the damage.\n0.75 = 25% fall damage taken by the falling mob and 75% damage taken by the mob being landed upon.")))
                                .then(CommandManager.argument("set", FloatArgumentType.floatArg(0, 1))
                                        .executes((context -> runFloatConfigCommand(context, (val) -> SolidMobsMain.solidMobsConfigData.fallDamageAmountAbsorbedByLandedOnMob = val))))
                        )
                        .then(CommandManager.literal("shovingMobsCoolDownTicks").executes((context ->
                                        returnMessageValue(context, SolidMobsMain.solidMobsConfigData.fallDamageAmountAbsorbedByLandedOnMob,
                                                "Sets the time in ticks between players being able to shove mobs.\n20 ticks = 1 second.")))
                                .then(CommandManager.argument("set", IntegerArgumentType.integer(1, 9999999))
                                        .executes((context -> runIntConfigCommand(context, (val) -> SolidMobsMain.solidMobsConfigData.fallDamageAmountAbsorbedByLandedOnMob = val))))
                        )
                        .then(CommandManager.literal("resetAllSettings").executes(SolidMobsCommands::resetAllSettings))
                        .then(CommandManager.literal("listAllSettings").executes(SolidMobsCommands::printSettings))
                )
        );
    }

    private static LiteralArgumentBuilder<ServerCommandSource> entityListCommand(String name, Set<String> list, Command<ServerCommandSource> listPrinter) {
        return CommandManager.literal(name)
                .executes(listPrinter)
                .then(CommandManager.literal("list")
                        .executes(listPrinter))
                .then(CommandManager.literal("add")
                        .then(CommandManager.argument("text", StringArgumentType.string())
                                .executes(context -> listAdd(context, list, name))))
                .then(CommandManager.literal("remove")
                        .then(CommandManager.argument("text", StringArgumentType.string())
                                .executes(c-> listRemove(c, list, name))))
                .then(CommandManager.literal("clear")
                        .executes(context -> listClear(context, list, name)))
                .then(CommandManager.literal("add_all_entities")
                        .executes(c -> addAllToList(c, list, name)));
    }

    private static int addAllToList(CommandContext<ServerCommandSource> context, Set<String> list, String name) {
        int size = list.size();
        Registries.ENTITY_TYPE.forEach(entityType -> list.add(entityType.toString()));
        list.remove(EntityType.PLAYER.toString()); // Don't add players to the blacklist
        SolidMobsMain.saveConfig();
        SolidMobsMain.resetExemptions();
        context.getSource().getServer().getPlayerManager().getPlayerList().forEach(SolidMobsCrossPlatformHelper::sendConfigToClient);
        sendCommandFeedback(context, ((list.size() - size) + " new entities have been added to the " + name));
        return 1;
    }

    private static @NotNull Command<ServerCommandSource> blacklistList() {
        return c -> {
            sendCommandFeedback(c, ("The HARDCODED collision blacklist (set by the settings or the dev): "
                    + SolidMobsMain.EXEMPT_ENTITIES.toString()
                    + "\n§7_____________________________§r\nThe Custom collision blacklist (set by you): "
                    + SolidMobsMain.solidMobsConfigData.entityCollisionBlacklist));
            return 1;
        };
    }

    private static @NotNull Command<ServerCommandSource> bounceListList() {
        return c -> {
            sendCommandFeedback(c, ("The Custom entity bounce list: "
                    + SolidMobsMain.solidMobsConfigData.entityBounceList));
            return 1;
        };
    }
}
