package rhymestudio.rhyme.datagen.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JavaOps;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.Unbreakable;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;
import rhymestudio.rhyme.core.dataSaver.dataComponent.CardQualityComponent;
import rhymestudio.rhyme.core.recipe.AmountIngredient;
import rhymestudio.rhyme.core.recipe.CardUpLevelRecipe;
import rhymestudio.rhyme.core.registry.ModDataComponentTypes;
import rhymestudio.rhyme.core.registry.items.MaterialItems;
import rhymestudio.rhyme.core.registry.items.PlantItems;
import rhymestudio.rhyme.datagen.tag.ModTags;

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
                .addition(MaterialItems.SOLID_SUN, 8)
                .addition1(MaterialItems.SOLID_SUN, 8)
                .build();
        gen(PlantItems.SUN_FLOWER, 2)
                .addition(MaterialItems.SOLID_SUN, 8)
                .addition1(MaterialItems.SOLID_SUN, 8)
                .build();
        gen(PlantItems.SUN_FLOWER, 3)
                .addition(MaterialItems.SOLID_SUN, 8)
                .addition1(MaterialItems.SOLID_SUN, 8)
                .build();
        gen(PlantItems.SUN_FLOWER, 4)
                .addition(MaterialItems.SOLID_SUN, 8)
                .addition1(MaterialItems.SOLID_SUN, 8)
                .build();

        //豌豆射手
        gen(PlantItems.PEA_ITEM, 1)
                .addition(MaterialItems.PLANT_GENE, 5)
                .addition1(MaterialItems.PLANT_GENE, 5)
                .build();
        gen(PlantItems.PEA_ITEM, 2)
                .addition(MaterialItems.PLANT_GENE, 5)
                .addition1(MaterialItems.PLANT_GENE, 5)
                .build();
        gen(PlantItems.PEA_ITEM, 3)
                .addition(MaterialItems.PLANT_GENE, 5)
                .addition1(MaterialItems.PLANT_GENE, 5)
                .build();
        gen(PlantItems.PEA_ITEM, 4)
                .addition(MaterialItems.PLANT_GENE, 5)
                .addition1(MaterialItems.PLANT_GENE, 5)
                .build();

        //寒冰射手
        gen(PlantItems.SNOW_PEA_ITEM, 1)
                .addition(MaterialItems.SNOW_GENE, 5)
                .addition1(MaterialItems.PEA_GENE, 5)
                .build();
        gen(PlantItems.SNOW_PEA_ITEM, 2)
                .addition(MaterialItems.SNOW_GENE, 5)
                .addition1(MaterialItems.PEA_GENE, 5)
                .build();
        gen(PlantItems.SNOW_PEA_ITEM, 3)
                .addition(MaterialItems.SNOW_GENE, 5)
                .addition1(MaterialItems.PEA_GENE, 5)
                .build();
        gen(PlantItems.SNOW_PEA_ITEM, 4)
                .addition(MaterialItems.SNOW_GENE, 5)
                .addition1(MaterialItems.PEA_GENE, 5)
                .build();

        //双重射手
        gen(PlantItems.REPEATER_ITEM, 1)
                .addition(MaterialItems.PEA_GENE, 5)
                .addition1(MaterialItems.PEA_GENE, 5)
                .build();
        gen(PlantItems.REPEATER_ITEM, 2)
                .addition(MaterialItems.PEA_GENE, 5)
                .addition1(MaterialItems.PEA_GENE, 5)
                .build();
        gen(PlantItems.REPEATER_ITEM, 3)
                .addition(MaterialItems.PEA_GENE, 5)
                .addition1(MaterialItems.PEA_GENE, 5)
                .build();
        gen(PlantItems.REPEATER_ITEM, 4)
                .addition(MaterialItems.PEA_GENE, 5)
                .addition1(MaterialItems.PEA_GENE, 5)
                .build();

        //小喷菇
        gen(PlantItems.PUFF_SHROOM_ITEM, 1)
                .addition(MaterialItems.MUSHROOM_GENE, 5)
                .addition1(MaterialItems.MUSHROOM_GENE, 5)
                .build();
        gen(PlantItems.PUFF_SHROOM_ITEM, 2)
                .addition(MaterialItems.MUSHROOM_GENE, 5)
                .addition1(MaterialItems.MUSHROOM_GENE, 5)
                .build();
        gen(PlantItems.PUFF_SHROOM_ITEM, 3)
                .addition(MaterialItems.MUSHROOM_GENE, 5)
                .addition1(MaterialItems.MUSHROOM_GENE, 5)
                .build();
        gen(PlantItems.PUFF_SHROOM_ITEM, 4)
                .addition(MaterialItems.MUSHROOM_GENE, 5)
                .addition1(MaterialItems.MUSHROOM_GENE, 5)
                .build();

        //土豆雷
        gen(PlantItems.POTATO_MINE_ITEM, 1)
                .addition(MaterialItems.HIDDEN_GENE, 5)
                .addition1(MaterialItems.HIDDEN_GENE, 5)
                .build();
        gen(PlantItems.POTATO_MINE_ITEM, 2)
                .addition(MaterialItems.HIDDEN_GENE, 5)
                .addition1(MaterialItems.HIDDEN_GENE, 5)
                .build();
        gen(PlantItems.POTATO_MINE_ITEM, 3)
                .addition(MaterialItems.HIDDEN_GENE, 5)
                .addition1(MaterialItems.HIDDEN_GENE, 5)
                .build();
        gen(PlantItems.POTATO_MINE_ITEM, 4)
                .addition(MaterialItems.HIDDEN_GENE, 5)
                .addition1(MaterialItems.HIDDEN_GENE, 5)
                .build();

        //坚果墙，我硬死你
        gen(PlantItems.NUT_WALL_ITEM, 1)
                .addition(Items.IRON_INGOT, 8)
                .addition1(Items.COPPER_INGOT, 8)
                .build();
        gen(PlantItems.NUT_WALL_ITEM, 2)
                .addition(Items.GOLD_INGOT, 4)
                .addition1(Items.LAPIS_LAZULI, 6)
                .build();
        gen(PlantItems.NUT_WALL_ITEM, 3)
                .addition(Items.REDSTONE, 8)
                .addition1(Items.EMERALD, 8)
                .build();
        gen(PlantItems.NUT_WALL_ITEM, 4)
                .addition(Items.DIAMOND, 4)
                .addition1(Items.NETHERITE_INGOT, 1)
                .build();

        //卷心菜投手
        gen(PlantItems.CABBAGE_PULT_ITEM, 1)
                .addition(MaterialItems.THROWABLE_GENE, 5)
                .addition1(MaterialItems.THROWABLE_GENE, 5)
                .build();
        gen(PlantItems.CABBAGE_PULT_ITEM, 2)
                .addition(MaterialItems.THROWABLE_GENE, 5)
                .addition1(MaterialItems.THROWABLE_GENE, 5)
                .build();
        gen(PlantItems.CABBAGE_PULT_ITEM, 3)
                .addition(MaterialItems.THROWABLE_GENE, 5)
                .addition1(MaterialItems.THROWABLE_GENE, 5)
                .build();
        gen(PlantItems.CABBAGE_PULT_ITEM, 4)
                .addition(MaterialItems.THROWABLE_GENE, 5)
                .addition1(MaterialItems.THROWABLE_GENE, 5)
                .build();

        //大嘴花
        gen(PlantItems.CHOMPER_ITEM, 1)
                .addition(MaterialItems.STRONG_GENE, 5)
                .addition1(MaterialItems.STRONG_GENE, 5)
                .build();
        gen(PlantItems.CHOMPER_ITEM, 2)
                .addition(MaterialItems.STRONG_GENE, 5)
                .addition1(MaterialItems.STRONG_GENE, 5)
                .build();
        gen(PlantItems.CHOMPER_ITEM, 3)
                .addition(MaterialItems.STRONG_GENE, 5)
                .addition1(MaterialItems.STRONG_GENE, 5)
                .build();
        gen(PlantItems.CHOMPER_ITEM, 4)
                .addition(MaterialItems.STRONG_GENE, 5)
                .addition1(MaterialItems.STRONG_GENE, 5)
                .build();

        // level == 0 为永久卡片
        gen(Ingredient.of(ModTags.Items.CARD),0)
                .addition(MaterialItems.TACO, 32)
                .addition1(MaterialItems.PEA_GENE, 32)
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


    public void genRecipe(ItemStack result, int level, AmountIngredient addition,  Ingredient base, AmountIngredient addition2,AmountIngredient template,String suffix){
        result.set(ModDataComponentTypes.CARD_QUALITY.get(), CardQualityComponent.of(level));
        JsonElement je = parseCodec(CardUpLevelRecipe.CODEC.encodeStart(JavaOps.INSTANCE, new CardUpLevelRecipe( addition,base, addition2,template,result)));
        JsonObject jo = je.getAsJsonObject();
        jo.get("addition").getAsJsonObject().addProperty("type",amountIngredientType());
        jo.get("addition1").getAsJsonObject().addProperty("type",amountIngredientType());
        jo.get("template").getAsJsonObject().addProperty("type",amountIngredientType());
        addJson(jo,result,suffix);
    }

    protected Builder gen(DeferredItem<Item> result, int level) {
        return new Builder(result, level);
    }
    protected Builder gen(Ingredient result, int level){return new Builder(result, level);}

    protected class Builder {
        private final Ingredient result;
        private final int level;

        private Item addition;
        int additionAmount;

        private Ingredient base;

        private Item addition1;
        int addition1Amount;

        private Item template;
        int templateAmount;

        public Builder(DeferredItem<Item> result, int level) {
            this.result = Ingredient.of(result.get());
            this.base = Ingredient.of(result);
            this.level = level;
        }
        public Builder(Ingredient result, int level) {
            this.result = Ingredient.of(PlantItems.SUN_FLOWER);
            this.base = result;
            this.level = level;
        }

        public Builder addition(DeferredItem<Item> template, int amount) {
            this.addition = template.get();
            this.additionAmount = amount;
            return this;
        }
        public Builder addition(Item template, int amount) {
            this.addition = template;
            this.additionAmount = amount;
            return this;
        }

        public Builder addition1(DeferredItem<Item> addition, int amount) {
            this.addition1 = addition.get();
            this.addition1Amount = amount;
            return this;
        }
        public Builder addition1(Item addition, int amount) {
            this.addition1 = addition;
            this.addition1Amount = amount;
            return this;
        }

        public Builder template(DeferredItem<Item> addition, int amount) {
            this.template = addition.get();
            this.templateAmount = amount;
            return this;
        }
        public Builder template(Item addition, int amount) {
            this.template = addition;
            this.templateAmount = amount;
            return this;
        }

        public void build(String suffix) {
            ItemStack res = result.getItems()[0];
            if(level == 0)
                res.set((DataComponents.UNBREAKABLE),new Unbreakable(true));
            if(template == null){
                template = switch (level){
                    case 0 -> MaterialItems.TACO.asItem();
                    case 1 -> Items.IRON_INGOT;
                    case 2 -> Items.GOLD_INGOT;
                    case 3 -> Items.DIAMOND;
                    case 4 -> Items.EMERALD;
                    default -> throw new IllegalStateException("Unexpected value: " + level);
                };
                templateAmount = level * 5;
                if(level == 0) templateAmount = 32;
            }
            genRecipe(res, level,
                    new AmountIngredient(Ingredient.of(addition), additionAmount),
                    base,
                    new AmountIngredient(Ingredient.of(addition1), addition1Amount),
                    new AmountIngredient(Ingredient.of(template), templateAmount),
                    suffix);
            }

        public void build() {
            build("");
        }
    }

}
