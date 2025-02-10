package world.maryt.roughmobsrevamped_melee_patch;

import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class MixinInit implements ILateMixinLoader {

    @Override
    public List<String> getMixinConfigs() {
        return Collections.singletonList("mixins.roughmobsrevamped_melee_patch.json");
    }
}
