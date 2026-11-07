package team.rainfall.finality.api.mixin;

import com.badlogic.gdx.files.FileHandle;
import team.rainfall.finality.api.mod.ModFileLoader;
import team.rainfall.lumipatch.At;
import team.rainfall.lumipatch.CallbackInfo;
import team.rainfall.lumipatch.Inject;
import team.rainfall.lumipatch.Mixin;

/** Makes Loader-selected mod directories visible to AoH2DE's FileManager. */
@Mixin(target = "age.of.civilizations2.jakowski.lukasz.Files.FileManager")
public class MixinFileManager {
    @Inject(method = "loadFile", at = @At("HEAD"), cancellable = true)
    private static void finality$loadFromMods(String name, CallbackInfo callbackInfo) {
        FileHandle file = ModFileLoader.find(name);
        if (file != null) callbackInfo.cancel(file);
    }
}
