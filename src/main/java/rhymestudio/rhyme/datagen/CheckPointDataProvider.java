package rhymestudio.rhyme.datagen;

import com.mojang.serialization.Codec;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.checkpoint.checkpoint.CheckPoint;
import rhymestudio.rhyme.core.checkpoint.entitygroup.WeightSelectedZombie;
import rhymestudio.rhyme.core.registry.entities.Zombies;

import java.util.List;

/**
 * 生成关卡信息
 */
public class CheckPointDataProvider extends AbstractExistCodecProvider<CheckPoint> {

    public CheckPointDataProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void run() {

        gen(Rhyme.space("lvl_1"), CheckPoint.builder()

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


                .build());

    }

    @Override
    protected Codec<CheckPoint> getCodec() {
        return CheckPoint.MAP_CODEC.codec();
    }

    @Override
    public String getName() {
        return "checkpoint/simple_checkpoint";
    }
}