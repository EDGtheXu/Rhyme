package rhymestudio.rhyme.config.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.ModConfigSpec;

@OnlyIn(Dist.CLIENT)
public class ClientConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static ModConfigSpec.BooleanValue IsOpenBgm  = BUILDER.comment("open bgm").define("is_open_bgm", true);


    public static final ModConfigSpec SPEC = BUILDER.build();

}
