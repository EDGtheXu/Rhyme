package rhymestudio.rhyme.core.registry;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.entity.AbstractMonster;
import rhymestudio.rhyme.core.registry.entities.MiscEntities;
import rhymestudio.rhyme.core.registry.entities.Zombies;
import rhymestudio.rhyme.core.registry.entities.PlantEntities;

import static net.minecraft.world.entity.Mob.checkMobSpawnRules;
import static rhymestudio.rhyme.Rhyme.MODID;

@Mod.EventBusSubscriber(modid = Rhyme.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
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
        event.put(PlantEntities.SUN_FLOWER.get(), genericPlant.build());
        event.put(PlantEntities.PEA.get(), genericPlant.build());
        event.put(PlantEntities.SNOW_PEA.get(), genericPlant.build());
        event.put(PlantEntities.REPEATER.get(), genericPlant.build());
        event.put(PlantEntities.PUFF_SHROOM.get(), genericPlant.build());
        event.put(PlantEntities.SUN_SHROOM.get(), genericPlant.build());
        event.put(PlantEntities.WALL_NUT.get(), genericPlant.build());
        event.put(PlantEntities.CABBAGE_PULT.get(), genericPlant.build());
        event.put(PlantEntities.CHOMPER.get(), genericPlant.build());


        event.put(PlantEntities.POTATO_MINE.get(), genericPlant.build());

        //僵尸
        event.put(Zombies.NORMAL_ZOMBIE.get(), AbstractMonster.createAttributes().build());
        event.put(Zombies.CONE_ZOMBIE.get(), AbstractMonster.createAttributes().build());
        event.put(Zombies.IRON_BUCKET_ZOMBIE.get(), AbstractMonster.createAttributes().build());

        //疯狂戴夫
        event.put(PlantEntities.CRAZY_DAVE.get(), AbstractMonster.createAttributes().add(Attributes.MOVEMENT_SPEED, 0.5f).build());

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
    public static void spawnPlacementRegister(SpawnPlacementRegisterEvent event) {
        event.register(Zombies.NORMAL_ZOMBIE.get(),  SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModEntities::checkBloodCrawlerSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(Zombies.CONE_ZOMBIE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModEntities::checkBloodCrawlerSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(Zombies.IRON_BUCKET_ZOMBIE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModEntities::checkBloodCrawlerSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
        event.register(PlantEntities.CRAZY_DAVE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ModEntities::checkBloodCrawlerSpawn, SpawnPlacementRegisterEvent.Operation.REPLACE);
    }

    public static void registerEntities(IEventBus modEventBus) {
        PlantEntities.ENTITIES.register(modEventBus);
        Zombies.ZOMBIES.register(modEventBus);
        MiscEntities.ENTITIES.register(modEventBus);
    }

    public static String Key(String key){
        return MODID + ":entity." + key;
    }
}
