Solid mobs change-log

- improved general runtime efficiency.
- Forge users can now join Fabric servers & have their settings sync correctly.*(Forge doesn't allow this the other way around :/)*
- added `/solid_mobs` command with options for all config settings to allow tweaking in-game *(server usage requires operator permissions)*.
- added `entityBlacklist` subcommand to tweak what collisions are enabled in-game.
- added `listRecentCollisions` subcommand to list recent entity collisions to aid with identifying and blacklisting unwanted collisions.
- added `platformMode` option when true mobs will only collide on their top side, and you can press crouch to fall through them like a platform.
- added `canNonSavingEntitesCollide` option to control collisions with non-saving/null entities *(occasionally used in mods for decorative or logical purposes, like `wall jump`)*.
- added `falling_block` entities to the hard coded blacklist, various reasons/issues.
- fixed a crash when trying to shove an entity after it has been removed.
- fixed a crash when certain entities landed on others.


## V1.6
- added forge support
- added `entityCollisionBlacklist` in the config file which lets you blacklist certain mobs from having collisions, supports modded entities, formatted like:
` "entityCollisionBlacklist": ["entity.minecraft.creeper","entity.ratsmischief.rat"]`

## v1.5
- fixed an issue with dedicated servers crashing on load

## v1.4
- fixed an issue with item entities glitching around when thrown and similar problems with projectile entities


## v1.3
- added ability to shove mobs out of your way without punching if you get stuck, simply right click them while crouching to give a small knock-back, has a cool-down time set by config, *(less effective on villagers, longer cooldown & triggers reaction from hostile mobs)*
- config option added to allow or disable villager collisions *(A big part of villagers I believe is that they have a mind of their own, I do not want to encroach on this and have limited their shove-ability, as this means you can possibly get stuck the option to disable villager collisions has been added)*
- set collisions to only apply to living mobs, *(prevents issues with mods like Immersive portal's portal entities)*
- item, item frame & painting options and collisions removed for now
- fixed hostile mobs standing on their being unable to attack them


## v1.2
- dead or dying entities no longer have collisions fixing incompatibility with fast mob farms
- setting to allow invisible entity collisions *(disabled by default)*
- item frame and painting collisions now disabled by default
- fall damage transferred to a fallen upon entity now registers to the victim as an attack from the faller *(e.g. hurting zombie-piglins in this way will trigger their hostility)*
- added Allays to the pet collision exemption list in 1.19 version