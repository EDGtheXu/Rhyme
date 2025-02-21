package rhymestudio.rhyme.core.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.checkpoint.*;
import rhymestudio.rhyme.core.checkpoint.checkpoint.CheckPoint;
import rhymestudio.rhyme.core.checkpoint.checkpoint.CheckPointManager;
import rhymestudio.rhyme.core.checkpoint.entitygroup.IEntityTypeGroup;
import rhymestudio.rhyme.core.checkpoint.spawner.IZombieSpawner;
import rhymestudio.rhyme.core.registry.ModBlocks;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class ZombieFlagBlock extends BaseEntityBlock {
    public ZombieFlagBlock(Properties properties) {
        super(properties);
    }

    public static final MapCodec<ZombieFlagBlock> CODEC = simpleCodec(ZombieFlagBlock::new);

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ZombieFlagBlockEntity(blockPos, blockState);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(!level.isClientSide){
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if(blockEntity instanceof ZombieFlagBlockEntity entity) {
                ResourceLocation location = Rhyme.space("lvl_1");
                if(entity.waveManager == WaveManager.EMPTY) {

                    entity.waveManager = new WaveManager(entity, CheckPointManager.<CheckPoint>getCheckPoint(location).get());
                    entity.bossEvent = (ServerBossEvent) new ServerBossEvent(Component.translatable(location.toLanguageKey()), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS).setDarkenScreen(true);

                    return ItemInteractionResult.SUCCESS;
                }
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }


    @Override
    public <T extends BlockEntity> BlockEntityTicker getTicker(@NotNull Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlocks.ZOMBIE_FLAG_BLOCK_ENTITY.get(), (level, pos, state, blockEntity)->{

            if(!level.isClientSide()) {

                blockEntity.time++;
                if( blockEntity.time < ZombieFlagBlockEntity.TIME_PRE_SPAWN) return;
                if(blockEntity.waveManager == WaveManager.EMPTY) {
                    blockEntity.time = 0;
                    return;
                }
                // 服务端更新状态

                blockEntity.state = blockEntity.waveManager.update();
                var players = level.players();

                for (Player player : players) {
                    if(player.distanceToSqr(Vec3.atCenterOf(blockEntity.getBlockPos())) < 5*5) {
                        ((ServerPlayer) player).connection.send(ClientboundBlockEntityDataPacket.create(blockEntity));

                        if(blockEntity.bossEvent != null)
                            blockEntity.bossEvent.addPlayer((ServerPlayer) player);
                    }else{
                        if(blockEntity.bossEvent != null)
                            blockEntity.bossEvent.removePlayer((ServerPlayer) player);
                    }
                }

                // 更新怪物列表
                if(--blockEntity.checkInterval <= 0) {
                    if (blockEntity.bossEvent != null) {
                        blockEntity.bossEvent.setProgress((float) (blockEntity.waveManager.waveCount - blockEntity.waveManager.currentWave) /
                                blockEntity.waveManager.waveCount);
                    }

                    blockEntity.checkInterval = blockEntity._checkInterval;
                    for (Iterator<UUID> iterator = blockEntity.monsters.iterator(); iterator.hasNext(); ) {
                        UUID uuid = iterator.next();
                        var entity = ((ServerLevel) level).getEntity(uuid);
                        if (entity == null || !entity.isAlive()) {
                            iterator.remove();
                        }
                    }
                }

                // 结束
                if (blockEntity.state == WaveManager.State.OVER) {
                    --blockEntity.timeDelay;
                    if (blockEntity.isOver()) {
                        level.setBlock(pos, Blocks.BONE_BLOCK.defaultBlockState(), 2);

                    }
                }

            }else{
//                System.out.println(blockEntity.time + " " + blockEntity.remain);
            }
        });
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if(!level.isClientSide){
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if(blockEntity instanceof ZombieFlagBlockEntity entity) {
                if(entity.bossEvent != null)
                    entity.bossEvent.removeAllPlayers();
            }
        }
    }

    public static final class  ZombieFlagBlockEntity extends BlockEntity implements IZombieSpawner {

        static final int TIME_PRE_SPAWN = 50;
        ServerBossEvent bossEvent;
        // common
        int time = 0;

        // client side only
        int remain = 0;

        // server side only
        WaveManager waveManager = WaveManager.EMPTY;
        WaveManager.State state = WaveManager.State.IN_PROGRESS;
        List<UUID> monsters = new LinkedList<>();
        final int _checkInterval = 50;
        int checkInterval = 50;
        int timeDelay = 100;

        public ZombieFlagBlockEntity(BlockPos pos, BlockState blockState) {
            super(ModBlocks.ZOMBIE_FLAG_BLOCK_ENTITY.get(), pos, blockState);


        }



        @Override
        public void spawnZombie(WaveManager manager, IEntityTypeGroup zombieInfo) {
            EntityType<?> type = zombieInfo.getType();
            if (level != null) {
                int count = zombieInfo.getCount();
                for (int i = 0; i < count; i++) {
                    var entity = type.create(level);
                    monsters.add(entity.getUUID());
                    entity.setPos(
                            this.worldPosition.getX() + this.level.random.nextFloat() * 5 - 2.5f,
                            this.worldPosition.getY() + 1,
                            this.worldPosition.getZ() + this.level.random.nextFloat() * 5 - 2.5f
                    );
                    level.addFreshEntity(entity);
                }
            }
        }

        @Override
        public boolean isOver() {
            return state== WaveManager.State.OVER && timeDelay <= 0 && monsters.isEmpty();
        }

        @Override
        public boolean shouldNextWave(WaveManager.State state) {
//            return true;
            return monsters.isEmpty();
        }

        // 同步

        @Override
        public Packet<ClientGamePacketListener> getUpdatePacket() {
            return ClientboundBlockEntityDataPacket.create(this);
        }

        @Override
        public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
            super.onDataPacket(net, pkt, lookupProvider);
            CompoundTag tag = pkt.getTag();
            if(tag.contains("time")) {
                time = tag.getInt("time");
                waveManager.currentWaveTime = time;
            }
            if(tag.contains("group")){
                waveManager.currentGroup = tag.getInt("group");
            }
            if(tag.contains("wave")){
                waveManager.currentWave = tag.getInt("wave");
            }
            if(tag.contains("remain"))
                remain = tag.getInt("remain");
        }

        @Override
        public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
            CompoundTag tag = super.getUpdateTag(registries);
            tag.putInt("time", time);
            tag.putInt("group", waveManager.currentGroup);
            tag.putInt("wave", waveManager.currentWave);
            tag.putInt("remain", monsters.size());
            return tag;
        }

        // 存储

        @Override
        protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
            super.loadAdditional(tag, registries);
            if(tag.contains("time")) {
                time = tag.getInt("time");
                waveManager.currentWaveTime = time;
            }
            if(tag.contains("group")) {
                waveManager.currentGroup = tag.getInt("group");
            }
            if(tag.contains("wave")) {
                waveManager.currentWave = tag.getInt("wave");
            }
            if(tag.contains("remain")) {
                int count = tag.getInt("remain");
                for (int i = 0; i < count; i++) {
                    String key = String.format("monster%d", i);
                    if(tag.contains(key))
                        monsters.add(tag.getUUID(key));
                }
            }
        }
//
        @Override
        protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
            super.saveAdditional(tag, registries);
            tag.putInt("time", time);
            tag.putInt("group", waveManager.currentGroup);
            tag.putInt("wave", waveManager.currentWave);
            tag.putInt("remain", monsters.size());
            for (int i = 0; i < monsters.size(); i++) {
                tag.putUUID(String.format("monster%d", i), monsters.get(i));
            }
        }
    }

}
