package rhymestudio.rhyme.datagen.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JavaOps;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredItem;
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


    }

    @Override
    protected String pathSuffix() {
        return "_up_level";
    }

    @Override
    public String getName() {
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
        private DeferredItem<Item> template;
        int templateAmount;
        private DeferredItem<Item> base;
        private DeferredItem<Item> addition;
        int additionAmount;

        public Builder(DeferredItem<Item> result, int level) {
            this.result = result;
            this.base = result;
            this.level = level;
        }
        public Builder template(DeferredItem<Item> template,int amount) {
            this.template = template;
            this.templateAmount = amount;
            return this;
        }
        public Builder base(DeferredItem<Item> base) {
            this.base = base;
            return this;
        }
        public Builder addition(DeferredItem<Item> addition,int amount) {
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
