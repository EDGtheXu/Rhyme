package rhymestudio.rhyme.config;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.ModConfigSpec;

@OnlyIn(Dist.CLIENT)
public class ClientConfig {
    public static ModConfigSpec SPEC;

    public static ModConfigSpec.BooleanValue IsOpenBgm;
    public static ModConfigSpec.BooleanValue IsOpenEffectOverLay;


    public static boolean isOpenBgm;
    public static boolean isOpenEffectOverLay;

    public static void load(){
        isOpenBgm = IsOpenBgm.get();
        isOpenEffectOverLay = IsOpenEffectOverLay.get();
    }

    public static ModConfigSpec init(){
        ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

        IsOpenBgm  = BUILDER
                .comment("open bgm")
                .define("is_open_bgm", true);
        IsOpenEffectOverLay = BUILDER
                .comment("open effect overlay")
                .define("is_open_effect_overlay", true);

        ModConfigSpec SPEC = BUILDER.build();
        ClientConfig.SPEC = SPEC;
        return SPEC;
    }

}
