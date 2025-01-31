package rhymestudio.rhyme.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import rhymestudio.rhyme.core.registry.ModRecipes;
import rhymestudio.rhyme.core.registry.items.MaterialItems;

import static net.minecraft.world.level.block.BarrelBlock.FACING;
import static net.minecraft.world.level.block.state.properties.BlockStateProperties.WATERLOGGED;

public class SunCreatorBlock extends BaseEntityBlock  {

    private static final Component CONTAINER_TITLE = Component.translatable("container.rhyme.sun_creator");

    public SunCreatorBlock(Properties properties) {
        super(properties);
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
            if(!level.isNight() && !blockEntity.items.get(1).isEmpty() &&
                ! blockEntity.items.get(2).isEmpty()
            ) {
                var validRecipes = level.getRecipeManager().getRecipeFor(ModRecipes.SUN_CREATOR_SEC_TYPE.get(), new SimpleContainer(blockEntity.items.get(1), blockEntity.items.get(2)), level);

                int t = blockEntity.time;
                if(validRecipes.isPresent()){
                    if( t >= blockEntity.interval){
                        ItemStack it = blockEntity.items.get(0);
                        int has = it.getCount();
                        var recipe = validRecipes.get();
                        var res = recipe.getResultItem(null);
                        if(has < 64 &&( it.getItem() == res.getItem() || it.isEmpty())){
                            blockEntity.time = 0;
                            blockEntity.items.get(1).shrink(1);
                            blockEntity.items.get(2).shrink(1);
                            blockEntity.items.set(0, new ItemStack(MaterialItems.SOLID_SUN.get(), has + res.getCount()));
                        }
                    }else{
                        blockEntity.time++;
                    }
                }
            }
        });}

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand pHand, BlockHitResult pHit) {
        if(state.hasBlockEntity()){
            if(!level.isClientSide) player.openMenu(state.getMenuProvider(level, pos));
            var data = player.getCapability(ModAttachments.PLAYER_STORAGE);
            data.ifPresent(d->{
                d.x = pos.getX();
                d.y = pos.getY();
                d.z = pos.getZ();
            });

            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    public static final class SunCreatorBlockEntity extends BaseContainerBlockEntity {
        public int interval = 100 ;
        public int time = 0;
        private final ContainerData dataAccess;
        private NonNullList<ItemStack> items;

        public SunCreatorBlockEntity(BlockEntityType<SunCreatorBlockEntity> type, BlockPos pos, BlockState state) {
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

        public SunCreatorBlockEntity(BlockPos pos, BlockState state) {
            this(ModBlocks.SUN_CREATOR_BLOCK_ENTITY.get(), pos, state);
        }

        @Override
        public Packet<ClientGamePacketListener> getUpdatePacket() {
            return ClientboundBlockEntityDataPacket.create(this);
        }

        @Override
        public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
            CompoundTag tag = pkt.getTag();
            time = tag.getInt("time");
            this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
            ContainerHelper.loadAllItems(tag, this.items);
        }

        @Override
        public CompoundTag getUpdateTag() {
            CompoundTag tag = super.getUpdateTag();
            tag.putInt("time", time);
            ContainerHelper.saveAllItems(tag, this.items);
            return tag;
        }

        @Override
        public void load(CompoundTag tag) {
            super.load(tag);
            time = tag.getInt("time");
            this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
            ContainerHelper.loadAllItems(tag, this.items);
        }

        protected void saveAdditional(CompoundTag tag) {
            super.saveAdditional(tag);
            tag.putInt("time", time);
            ContainerHelper.saveAllItems(tag, this.items);
        }

        @Override
        protected Component getDefaultName() {
            return CONTAINER_TITLE;
        }

        @Override
        public void setItem(int index, ItemStack stack) {
            ItemStack itemstack = getItem(index);
            boolean flag = !stack.isEmpty() && ItemStack.isSameItemSameTags(itemstack, stack);
            items.set(index, stack);
//            stack.limitSize(getMaxStackSize(stack));
            if (index < 3 && !flag) {
                setChanged();
            }

        }

        @Override
        public boolean stillValid(Player player) {
            return player.distanceToSqr(this.getBlockPos().getX() + 0.5D, this.getBlockPos().getY() + 0.5D, this.getBlockPos().getZ() + 0.5D) < 64.0;
        }

        @Override
        protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
            return new SunCreatorMenu(id, inventory, this,this.dataAccess);
        }

        @Override
        public int getContainerSize() {
            return 3;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public ItemStack getItem(int i) {
            return this.items.get(i);
        }

        public ItemStack removeItem(int pIndex, int pCount) {
            ItemStack $$2 = ContainerHelper.removeItem(this.items, pIndex, pCount);
            if (!$$2.isEmpty()) {
                this.setChanged();
            }

            return $$2;
        }

        public ItemStack removeItemNoUpdate(int pIndex) {
            ItemStack $$1 = this.items.get(pIndex);
            if ($$1.isEmpty()) {
                return ItemStack.EMPTY;
            } else {
                this.items.set(pIndex, ItemStack.EMPTY);
                return $$1;
            }
        }

        public void clearContent() {
            this.items.clear();
            this.setChanged();
        }
    }


    @Override
    public RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new SunCreatorBlockEntity(pPos, pState);
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
