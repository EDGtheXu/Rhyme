//package rhymestudio.rhyme.core.registry;
//
//import net.minecraft.core.component.DataComponentType;
//import net.minecraft.core.registries.Registries;
//import net.neoforged.neoforge.registries.DeferredRegister;
//import rhymestudio.rhyme.core.dataSaver.dataComponent.CardQualityComponentType;
//import rhymestudio.rhyme.core.dataSaver.dataComponent.EntitySaverComponentType;
//import rhymestudio.rhyme.core.dataSaver.dataComponent.ItemDataMapComponent;
//import rhymestudio.rhyme.core.dataSaver.dataComponent.ModRarity;
//
//import java.util.function.Supplier;
//
//import static rhymestudio.rhyme.Rhyme.MODID;
//
//public final class ModDataComponentTypes {
//    public static final DeferredRegister<DataComponentType<?>> TYPES = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, MODID);
//
//    public static final Supplier<DataComponentType<ModRarity>> MOD_RARITY =
//            TYPES.register("mod_rarity_component", () -> DataComponentType.<ModRarity>builder().persistent(ModRarity.CODEC).networkSynchronized(ModRarity.STREAM_CODEC).cacheEncoding().build());
//    public static final Supplier<DataComponentType<CardQualityComponentType>> CARD_QUALITY =
//            TYPES.register("card_quality_component", () -> DataComponentType.<CardQualityComponentType>builder().persistent(CardQualityComponentType.CODEC).networkSynchronized(CardQualityComponentType.STREAM_CODEC).cacheEncoding().build());
//    public static final Supplier<DataComponentType<ItemDataMapComponent>> ITEM_DAT_MAP =
//            TYPES.register("item_data_map_component", () -> DataComponentType.<ItemDataMapComponent>builder().persistent(ItemDataMapComponent.CODEC).networkSynchronized(ItemDataMapComponent.STREAM_CODEC).cacheEncoding().build());
//
//    public static final Supplier<DataComponentType<EntitySaverComponentType>> ITEM_ENTITY_TAG =
//            TYPES.register("item_entity_tag_component", () -> DataComponentType.<EntitySaverComponentType>builder().persistent(EntitySaverComponentType.CODEC).networkSynchronized(EntitySaverComponentType.STREAM_CODEC).cacheEncoding().build());
//
//
//}
