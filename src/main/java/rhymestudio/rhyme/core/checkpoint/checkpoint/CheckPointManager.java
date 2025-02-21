package rhymestudio.rhyme.core.checkpoint.checkpoint;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.checkpoint.ModCheckPoints;

import java.io.IOException;
import java.io.Reader;
import java.util.*;

/**
 * <h1>关卡管理类</h1>
 * 查询注册的关卡数据，从数据包加载关卡数据
 */
public class CheckPointManager {

    private static final Map<ICheckPointType<?>, ICheckPoint<?>> BY_TYPE = new HashMap<>();
    private static final Map<ResourceLocation, ICheckPoint<?>> BY_NAME = new HashMap<>();

    public static <T extends ICheckPointType<?>> ICheckPoint<?> getCheckPoint(T type) {
        return BY_TYPE.get(type);
    }

    public static <T extends ICheckPoint<?>>  Optional<T> getCheckPoint(ResourceLocation name) {
        try {
            return Optional.of((T) BY_NAME.get(name));
        }
        catch (ClassCastException | NullPointerException e ){
            return Optional.empty();
        }
    }


    public static Optional<ICheckPoint<?>> getRandom(){
        List<ICheckPoint<?>> checkPoints = new ArrayList<>(BY_NAME.values());
        if (checkPoints.isEmpty()) {
            return Optional.empty();
        }
        Random random = new Random();
        int randomIndex = random.nextInt(checkPoints.size());
        return Optional.of(checkPoints.get(randomIndex));
    }


    public static void registerFromJson(ResourceManager manager) {
        Map<ResourceLocation, Resource> jsons = manager.listResources("checkpoint", r -> r.getPath().endsWith(".json"));
        jsons.forEach((k,v)->{
            try {
                String[] split = k.getPath().split("[/.]");
                String path = split[1];
                String name = split[2];
                Reader reader = manager.openAsReader(k);


                JsonObject jsonobject = GsonHelper.parse(reader);
                var checkpoint = CheckPoint.MAP_CODEC.codec().decode(JsonOps.INSTANCE, jsonobject).result().get().getFirst();
                var type = ModCheckPoints.CHECK_POINTS.getEntries().stream().filter(e->e.get().name().equals(path)).findAny().get().get();
                BY_TYPE.put(type, checkpoint);
                BY_NAME.put(checkpoint.name(), checkpoint);
            } catch (IOException e) {
                Rhyme.LOGGER.warn("Failed to load checkpoint: " + e.getMessage());
            }
        });
    }

}
