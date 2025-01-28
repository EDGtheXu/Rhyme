package rhymestudio.rhyme.core.entity.proj;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import rhymestudio.rhyme.core.entity.BaseProj;

public class LineProj extends BaseProj {
    private int existTick;

    @Override
    public int waveDur() {
        return existTick;
    }

    public LineProj(EntityType<? extends LineProj> pEntityType, Level pLevel,ResourceLocation texture) {
        this(pEntityType,pLevel,texture,null);
    }
    public LineProj(EntityType<? extends LineProj> pEntityType, Level pLevel,ResourceLocation texture, MobEffectInstance pEffect) {
        super(pEntityType,pLevel,pEffect);
        this.texture = texture;
        this.existTick = 20 * 5;
    }

    public LineProj setExistTick(int existTick) {
        this.existTick = existTick;
        return this;
    }


    @Override
    public void tick(){
        super.tick();
    }
}
