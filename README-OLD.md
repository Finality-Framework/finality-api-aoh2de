# Finality API for AoH2DE

This project provides the Finality Framework 2 API for Age of History II:
Definitive Edition. It contains the same plugin lifecycle, metadata, interaction,
logging, and mod-list APIs as the AoH3 API project.

The game classes are compiled from `libs/game.jar`, while the matching source
archive is kept at `libs/game-sources.jar` for navigation and development. The
AoH2DE game starts through `aoc.kingdoms.lukasz.jakowski.AA_Game`; the API is
initialized by `MixinAA_Game` when that class is loaded.

Build the plugin with:

```sh
./gradlew packagePlugin
```

The plugin archive is written to `build/plugins/team.rainfall.finality.api.aoh2de-1.0.0.jar`.

Custom views use the same callback-based API as AoH3:

```java
MenuProxy.PROXY.addView("my-view", ignored -> {
    ArrayList<Menu> pages = new ArrayList<>();
    pages.add(new MyMenu());
    return pages;
});
MenuProxy.setViewID("my-view");
```

`MixinMenuManager` adds the custom-view registration and switching methods
directly to AoH2DE's `MenuManager`. Built-in views can still be selected with
`ViewAPI.setView(...)` and `ViewAPI.setViewWithoutAnimation(...)`.

Mod loading is provided by Finality Loader: the `--mods` JSON file is read
before the game starts, each listed directory's `plugins/*.jar` files are
loaded, and the generated `ModList` metadata is available to the API. AoH2DE's
`SteamManagerM` and `Steam_Game` are empty compatibility classes, so a Steam
workshop loader cannot be attached to them. `MixinSteamManager` targets the
actual `AoCGame.create` Steam boundary and exposes Steam availability without
duplicating the Loader's plugin loading.

`MixinFileManager` extends the game's real `FileManager.loadFile` entry point.
For every requested resource it checks the Loader-selected mod directories in
order and returns the first matching file, so mod resources override base game
resources while normal AoH2DE resource lookup remains available.
