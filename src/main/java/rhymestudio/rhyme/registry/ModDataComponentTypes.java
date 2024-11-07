package rhymestudio.rhyme.registry;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;
import rhymestudio.rhyme.dataComponent.CardQualityComponent;
import rhymestudio.rhyme.dataComponent.ModRarity;

import java.util.function.Supplier;

import static rhymestudio.rhyme.Rhyme.MODID;

public final class ModDataComponentTypes {
    public static final DeferredRegister<DataComponentType<?>> TYPES = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, MODID);

    public static final Supplier<DataComponentType<ModRarity>> MOD_RARITY =
            TYPES.register("mod_rarity_component", () -> DataComponentType.<ModRarity>builder().persistent(ModRarity.CODEC).networkSynchronized(ModRarity.STREAM_CODEC).cacheEncoding().build());
    public static final Supplier<DataComponentType<CardQualityComponent>> CARD_QUALITY =
            TYPES.register("card_quality_component", () -> DataComponentType.<CardQualityComponent>builder().persistent(CardQualityComponent.CODEC).networkSynchronized(CardQualityComponent.STREAM_CODEC).cacheEncoding().build());

}
