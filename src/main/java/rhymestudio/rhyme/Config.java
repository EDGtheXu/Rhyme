package rhymestudio.rhyme;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static ModConfigSpec.BooleanValue IsOpenBgm  = BUILDER.comment("open bgm").define("isOpenBgm", true);


    static final ModConfigSpec SPEC = BUILDER.build();

}
