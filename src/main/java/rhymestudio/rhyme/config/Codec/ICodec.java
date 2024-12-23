package rhymestudio.rhyme.config.Codec;

import com.google.gson.*;

public interface ICodec<T> extends JsonSerializer<T>, JsonDeserializer<T> {

    GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
    static void register(Class<?> type, ICodec<?> codec){
        builder.registerTypeAdapter(type, codec);
    }
    static Gson getGson() {
        return builder.create();
    }

}