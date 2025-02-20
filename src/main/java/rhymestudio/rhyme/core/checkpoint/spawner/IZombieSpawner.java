package rhymestudio.rhyme.core.checkpoint.spawner;

import rhymestudio.rhyme.core.checkpoint.WaveManager;
import rhymestudio.rhyme.core.checkpoint.entitygroup.IEntityTypeGroup;

/**
 * 僵尸生成器
 */
public interface IZombieSpawner {

    /**
     * 生成一轮僵尸组
     * @param manager 波管理器
     * @param zombieInfo 僵尸信息
     */
    void spawnZombie(WaveManager manager, IEntityTypeGroup zombieInfo);

    /**
     * 关卡是否已经生成完毕
     */
    boolean isOver();

    boolean shouldNextWave(WaveManager.State state);

}
