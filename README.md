# YamlToBot
[![Github All Releases](https://img.shields.io/github/downloads/jusanov/yamltobot/total.svg?style=flat-square)](https://github.com/Jusanov/YamlToBot/releases)
[![GitHub release](https://img.shields.io/github/release/jusanov/yamltobot.svg?style=flat-square)](https://github.com/Jusanov/YamlToBot/releases)
[![GitHub last commit](https://img.shields.io/github/last-commit/jusanov/yamltobot.svg?style=flat-square)](https://github.com/Jusanov/YamlToBot/commits/master)
[![Wiki](https://img.shields.io/badge/Wiki-Home-red.svg?style=flat-square)](https://github.com/Jusanov/YamlToBot/wiki)
[![GitHub License](https://img.shields.io/github/license/jusanov/yamltobot.svg?style=flat-square)](https://github.com/Jusanov/YamlToBot/blob/master/LICENSE)

Create a bot from the given YAML file.

### First-time Usage

When beginning with YamlToBot, you may simply run the Jar file and it should automatically generate a default configuration file that looks something like this:

```yaml
activity: "YamlToBot"
channels:
  - Jusanov
commands: 
  - 
    enabled: "true"
    message: 
      - "pong!"
    name: "ping"
name: "MyFirstBot"
prefix: "::"
token: 123456789012345678
```

To get started, all you have to do is fill out the "token" option in the config with your bot's token. If your using Discord, then this will be your bot's Client Secret. If you are using Twitch, this will be your bot's application [OAuth token](https://dev.twitch.tv/docs/authentication/).