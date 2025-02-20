package rhymestudio.rhyme.core.checkpoint.entitygroup;

import com.mojang.serialization.MapCodec;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.registry.ModRegistry;

import java.util.function.Supplier;

/**
 * 注册僵尸组选择器的类型
 */
public class EntityTypeGroupProviderTypes {
    public static final ModRegistry.EntityTypeGroupProviders ENTITY_TYPE_GROUP_PROVIDERS = ModRegistry.EntityTypeGroupProviders.create(Rhyme.MODID);

    public static final Supplier<EntityTypeGroupProvider> SINGLE_ENTITY_TYPE = register("single_entity_type", SingleZombie.CODEC);
    public static final Supplier<EntityTypeGroupProvider> WEIGHTED_ENTITY_TYPE = register("weighted_entity_type", WeightSelectedZombie.CODEC);



    private static Supplier<EntityTypeGroupProvider> register(String name, MapCodec<? extends IEntityTypeGroup> codec) {
        var type = ENTITY_TYPE_GROUP_PROVIDERS.register(name, ()->new EntityTypeGroupProvider(codec));
        return type;
    }
}
