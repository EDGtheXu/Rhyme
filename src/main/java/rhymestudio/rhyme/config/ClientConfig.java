package rhymestudio.rhyme.config;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.ModConfigSpec;

@OnlyIn(Dist.CLIENT)
public class ClientConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static ModConfigSpec.BooleanValue IsOpenBgm  = BUILDER.comment("open bgm").define("is_open_bgm", true);
    public static ModConfigSpec.BooleanValue IsOpenEffectOverLay  = BUILDER.comment("open effect overlay").define("is_open_effect_overlay", true);


    public static boolean isOpenBgm;
    public static boolean isOpenEffectOverLay;

    public static void load(){
        isOpenBgm = IsOpenBgm.get();
        isOpenEffectOverLay = IsOpenEffectOverLay.get();
    }
    public static final ModConfigSpec SPEC = BUILDER.build();

}
