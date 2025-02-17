package rhymestudio.rhyme.core.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.worldgen.StaffStructure;
import rhymestudio.rhyme.core.worldgen.structure.StaffStructures;

import java.util.function.Supplier;

public final class ModStructures {
    public static final DeferredRegister<StructureType<?>> TYPES = DeferredRegister.create(BuiltInRegistries.STRUCTURE_TYPE, Rhyme.MODID);
    public static final DeferredRegister<StructurePieceType> PIECE_TYPES = DeferredRegister.create(BuiltInRegistries.STRUCTURE_PIECE, Rhyme.MODID);

    public static final Supplier<StructureType<StaffStructures.DaveHouseStructure>> DAVE_HOUSE = TYPES.register("dave_house", () -> () -> StaffStructure.getCodec(StaffStructures.DaveHouseStructure::new));
    public static final Supplier<StructurePieceType.ContextlessType> DAVE_HOUSE_PIECE = PIECE_TYPES.register("dave_house_piece", () -> StaffStructure.Piece::new);

    public static final Supplier<StructureType<StaffStructures.DaveHouseStructure2>> DAVE_HOUSE2 = TYPES.register("dave_house2", () -> () -> StaffStructure.getCodec(StaffStructures.DaveHouseStructure2::new));


    public static void register(IEventBus bus) {
        TYPES.register(bus);
        PIECE_TYPES.register(bus);
    }
}
