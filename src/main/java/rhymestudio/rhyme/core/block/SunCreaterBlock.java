package rhymestudio.rhyme.core.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import rhymestudio.rhyme.core.menu.SunCreatorMenu;
import rhymestudio.rhyme.core.registry.ModAttachments;
import rhymestudio.rhyme.core.registry.ModBlocks;
import rhymestudio.rhyme.core.registry.items.MaterialItems;

import static net.minecraft.world.level.block.BarrelBlock.FACING;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

public class SunCreaterBlock extends BaseEntityBlock  {

    private static final Component CONTAINER_TITLE = Component.translatable("container.rhyme.sun_creator");

    public SunCreaterBlock(Properties properties) {
        super(properties);
    }

    public static final MapCodec<SunCreaterBlock> CODEC = simpleCodec(SunCreaterBlock::new);

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    public void fallOn(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos, net.minecraft.world.entity.Entity entity, float fallDistance) {
        if (entity.isSuppressingBounce()) {
            super.fallOn(level, state, pos, entity, fallDistance);
        }
    }

//    @Override
//    public @Nullable MenuProvider getMenuProvider(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos) {
//        return new SimpleMenuProvider((pContainerId, pPlayerInventory, pPlayer) -> new SunCreatorMenu(pContainerId, pPlayerInventory, new SimpleContainerData(2)), CONTAINER_TITLE);
//    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker getTicker(@NotNull Level pLevel, @NotNull BlockState pState, @NotNull BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide ? null : createTickerHelper(pBlockEntityType, ModBlocks.SUN_CREATOR_BLOCK_ENTITY.get(), (level, pos, state, blockEntity)->{
            if(!level.isNight() && blockEntity.getItems().get(1).is(MaterialItems.GENERAL_SEED)
                && blockEntity.getItems().get(2).is(MaterialItems.PEA_GENE)
            ) {
                int t = blockEntity.time;
                if(t >= blockEntity.interval){
                    int has = blockEntity.getItems().get(0).getCount();
                    if(has < 64){
                        blockEntity.time = 0;
                        blockEntity.getItems().get(1).shrink(1);
                        blockEntity.getItems().get(2).shrink(1);
                        blockEntity.getItems().set(0, new ItemStack(MaterialItems.SOLID_SUN.get(), has + 1));
                    }
                }else{
                    blockEntity.time++;
                }
            }
        });}

    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(state.hasBlockEntity()){
            if(!level.isClientSide) player.openMenu(state.getMenuProvider(level, pos));
            var data = player.getData(ModAttachments.PLAYER_STORAGE);
            data.x = pos.getX();
            data.y = pos.getY();
            data.z = pos.getZ();
            return ItemInteractionResult.SUCCESS;
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    public static final class SunCreaterBlockEntity extends BaseContainerBlockEntity {
        public static int MAX_COUNT = 64;
        public int interval = 20 * 60;
        public int time = 0;
        private final ContainerData dataAccess;
        private NonNullList<ItemStack> items;

        public SunCreaterBlockEntity(BlockEntityType<SunCreaterBlockEntity> type, BlockPos pos, BlockState state) {
            super(type, pos, state);
            this.items = NonNullList.withSize(3, ItemStack.EMPTY);
            this.dataAccess = new ContainerData() {
                public int get(int id) {
                    return switch (id) {
                        case 0 -> time;
                        case 1 -> interval;
                        default -> 0;
                    };
                }
                public void set(int id, int value) {
                    switch (id) {
                        case 0 -> time = value;
                        case 1 -> interval = value;
                    }

                }
                public int getCount() {
                    return 2;
                }
            };

        }

        public SunCreaterBlockEntity(BlockPos pos, BlockState state) {
            this(ModBlocks.SUN_CREATOR_BLOCK_ENTITY.get(), pos, state);
        }

        @Override
        public Packet<ClientGamePacketListener> getUpdatePacket() {
            return ClientboundBlockEntityDataPacket.create(this);
        }

        @Override
        public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
            CompoundTag tag = pkt.getTag();
            time = tag.getInt("time");
            this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
            ContainerHelper.loadAllItems(tag, this.items, lookupProvider);
        }

        @Override
        public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
            CompoundTag tag = super.getUpdateTag(registries);
            tag.putInt("time", time);
            ContainerHelper.saveAllItems(tag, this.items, registries);
            return tag;
        }

        protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
            super.loadAdditional(tag, registries);
            time = tag.getInt("time");
            this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
            ContainerHelper.loadAllItems(tag, this.items, registries);
        }

        protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
            super.saveAdditional(tag, registries);
            tag.putInt("time", time);
            ContainerHelper.saveAllItems(tag, this.items, registries);
        }

        @Override
        protected Component getDefaultName() {
            return CONTAINER_TITLE;
        }

        @Override
        protected NonNullList<ItemStack> getItems() {
            return items;
        }

        @Override
        protected void setItems(NonNullList<ItemStack> nonNullList) {
             items = nonNullList;
        }
        @Override
        public void setItem(int index, ItemStack stack) {
            ItemStack itemstack = getItem(index);
            boolean flag = !stack.isEmpty() && ItemStack.isSameItemSameComponents(itemstack, stack);
            getItems().set(index, stack);
            stack.limitSize(getMaxStackSize(stack));
            if (index < 3 && !flag) {
                setChanged();
            }

        }
        @Override
        protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
            return new SunCreatorMenu(id, inventory, this,this.dataAccess);
        }

        @Override
        public int getContainerSize() {
            return 3;
        }
    }


    @Override
    protected RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new SunCreaterBlockEntity(pPos, pState);
    }

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
    public BlockState updateShape(BlockState pState, @NotNull Direction pDirection, @NotNull BlockState pNeighborState, @NotNull LevelAccessor pLevel, @NotNull BlockPos pPos, @NotNull BlockPos pNeighborPos) {
        if (pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }
        return pState;
    }

    public FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : Fluids.EMPTY.defaultFluidState();
    }
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return Shapes.or(box(0, 0, 0, 16, 13, 16));
    }

}
