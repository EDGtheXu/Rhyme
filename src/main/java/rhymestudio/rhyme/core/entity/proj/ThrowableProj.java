package rhymestudio.rhyme.core.entity.proj;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.entity.BaseProj;
import rhymestudio.rhyme.core.entity.anim.curve.Bezier2Curse;
import rhymestudio.rhyme.core.entity.anim.curve.Curve;

public class ThrowableProj extends BaseProj {
    private float step = 0f;
    private Vec3 heightPos;
    private Vec3 targetPos;
    private Curve curse;

    public ThrowableProj(EntityType<? extends ThrowableProj> pEntityType, Level pLevel, ResourceLocation texture) {
        this(pEntityType,pLevel,texture,null);
        this.texture = texture;
    }

    public ThrowableProj(EntityType<? extends ThrowableProj> pEntityType, Level pLevel, ResourceLocation texture, MobEffectInstance pEffect) {
        super(pEntityType,pLevel, pEffect);
        this.texture = texture;
    }

    public ThrowableProj setTargetPos(Vec3 targetPos) {
        this.targetPos = targetPos;
        return this;
    }

    public ThrowableProj setEffect(MobEffectInstance effect) {
        this.effect = effect;
        return this;
    }

    public Vec3 getNexPos(){
        step += 0.05f;
        return curse.cal(step);
    }

    @Override
    public int waveDur() {return 100;}

    public static final EntityDataAccessor<Vector3f> DATA_HEIGHT = SynchedEntityData.defineId(ThrowableProj.class, EntityDataSerializers.VECTOR3);
    public static final EntityDataAccessor<Vector3f> DATA_TARGET = SynchedEntityData.defineId(ThrowableProj.class, EntityDataSerializers.VECTOR3);

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_HEIGHT, new Vector3f(0,0,0));
        builder.define(DATA_TARGET, new Vector3f(0,0,0));

    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> var1) {
        super.onSyncedDataUpdated(var1);
        if (var1 == DATA_HEIGHT) {
            heightPos = new Vec3(this.entityData.get(DATA_HEIGHT));
        }
        if (var1 == DATA_TARGET) {
            targetPos = new Vec3(this.entityData.get(DATA_TARGET));
        }
    }

    @Override
    public void onAddedToLevel(){
        super.onAddedToLevel();
        if(!level().isClientSide) {
            if(targetPos==null) {
                discard();
                return;
            }
            this.heightPos = position().add(targetPos).multiply(0.5f,0.5f,0.5f).add(0,10f,0);
            this.entityData.set(DATA_HEIGHT, new Vector3f((float)heightPos.x,(float)heightPos.y,(float)heightPos.z));
            this.entityData.set(DATA_TARGET, new Vector3f((float)targetPos.x,(float)targetPos.y,(float)targetPos.z));
        }
    }

    @Override
    public void tick(){
        super.tick();

        if(curse==null) {
            if(targetPos==null || heightPos==null) return;

            curse = new Bezier2Curse(position(),heightPos,targetPos);
        }
        this.setPos(getNexPos());
    }

    public static class TextureLib{
        public static final ResourceLocation CABBAGE_TEXTURE = Rhyme.space("textures/entity/cabbage_pult.png");


    }
}
