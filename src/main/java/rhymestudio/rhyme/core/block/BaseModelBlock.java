package rhymestudio.rhyme.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class BaseModelBlock extends Block  {

    public BaseModelBlock(Properties properties) {super(properties);}

    @Override
    public RenderShape getRenderShape(BlockState state) {return RenderShape.MODEL;}

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {return super.getShape(pState, pLevel, pPos, pContext);}
}
