# Solid Mobs
a Minecraft Fabric mod that gives entities collisions and interactions based on that

This mod is still in beta please provide feedback to https://discord.com/invite/rURmwrzUcz

This mod allows mobs to have collisions like blocks or boats.

Standing on a mob has you move with them, and you can hold crouch to stick to the mob and not fall off (as can happen with high lag)

Slimes can be set to be bouncy like slime blocks

Mobs can be set to take a percentage of fall damage when fallen on (feels like mario)

The config allows tweaking of certain collisions, for example you can disable player collisions, pet collisions, & even tweak the amount of fall damage transferred to mobs being fallen onto.

this mod **MUST be on both the server and client**, otherwise you will have stuttering and de-sync while colliding with mobs (you won't crash or any issue like that, but it is annoying)

### Features:
- all living mobs are collide-able (with some exceptions that can be set by the config & some hardcoded like the ender dragon that act a bit weird)
- config file (*"minecraft/config/solid_mobs.json"*) can be set to enable or disable certain collisions and tweak fall damage transfer amount
- option to have the mobs fallen onto take a percentage of the fall damage
- option to have slimes be bouncy like slime blocks
- option to disable pet mob collisions
- when walking/riding a mob you move with them
- can hold crouch while on a mob to snap to it and never fall off
- Server config will sync with joining clients
- mod will auto disable if the server does not have this mod installed
