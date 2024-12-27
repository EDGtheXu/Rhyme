package rhymestudio.rhyme.datagen.recipe;


import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import rhymestudio.rhyme.core.registry.ModBlocks;
import rhymestudio.rhyme.core.registry.items.ArmorItems;
import rhymestudio.rhyme.core.registry.items.MaterialItems;
import rhymestudio.rhyme.core.registry.items.PlantItems;

import java.util.concurrent.CompletableFuture;

import static rhymestudio.rhyme.Rhyme.MODID;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {

        //光萃台
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.SUN_CREATOR_BLOCK.get())
                .pattern(" C ")
                .pattern("BAB")
                .pattern("AAA")
                .define('A',Items.IRON_INGOT)
                .define('B',Items.WHEAT_SEEDS)
                .define('C',Items.EMERALD)
                .unlockedBy("has_emerald",has(Items.EMERALD))
                .save(recipeOutput);
        //路障
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ArmorItems.CONE_HELMET.get())
                .pattern(" A ")
                .pattern("AAA")
                .define('A',Items.TERRACOTTA)
                .unlockedBy("has_terracotta",has(Items.TERRACOTTA))
                .save(recipeOutput);

        //僵尸的铁桶
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ArmorItems.IRON_BUCKET_HELMET.get())
                .pattern("AAA")
                .pattern("A A")
                .pattern(" A ")
                .define('A',Items.IRON_INGOT)
                .unlockedBy("has_iron_ingot",has(Items.IRON_INGOT))
                .save(recipeOutput);


        // 万能种子配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MaterialItems.GENERAL_SEED.get())
                .requires(Items.WHEAT_SEEDS,2)
                .unlockedBy("has_wheat_seeds",has(Items.WHEAT_SEEDS))
                .save(recipeOutput);

        // 植物基因配方
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MaterialItems.PLANT_GENE.get())
                .requires(Items.WHEAT_SEEDS,4)
                .unlockedBy("has_wheat_seeds",has(Items.WHEAT_SEEDS))
                .save(recipeOutput);


        SmithingTransformRecipeBuilder.smithing(Ingredient.of(MaterialItems.PLANT_GENE.get()), Ingredient.of(MaterialItems.SOLID_SUN.get()),Ingredient.of( PlantItems.PEA_ITEM.get()), RecipeCategory.MISC, PlantItems.REPEATER_ITEM.get())
                .unlocks("has_plant_gene",has(MaterialItems.PLANT_GENE.get()))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(MODID, "plant_gene_to_repeater"));
//
//        cookRecipes(recipeOutput, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING_RECIPE, CampfireCookingRecipe::new, 100);

        //豌豆基因
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MaterialItems.PEA_GENE)
                .pattern("BBB")
                .pattern("BAB")
                .pattern("BBB")
                .define('A',MaterialItems.PLANT_GENE)
                .define('B',MaterialItems.PEA)
                .unlockedBy("has_pea",has(MaterialItems.PEA))
                .save(recipeOutput);

        //寒冷基因
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MaterialItems.SNOW_GENE)
                .pattern("BBB")
                .pattern("BAB")
                .pattern("BBB")
                .define('A',MaterialItems.PLANT_GENE)
                .define('B',Items.SNOWBALL)
                .unlockedBy("has_snow_ball",has(Items.SNOWBALL))
                .save(recipeOutput);

        //土豆基因
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MaterialItems.POTATO_GENE)
                .pattern("BBB")
                .pattern("BAB")
                .pattern("BBB")
                .define('A',MaterialItems.PLANT_GENE)
                .define('B',Items.POTATO)
                .unlockedBy("has_potato",has(Items.POTATO))
                .save(recipeOutput);

        //蘑菇基因
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MaterialItems.MUSHROOM_GENE)
                .pattern("CBC")
                .pattern("BAB")
                .pattern("CBC")
                .define('A',MaterialItems.PLANT_GENE)
                .define('B',Items.RED_MUSHROOM)
                .define('C',Items.BROWN_MUSHROOM)
                .unlockedBy("has_red_mushroom",has(Items.RED_MUSHROOM))
                .unlockedBy("has_brown_mushroom",has(Items.BROWN_MUSHROOM))
                .save(recipeOutput);

        //坚果基因
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MaterialItems.NUT_GENE)
                .pattern("BBB")
                .pattern("BAB")
                .pattern("BBB")
                .define('A',MaterialItems.PLANT_GENE)
                .define('B',MaterialItems.NUT)
                .unlockedBy("has_nut",has(MaterialItems.NUT))
                .save(recipeOutput);

        //卷心菜基因
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MaterialItems.CABBAGE_GENE)
                .pattern("BBB")
                .pattern("BAB")
                .pattern("BBB")
                .define('A',MaterialItems.PLANT_GENE)
                .define('B',MaterialItems.CABBAGE)
                .unlockedBy("has_cabbage",has(MaterialItems.CABBAGE))
                .save(recipeOutput);

        //易怒基因
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MaterialItems.ANGER_GENE)
                .pattern("BBB")
                .pattern("BAB")
                .pattern("BBB")
                .define('A',MaterialItems.PLANT_GENE)
                .define('B',MaterialItems.CHILI)
                .unlockedBy("has_chili",has(MaterialItems.CHILI))
                .save(recipeOutput);

        //壮力基因
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MaterialItems.STRONG_GENE)
                .pattern("BBB")
                .pattern("BAB")
                .pattern("BBB")
                .define('A',MaterialItems.PLANT_GENE)
                .define('B',MaterialItems.MUSCLE)
                .unlockedBy("has_muscle",has(MaterialItems.MUSCLE))
                .save(recipeOutput);
    }

    protected static <T extends AbstractCookingRecipe> void cookRecipes(
            RecipeOutput recipeOutput, String cookingMethod, RecipeSerializer<T> cookingSerializer, AbstractCookingRecipe.Factory<T> recipeFactory, int cookingTime
    ) {
//        simpleCookingRecipe(recipeOutput, cookingMethod, cookingSerializer, recipeFactory, cookingTime, ModItems.BREAD_SWORD, ModItems.BREAD_SWORD_HOT, 0.35F);
//        simpleCookingRecipe(recipeOutput, cookingMethod, cookingSerializer, recipeFactory, cookingTime, ModItems.BREAD_SWORD_HOT, ModItems.BREAD_SWORD_VERY_HOT, 0.35F);
//

    }

    protected static <T extends AbstractCookingRecipe> void simpleCookingRecipe(
            RecipeOutput recipeOutput,
            String cookingMethod,
            RecipeSerializer<T> cookingSerializer,
            AbstractCookingRecipe.Factory<T> recipeFactory,
            int cookingTime,
            ItemLike material,
            ItemLike result,
            float experience
    ) {
        SimpleCookingRecipeBuilder.generic(Ingredient.of(material), RecipeCategory.FOOD, result, experience, cookingTime, cookingSerializer, recipeFactory)
                .unlockedBy(getHasName(material), has(material))
                .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(MODID,getItemName(result) + "_from_" + cookingMethod));
    }
}
