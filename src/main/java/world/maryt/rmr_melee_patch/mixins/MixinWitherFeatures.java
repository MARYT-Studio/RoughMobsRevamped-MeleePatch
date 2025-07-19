package world.maryt.rmr_melee_patch.mixins;
import com.p1ut0nium.roughmobsrevamped.config.RoughConfig;
import com.p1ut0nium.roughmobsrevamped.features.WitherFeatures;
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


@Mixin(value = WitherFeatures.class, remap = false)
public abstract class MixinWitherFeatures {

    @Shadow
    private boolean pushAttackersAway;
    @Shadow
    private boolean enableKnockBackDamage;

    @Unique
    float rMR_MeleePatch$witherFeatureRange;

    @Unique
    boolean rMR_MeleePatch$isWithinFeatureRange(Entity entityA, Entity entityB) {
        if (rMR_MeleePatch$witherFeatureRange <= 0.0f) return true;
        return entityA.getDistance(entityB) <= rMR_MeleePatch$witherFeatureRange;
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
        rMR_MeleePatch$witherFeatureRange = RoughConfig.getFloat(
                ((WitherFeatures)(Object)this).name,
                "FeatureRange",
                1.0f,
                -1.0f,
                1024.0f,
                "Wither only affect targets within this radius with its features. Value not greater than 0 disables range check.");
    }

    /**
     * @author RisingInIris2017
     * @reason add radius check
     */
    @Overwrite
    public void onDefend(Entity target, Entity attacker, Entity immediateAttacker, LivingAttackEvent event) {
        if (this.pushAttackersAway &&
                attacker instanceof EntityLivingBase &&
                attacker == immediateAttacker &&
                rMR_MeleePatch$isWithinFeatureRange(target, attacker)) {
            FeatureHelper.knockback(target, (EntityLivingBase)attacker, 1.0F, 0.05F);
            if (this.enableKnockBackDamage) {
                attacker.attackEntityFrom(DamageSource.GENERIC, 4.0F);
            }

            FeatureHelper.playSound(target, SoundEvents.ENTITY_WITHER_BREAK_BLOCK, 0.7F, 1.0F);
        }
    }
}
