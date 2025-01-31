package rhymestudio.rhyme.core.registry;

import com.mojang.datafixers.DSL;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;


import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.block.CardUpLevelBlock;
import rhymestudio.rhyme.core.block.SunCreatorBlock;


import java.util.function.Supplier;

import static rhymestudio.rhyme.Rhyme.add_zh_en;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,Rhyme.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Rhyme.MODID);
    public static final DeferredRegister<Item> BLOCK_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,Rhyme.MODID);



    public static final Supplier<BaseEntityBlock> SUN_CREATOR_BLOCK = register("sun_creator_block","光萃台", () -> new SunCreatorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).destroyTime(30).noOcclusion()));
    public static final RegistryObject<BlockEntityType<SunCreatorBlock.SunCreatorBlockEntity>> SUN_CREATOR_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("sun_creator_block_entity", () -> BlockEntityType.Builder.of(SunCreatorBlock.SunCreatorBlockEntity::new, SUN_CREATOR_BLOCK.get()).build(DSL.remainderType()));

    public static final Supplier<BaseEntityBlock> CARD_UP_LEVEL_BLOCK = register("card_up_level_block","卡片升阶台", () -> new CardUpLevelBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).destroyTime(30).noOcclusion()));
    public static final RegistryObject<BlockEntityType<CardUpLevelBlock.CardUpLevelBlockEntity>> CARD_UP_LEVEL_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("card_up_level_block_entity", () -> BlockEntityType.Builder.of(CardUpLevelBlock.CardUpLevelBlockEntity::new, CARD_UP_LEVEL_BLOCK.get()).build(DSL.remainderType()));


//    public static final Supplier<Block> CONE_BLOCK = register("cone","路障", ()->new BaseModelBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD).noOcclusion()){
//        public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
//            return Shapes.or(box(3, 0, 3, 13, 1, 13),
//                    box(4, 1, 4, 12, 4, 12),
//                    box(5, 3, 5, 11, 9, 11),
//                    box(6, 9, 6, 10, 12, 10));
//        }
//    });



    public static <T extends Block> RegistryObject<T> register(String name,String zh,Supplier<T> blockSupplier) {
        RegistryObject<T> block =  BLOCKS.register(name, blockSupplier);
        RegistryObject<Item> item = BLOCK_ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        add_zh_en(item, zh);
        return block ;
    }
}
