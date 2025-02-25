package rhymestudio.rhyme.core.checkpoint.checkpoint;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import rhymestudio.rhyme.core.checkpoint.ModCheckPoints;
import rhymestudio.rhyme.core.checkpoint.entitygroup.IEntityTypeGroup;
import rhymestudio.rhyme.core.checkpoint.entitygroup.SingleZombie;
import rhymestudio.rhyme.core.checkpoint.entitygroup.WeightSelectedZombie;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <h1> 关卡僵尸信息 </h1>
 * <h3> 记录关卡的配置 </h3>
 * <p> <b>CheckPoint</b>: List&lt;Wave&gt;</p>
 * <p> <b> Wave</b>: TreeMap&lt;Integer, SingleZombie&gt;</p>
 * @param waves 波次列表
 */
public record CheckPoint(List<Wave> waves, ResourceLocation name, ResourceLocation lootTable, int index) implements ICheckPoint<CheckPoint> {

    public static final MapCodec<CheckPoint> MAP_CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            Codec.list(Wave.CODEC).fieldOf("waves").forGetter(CheckPoint::waves),
            net.minecraft.resources.ResourceLocation.CODEC.fieldOf("name").forGetter(CheckPoint::name),
            net.minecraft.resources.ResourceLocation.CODEC.fieldOf("lootTable").forGetter(CheckPoint::lootTable),
            Codec.INT.fieldOf("index").forGetter(CheckPoint::index)
            ).apply(instance, CheckPoint::new));

    @Override
    public CheckPointProvider getCodec() {
        return CheckPointProviderTypes.SIMPLE_CHECKPOINT_PROVIDER.get();
    }

    @Override
    public ICheckPointType<CheckPoint> getType() {
        return ModCheckPoints.SIMPLE_CHECKPOINT;
    }

    public static CheckPoint.Builder builder(ResourceLocation name, int index) {
        return new CheckPoint.Builder(name, index);
    }

    /**
     * <h2> 构建一个关卡 </h2>
     */
    public static class Builder{
        private final List<Wave> waves;
        ResourceLocation lootTable;
        ResourceLocation name;
        int index;

        public Builder(ResourceLocation name, int index) {
            this.waves = new ArrayList<>();
            this.name = name;
            this.index = index;
        }

        public Wave.WaveBuilder addWave(boolean isBlocking) {
            return new Wave.WaveBuilder(this, isBlocking);
        }

        public Builder addLootTable(ResourceLocation lootTable) {
            this.lootTable = lootTable;
            return this;
        }

        public CheckPoint build() {
            return new CheckPoint(waves, name, lootTable, index);
        }
    }

    /**
     * <h1>波次</h1>
     * 记录波次的配置
     * @param zombies 僵尸配置
     */
    public record Wave(TreeMap<Integer, IEntityTypeGroup> zombies, List<Integer> indexList, boolean isBlock) {

        public static Codec<Wave> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                Codec.unboundedMap(Codec.STRING, IEntityTypeGroup.TYPED_CODEC).fieldOf("zombies").forGetter(
                        sin-> sin.zombies.entrySet().stream()
                                .map(entry->new AbstractMap.SimpleEntry<>(entry.getKey().toString(), entry.getValue()))
                                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue))
                ),
                Codec.BOOL.fieldOf("isBlock").forGetter(Wave::isBlock))
               .apply(instance, (zombies, isBlock) -> {
                   var tree1 = new TreeMap<>(zombies.entrySet().stream()
                           .map(entry->new AbstractMap.SimpleEntry<>(Integer.parseInt(entry.getKey()), entry.getValue()))
                           .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue)));
                   return new Wave(tree1, tree1.keySet().stream().toList(), isBlock);
                }));

        /**
         * <h2> 构建一波僵尸 </h2>
         */
        public static class WaveBuilder{
            private final boolean isBlock;
            private final Builder parent;
            TreeMap<Integer, IEntityTypeGroup> zombies = new TreeMap<>();
            public WaveBuilder(Builder parent, boolean isBlock) {
                this.isBlock = isBlock;
                this.parent = parent;
            }

            /**
             * 添加单个种类僵尸
             * @param time 生成时间
             * @param type 僵尸类型
             * @param count 僵尸数量
             * @return 当前WaveBuilder
             */
            public WaveBuilder addZombie(int time, EntityType<?> type, int count) {
                zombies.put(time, new SingleZombie(type, count));
                return this;
            }

            /**
             * 僵尸权重选择
             * @param time 生成时间
             * @param types 僵尸类型列表
             * @param count 抽取数量
             * @return 当前WaveBuilder
             */
            public WaveBuilder addZombieList(int time, List<WeightSelectedZombie.tuple> types, int count) {
                zombies.put(time, new WeightSelectedZombie(types, count));
                return this;
            }

            /**
             * 构建一波僵尸
             * @return 当前Builder
             */
            public Builder buildWave() {
                Wave wave = new Wave(zombies, zombies.keySet().stream().toList(), isBlock);
                parent.waves.add(wave);
                return parent;
            }
        }
    }

}
