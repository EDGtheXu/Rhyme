package rhymestudio.rhyme.core.dataSaver.dataComponent;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.Nullable;
import rhymestudio.rhyme.core.registry.ModDataComponentTypes;
import rhymestudio.rhyme.core.registry.items.IconItems;

import java.util.ArrayList;
import java.util.List;

/**
 * 物品等级组件，用于渲染背景和属性加成
 * @param level
 * @param source
 */
public record CardQualityComponent(int level,int color, ResourceLocation source) implements DataComponentType<CardQualityComponent> {

    public static List<CardQualityComponent> _levels = new ArrayList<>();
    public static final CardQualityComponent COPPER     = register(0,0xc49a49,IconItems.COPPER);
    public static final CardQualityComponent SILVER     = register(1,0xb6c2c6 ,IconItems.SILVER);
    public static final CardQualityComponent GOLD       = register(2, 0xfce804,IconItems.GOLD);
    public static final CardQualityComponent DIAMOND    = register(3, 0x04a7fc,IconItems.DIAMOND);
    public static final CardQualityComponent EMERALD    = register(4,0x04fc4a ,IconItems.EMERALD);



    public static CardQualityComponent register(int level,int color, DeferredItem<Item> qualityItem) {
        var q = new CardQualityComponent(level, color,qualityItem.getId());
        _levels.add(q);
        return q;
    }

    public CardQualityComponent increaseLevel() {return _levels.get(level < _levels.size() - 1? level + 1 : _levels.size() - 1);}

    public CardQualityComponent decreaseLevel() {return _levels.get(level > 0 ? level - 1 : 0);}

    public static CardQualityComponent of(int level) {return _levels.get(level);}

    public Item getQualityItem() {return BuiltInRegistries.ITEM.get(source);}
    public int getQualityColor() {;return color;}

    public static boolean tryUpLevel(ItemStack stack){
        var data = stack.getComponents().get(ModDataComponentTypes.CARD_QUALITY.get());
        if(data == null) return false;
        if(data.increaseLevel() == data) return false;
        stack.set(ModDataComponentTypes.CARD_QUALITY.get(), data.increaseLevel());
        return true;
    }

    public static boolean tryDownLevel(ItemStack stack){
        var data = stack.getComponents().get(ModDataComponentTypes.CARD_QUALITY.get());
        if(data == null) return false;
        if(data.decreaseLevel() == data) return false;
        stack.set(ModDataComponentTypes.CARD_QUALITY.get(), data.decreaseLevel());
        return true;
    }


    public static final Codec<CardQualityComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("card_level").forGetter(CardQualityComponent::level),
            Codec.INT.fieldOf("card_color").forGetter(CardQualityComponent::color),
            ResourceLocation.CODEC.fieldOf("card_source").forGetter(CardQualityComponent::source)
    ).apply(instance, CardQualityComponent::new));

    public static final StreamCodec<ByteBuf, CardQualityComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, CardQualityComponent::level,
            ByteBufCodecs.INT, CardQualityComponent::color,
            ResourceLocation.STREAM_CODEC, CardQualityComponent::source,
            CardQualityComponent::new
    );

    @Override
    public @Nullable Codec<CardQualityComponent> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<? super RegistryFriendlyByteBuf, CardQualityComponent> streamCodec() {
        return STREAM_CODEC;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof CardQualityComponent component)) return false;
        return component.level == level && component.source.equals(source);
    }

}
