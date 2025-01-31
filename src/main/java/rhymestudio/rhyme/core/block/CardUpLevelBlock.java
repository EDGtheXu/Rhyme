package rhymestudio.rhyme.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rhymestudio.rhyme.core.menu.CardUpLevelMenu;
import rhymestudio.rhyme.core.registry.ModBlocks;

public class CardUpLevelBlock extends BaseEntityBlock {

    private static final Component CONTAINER_TITLE = Component.translatable("container.rhyme.card_up_level");

    public CardUpLevelBlock(Properties properties) {
        super(properties);
    }

    @Override
    public RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }


    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand pHand, BlockHitResult pHit) {
        if(state.hasBlockEntity()){
            if(!level.isClientSide) player.openMenu(state.getMenuProvider(level, pos));
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public @Nullable MenuProvider getMenuProvider(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos) {
        return new SimpleMenuProvider((pContainerId, pPlayerInventory, pPlayer) -> new CardUpLevelMenu(pContainerId, pPlayerInventory, ContainerLevelAccess.create(pLevel, pPos)), CONTAINER_TITLE);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new CardUpLevelBlockEntity(blockPos, blockState);
    }

    public static final class  CardUpLevelBlockEntity extends BlockEntity {
        public CardUpLevelBlockEntity(BlockPos pos, BlockState blockState) {
            super(ModBlocks.CARD_UP_LEVEL_BLOCK_ENTITY.get(), pos, blockState);
        }


    }
}
