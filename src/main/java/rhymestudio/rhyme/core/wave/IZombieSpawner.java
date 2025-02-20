package rhymestudio.rhyme.core.wave;

public interface IZombieSpawner {

    void spawnZombie(WaveManager manager, WaveManager.SingleZombie zombieInfo);

    boolean isOver();

    boolean shouldNextWave(WaveManager.State state);

}
