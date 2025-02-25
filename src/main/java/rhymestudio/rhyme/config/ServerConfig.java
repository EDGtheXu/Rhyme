package rhymestudio.rhyme.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ServerConfig {
    public static ModConfigSpec.ConfigValue<Double> DaveDropRate;
    public static ModConfigSpec.ConfigValue<Integer> PlantConsumeAdditionStep;
    public static ModConfigSpec.ConfigValue<Double> PLANT_ATTRIBUTES_MULTIPLIER_HEALTH;
    public static ModConfigSpec.ConfigValue<Double> PLANT_ATTRIBUTES_MULTIPLIER_DAMAGE ;

    public static ModConfigSpec.ConfigValue<Double> PLANT_ATTRIBUTES_MULTIPLIER_HEALTH_PER_LEVEL;
    public static ModConfigSpec.ConfigValue<Double> PLANT_ATTRIBUTES_MULTIPLIER_DAMAGE_PER_LEVEL;

    public static ModConfigSpec.ConfigValue<Double> CHANCE_TO_DROP_MONEY;
    public static ModConfigSpec.ConfigValue<Double> CHANCE_TO_DROP_KEY;

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

        PLANT_ATTRIBUTES_MULTIPLIER_HEALTH_PER_LEVEL = BUILDER
                .comment("Multiplier for plant attributes health every lvl.")
                .defineInRange("plant_attributes_multiplier_health_per_level", 0.2F, 0.0625f, 10f);
        PLANT_ATTRIBUTES_MULTIPLIER_DAMAGE_PER_LEVEL = BUILDER
                .comment("Multiplier for plant attributes damage every lvl.")
                .defineInRange("plant_attributes_multiplier_damage_per_level", 0.2F, 0.0625f, 10f);

        CHANCE_TO_DROP_MONEY = BUILDER
                .comment("Chance to drop money when monster is killed.")
                .defineInRange("chance_to_drop_money", 0.2F, 0f, 1f);
        CHANCE_TO_DROP_KEY = BUILDER
                .comment("Chance to drop key when monster is killed.")
                .defineInRange("chance_to_drop_key", 0.05F, 0f, 1f);


        SPEC = BUILDER.build();
        return SPEC;
    }
}
