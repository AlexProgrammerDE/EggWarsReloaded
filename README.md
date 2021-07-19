# EggWarsReloaded

[![Discord embed](https://discordapp.com/api/guilds/739784741124833301/embed.png)](https://discord.gg/CDrcxzH) [![Build Status](https://ci.codemc.io/job/AlexProgrammerDE/job/EggWarsReloaded/badge/icon)](https://ci.codemc.io/job/AlexProgrammerDE/job/EggWarsReloaded/)

-----
**Best free EggWars plugin out there! (WIP)**

## Notice

This plugin is in its alpha state and **WILL** receive breaking changes.

## Permissions

```yaml
permissions:
  eggwarsreloaded.*:
    description: Access to all eggwars permissions.
    children:
      eggwarsreloaded.player: true
      eggwarsreloaded.admin: true
  eggwarsreloaded.player:
    default: true
    description: Basic access for the player.
    children:
      eggwarsreloaded.command.help: true
      eggwarsreloaded.command.join: true
      eggwarsreloaded.command.randomjoin: true
  eggwarsreloaded.admin:
    default: op
    description: Access to all admin commands.
    children:
      eggwarsreloaded.command.reload: true
      eggwarsreloaded.command.addarena: true
      eggwarsreloaded.command.delarena: true
      eggwarsreloaded.command.kick: true
      eggwarsreloaded.command.edit: true
      eggwarsreloaded.command.endgame: true
      eggwarsreloaded.forcestart: true
  eggwarsreloaded.command.help:
    description: Permission to run the help command.
  eggwarsreloaded.command.reload:
    description: Permission to run the reload command.
  eggwarsreloaded.command.join:
    description: Permission to run the join command.
  eggwarsreloaded.command.randomjoin:
    description: Permission to run the randomjoin command.
  eggwarsreloaded.command.addarena:
    description: Permission to run the addarena command.
  eggwarsreloaded.command.delarena:
    description: Permission to run the delarena command.
  eggwarsreloaded.command.kick:
    description: Permission to run the kick command.
  eggwarsreloaded.command.edit:
    description: Permission to run the edit command.
  eggwarsreloaded.command.endgame:
    description: Permission to run the edit command.
  eggwarsreloaded.forcestart:
    description: Permission to get the forcestart option.
```
