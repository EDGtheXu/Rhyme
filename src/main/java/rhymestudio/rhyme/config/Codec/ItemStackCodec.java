package rhymestudio.rhyme.config.Codec;

import com.google.gson.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.lang.reflect.Type;

public class ItemStackCodec implements ICodec<ItemStack> {
    @Override
    public ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        String item = obj.get("item").getAsString();
        int count = obj.get("count").getAsInt();
        ResourceLocation itemRL = ResourceLocation.parse(item);
        return new ItemStack(BuiltInRegistries.ITEM.get(itemRL), count);
    }

    @Override
    public JsonElement serialize(ItemStack src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        obj.addProperty("item", ForgeRegistries.ITEMS.getKey(src.getItemHolder().get()).toString());
        obj.addProperty("count", src.getCount());
        return obj;
    }

    public static ICodec<ItemStack> ITEM_STACK_COMPONENT_CODEC = new ItemStackCodec() {
        @Override
        public ItemStack deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            ItemStack it = super.deserialize(json,typeOfT,context);
            JsonObject obj = json.getAsJsonObject();
            JsonArray arr = obj.get("component").getAsJsonArray();
            int level = 0;
            for(int i=0;i<arr.size();i++){
                var l=arr.get(i).getAsJsonObject().get("level");
                if(l!=null){
                    level = l.getAsInt();
                }
            }

            return it;

        }

        @Override
        public JsonElement serialize(ItemStack src, Type typeOfSrc, JsonSerializationContext context) {
            JsonElement element = super.serialize(src,typeOfSrc,context);
            JsonObject obj = element.getAsJsonObject();
            JsonArray components = new JsonArray();
//            var data = src.getComponents().get(ModDataComponentTypes.CARD_QUALITY.get());
//            if(data==null){
                JsonObject c = new JsonObject();
                c.addProperty("level",0);
                components.add(c);
//            }

            obj.add("component",components);
            return obj;
        }
    };

}
