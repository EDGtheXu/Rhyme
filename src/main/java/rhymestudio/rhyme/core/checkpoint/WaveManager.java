package rhymestudio.rhyme.core.checkpoint;

import rhymestudio.rhyme.core.checkpoint.checkpoint.CheckPoint;
import rhymestudio.rhyme.core.checkpoint.spawner.IZombieSpawner;

import javax.annotation.Nullable;
import java.util.*;

/**
 * <h1>波次管理器</h1>
 * <p> 关卡 + 生成器 + 状态管理 </p>
 * 管理关卡的波次，生成僵尸，更新状态等。
 */
public class WaveManager {

    public static final int MIN_WAVE_BLOCK_TICKS = 5 * 20;
    public static final int MAX_WAVE_BLOCK_TICKS = 10 * 20;
    public static final WaveManager EMPTY = new WaveManager(null, null);

    // 关卡数值状态
    public State state;
    public int currentWaveTime;
    public int currentWave;
    public int currentGroup;

    // 关卡参数
    public List<CheckPoint.Wave> waves = new ArrayList<>();
    public int waveCount;
    IZombieSpawner spawner;

    /**
     * 构造器
     * @param spawner 僵尸生成器
     * @param checkPoint 关卡信息
     */
    public WaveManager(IZombieSpawner spawner, CheckPoint checkPoint) {
        if(checkPoint != null) {
            this.waveCount = checkPoint.waves().size();
            this.waves = checkPoint.waves();
        }

        this.spawner = spawner;

        state = State.IN_PROGRESS;
    }

    public enum State{
        OVER,           // 关卡结束
        IN_PROGRESS,    // 关卡进行中
        NEXT_SPAWN,     // 下一组僵尸生成
        NEXT_WAVE       // 即将进入下一波
    }

    /**
     * 能否生成僵尸
     * @return true: 可以生成，false: 不能生成
     */
    public State predictState() {
        CheckPoint.Wave wave = getWave();
        if (wave == null || waves.isEmpty()) {
            // 已经完成关卡
            return State.OVER;
        }

        if (wave.indexList().size() == currentGroup) {
            // 波次已经结束，进入下一波
//            removeWave();
            return State.NEXT_WAVE;
        }
        int nextTime = wave.zombies().firstKey();
        if(currentWaveTime > nextTime && !wave.isBlock() || currentWaveTime > nextTime + MAX_WAVE_BLOCK_TICKS && wave.isBlock()){
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
        CheckPoint.Wave wave = getWave();
//        System.out.println("current wave time: " + currentWaveTime + " state: " + state);

        if(state == State.NEXT_WAVE){
//            System.out.println("state: " + state);
            if(spawner.shouldNextWave(state) && currentWaveTime - waves.getLast().zombies().lastKey() > MIN_WAVE_BLOCK_TICKS){
                // 到达下一波的触发条件, 且当前组时间超过最小时间间隔
                nextWave();
                currentWaveTime = 0;
                currentGroup = 0;
            }

        }else if(state == State.NEXT_SPAWN){
//            System.out.println("state: " + state);

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
     * 下一波
     */
    public void nextWave() {
        if (!waves.isEmpty()) {
            currentWave++;
        }
    }

    /**
     * 获取当前波次的僵尸信息
      */
    public @Nullable CheckPoint.Wave getWave() {
        return waves.isEmpty() ? null :
                (currentWave >= waves.size() ? null : waves.get(currentWave));
    }

    /**
     * 打印所有波次的僵尸配置
      */
    public void printAllWaves() {
        Iterable<CheckPoint.Wave> iterable = waves;
        int c = 1;
        for (CheckPoint.Wave wave : iterable) {
            System.out.println("Wave " + c++ + ": " + wave.zombies() + " " + wave.isBlock());
        }
    }


 }
