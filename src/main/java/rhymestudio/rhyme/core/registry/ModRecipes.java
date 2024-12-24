package rhymestudio.rhyme.core.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.crafting.IngredientType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.recipe.AmountIngredient;
import rhymestudio.rhyme.core.recipe.CardUpLevelRecipe;
import rhymestudio.rhyme.core.recipe.SunCreatorRecipe;

import java.util.function.Supplier;

import static rhymestudio.rhyme.Rhyme.MODID;

public final class ModRecipes {
    public static final String SUN_CREATOR_ID = "sun_creator";
    public static final String CARD_UP_LEVEL_ID = "card_up_level";

    public static final String AMOUNT_INGREDIENT_ID = "amount_ingredient";

    public static final DeferredRegister<IngredientType<?>> INGREDIENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.INGREDIENT_TYPES, MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, MODID);

    public static final Supplier<IngredientType<AmountIngredient>> AMOUNT_INGREDIENT_TYPE = INGREDIENT_TYPES.register(AMOUNT_INGREDIENT_ID, () -> new IngredientType<>(AmountIngredient.CODEC, AmountIngredient.STREAM_CODEC));


    public static final Supplier<RecipeType<SunCreatorRecipe>> SUN_CREATOR_TYPE = RECIPE_TYPES.register(SUN_CREATOR_ID, () -> RecipeType.simple(Rhyme.space(SUN_CREATOR_ID)));
    public static final Supplier<RecipeSerializer<SunCreatorRecipe>> SUN_CREATOR_SERIALIZER = RECIPE_SERIALIZERS.register(SUN_CREATOR_ID, SunCreatorRecipe.Serializer::new);


    public static final Supplier<RecipeType<CardUpLevelRecipe>> CARD_UP_LEVEL_TYPE = RECIPE_TYPES.register(CARD_UP_LEVEL_ID, () -> RecipeType.simple(Rhyme.space(CARD_UP_LEVEL_ID)));
    public static final Supplier<RecipeSerializer<CardUpLevelRecipe>> CARD_UP_LEVEL_SERIALIZER = RECIPE_SERIALIZERS.register(CARD_UP_LEVEL_ID, CardUpLevelRecipe.Serializer::new);



    public static void register(IEventBus eventBus) {
        INGREDIENT_TYPES.register(eventBus);
        RECIPE_TYPES.register(eventBus);
        RECIPE_SERIALIZERS.register(eventBus);
    }
}
