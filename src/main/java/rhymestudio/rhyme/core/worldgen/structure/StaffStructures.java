package rhymestudio.rhyme.core.worldgen.structure;

import net.minecraft.world.level.levelgen.structure.StructureType;
import rhymestudio.rhyme.core.registry.ModStructures;
import rhymestudio.rhyme.core.worldgen.StaffStructure;

import java.util.List;

/**
 * json结构固定写法
 */
public class StaffStructures {
    public static class DaveHouseStructure extends StaffStructure {
        public DaveHouseStructure(StructureSettings settings, List<String> path) {
            super(settings, path);
        }
        @Override
        public StructureType<?> type() {
            return ModStructures.DAVE_HOUSE.get();
        }
    }

    // 测试复用的，可以删掉
    public static class DaveHouseStructure2 extends StaffStructure {
        public DaveHouseStructure2(StructureSettings settings,  List<String> path) {
            super(settings, path);
        }
        @Override
        public StructureType<?> type() {
            return ModStructures.DAVE_HOUSE2.get();
        }
    }

}
