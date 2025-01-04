package rhymestudio.rhyme.core.recipe;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.neoforged.neoforge.common.crafting.ICustomIngredient;
import net.neoforged.neoforge.common.crafting.IngredientType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rhymestudio.rhyme.core.registry.ModRecipes;

import java.util.Arrays;
import java.util.stream.Stream;

public record AmountIngredient(Ingredient ingredient, int amount) implements ICustomIngredient {
    public static final Ingredient EMPTY = new Ingredient(new AmountIngredient(Ingredient.EMPTY, 0));
    public static final MapCodec<AmountIngredient> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Ingredient.CODEC.fieldOf("ingredient").orElse(Ingredient.EMPTY).forGetter(AmountIngredient::ingredient),
            ExtraCodecs.POSITIVE_INT.fieldOf("count").orElse(0).forGetter(AmountIngredient::amount)
    ).apply(instance, AmountIngredient::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, AmountIngredient> STREAM_CODEC = ByteBufCodecs.fromCodecWithRegistries(CODEC.codec());

    @Override
    public @NotNull Stream<ItemStack> getItems() {
        return Arrays.stream(ingredient.getItems()).peek(itemStack -> itemStack.setCount(amount));
    }

    @Override
    public boolean test(@Nullable ItemStack pStack) {
        if (pStack == null) return false;
        if (pStack.getCount() < amount) {
            return false;
        } else {
            return ingredient.test(pStack);
        }
    }

    @Override
    public boolean isSimple() {
        return false;
    }

    @Override
    public @NotNull IngredientType<AmountIngredient> getType() {
        return ModRecipes.AMOUNT_INGREDIENT_TYPE.get();
    }
}
