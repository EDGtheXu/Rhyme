package rhymestudio.rhyme.core.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.checkpoint.checkpoint.CheckPointProvider;
import rhymestudio.rhyme.core.checkpoint.entitygroup.EntityTypeGroupProvider;
import rhymestudio.rhyme.core.checkpoint.checkpoint.ICheckPointType;

import static net.minecraft.resources.ResourceKey.createRegistryKey;

public class ModRegistry {

    // Key
//    public static final ResourceKey<Registry<DaveTrades>> DAVE_SHOP = Rhyme.createResourceKey("dave_shop");
    public static final ResourceKey<Registry<ICheckPointType<?>>> CHECK_POINT_KEY = createRegistryKey(Rhyme.space("check_point"));
    public static final ResourceKey<Registry<EntityTypeGroupProvider>> ENTITY_TYPE_GROUP_PROVIDER_KEY = createRegistryKey(Rhyme.space("entity_type_group_provider"));
    public static final ResourceKey<Registry<CheckPointProvider>> CHECK_POINT_PROVIDER_KEY = createRegistryKey(Rhyme.space("check_point_provider"));


    // Registries
    public static final Registry<ICheckPointType<?>> CHECK_POINT_REGISTRY = new RegistryBuilder<>(CHECK_POINT_KEY).create();
    public static final Registry<EntityTypeGroupProvider> ENTITY_TYPE_GROUP_PROVIDER_REGISTRY = new RegistryBuilder<>(ENTITY_TYPE_GROUP_PROVIDER_KEY).create();
    public static final Registry<CheckPointProvider> CHECK_POINT_PROVIDER_REGISTRY = new RegistryBuilder<>(CHECK_POINT_PROVIDER_KEY).create();

    // Deferred Registers
    public static class CheckPointTypes extends DeferredRegister<ICheckPointType<?>> {
        protected CheckPointTypes(String namespace) {
            super(CHECK_POINT_KEY, namespace);
        }

        public static CheckPointTypes create(String mod_id) {
            return new CheckPointTypes(mod_id);
        }
    }

    public static class EntityTypeGroupProviders extends DeferredRegister<EntityTypeGroupProvider> {
        protected EntityTypeGroupProviders(String namespace) {
            super(ENTITY_TYPE_GROUP_PROVIDER_KEY, namespace);
        }

        public static EntityTypeGroupProviders create(String mod_id) {
            return new EntityTypeGroupProviders(mod_id);
        }
    }

    public static class CheckPointProviders extends DeferredRegister<CheckPointProvider> {
        protected CheckPointProviders(String namespace) {
            super(CHECK_POINT_PROVIDER_KEY, namespace);
        }

        public static CheckPointProviders create(String mod_id) {
            return new CheckPointProviders(mod_id);
        }
    }

    // Event Handler
    public static void newRegistry(NewRegistryEvent event) {
        event.register(ModRegistry.CHECK_POINT_REGISTRY);
        event.register(ModRegistry.ENTITY_TYPE_GROUP_PROVIDER_REGISTRY);
        event.register(ModRegistry.CHECK_POINT_PROVIDER_REGISTRY);
    }

}
