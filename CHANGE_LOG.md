Solid mobs change-log

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