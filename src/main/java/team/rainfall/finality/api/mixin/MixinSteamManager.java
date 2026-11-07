package team.rainfall.finality.api.mixin;

import com.codedisaster.steamworks.SteamAPI;
import team.rainfall.finality.FinalityLogger;
import team.rainfall.lumipatch.At;
import team.rainfall.lumipatch.CallbackInfo;
import team.rainfall.lumipatch.Inject;
import team.rainfall.lumipatch.Mixin;

/** Steam bridge for AoH2DE, whose SteamManagerM is an empty compatibility class. */
@Mixin(target = "age.of.civilizations2.jakowski.lukasz.AoCGame")
public class MixinSteamManager {
    @Inject(method = "create", at = @At("HEAD"))
    private final void finality$initSteamBridge(CallbackInfo callbackInfo) {
        try {
            SteamAPI.isSteamRunning();
        } catch (Throwable throwable) {
            FinalityLogger.warn("AoH2DE Steam API is unavailable");
        }
    }

    public static boolean isSteamAvailable() {
        try {
            return SteamAPI.isSteamRunning();
        } catch (Throwable throwable) {
            return false;
        }
    }
}
