package team.rainfall.finality.api.mixin;

import team.rainfall.finality.api.FinalityAPI;
import team.rainfall.lumipatch.At;
import team.rainfall.lumipatch.CallbackInfo;
import team.rainfall.lumipatch.Inject;
import team.rainfall.lumipatch.Mixin;

@Mixin(target = "aoc.kingdoms.lukasz.jakowski.AA_Game")
public class MixinAA_Game {
    @Inject(method = "create", at = @At("TAIL"))
    protected final void inject$create(CallbackInfo callbackInfo) {
        FinalityAPI.INSTANCE.init();
    }
}
