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

                .addLootTable(ModChestLoot.daveChest.location())
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