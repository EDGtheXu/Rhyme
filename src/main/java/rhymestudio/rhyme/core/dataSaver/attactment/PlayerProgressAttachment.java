package rhymestudio.rhyme.core.dataSaver.attactment;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.checkpoint.checkpoint.ICheckPoint;
import rhymestudio.rhyme.core.checkpoint.checkpoint.ICheckPointType;
import rhymestudio.rhyme.core.registry.ModAttachments;
import rhymestudio.rhyme.core.registry.ModRegistry;
import rhymestudio.rhyme.network.c2s.ClientEventBoundPacket;
import rhymestudio.rhyme.network.s2c.PlayerChapterPacket;
import rhymestudio.rhyme.utils.AdapterUtils;

import java.util.*;

/**
 * <h1> 关卡进度 </h1>
 */
public class PlayerProgressAttachment implements INBTSerializable<CompoundTag> {

    private final Map<ICheckPointType<?>, List<Integer>> chapterMapList = new HashMap<>();

    /**
     * 标记完成章节
     * @param type 章节类型
     * @param chapter 章节
     */
    public void markChapter(ICheckPointType<?> type, int chapter) {
        if (!chapterMapList.containsKey(type)) {
            chapterMapList.put(type, new ArrayList<>());
        }
        if(!chapterMapList.get(type).contains(chapter))
            chapterMapList.get(type).add(chapter);
    }

    public void markChapter(ICheckPoint<?> checkpoint) {
        ICheckPointType<?> type = checkpoint.getType();
        int chapter = checkpoint.index();
        if (!chapterMapList.containsKey(type)) {
            chapterMapList.put(type, new ArrayList<>());
        }
        if(!chapterMapList.get(type).contains(chapter))
            chapterMapList.get(type).add(chapter);
    }

    /**
     * 获取章节列表
     * @param type 章节类型
     * @return 章节列表
     */
    public List<Integer> getChapter(ICheckPointType<?> type) {
        return chapterMapList.getOrDefault(type, new ArrayList<>());
    }

    public static void sync(ServerPlayer player){
        AdapterUtils.sendPacketToPlayer(new PlayerChapterPacket(player.getData(ModAttachments.PLAYER_PROGRESS_STORAGE.get())), player);
    }

    public static void openChapterMenu(Player player) {
        AdapterUtils.sendPacketToServer(new ClientEventBoundPacket(1));
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        for (ICheckPointType<?> type : chapterMapList.keySet()) {
            CompoundTag chapterTag = new CompoundTag();
            List<Integer> chapterList = chapterMapList.get(type);
            chapterTag.putIntArray("chapters", chapterList.stream().mapToInt(i -> i).distinct().toArray());
            tag.put(type.resource().toString(), chapterTag);
        }
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag tag) {
        for (String key : tag.getAllKeys()) {
            ICheckPointType<?> type = ModRegistry.CHECKPOINT_TYPE_REGISTRY.get(Rhyme.parse(key));
//            ICheckPointType<?> type = provider.lookupOrThrow(ModRegistry.CHECKPOINT_TYPE_KEY).;
            if (type != null) {
                CompoundTag chapterTag = tag.getCompound(key);
                int[] chapters = chapterTag.getIntArray("chapters");
                List<Integer> chapterList = new ArrayList<>(Arrays.stream(chapters).distinct().boxed().toList());
                chapterMapList.put(type, chapterList);
            }else{
                Rhyme.LOGGER.warn("Unknown checkpoint type: " + key);
            }
        }
    }
}
