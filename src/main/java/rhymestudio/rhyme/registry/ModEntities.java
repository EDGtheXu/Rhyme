package rhymestudio.rhyme.registry;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.entity.AbstractMonster;
import rhymestudio.rhyme.registry.Entities.PlantEntities;
import rhymestudio.rhyme.registry.Entities.Zombies;

import static net.minecraft.world.entity.Mob.checkMobSpawnRules;
import static rhymestudio.rhyme.registry.Entities.PlantEntities.*;

@EventBusSubscriber(modid = Rhyme.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEntities {



    //tip 注册属性
    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {

        AttributeSupplier.Builder genericPlant = Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH)
                .add(Attributes.ATTACK_DAMAGE)
                .add(Attributes.FOLLOW_RANGE, 16)
                .add(Attributes.MOVEMENT_SPEED, 0f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1)

                ;


        //植物
        event.put(SUN_FLOWER.get(), genericPlant.build());
        event.put(PEA.get(), genericPlant.build());
        event.put(ICE_PEA.get(), genericPlant.build());
        event.put(DOUBLE_PEA.get(), genericPlant.build());
        event.put(PUFF_SHROOM.get(), genericPlant.build());
        event.put(WALL_NUT.get(), genericPlant.build());
        event.put(CABBAGE_PULT.get(), genericPlant.build());


        event.put(PlantEntities.POTATO_MINE.get(), genericPlant.build());

        //僵尸
        event.put(Zombies.NORMAL_ZOMBIE.get(), AbstractMonster.createAttributes().build());

    }

    public static boolean checkBloodCrawlerSpawn(EntityType<? extends Mob> type, LevelAccessor pLevel, MobSpawnType pSpawnType, BlockPos pPos, RandomSource pRandom) {
        if (!(pLevel instanceof Level level)) {
            return false; // 如果 pLevel 不是 Level 的实例，返回 false
        }

        if (!checkMobSpawnRules(type, pLevel, pSpawnType, pPos, pRandom)) {
            return false; // 如果不满足基本生成规则，返回 false
        }

        int y = pPos.getY();
        if (y >= 260) {
            return false; // 不能生成在 y = 260 或更高的位置
        }

        return true;
    }
    //tip 生成位置
    @SubscribeEvent
    public static void spawnPlacementRegister(RegisterSpawnPlacementsEvent event) {
        event.register(Zombies.NORMAL_ZOMBIE.get(),  SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModEntities::checkBloodCrawlerSpawn, RegisterSpawnPlacementsEvent.Operation.OR);

    }

    public static void registerEntities(IEventBus modEventBus) {
        PlantEntities.ENTITIES.register(modEventBus);
        Zombies.ZOMBIES.register(modEventBus);
    }
}
