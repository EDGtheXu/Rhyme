package rhymestudio.rhyme.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.event.config.ModConfigEvent;

public class ServerConfig {
    public static ForgeConfigSpec.ConfigValue<Double> DaveDropRate;
    public static ForgeConfigSpec.ConfigValue<Integer> PlantConsumeAdditionStep;
    public static ForgeConfigSpec.ConfigValue<Double> PLANT_ATTRIBUTES_MULTIPLIER_HEALTH;
    public static ForgeConfigSpec.ConfigValue<Double> PLANT_ATTRIBUTES_MULTIPLIER_DAMAGE ;

    public static ForgeConfigSpec.ConfigValue<Double> PLANT_ATTRIBUTES_MULTIPLIER_HEALTH_PER_LEVEL;
    public static ForgeConfigSpec.ConfigValue<Double> PLANT_ATTRIBUTES_MULTIPLIER_DAMAGE_PER_LEVEL;

    public static ForgeConfigSpec SPEC;

    public static ForgeConfigSpec init() {
        ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

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

        PLANT_ATTRIBUTES_MULTIPLIER_HEALTH_PER_LEVEL = BUILDER
                .comment("Multiplier for plant attributes health every lvl.")
                .defineInRange("plant_attributes_multiplier_health_per_level", 0.2F, 0.0625f, 10f);
        PLANT_ATTRIBUTES_MULTIPLIER_DAMAGE_PER_LEVEL = BUILDER
                .comment("Multiplier for plant attributes damage every lvl.")
                .defineInRange("plant_attributes_multiplier_damage_per_level", 0.2F, 0.0625f, 10f);

        SPEC = BUILDER.build();
        return SPEC;
    }
}
