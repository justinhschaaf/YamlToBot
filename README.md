![# YamlToBot](https://raw.githubusercontent.com/Jusanov/YamlToBot/master/core/src/main/resources/assets/logo.png)

[![Github All Releases](https://img.shields.io/github/downloads/jusanov/yamltobot/total.svg?style=flat-square)](https://github.com/Jusanov/YamlToBot/releases)
[![GitHub release](https://img.shields.io/github/release/jusanov/yamltobot.svg?style=flat-square)](https://github.com/Jusanov/YamlToBot/releases)
[![GitHub last commit](https://img.shields.io/github/last-commit/jusanov/yamltobot.svg?style=flat-square)](https://github.com/Jusanov/YamlToBot/commits/master)
[![Wiki](https://img.shields.io/badge/Wiki-Home-red.svg?style=flat-square)](https://github.com/Jusanov/YamlToBot/wiki)
[![GitHub License](https://img.shields.io/github/license/jusanov/yamltobot.svg?style=flat-square)](https://github.com/Jusanov/YamlToBot/blob/master/LICENSE)

Create a bot from the given YAML file.

# First-time Usage

When beginning with YamlToBot, you may simply run the Jar file and it should automatically generate a default configuration file that looks something like this:

```yaml
activity: "YamlToBot"
commands: 
  - 
    description: "Play Ping Pong!"
    enabled: "true"
    message: 
      - "pong!"
    name: "ping"
  - 
    builtin: "true"
    description: "Shows a list of commands."
    enabled: "true"
    message: 
      - "%0%Commands:"
      - "%1%%cmd% | %desc%"
    name: "help"
    predefined-function: "%int%HelpCommand"
name: "MyFirstBot"
prefix: "::"
token: 123456789012345678
```

To get started, all you have to do is fill out the "token" option in the config with your bot's token. If your using Discord, then this will be your bot's Client Secret. If you are using Twitch, this will be your bot's application [OAuth token](https://dev.twitch.tv/docs/authentication/). For a more in-depth tutorial for getting started, [visit the wiki](https://github.com/jusanov/yamltobot/wiki).

# APIs Used

 - [Camel](https://github.com/decorators-squad/camel) by [decorators-squad](https://github.com/decorators-squad)
 - [Javacord](https://github.com/Javacord/Javacord) by [Javacord](https://github.com/Javacord)
 - [Twitch4J](https://github.com/twitch4j/twitch4j) by [PhilippHeuer](https://github.com/PhilippHeuer)
 - [Jusanov's Utils](https://github.com/Jusanov/JusanovsUtils) by [Jusanov](https://github.com/Jusanov)
