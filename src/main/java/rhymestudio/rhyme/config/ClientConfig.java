package rhymestudio.rhyme.config;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.ModConfigSpec;

@OnlyIn(Dist.CLIENT)
public class ClientConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    static ModConfigSpec.BooleanValue IsOpenBgm  = BUILDER.comment("open bgm").define("is_open_bgm", true);


    public static boolean isOpenBgm;

    public static void load(){
        isOpenBgm = IsOpenBgm.get();
    }
    public static final ModConfigSpec SPEC = BUILDER.build();

}
