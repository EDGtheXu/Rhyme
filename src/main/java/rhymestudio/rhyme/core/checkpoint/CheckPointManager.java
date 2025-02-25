package rhymestudio.rhyme.core.checkpoint;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.checkpoint.checkpoint.ICheckPoint;
import rhymestudio.rhyme.core.checkpoint.checkpoint.ICheckPointType;

import java.io.IOException;
import java.io.Reader;
import java.util.*;

/**
 * <h1>关卡管理类</h1>
 * 查询注册的关卡数据，从数据包加载关卡数据
 */
public class CheckPointManager {

    private static final Map<ICheckPointType<?>, TreeMap<Integer, ICheckPoint<?>>> BY_TYPE = new HashMap<>();
    private static final Map<ResourceLocation, ICheckPoint<?>> BY_NAME = new HashMap<>();

    /**+
     * 获取指定类型的某个关卡数据
     * @param type 关卡类型
     * @param index 关卡索引
     * @return 关卡数据
     * @param <T> 关卡类型
     */
    public static <T extends ICheckPointType<?>, U extends ICheckPoint<?>> Optional<U> getCheckPoint(T type, int index) {
        if (!BY_TYPE.containsKey(type)) {
            return Optional.empty();
        }
        if (!BY_TYPE.get(type).containsKey(index)) {
            return Optional.empty();
        }
        return Optional.of((U) BY_TYPE.get(type).get(index));
    }

    /**
     * 获取指定名称的关卡数据
     * @param name 关卡名称
     * @return 关卡数据
     * @param <T> 关卡类型
     */
    public static <T extends ICheckPoint<?>>  Optional<T> getCheckPoint(ResourceLocation name) {
        try {
            return Optional.of((T) BY_NAME.get(name));
        }
        catch (ClassCastException | NullPointerException e ){
            return Optional.empty();
        }
    }


    /**
     * 随机获取一个关卡数据
     * @return 关卡数据
     */
    public static Optional<ICheckPoint<?>> getRandom(){
        List<ICheckPoint<?>> checkPoints = new ArrayList<>(BY_NAME.values());
        if (checkPoints.isEmpty()) {
            return Optional.empty();
        }
        Random random = new Random();
        int randomIndex = random.nextInt(checkPoints.size());
        return Optional.of(checkPoints.get(randomIndex));
    }

    /**
     * 获取指定类型的关卡数量
     * @param type 关卡类型
     * @return 关卡数量
     */
    public static int getCount(ICheckPointType<?> type) {
        return BY_TYPE.get(type).size();
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
                var checkpoint = ICheckPoint.TYPED_CODEC.decode(JsonOps.INSTANCE, jsonobject).result().get().getFirst();
                var type = ModCheckPoints.CHECK_POINTS.getEntries().stream().filter(e->e.get().resource().getPath().equals(path)).findAny().get().get();
                if(!BY_TYPE.containsKey(type)){
                    BY_TYPE.put(type, new TreeMap<>());
                }
                BY_TYPE.get(type).put(checkpoint.index(), checkpoint);
                BY_NAME.put(checkpoint.name(), checkpoint);
            } catch (IOException e) {
                Rhyme.LOGGER.warn("Failed to load checkpoint: " + e.getMessage());
            }
        });
    }

}
