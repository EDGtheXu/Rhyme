package rhymestudio.rhyme.core.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.AbstractIngredient;
import net.minecraftforge.common.crafting.IIngredientSerializer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rhymestudio.rhyme.Rhyme;

import java.util.Arrays;
import java.util.stream.Stream;

public class AmountIngredient extends AbstractIngredient{
    public static final AmountIngredient EMPTY = new AmountIngredient();
    private ItemStack itemStack;
    private final Ingredient ingredient;
    private final int amount;

    public AmountIngredient(ItemStack itemStack) {
        super(Stream.of(new Ingredient.ItemValue(itemStack)));
        ingredient = Ingredient.of(itemStack);
//        this.itemStack = itemStack;
        this.amount = itemStack.getCount();
    }

    public AmountIngredient(Ingredient ingredient, int amount) {
        super(Arrays.stream(ingredient.getItems()).map(Ingredient.ItemValue::new));
        this.ingredient = ingredient;
        this.amount = amount;
    }

    protected AmountIngredient(Stream<? extends Value> pValues ,int amount) {
        super(pValues);
        ingredient = this;
        this.amount = amount;
    }

    AmountIngredient() {
        super(Stream.empty());
        ingredient = Ingredient.EMPTY;
        this.amount = 0;
    }

    public static AmountIngredient of(ItemStack itemStack) {
        return new AmountIngredient(itemStack);
    }

    public static AmountIngredient of(TagKey<Item> itemStack, int amount) {
        return fromValues(Stream.of(new TagValue(itemStack)), amount);
    }

    public static AmountIngredient fromValues(Stream<? extends Value> pStream, int amount) {
        AmountIngredient ingredient = new AmountIngredient(pStream,amount);
        return ingredient.isEmpty() ? EMPTY : ingredient;
    }

//    public Item getItem() {
//        return itemStack.getItem();
//    }

    public int getCount() {
        return amount;
    }

//    public ItemStack getItemStack() {
//        return itemStack;
//    }

//    @Override
//    public ItemStack @NotNull [] getItems() {
//        return new ItemStack[]{itemStack};
//    }

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
    public @NotNull IIngredientSerializer<AmountIngredient> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public @NotNull JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", TYPE().toString());
        serialize(jsonObject, ingredient, amount);
        return jsonObject;
    }

    public JsonElement toJsonIngredient() {
        if (this.values.length == 1) {
            return this.values[0].serialize();
        } else {
            JsonArray jsonarray = new JsonArray();
            Value[] var2 = this.values;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                Value ingredient$value = var2[var4];
                jsonarray.add(ingredient$value.serialize());
            }

            return jsonarray;
        }
    }

    public static AmountIngredient fromJson(JsonObject json, String name) {
        return AmountIngredient.Serializer.INSTANCE.parse(GsonHelper.convertToJsonObject(GsonHelper.getNonNull(json,
                name).getAsJsonObject(), TYPE().toString()));
    }

    public static AmountIngredient fromJson(JsonObject json) {
        return AmountIngredient.Serializer.INSTANCE.parse(json);
    }

    public static AmountIngredient fromNetwork(FriendlyByteBuf buffer) {
        return Serializer.INSTANCE.parse(buffer);
    }


    public static ResourceLocation TYPE(){
        return Rhyme.space("amount_ingredient");
    }



    public static void serialize(JsonObject jsonObject, Ingredient ingredient, int amount) {
        JsonObject ings = new JsonObject();
        jsonObject.add("ingredient", ings);
        if( ingredient instanceof AmountIngredient am){
            am.toJsonIngredient().getAsJsonObject().asMap().forEach(ings::add);
        }else
            ingredient.toJson().getAsJsonObject().asMap().forEach(ings::add);
        jsonObject.addProperty("amount", amount);
//        ItemStack.CODEC.encodeStart(JsonOps.INSTANCE, itemStack).result().ifPresent(js->js.getAsJsonObject().asMap().forEach(jsonObject::add));
    }

    public static class Serializer implements IIngredientSerializer<AmountIngredient> {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public @NotNull AmountIngredient parse(FriendlyByteBuf buffer) {
            return new AmountIngredient(buffer.readItem());
        }

        @Override
        public @NotNull AmountIngredient parse(@NotNull JsonObject json) {
            Ingredient ingredient = Ingredient.fromJson(json.get("ingredient"));
            int amount = json.get("amount").getAsInt();
            return new AmountIngredient(ingredient, amount);
        }

        @Override
        public void write(FriendlyByteBuf buffer, AmountIngredient ingredient) {
//            buffer.writeItem(ingredient.itemStack);
            ingredient.ingredient.toNetwork(buffer);
            buffer.writeInt(ingredient.amount);
        }
    }

//    public static class AmountItemValue implements Value {
//        private final Collection<ItemStack> itemStacks;
//
//        public AmountItemValue(ItemStack itemStack) {
//            this.itemStacks = Collections.singleton(itemStack);
//        }
//
//        @Override
//        public @NotNull Collection<ItemStack> getItems() {
//            return itemStacks;
//        }
//
//        @Override
//        public @NotNull JsonObject serialize() {
//            JsonObject jsonObject = new JsonObject();
//            ItemStack itemStack = itemStacks.iterator().next();
//            AmountIngredient.serialize(jsonObject, itemStack);
//            return jsonObject;
//        }
//    }

}
