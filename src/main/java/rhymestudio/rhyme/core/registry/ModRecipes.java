package rhymestudio.rhyme.core.registry;

import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import rhymestudio.rhyme.core.recipe.CardUpLevelRecipe;
import rhymestudio.rhyme.core.recipe.SunCreatorRecipe;
import rhymestudio.rhyme.core.recipe.SunCreatorSecRecipe;

import java.util.function.Supplier;

import static rhymestudio.rhyme.Rhyme.MODID;

public final class ModRecipes {
    public static final String SUN_CREATOR_ID = "sun_creator";
    public static final String CARD_UP_LEVEL_ID = "card_up_level";

    public static final String SUN_CREATOR_SEC_ID = "sun_creator_sec";

    public static final String AMOUNT_INGREDIENT_ID = "amount_ingredient";

//    public static final DeferredRegister<IngredientType<?>> INGREDIENT_TYPES = DeferredRegister.create(ForgeRegistries.Keys.INGREDIENT_TYPES, MODID);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, MODID);

//    public static final Supplier<IngredientType<AmountIngredient>> AMOUNT_INGREDIENT_TYPE = INGREDIENT_TYPES.register(AMOUNT_INGREDIENT_ID, () -> new IngredientType<>(AmountIngredient.CODEC, AmountIngredient.STREAM_CODEC));


    public static final Supplier<RecipeType<SunCreatorRecipe>> SUN_CREATOR_TYPE = registerType(SUN_CREATOR_ID);
    public static final Supplier<RecipeSerializer<SunCreatorRecipe>> SUN_CREATOR_SERIALIZER = RECIPE_SERIALIZERS.register(SUN_CREATOR_ID, SunCreatorRecipe.Serializer::new);

    public static final Supplier<RecipeType<SunCreatorSecRecipe>> SUN_CREATOR_SEC_TYPE = registerType(SUN_CREATOR_SEC_ID);
    public static final Supplier<RecipeSerializer<SunCreatorSecRecipe>> SUN_CREATOR_SEC_SERIALIZER = RECIPE_SERIALIZERS.register(SUN_CREATOR_SEC_ID, SunCreatorSecRecipe.Serializer::new);


    public static final Supplier<RecipeType<CardUpLevelRecipe>> CARD_UP_LEVEL_TYPE = registerType(CARD_UP_LEVEL_ID);
    public static final Supplier<RecipeSerializer<CardUpLevelRecipe>> CARD_UP_LEVEL_SERIALIZER = RECIPE_SERIALIZERS.register(CARD_UP_LEVEL_ID, CardUpLevelRecipe.Serializer::new);


    private static <R extends Recipe<?>> Supplier<RecipeType<R>> registerType(String id) {
        return RECIPE_TYPES.register(id, () -> new RecipeType<>() {
            @Override
            public String toString() {
                return MODID+":" + id;
            }
        });
    }

    public static void register(IEventBus eventBus) {
//        INGREDIENT_TYPES.register(eventBus);
        RECIPE_TYPES.register(eventBus);
        RECIPE_SERIALIZERS.register(eventBus);
    }
}
