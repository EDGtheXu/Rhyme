package rhymestudio.rhyme.core.checkpoint.spawner;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import rhymestudio.rhyme.core.checkpoint.WaveManager;
import rhymestudio.rhyme.core.checkpoint.entitygroup.IEntityTypeGroup;

import java.util.*;

/**
 * 僵尸生成器
 */
public interface IZombieSpawner {

    SpawnerProperties getSpawnerProperties();

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

    BossEvent getBossEvent();

    Level getSeverLevel();

    /**
     * 当每个玩家在区域内每次检测触发
     * @param player
     */
    void onPlayerIn(ServerPlayer player);

    /**
     * 当每次检测触发
     */
    void onUpdateInternal();

    /**
     * 当事件未结束，区域自动移除
     */
    void remove();

    /**
     * 每刻更新
     */
    default void updateTick(){


        // 更新怪物列表

        if(--getSpawnerProperties().checkInterval <= 0) {
            onUpdateInternal();

            // 服务端更新状态
            var players = getSeverLevel().players();
            for (Player player : players) {
                if(player instanceof ServerPlayer serverPlayer) {
                    if (player.distanceToSqr(getSpawnerProperties().pos) < getSpawnerProperties().maxDistance * getSpawnerProperties().maxDistance) {
                        onPlayerIn(serverPlayer);

                        if (getBossEvent() instanceof  ServerBossEvent event)
                            event.addPlayer((ServerPlayer) player);
                    } else {
                        if (getBossEvent() instanceof  ServerBossEvent event)
                            event.removePlayer((ServerPlayer) player);
                    }
                }
            }

            if(getBossEvent() instanceof ServerBossEvent event && event.getPlayers().isEmpty()){
                if(getSpawnerProperties().removeDelay-- <= 0) {
                    remove();
                    return;
                }
            }else
                getSpawnerProperties().removeDelay = getSpawnerProperties()._removeDelay;

            getSpawnerProperties().checkInterval = getSpawnerProperties()._checkInterval;
            for (Iterator<UUID> iterator = getSpawnerProperties().monsters.iterator(); iterator.hasNext(); ) {
                UUID uuid = iterator.next();
                if(getSeverLevel() instanceof ServerLevel serverLevel) {
                    var entity = serverLevel.getEntity(uuid);
                    if (entity == null || !entity.isAlive()) {
                        iterator.remove();
                    }
                }
            }
        }
    }




    class SpawnerProperties{
        public final int _checkInterval;

        public int checkInterval;
        public int finishDelay;
        public List<UUID> monsters;
        final Vec3 pos;
        public float maxDistance;

        public int _removeDelay;
        private int removeDelay;

        public SpawnerProperties(int checkInterval, int finishDelay, Vec3 pos, float maxDistance){

            // 结束延迟
            this.finishDelay = finishDelay;

            // 事件僵尸列表
            this.monsters = new LinkedList<>();

            // 事件触发位置
            this.pos = pos;

            // 事件触发距离
            this.maxDistance = maxDistance;

            // 检测间隔
            this._checkInterval = checkInterval;
            this.checkInterval = checkInterval;

            // 没有玩家在区域内，移除事件延迟
            this._removeDelay = 60 / checkInterval;
            this.removeDelay = _removeDelay;
        }

        public void loadMonsters(CompoundTag tag){
            if(tag.contains("remain")) {
                int count = tag.getInt("remain");
                for (int i = 0; i < count; i++) {
                    String key = String.format("monster%d", i);
                    if(tag.contains(key))
                        monsters.add(tag.getUUID(key));
                }
            }
        }

        public void saveMonsters(CompoundTag tag){
            tag.putInt("remain", monsters.size());
            for (int i = 0; i < monsters.size(); i++) {
                tag.putUUID(String.format("monster%d", i), monsters.get(i));
            }
        }
    }
}
