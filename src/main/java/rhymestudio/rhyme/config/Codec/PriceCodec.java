package rhymestudio.rhyme.config.Codec;

import com.google.gson.*;
import net.minecraft.world.item.ItemStack;

import java.lang.reflect.Type;

public class PriceCodec implements ICodec<Price>{
    @Override
    public Price deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        int money = jsonObject.get("money").getAsInt();
        JsonObject item = jsonObject.get("item").getAsJsonObject();
        ItemStack itemStack = context.deserialize(item, ItemStack.class);
        return new Price(money, itemStack);
    }

    @Override
    public JsonElement serialize(Price src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("money",src.price);
        jsonObject.add("item", context.serialize(src.itemStack));
        return jsonObject;
    }
}
