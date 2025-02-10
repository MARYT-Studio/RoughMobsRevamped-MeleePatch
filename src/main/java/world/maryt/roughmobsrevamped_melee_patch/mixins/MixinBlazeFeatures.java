package world.maryt.roughmobsrevamped_melee_patch.mixins;
import com.p1ut0nium.roughmobsrevamped.config.RoughConfig;
import com.p1ut0nium.roughmobsrevamped.features.BlazeFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(value = BlazeFeatures.class, remap = false)
public abstract class MixinBlazeFeatures {
    @Unique
    float roughMobsRevamped_MeleePatch$blazeFeatureRange;

    @Inject(
            method = "initConfig",
            at = @At(
                    value = "INVOKE",
                    shift = At.Shift.AFTER,
                    target = "Lcom/p1ut0nium/roughmobsrevamped/features/EntityFeatures;initConfig()V",
                    remap = false
            )
    )
    private void inject_initConfig(CallbackInfo ci) {
        roughMobsRevamped_MeleePatch$blazeFeatureRange = RoughConfig.getFloat(
                ((BlazeFeatures)(Object)this).name,
                "BlazeFeatureRange",
                1.0f,
                -1.0f,
                16.0f,
                "Blaze only affect targets within this radius with its features. Value not greater than 0 disables range check.");
    }
}
