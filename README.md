# Finality API for AoH2DE

## Purpose

This project provides the Finality Framework 2 API for Age of History II:
Definitive Edition. It exposes the plugin lifecycle, metadata, interaction,
logging, mod-list, and custom-view APIs. The API is initialized by
`MixinAA_Game` when the game class is loaded, and extends the game's resource
loading, menu, and Steam boundaries through `MixinFileManager`,
`MixinMenuManager`, and `MixinSteamManager`.

## Build

The build requires `libs/game.jar`, `libs/game-sources.jar`, and
`libs/finality-gradle-1.0.0.jar`. From this directory, run:

```sh
./gradlew packagePlugin
```

The plugin archive is written to
`build/plugins/team.rainfall.finality.api.aoh2de-1.0.0.jar`.

## License

This project is licensed under the GNU Lesser General Public License v3.0
(LGPLv3). See [LICENSE](LICENSE).
