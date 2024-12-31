package rhymestudio.rhyme.config;

import net.neoforged.neoforge.common.ModConfigSpec;


public class ServerConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static ModConfigSpec.ConfigValue<Double> DaveDropRate  = BUILDER.comment("dave drop rate").define("dave_drop_rate", 0.3);
    public static ModConfigSpec.ConfigValue<Integer> PlantConsumeAdditionStep  = BUILDER.comment("plant consume addition step").define("plant_consume_addition_step", 1);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
