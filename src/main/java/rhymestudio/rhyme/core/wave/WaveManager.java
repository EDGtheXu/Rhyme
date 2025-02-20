package rhymestudio.rhyme.core.wave;

import net.minecraft.world.entity.EntityType;

import javax.annotation.Nullable;
import java.util.*;

/**
 * <h1>波次管理器</h1>
 * 管理关卡的波次，生成僵尸，更新状态等。
 */
public class WaveManager {
    public static final int MIN_WAVE_BLOCK_TICKS = 5 * 20;
    public static final int MAX_WAVE_BLOCK_TICKS = 10 * 20;
    public int waveCount;
    public int currentWaveTime;
    public int currentWave;
    public int currentGroup;
    public List<Wave> waves;
    public State state;

    IZombieSpawner spawner;

    WaveManager(IZombieSpawner spawner) {
        this.waveCount = 0;
        this.waves = new ArrayList<>();
        state = State.IN_PROGRESS;
        this.spawner = spawner;
    }

    public enum State{
        OVER, // 关卡结束
        IN_PROGRESS, // 关卡进行中
        NEXT_SPAWN, // 下一组僵尸生成
        NEXT_WAVE // 即将进入下一波
    }

    /**
     * 能否生成僵尸
     * @return true: 可以生成，false: 不能生成
     */
    public State predictState() {
        Wave wave = getWave();
        if (wave == null || waves.isEmpty()) {
            // 已经完成关卡
            return State.OVER;
        }

        if (wave.indexList().size() == currentGroup) {
            // 波次已经结束，进入下一波
//            removeWave();
            return State.NEXT_WAVE;
        }
        int nextTime = wave.zombies.firstKey();
        if(currentWaveTime > nextTime && !wave.isBlock || currentWaveTime > nextTime + MAX_WAVE_BLOCK_TICKS && wave.isBlock){
            // 到达时间点，生成下一组僵尸
            return State.NEXT_SPAWN;
        }
        // 未到达时间点，继续等待
        return State.IN_PROGRESS;
    }

    /**
     * 更新状态
     */
    public State update() {
        currentWaveTime++;
        state = predictState();
        Wave wave = getWave();
        System.out.println("current wave time: " + currentWaveTime + " state: " + state);

        if(state == State.NEXT_WAVE){
            System.out.println("state: " + state);
            if(spawner.shouldNextWave(state) && currentWaveTime - waves.getLast().zombies.lastKey() > MIN_WAVE_BLOCK_TICKS){
                // 到达下一波的触发条件, 且当前组时间超过最小时间间隔
                removeWave();
                currentWaveTime = 0;
                currentGroup = 0;
            }

        }else if(state == State.NEXT_SPAWN){
            System.out.println("state: " + state);

            if (wave != null) {
                int key = wave.indexList().get(currentGroup++);
                var info = wave.zombies().get(key);
                spawner.spawnZombie(this, info);
                currentWaveTime = 0;
            }

        }
        return state;
    }

    /**
     * 生成僵尸
     */
//    public void generateZombies(Level level, SingleZombie zombie, BlockPos center){
//        System.out.println( "generate zombie :" + zombie.type.toString() + " " + zombie.count);
//        var entity = zombie.type.create(level);
//        entity.setPos(center.getX(), center.getY(), center.getZ());
//        level.addFreshEntity(entity);
//    }

    /**
     * 添加波次
     */
    public void addWave(Wave wave) {
        waveCount++;
        waves.add(wave);
    }

    /**
     * 删除波次
     */
    public void removeWave() {
        if (!waves.isEmpty()) {
            currentWave++;
        }
    }

    /**
     * 获取当前波次的僵尸信息
      */
    public @Nullable Wave getWave() {
        return waves.isEmpty() ? null :
                (currentWave >= waves.size() ? null : waves.get(currentWave));
    }

    /**
     * 打印所有波次的僵尸配置
      */
    public void printAllWaves() {
        Iterable<Wave> iterable = waves;
        int c = 1;
        for (Wave wave : iterable) {
            System.out.println("Wave " + c++ + ": " + wave.zombies + " " + wave.isBlock);
        }
    }


    public static Builder builder(IZombieSpawner spawner) {
        return new Builder(spawner);
    }
    /**
     * 构建一个关卡
     */
    public static class Builder{
        private final WaveManager waveManager;

        public Builder(IZombieSpawner spawner) {
            waveManager = new WaveManager(spawner);
        }

        public Wave.WaveBuilder addWave(boolean isBlock) {
            return new Wave.WaveBuilder(this, isBlock);
        }

        public WaveManager build() {
            return waveManager;
        }
    }



    /**
     * <h1>波次</h1>
     * 记录波次的配置
     * @param zombies 僵尸配置
     */
    public record Wave(TreeMap<Integer, SingleZombie> zombies,List<Integer> indexList, boolean isBlock) {

        public Map.Entry<Integer, SingleZombie> poll(){
            return zombies.pollFirstEntry();
        }
        /**
         * 构建一波僵尸
         */
        public static class WaveBuilder{
            private final boolean isBlock;
            private final Builder parent;
            TreeMap<Integer, SingleZombie> zombies = new TreeMap<>();
            public WaveBuilder(Builder parent, boolean isBlock) {
                this.isBlock = isBlock;
                this.parent = parent;
            }

            public WaveBuilder addZombie(int time, EntityType<?> type, int count) {
                zombies.put(time, new SingleZombie(type, count));
                return this;
            }

            public Builder buildWave() {
                Wave wave = new Wave(zombies, zombies.keySet().stream().toList(), isBlock);
                parent.waveManager.addWave(wave);
                return parent;
            }
        }
    }

    /**
     * <h1>僵尸组</h1>
     *    记录单个僵尸组的配置
     * @param type 僵尸类型
     * @param count 僵尸数量
     */
    public record SingleZombie(EntityType<?> type, int count){

    }
}
