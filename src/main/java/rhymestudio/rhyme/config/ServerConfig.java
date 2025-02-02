package rhymestudio.rhyme.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerConfig {
    public static ModConfigSpec.ConfigValue<Double> DaveDropRate;
    public static ModConfigSpec.ConfigValue<Integer> PlantConsumeAdditionStep;
    public static ModConfigSpec.ConfigValue<Double> PLANT_ATTRIBUTES_MULTIPLIER_HEALTH;
    public static ModConfigSpec.ConfigValue<Double> PLANT_ATTRIBUTES_MULTIPLIER_DAMAGE ;
    public static ModConfigSpec SPEC;

    public static ModConfigSpec init() {
        ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

        DaveDropRate  = BUILDER
                .comment("dave drop rate when he is dead")
                .defineInRange("dave_drop_rate", 0.3, 0.0, 100);
        PlantConsumeAdditionStep  = BUILDER
                .comment("plant consume addition1 every step")
                .defineInRange("plant_consume_addition_step", 1,0, 100);
        PLANT_ATTRIBUTES_MULTIPLIER_HEALTH = BUILDER
                .comment("Multiplier for plant attributes health.")
                .defineInRange("plant_attributes_multiplier_health", 0.5F, 0.0625f, 10f);
        PLANT_ATTRIBUTES_MULTIPLIER_DAMAGE = BUILDER
                .comment("Multiplier for plant attributes damage.")
                .defineInRange("plant_attributes_multiplier_damage", 0.7F, 0.0625f, 10f);

        SPEC = BUILDER.build();
        return SPEC;
    }
}
