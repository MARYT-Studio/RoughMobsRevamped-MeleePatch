package world.maryt.rmr_melee_patch.mixins;
import com.p1ut0nium.roughmobsrevamped.config.RoughConfig;
import com.p1ut0nium.roughmobsrevamped.features.BlazeFeatures;
import com.p1ut0nium.roughmobsrevamped.misc.FeatureHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(value = BlazeFeatures.class, remap = false)
public abstract class MixinBlazeFeatures {

    @Shadow
    private boolean pushAttackersAway;
    @Shadow
    private boolean flameTouch;
    @Shadow
    private float pushStrength;

    @Unique
    float rMR_MeleePatch$blazeFeatureRange;

    @Unique
    boolean rMR_MeleePatch$isWithinFeatureRange(Entity entityA, Entity entityB) {
        return (rMR_MeleePatch$blazeFeatureRange > 0) && entityA.getDistance(entityB) <= rMR_MeleePatch$blazeFeatureRange;
    }

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
        rMR_MeleePatch$blazeFeatureRange = RoughConfig.getFloat(
                ((BlazeFeatures)(Object)this).name,
                "FeatureRange",
                1.0f,
                -1.0f,
                16.0f,
                "Blaze only affect targets within this radius with its features. Value not greater than 0 disables range check.");
    }

    /**
     * @author RisingInIris2017
     * @reason add radius check
     */
    @Overwrite
    public void onDefend(Entity target, Entity attacker, Entity immediateAttacker, LivingAttackEvent event) {
        if (this.pushAttackersAway &&
                attacker instanceof EntityLivingBase &&
                attacker == immediateAttacker && rMR_MeleePatch$isWithinFeatureRange(target, immediateAttacker)) {
            FeatureHelper.knockback(target, (EntityLivingBase)attacker, 1.0F, 0.05F);
            attacker.attackEntityFrom(DamageSource.GENERIC, this.pushStrength);
            if (this.flameTouch) {
                attacker.setFire(8);
            }

            FeatureHelper.playSound(target, SoundEvents.BLOCK_FIRE_EXTINGUISH, 0.7F, 1.0F);
        }
    }
}
