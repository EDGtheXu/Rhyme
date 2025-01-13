package rhymestudio.rhyme.config;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;


public class ServerConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static ModConfigSpec.ConfigValue<Double> DaveDropRate  = BUILDER.comment("dave drop rate when he is dead").define("dave_drop_rate", 0.3);
    public static ModConfigSpec.ConfigValue<Integer> PlantConsumeAdditionStep  = BUILDER.comment("plant consume addition1 every step").define("plant_consume_addition_step", 1);

    public static final ModConfigSpec SPEC = BUILDER.build();

    public static double daveDropRate;
    public static int plantConsumeAdditionStep;


    static void init() {
        daveDropRate = DaveDropRate.get();
        plantConsumeAdditionStep = PlantConsumeAdditionStep.get();

    }
    @SubscribeEvent
    public static void onLoad(final ModConfigEvent event) {
        init();
    }
}
