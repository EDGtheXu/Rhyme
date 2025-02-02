package rhymestudio.rhyme.datagen.recipe;


import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import rhymestudio.rhyme.core.registry.ModBlocks;
import rhymestudio.rhyme.core.registry.items.ArmorItems;
import rhymestudio.rhyme.core.registry.items.MaterialItems;
import rhymestudio.rhyme.core.registry.items.ToolItems;

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

        //植物铲子
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ToolItems.PLANT_SHOVEL.get())
                .pattern(" A ")
                .pattern("BCB")
                .pattern(" C ")
                .define('A',Items.IRON_INGOT)
                .define('B',MaterialItems.GENERAL_SEED)
                .define('C',Items.STICK)
                .unlockedBy("has_general_seed",has(MaterialItems.GENERAL_SEED))
                .save(recipeOutput);

        //手推车
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ToolItems.PLANT_PUTTER.get())
                .pattern("B  ")
                .pattern("ACA")
                .pattern(" A ")
                .define('A', Items.IRON_INGOT)
                .define('B', ItemTags.PLANKS)
                .define('C', ItemTags.DIRT)
                .unlockedBy("has_iron_ingot_for_putter",has(Items.IRON_INGOT))
                .save(recipeOutput);

        //玉米卷
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, MaterialItems.TACO.get())
                .pattern("ABA")
                .define('A', Items.BREAD)
                .define('B', MaterialItems.PLANT_GENE)
                .unlockedBy("has_plant_gene_for_taco",has(MaterialItems.PLANT_GENE))
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


//        cookRecipes(recipeOutput, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING_RECIPE, CampfireCookingRecipe::new, 100);

        //豌豆基因
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MaterialItems.PEA_GENE)
                .pattern("BBB")
                .pattern("BAB")
                .pattern("BBB")
                .define('A',MaterialItems.PLANT_GENE)
                .define('B',Items.WHEAT_SEEDS)
                .unlockedBy("has_wheat_seeds",has(Items.WHEAT_SEEDS))
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
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MaterialItems.HIDDEN_GENE)
                .pattern("BBB")
                .pattern("BAB")
                .pattern("BBB")
                .define('A',MaterialItems.PLANT_GENE)
                .define('B', Items.INK_SAC)
                .unlockedBy("has_ink_sac",has(Items.INK_SAC))
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
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, MaterialItems.NUT_GENE)
                .requires(MaterialItems.PLANT_GENE)
                .requires(Items.NAUTILUS_SHELL)
                .unlockedBy("has_nautilus_shell",has(Items.NAUTILUS_SHELL))
                .save(recipeOutput);

        //投手基因
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MaterialItems.THROWABLE_GENE)
                .pattern("BBB")
                .pattern("BAB")
                .pattern("BBB")
                .define('A',MaterialItems.PLANT_GENE)
                .define('B',Items.WHEAT)
                .unlockedBy("has_wheat",has(Items.WHEAT))
                .save(recipeOutput);

        //易怒基因
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MaterialItems.ANGER_GENE.get())
                .pattern("BBB")
                .pattern("BAB")
                .pattern("BBB")
                .define('A',MaterialItems.PLANT_GENE)
                .define('B',Items.BLAZE_POWDER)
                .unlockedBy("has_blaze_powder",has(Items.BLAZE_POWDER))
                .save(recipeOutput);

        //壮力基因
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, MaterialItems.STRONG_GENE)
                .pattern("BBB")
                .pattern("BAB")
                .pattern("BBB")
                .define('A',MaterialItems.PLANT_GENE)
                .define('B',Items.BEEF)
                .unlockedBy("has_beef",has(Items.BEEF))
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
