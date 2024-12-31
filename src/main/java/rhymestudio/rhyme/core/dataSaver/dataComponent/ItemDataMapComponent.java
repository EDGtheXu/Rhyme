package rhymestudio.rhyme.core.dataSaver.dataComponent;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ItemDataMapComponent implements DataComponentType<ItemDataMapComponent> {
    private final JsonObject itemDataMap;
    private final Map<String, JsonElement> itemData;


    public static DataMapBuilder builder(){
        return new DataMapBuilder();
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

    public static final Codec<ItemDataMapComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("itemDataMap").forGetter(o-> o.itemDataMap.toString() )
    ).apply(instance, s->new ItemDataMapComponent(JsonParser.parseString(s).getAsJsonObject())));

    public static final StreamCodec<ByteBuf, ItemDataMapComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, i->i.itemDataMap.toString(),
            s->new ItemDataMapComponent(JsonParser.parseString(s).getAsJsonObject())
    );

    @Override
    public @Nullable Codec<ItemDataMapComponent> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<? super RegistryFriendlyByteBuf, ItemDataMapComponent> streamCodec() {
        return STREAM_CODEC;
    }
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
