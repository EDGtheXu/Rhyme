package rhymestudio.rhyme.config;


import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.config.ModConfigEvent;

public class ServerConfig {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec.ConfigValue<Double> DaveDropRate  = BUILDER.comment("dave drop rate when he is dead").define("dave_drop_rate", 0.3);
    public static ForgeConfigSpec.ConfigValue<Integer> PlantConsumeAdditionStep  = BUILDER.comment("plant consume addition1 every step").define("plant_consume_addition_step", 1);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

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
