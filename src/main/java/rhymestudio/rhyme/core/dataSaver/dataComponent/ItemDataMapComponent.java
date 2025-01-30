package rhymestudio.rhyme.core.dataSaver.dataComponent;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ItemDataMapComponent extends AbstractDataComponent<ItemDataMapComponent> {
    private JsonObject itemDataMap;
    private Map<String, JsonElement> itemData;

    public static DataMapBuilder builder(){
        return new DataMapBuilder();
    }

    @Override
    public String name() {
        return "item_data_map";
    }

    @Override
    public void readFromNBT(CompoundTag tag) {
        if (!tag.contains("item_data_map")) {
            setInvalid();
            return;
        }
        this.itemDataMap = GsonHelper.parse(tag.getString("item_data_map"));
    }

    @Override
    public void writeToNBT(CompoundTag tag) {
        tag.putString("item_data_map", itemDataMap.toString());
    }

    public static class DataMapBuilder{
        Map<String,Map<String, JsonElement>> itemData = new HashMap<>();

        public DataMapBuilder add(String key, String name, Object value) {
            if(!itemData.containsKey(key)){
                itemData.put(key, new HashMap<>());
            }
            if(value instanceof Float)
                itemData.get(key).put(name,new JsonPrimitive((Float)value));
            else if(value instanceof Boolean)
                itemData.get(key).put(name,new JsonPrimitive((Boolean)value));
            else if(value instanceof String)
                itemData.get(key).put(name,new JsonPrimitive((String)value));
            else if(value instanceof Integer)
                itemData.get(key).put(name,new JsonPrimitive((Integer)value));
            return this;
        }

        public ItemDataMapComponent build() {
            JsonObject itemDataMap = new JsonObject();
            for(String key: itemData.keySet()){
                JsonObject subObject = new JsonObject();
                for(String name: itemData.get(key).keySet()){
                    subObject.add(name, itemData.get(key).get(name));
                }
                itemDataMap.add(key, subObject);
            }
            return new ItemDataMapComponent(itemDataMap);
        }
    }
    public int getInt(String key,String name) {
        try{
            return itemData.get(key).getAsJsonObject().get(name).getAsInt();
        }catch (Exception e){
            System.out.println("Error in getting float value for key: " + key + " and name: " + name);
            return 0;
        }
    }

    public float getFloat(String key,String name) {
        try{
            return itemData.get(key).getAsJsonObject().get(name).getAsFloat();
        }catch (Exception e){
            System.out.println("Error in getting float value for key: " + key + " and name: " + name);
            return 0;
        }
    }

    public boolean getBoolean(String key,String name) {
        try{
            return itemData.get(key).getAsJsonObject().get(name).getAsBoolean();
        }catch (Exception e){
            System.out.println("Error in getting boolean value for key: " + key + " and name: " + name);
            return false;
        }
    }




    public ItemDataMapComponent(JsonObject itemDataMap) {
        this.itemDataMap = itemDataMap;
        itemData = itemDataMap.asMap();
    }

    public ItemDataMapComponent(ItemStack stack){
        this.readFromNBT(stack.getOrCreateTag());
    }

    public static final Codec<ItemDataMapComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("itemDataMap").forGetter(o-> o.itemDataMap.toString() )
    ).apply(instance, s->new ItemDataMapComponent(JsonParser.parseString(s).getAsJsonObject())));


    public int hashCode(){
        return itemDataMap.hashCode();
    }
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof ItemDataMapComponent component)) return false;

        return itemDataMap.equals(component.itemDataMap);
    }
}
