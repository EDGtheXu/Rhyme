package rhymestudio.rhyme.datagen;

import com.mojang.serialization.Codec;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.checkpoint.checkpoint.CheckPoint;
import rhymestudio.rhyme.core.checkpoint.checkpoint.ICheckPoint;
import rhymestudio.rhyme.core.checkpoint.entitygroup.WeightSelectedZombie;
import rhymestudio.rhyme.core.registry.entities.Zombies;
import rhymestudio.rhyme.datagen.loot.ModChestLoot;

import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 生成关卡信息
 */
public class CheckPointDataProvider extends AbstractExistCodecProvider<ICheckPoint<?>> {

    public CheckPointDataProvider(PackOutput output) {
        super(output);
    }

    public static ResourceLocation L1_1 = Rhyme.space("simple_checkpoint/lvl_1_1");
    public static ResourceLocation L1_2 = Rhyme.space("simple_checkpoint/lvl_1_2");
    public static ResourceLocation L1_3 = Rhyme.space("simple_checkpoint/lvl_1_3");
    public static ResourceLocation L1_4 = Rhyme.space("simple_checkpoint/lvl_1_4");
    public static ResourceLocation L1_5 = Rhyme.space("simple_checkpoint/lvl_1_5");
    public static ResourceLocation L1_6 = Rhyme.space("simple_checkpoint/lvl_1_6");
    public static ResourceLocation L1_7 = Rhyme.space("simple_checkpoint/lvl_1_7");
    public static ResourceLocation L1_8 = Rhyme.space("simple_checkpoint/lvl_1_8");
    public static ResourceLocation L1_9 = Rhyme.space("simple_checkpoint/lvl_1_9");
    public static ResourceLocation L1_10 = Rhyme.space("simple_checkpoint/lvl_1_10");

    @Override
    protected void run() {

        gen(L1_1, 1,  check -> check
                .addWave(false)
                .addZombie(20, EntityType.ZOMBIE, 1)
                .addZombieList(60, List.of(
                        new WeightSelectedZombie.tuple(Zombies.NORMAL_ZOMBIE.get(), 1),
                        new WeightSelectedZombie.tuple(Zombies.CONE_ZOMBIE.get(), 5)
                ), 1)
                .addZombie(100, Zombies.CONE_ZOMBIE.get(), 1)
                .buildWave()

                .addWave(false)
                .addZombie(20, EntityType.ZOMBIE, 1)
                .addZombie(60, Zombies.NORMAL_ZOMBIE.get(), 1)
                .addZombie(100, Zombies.CONE_ZOMBIE.get(), 1)
                .buildWave()

                .addLootTable(ModChestLoot.checkpoint_loot_lvl_1_1.location())
                .build());

        gen(L1_2, 2,  check -> check
                .addWave(false)
                .addZombie(20, EntityType.ZOMBIE, 1)
                .addZombieList(60, List.of(
                        new WeightSelectedZombie.tuple(Zombies.NORMAL_ZOMBIE.get(), 2),
                        new WeightSelectedZombie.tuple(Zombies.CONE_ZOMBIE.get(), 2)
                ), 1)
                .addZombie(100, Zombies.CONE_ZOMBIE.get(), 2)
                .buildWave()

                .addWave(false)
                .addZombie(20, EntityType.ZOMBIE, 2)
                .addZombie(60, Zombies.NORMAL_ZOMBIE.get(), 2)
                .addZombie(200, Zombies.IRON_BUCKET_ZOMBIE.get(), 1)
                .buildWave()

                .addLootTable(ModChestLoot.checkpoint_loot_lvl_1_2.location())
                .build());

        gen(L1_3, 3,  check -> check
                .addWave(false)
                .addZombie(20, EntityType.ZOMBIE, 1)
                .addZombieList(60, List.of(
                        new WeightSelectedZombie.tuple(Zombies.NORMAL_ZOMBIE.get(), 2),
                        new WeightSelectedZombie.tuple(Zombies.CONE_ZOMBIE.get(), 2)
                ), 1)
                .addZombieList(70, List.of(
                        new WeightSelectedZombie.tuple(Zombies.NORMAL_ZOMBIE.get(), 2),
                        new WeightSelectedZombie.tuple(Zombies.CONE_ZOMBIE.get(), 2)
                ), 1)
                .addZombie(100, Zombies.CONE_ZOMBIE.get(), 2)
                .buildWave()

                .addWave(false)
                .addZombie(20, EntityType.ZOMBIE, 1)
                .addZombie(60, Zombies.NORMAL_ZOMBIE.get(), 2)
                .addZombie(100, Zombies.CONE_ZOMBIE.get(), 2)
                .buildWave()

                .addWave(false)
                .addZombie(20, EntityType.ZOMBIE, 2)
                .addZombie(100, Zombies.IRON_BUCKET_ZOMBIE.get(), 1)
                .addZombie(150, Zombies.IRON_BUCKET_ZOMBIE.get(), 2)
                .addZombie(210, Zombies.POLE_VAULTING_ZOMBIE.get(), 1)
                .addZombie(220, Zombies.IRON_BUCKET_ZOMBIE.get(), 1)

                .buildWave()

                .addLootTable(ModChestLoot.checkpoint_loot_lvl_1_3.location())
                .build());

    }

    protected void gen(ResourceLocation location, CheckPoint checkPoint){
        super.gen(location, checkPoint);
        String name = checkPoint.getTranslatedName();

        Pattern pattern = Pattern.compile("lvl_(\\d+_\\d+)");
        Matcher matcher = pattern.matcher(name);
        if (matcher.find()){
            Rhyme.add_zh_en(name,"关卡 " + matcher.group(1).replace("_","-"));
        }
        else{
            Rhyme.add_zh_en(name,"关卡 "+ name);
        }

    }

    /**
     * 生成关卡信息
     * @param location 资源位置，关卡名称
     * @param index 关卡索引
     */
    protected void gen(ResourceLocation location, int index,  Function<CheckPoint.Builder, CheckPoint> function){
        gen(location, function.apply(CheckPoint.builder(location, index)));
    }


    @Override
    protected Codec<ICheckPoint<?>> getCodec() {
        return ICheckPoint.TYPED_CODEC;
    }

    @Override
    public String getName() {
        return "checkpoint";
    }
}