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
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.checkpoint.*;
import rhymestudio.rhyme.core.checkpoint.entitygroup.IEntityTypeGroup;
import rhymestudio.rhyme.core.checkpoint.spawner.IZombieSpawner;
import rhymestudio.rhyme.core.registry.ModAttachments;
import rhymestudio.rhyme.core.registry.ModBlocks;
import rhymestudio.rhyme.core.registry.ModDataComponentTypes;
import rhymestudio.rhyme.datagen.CheckPointDataProvider;

import static net.minecraft.world.level.block.BarrelBlock.FACING;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

public class ZombieBannerBlock extends BaseEntityBlock {
    public ZombieBannerBlock(Properties properties) {
        super(properties);
    }

    public static final MapCodec<ZombieBannerBlock> CODEC = simpleCodec(ZombieBannerBlock::new);

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return Block.box(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {return RenderShape.MODEL;}

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(WATERLOGGED);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext placeContext) {
        FluidState fluidstate = placeContext.getLevel().getFluidState(placeContext.getClickedPos());
        return defaultBlockState()
                .setValue(FACING, placeContext.getHorizontalDirection().getOpposite())
                .setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
    }

    @Override
    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
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
                ResourceLocation location = CheckPointDataProvider.L1_1;
                var data = stack.get(ModDataComponentTypes.CHECKPOINT_LOCATION);
                if(data!= null) {
                    if (entity.waveManager.isEmpty()) {
                        entity.waveManager = WaveManager.loadFromResource(entity, location);
                        if(entity.waveManager.isEmpty()){
                            // nbt不合法
                            player.sendSystemMessage(Component.literal("invalid nbt data"));
                        }else {
                            entity.bossEvent = (ServerBossEvent) new ServerBossEvent(Component.translatable(Rhyme.toLang(location)), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS).setDarkenScreen(true);
                            stack.shrink(1);
                        }
                        return ItemInteractionResult.SUCCESS;
                    }
                }
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }


    @Override
    public <T extends BlockEntity> BlockEntityTicker getTicker(@NotNull Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        return createTickerHelper(pBlockEntityType, ModBlocks.ZOMBIE_FLAG_BLOCK_ENTITY.get(), (level, pos, state, blockEntity)->{

            if(level instanceof ServerLevel serverLevel) {

                blockEntity.time++;
                if( blockEntity.time < ZombieFlagBlockEntity.TIME_PRE_SPAWN) return;
                if(blockEntity.waveManager.isEmpty()) {
                    blockEntity.time = 0;
                    return;
                }
                // 服务端更新状态

                WaveManager.State state1 = blockEntity.waveManager.update();

                blockEntity.update();
                // 结束
                if (state1 == WaveManager.State.OVER) {
                    --blockEntity.properties.timeDelay;
                    if (blockEntity.isOver()) {
                        // 生成战利品，保存玩家进度
                        serverLevel.getPlayers(p->p.distanceToSqr(Vec3.atCenterOf(blockEntity.getBlockPos())) < blockEntity.properties.maxDistance * blockEntity.properties.maxDistance).forEach(p->{
                            p.getData(ModAttachments.PLAYER_PROGRESS_STORAGE.get()).markChapter(blockEntity.waveManager.checkPoint);
                        });
                        level.setBlock(pos, Blocks.CHEST.defaultBlockState(), 2);
                        if(level.getBlockEntity(pos) instanceof ChestBlockEntity entity){
                            CompoundTag tag = new CompoundTag();
                            tag.putString("LootTable", blockEntity.waveManager.lootTable.toString());
                            entity.loadWithComponents(tag,level.registryAccess());
                        }
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
        super.onRemove(state, level, pos, newState, isMoving);
    }

    public static final class  ZombieFlagBlockEntity extends BlockEntity implements IZombieSpawner {

        static final int TIME_PRE_SPAWN = 50;
        ServerBossEvent bossEvent;
        // common
        int time = 0;

        // client side only
        int remain = 0;

        // server side only
        SpawnerProperties properties;
        WaveManager waveManager = WaveManager.EMPTY;

        public ZombieFlagBlockEntity(BlockPos pos, BlockState blockState) {
            super(ModBlocks.ZOMBIE_FLAG_BLOCK_ENTITY.get(), pos, blockState);

            properties = new SpawnerProperties(20, 20, Vec3.atCenterOf(this.getBlockPos()), 50);
        }



        @Override
        public void spawnZombie(WaveManager manager, IEntityTypeGroup zombieInfo) {
            EntityType<?> type = zombieInfo.getType();
            if (level != null) {
                int count = zombieInfo.getCount();
                for (int i = 0; i < count; i++) {
                    var entity = type.create(level);
                    properties.monsters.add(entity.getUUID());
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
            return waveManager.state == WaveManager.State.OVER && properties.timeDelay <= 0 && properties.monsters.isEmpty();
        }

        @Override
        public boolean shouldNextWave(WaveManager.State state) {
//            return true;
            return properties.monsters.isEmpty();
        }

        /* spawner */

        @Override
        public SpawnerProperties getSpawnerProperties() {
            return properties;
        }


        @Override
        public BossEvent getBossEvent() {
            return bossEvent;
        }

        @Override
        public Level getSeverLevel() {
            return this.level;
        }

        @Override
        public void onPlayerIn(ServerPlayer player) {
            player.connection.send(getUpdatePacket());

        }

        @Override
        public void onUpdateInternal() {
            if (bossEvent != null) {
                bossEvent.setProgress((float) (waveManager.waveCount - waveManager.currentWave) /
                        waveManager.waveCount);
            }
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
            waveManager.loadState(tag);
            if(tag.contains("remain"))
                remain = tag.getInt("remain");
        }

        @Override
        public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
            CompoundTag tag = super.getUpdateTag(registries);
            waveManager.saveState(tag);
            tag.putInt("remain", properties.monsters.size());

            return tag;
        }

        // 存储

        @Override
        protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
            super.loadAdditional(tag, registries);
            if(tag.contains("checkpoint")){
                ResourceLocation location = ResourceLocation.parse(tag.getString("checkpoint"));
                waveManager = WaveManager.loadFromResource(this, location);
                bossEvent = (ServerBossEvent) new ServerBossEvent(Component.translatable(location.toLanguageKey()), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS).setDarkenScreen(true);

            }
            waveManager.loadState(tag);
            time = waveManager.currentWaveTime;
            properties.loadMonsters(tag);
        }
//
        @Override
        protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
            super.saveAdditional(tag, registries);
            waveManager.saveState(tag);
            properties.saveMonsters(tag);
        }
    }

}
