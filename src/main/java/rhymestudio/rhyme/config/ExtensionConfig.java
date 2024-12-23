package rhymestudio.rhyme.config;

import com.google.gson.*;
import net.minecraft.util.GsonHelper;
import net.neoforged.fml.loading.FMLPaths;

import java.io.File;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static rhymestudio.rhyme.Rhyme.MODID;

public class ExtensionConfig {
    private final String name;
    private final Map<String, Element> keymap;

    public ExtensionConfig(String name, Map<String, Element> keymap){
        this.name = name;
        this.keymap = keymap;
    }
    public void load(){
        Path CONFIG_PATH = FMLPaths.CONFIGDIR.get().resolve(MODID);
        Path configFile = CONFIG_PATH.resolve(name + ".json");
        File file = configFile.toFile();
        try {
            Reader reader;
            JsonObject json;
            if (!file.exists()) {
                CONFIG_PATH.toFile().mkdirs();
                file.createNewFile();
                reader = new java.io.FileReader(file);
                json = new JsonObject();
            }else{
                reader = new java.io.FileReader(file);
                json = GsonHelper.parse(reader);
            }

            for (Map.Entry<String, Element> entry : keymap.entrySet()) {
                readFromJson(json, entry.getValue());
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Writer writer = new java.io.FileWriter(file);
            writer.write(gson.toJson(json));
            writer.close();
            reader.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void readFromJson(JsonObject json, Element obj) {
        JsonElement element = json.get(obj.name);
        if (element == null) {
            if(obj.value instanceof Boolean)
                json.addProperty(obj.name, (Boolean) obj.value);
            else if(obj.value instanceof Integer)
                json.addProperty(obj.name, (Integer) obj.value);
            else if(obj.value instanceof String)
                json.addProperty(obj.name, (String) obj.value);
            else if(obj.value instanceof Double)
                json.addProperty(obj.name, (Double) obj.value);
            else if(obj.value instanceof Float)
                json.addProperty(obj.name, (Float) obj.value);

            element = json.get(obj.name);
        }
        if(obj.value instanceof Boolean)
            obj.value = element.getAsBoolean();
        else if(obj.value instanceof Integer)
            obj.value = element.getAsInt();
        else if(obj.value instanceof String)
            obj.value = element.getAsString();
        else if(obj.value instanceof Double)
            obj.value = element.getAsDouble();
        else if(obj.value instanceof Float)
            obj.value = element.getAsFloat();
    }

    public static ConfigBuilder Builder(String name){
        return new ConfigBuilder(name);
    }
    public static class ConfigBuilder {
        private String name;
        private Map<String, Element> keymap = new HashMap<>();

        public ConfigBuilder(String name) {
            this.name = name;
        }

        public Element add(String name, Object value, String comment) {
            var element = new Element(name, value, comment);
            keymap.put(name, element);
            return element;
        }
        public Element add(String name, Object value) {
            return add(name, value, "");
        }

        public ExtensionConfig build() {
            return new ExtensionConfig(name, keymap);
        }
    }

    public static class Element {
        public String name;
        public Object value;
        public String comment;

        public Element(String name, Object value, String comment) {
            this.name = name;
            this.value = value;
            this.comment = comment;
        }
        public Object get(){
            return value;
        }
    }


}
