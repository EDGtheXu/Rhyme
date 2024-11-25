package rhymestudio.rhyme.registry;

import com.mojang.datafixers.DSL;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.block.BaseModelBlock;
import rhymestudio.rhyme.block.SunCreaterBlock;

import java.util.function.Supplier;

import static net.minecraft.world.phys.shapes.Shapes.box;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Rhyme.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Rhyme.MODID);
    public static final DeferredRegister.Items BLOCK_ITEMS = DeferredRegister.createItems(Rhyme.MODID);



    public static final Supplier<BaseEntityBlock> SUN_CREATOR_BLOCK = register("sun_creator_block","光萃台", () -> new SunCreaterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK).noOcclusion()));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SunCreaterBlock.SunCreaterBlockEntity>> SUN_CREATOR_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("sun_creator_block_entity", () -> BlockEntityType.Builder.of(SunCreaterBlock.SunCreaterBlockEntity::new, SUN_CREATOR_BLOCK.get()).build(DSL.remainderType()));


//    public static final Supplier<Block> CONE_BLOCK = register("cone","路障", ()->new BaseModelBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD).noOcclusion()){
//        public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
//            return Shapes.or(box(3, 0, 3, 13, 1, 13),
//                    box(4, 1, 4, 12, 4, 12),
//                    box(5, 3, 5, 11, 9, 11),
//                    box(6, 9, 6, 10, 12, 10));
//        }
//    });



    public static <T extends Block>Supplier<T> register(String name,String zh,Supplier<T> blockSupplier) {
        DeferredBlock<T> block =  BLOCKS.register(name, blockSupplier);
        DeferredItem<Item> item = BLOCK_ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        Rhyme.chineseProviders.add((c)->c.add(item.get(),zh));
        return block ;
    }
}
