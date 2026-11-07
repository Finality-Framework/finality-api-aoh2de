package team.rainfall.finality.api.mod;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import team.rainfall.finality.ModListMetadata;
import team.rainfall.finality.generated.ModList;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/** Resolves game resources from the mod directories selected by Finality Loader. */
public final class ModFileLoader {
    private ModFileLoader() {
    }

    public static FileHandle find(String name) {
        if (name == null || name.length() == 0 || Gdx.files == null) return null;
        ModListMetadata metadata = ModList.modList;
        if (metadata == null || !metadata.enabled || metadata.mods == null) return null;
        for (String directory : metadata.mods) {
            FileHandle file = findIn(directory, name);
            if (file != null) return file;
        }
        return null;
    }

    private static FileHandle findIn(String directory, String name) {
        try {
            Path root = Paths.get(directory).toAbsolutePath().normalize();
            Path resource = root.resolve(name).normalize();
            if (!resource.startsWith(root)) return null;
            File file = resource.toFile();
            if (!file.isFile()) return null;
            return Gdx.files.absolute(file.getAbsolutePath());
        } catch (RuntimeException ignored) {
            return null;
        }
    }
}
