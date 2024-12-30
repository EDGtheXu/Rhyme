package rhymestudio.rhyme.client.model;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.NotNull;
import rhymestudio.rhyme.core.entity.ICafeMob;

public abstract class AbstractAnimModel<T extends Mob & ICafeMob> extends HierarchicalModel<T> {

    public AbstractAnimModel() {

    }

    public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        if(getHead() != null){
            getHead().yRot = netHeadYaw * 0.017453292F;
            if(isRotatingZ()) getHead().zRot = headPitch * 0.017453292F;
            else getHead().xRot = headPitch * 0.017453292F;
        }

        var cafe = entity.getCafeAnimState();
        if(cafe.curAnimDef!= null)
            this.animate(cafe.curAnimState, cafe.curAnimDef, ageInTicks, cafe.globalAnimSpeed);
    }

    public boolean isRotatingZ() {
        return false;
    }
    public abstract ModelPart getHead();

/*
    public void link(String from, String to){
        if(animStateTransformers == null) animStateTransformers = new HashSet<>();
        animStateTransformers.add(new Pair<>(from, to));
    }
    public boolean canTransform(String from, String to){
        if(!activeStateTransformers() || animStateTransformers == null)return true;
        for(Pair<String, String> pair : animStateTransformers){
            if(pair.getA().equals(from) && pair.getB().equals(to)) return true;
        }
        return false;
    }
    public boolean activeStateTransformers(){
        return false;
    }
    public void createAnimStateTransformer(){
        animStateTransformers = new HashSet<>();
    }

 */
}