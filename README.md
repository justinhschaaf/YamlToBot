# ![YamlToBot](https://content.yamltobot.com/common/logos/logo256.png)

[![Github All Releases](https://img.shields.io/github/downloads/yamltobot/yamltobot/total.svg?style=flat-square)](https://yamltobot.com/download)
[![GitHub release](https://img.shields.io/github/release/yamltobot/yamltobot.svg?style=flat-square)](https://yamltobot.com/download)
[![GitHub last commit](https://img.shields.io/github/last-commit/yamltobot/yamltobot.svg?style=flat-square)](https://github.com/YamlToBot/YamlToBot/commits/master)
[![Wiki](https://img.shields.io/badge/Wiki-Home-red.svg?style=flat-square)](https://yamltobot.com/wiki)
[![GitHub License](https://img.shields.io/github/license/yamltobot/yamltobot.svg?style=flat-square)](https://github.com/YamlToBot/YamlToBot/blob/master/LICENSE)

Create a bot from the given YAML file.

# Deprecation/Archival Notice

As of February 1, 2024, YamlToBot and all associated repositories will be archived, and the website will be defunct. The project has outlived it's usefulness for me and hasn't been updated in so long to the point where it likely does not work anymore--and even if it does, you shouldn't use it. If you're interested in taking ownership and continuing the project, please reach out to me [via email](mailto:justin@justinschaaf.com) so we can work something out.

If you're still interested in what I've been up to all these years, [my website](https://justinschaaf.com) links to various projects and socials you may find interesting. Thank you for your support.

## Donate

If you like what I do, then please consider supporting me on Liberapay.

[![Donate](https://liberapay.com/assets/widgets/donate.svg)](https://liberapay.com/justinhschaaf) 

## First-time Usage

When beginning with YamlToBot, simply run the Jar file and it should automatically generate a default configuration file that looks something like this:

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
      - "Commands:"
      - "%cmd% | %desc%"
    name: "help"
    script: "HelpCommand"
    usage: "::help <command name>"
name: "MyFirstBot"
prefix: "::"
token: 123456789012345678
```

To get started, all you have to do is fill out the "token" option in the config with your bot's token. If your using Discord, then this will be your bot's Client Secret. If you are using Twitch, this will be your bot's application [OAuth token](https://dev.twitch.tv/docs/authentication/). For a more in-depth tutorial for getting started, [visit the wiki](https://yamltobot.com/wiki).

## APIs Used

| Name                                                                      | Author                                                    | Usage |
|-------                                                                    |-------                                                    |--------------------|
| [eo-yaml](https://github.com/decorators-squad/eo-yaml)                    | [decorators-squad](https://github.com/decorators-squad)   | Parsing the YAML configuration file |
| [Javacord](https://github.com/Javacord/Javacord)                          | [Javacord](https://github.com/Javacord)                   | Connecting to and interacting with Discord |
| [Twitch4J](https://github.com/twitch4j/twitch4j)                          | [PhilippHeuer](https://github.com/PhilippHeuer)           | Connecting to and interacting with Twitch |
| [beam-client-java](https://github.com/mixer/beam-client-java)             | [Mixer](https://mixer.com/)                               | Connecting to and interacting with Mixer |
| [YAOSJA](https://github.com/justinhschaaf/YAOSJA)                         | [Justin Schaaf](https://github.com/justinhschaaf)         | `FileManager.moveFile(source, dest)` for logs. |
| [JSON-P](https://javaee.github.io/jsonp/index.html)                       | [JavaEE](https://github.com/javaee)                       | Parsing JSON in the version checker. |
| [PgsLookAndFeel](https://www.pagosoft.com/projects/pgslookandfeel/)       | [Patrick Gotthardt](https://www.pagosoft.com)             | Look and Feel |
