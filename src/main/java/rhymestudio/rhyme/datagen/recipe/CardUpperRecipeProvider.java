package rhymestudio.rhyme.datagen.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JavaOps;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;
import rhymestudio.rhyme.core.dataSaver.dataComponent.CardQualityComponent;
import rhymestudio.rhyme.core.recipe.AmountIngredient;
import rhymestudio.rhyme.core.recipe.CardUpLevelRecipe;
import rhymestudio.rhyme.core.registry.ModDataComponentTypes;
import rhymestudio.rhyme.core.registry.items.MaterialItems;
import rhymestudio.rhyme.core.registry.items.PlantItems;

/**
 * 卡片进阶json生成 level: 0 - 4
 */
public class CardUpperRecipeProvider extends AbstractRecipeProvider {
    public CardUpperRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void run() {

        //向日葵
        gen(PlantItems.SUN_FLOWER, 1)
                .template(MaterialItems.SOLID_SUN, 8)
                .addition(MaterialItems.SOLID_SUN, 8)
                .build();
        gen(PlantItems.SUN_FLOWER, 2)
                .template(MaterialItems.SOLID_SUN, 8)
                .addition(MaterialItems.SOLID_SUN, 8)
                .build();
        gen(PlantItems.SUN_FLOWER, 3)
                .template(MaterialItems.SOLID_SUN, 8)
                .addition(MaterialItems.SOLID_SUN, 8)
                .build();
        gen(PlantItems.SUN_FLOWER, 4)
                .template(MaterialItems.SOLID_SUN, 8)
                .addition(MaterialItems.SOLID_SUN, 8)
                .build();

        //豌豆射手
        gen(PlantItems.PEA_ITEM, 1)
                .template(MaterialItems.PLANT_GENE, 5)
                .addition(MaterialItems.PLANT_GENE, 5)
                .build();
        gen(PlantItems.PEA_ITEM, 2)
                .template(MaterialItems.PLANT_GENE, 5)
                .addition(MaterialItems.PLANT_GENE, 5)
                .build();
        gen(PlantItems.PEA_ITEM, 3)
                .template(MaterialItems.PLANT_GENE, 5)
                .addition(MaterialItems.PLANT_GENE, 5)
                .build();
        gen(PlantItems.PEA_ITEM, 4)
                .template(MaterialItems.PLANT_GENE, 5)
                .addition(MaterialItems.PLANT_GENE, 5)
                .build();

        //寒冰射手
        gen(PlantItems.SNOW_PEA_ITEM, 1)
                .template(MaterialItems.SNOW_GENE, 5)
                .addition(MaterialItems.PEA_GENE, 5)
                .build();
        gen(PlantItems.SNOW_PEA_ITEM, 2)
                .template(MaterialItems.SNOW_GENE, 5)
                .addition(MaterialItems.PEA_GENE, 5)
                .build();
        gen(PlantItems.SNOW_PEA_ITEM, 3)
                .template(MaterialItems.SNOW_GENE, 5)
                .addition(MaterialItems.PEA_GENE, 5)
                .build();
        gen(PlantItems.SNOW_PEA_ITEM, 4)
                .template(MaterialItems.SNOW_GENE, 5)
                .addition(MaterialItems.PEA_GENE, 5)
                .build();

        //双重射手
        gen(PlantItems.REPEATER_ITEM, 1)
                .template(MaterialItems.PEA_GENE, 5)
                .addition(MaterialItems.PEA_GENE, 5)
                .build();
        gen(PlantItems.REPEATER_ITEM, 2)
                .template(MaterialItems.PEA_GENE, 5)
                .addition(MaterialItems.PEA_GENE, 5)
                .build();
        gen(PlantItems.REPEATER_ITEM, 3)
                .template(MaterialItems.PEA_GENE, 5)
                .addition(MaterialItems.PEA_GENE, 5)
                .build();
        gen(PlantItems.REPEATER_ITEM, 4)
                .template(MaterialItems.PEA_GENE, 5)
                .addition(MaterialItems.PEA_GENE, 5)
                .build();

        //小喷菇
        gen(PlantItems.PUFF_SHROOM_ITEM, 1)
                .template(MaterialItems.MUSHROOM_GENE, 5)
                .addition(MaterialItems.MUSHROOM_GENE, 5)
                .build();
        gen(PlantItems.PUFF_SHROOM_ITEM, 2)
                .template(MaterialItems.MUSHROOM_GENE, 5)
                .addition(MaterialItems.MUSHROOM_GENE, 5)
                .build();
        gen(PlantItems.PUFF_SHROOM_ITEM, 3)
                .template(MaterialItems.MUSHROOM_GENE, 5)
                .addition(MaterialItems.MUSHROOM_GENE, 5)
                .build();
        gen(PlantItems.PUFF_SHROOM_ITEM, 4)
                .template(MaterialItems.MUSHROOM_GENE, 5)
                .addition(MaterialItems.MUSHROOM_GENE, 5)
                .build();

        //土豆雷
        gen(PlantItems.POTATO_MINE_ITEM, 1)
                .template(MaterialItems.HIDDEN_GENE, 5)
                .addition(MaterialItems.HIDDEN_GENE, 5)
                .build();
        gen(PlantItems.POTATO_MINE_ITEM, 2)
                .template(MaterialItems.HIDDEN_GENE, 5)
                .addition(MaterialItems.HIDDEN_GENE, 5)
                .build();
        gen(PlantItems.POTATO_MINE_ITEM, 3)
                .template(MaterialItems.HIDDEN_GENE, 5)
                .addition(MaterialItems.HIDDEN_GENE, 5)
                .build();
        gen(PlantItems.POTATO_MINE_ITEM, 4)
                .template(MaterialItems.HIDDEN_GENE, 5)
                .addition(MaterialItems.HIDDEN_GENE, 5)
                .build();

        //坚果墙，我硬死你
        gen(PlantItems.NUT_WALL_ITEM, 1)
                .template(Items.IRON_INGOT, 8)
                .addition(Items.COPPER_INGOT, 8)
                .build();
        gen(PlantItems.NUT_WALL_ITEM, 2)
                .template(Items.GOLD_INGOT, 4)
                .addition(Items.LAPIS_LAZULI, 6)
                .build();
        gen(PlantItems.NUT_WALL_ITEM, 3)
                .template(Items.REDSTONE, 8)
                .addition(Items.EMERALD, 8)
                .build();
        gen(PlantItems.NUT_WALL_ITEM, 4)
                .template(Items.DIAMOND, 4)
                .addition(Items.NETHERITE_INGOT, 1)
                .build();

        //卷心菜投手
        gen(PlantItems.CABBAGE_PULT_ITEM, 1)
                .template(MaterialItems.THROWABLE_GENE, 5)
                .addition(MaterialItems.THROWABLE_GENE, 5)
                .build();
        gen(PlantItems.CABBAGE_PULT_ITEM, 2)
                .template(MaterialItems.THROWABLE_GENE, 5)
                .addition(MaterialItems.THROWABLE_GENE, 5)
                .build();
        gen(PlantItems.CABBAGE_PULT_ITEM, 3)
                .template(MaterialItems.THROWABLE_GENE, 5)
                .addition(MaterialItems.THROWABLE_GENE, 5)
                .build();
        gen(PlantItems.CABBAGE_PULT_ITEM, 4)
                .template(MaterialItems.THROWABLE_GENE, 5)
                .addition(MaterialItems.THROWABLE_GENE, 5)
                .build();

        //大嘴花
        gen(PlantItems.CHOMPER_ITEM, 1)
                .template(MaterialItems.STRONG_GENE, 5)
                .addition(MaterialItems.STRONG_GENE, 5)
                .build();
        gen(PlantItems.CHOMPER_ITEM, 2)
                .template(MaterialItems.STRONG_GENE, 5)
                .addition(MaterialItems.STRONG_GENE, 5)
                .build();
        gen(PlantItems.CHOMPER_ITEM, 3)
                .template(MaterialItems.STRONG_GENE, 5)
                .addition(MaterialItems.STRONG_GENE, 5)
                .build();
        gen(PlantItems.CHOMPER_ITEM, 4)
                .template(MaterialItems.STRONG_GENE, 5)
                .addition(MaterialItems.STRONG_GENE, 5)
                .build();
    }

    @Override
    protected String pathSuffix() {
        return "_up_level";
    }

    @Override
    public @NotNull String getName() {
        return "Card Upper Recipe Provider";
    }


    public void genRecipe(ItemStack result, int level, AmountIngredient template, Ingredient base, AmountIngredient addition, String suffix){
        result.set(ModDataComponentTypes.CARD_QUALITY.get(), CardQualityComponent.of(level));
        JsonElement je = parseCodec(CardUpLevelRecipe.CODEC.encodeStart(JavaOps.INSTANCE, new CardUpLevelRecipe(template, base, addition,result)));
        JsonObject jo = je.getAsJsonObject();
        jo.get("template").getAsJsonObject().addProperty("type",amountIngredientType());
        jo.get("addition").getAsJsonObject().addProperty("type",amountIngredientType());
        addJson(jo,result,suffix);
    }

    protected Builder gen(DeferredItem<Item> result, int level) {
        return new Builder(result, level);
    }

    protected class Builder {
        private final DeferredItem<Item> result;
        private final int level;
        private Item template;
        int templateAmount;
        private DeferredItem<Item> base;
        private Item addition;
        int additionAmount;

        public Builder(DeferredItem<Item> result, int level) {
            this.result = result;
            this.base = result;
            this.level = level;
        }
        public Builder template(DeferredItem<Item> template,int amount) {
            this.template = template.get();
            this.templateAmount = amount;
            return this;
        }
        public Builder template(Item template, int amount) {
            this.template = template;
            this.templateAmount = amount;
            return this;
        }
        public Builder base(DeferredItem<Item> base) {
            this.base = base;
            return this;
        }
        public Builder addition(DeferredItem<Item> addition,int amount) {
            this.addition = addition.get();
            this.additionAmount = amount;
            return this;
        }
        public Builder addition(Item addition,int amount) {
            this.addition = addition;
            this.additionAmount = amount;
            return this;
        }

        public void build(String suffix) {
            genRecipe(new ItemStack(result.get()), level,
                    new AmountIngredient(Ingredient.of(template), templateAmount),
                    Ingredient.of(base),
                    new AmountIngredient(Ingredient.of(addition), additionAmount),
                    suffix);
            }

        public void build() {
            build("");
        }
    }

}
