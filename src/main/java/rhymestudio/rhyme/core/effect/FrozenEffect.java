package rhymestudio.rhyme.core.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.UUID;

public class FrozenEffect extends MobEffect {

    public FrozenEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
        addAttributeModifier(Attributes.MOVEMENT_SPEED, UUID.randomUUID().toString() ,-0.5, AttributeModifier.Operation.MULTIPLY_BASE);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int pAmplifier) {
        super.applyEffectTick(entity,pAmplifier);
    }

//    @Override
//    public boolean shouldApplyEffectTickThisTick(int p_295368_, int p_294232_){
//        this.shouldApplyEffectTickThisTick()
//        return true;
//    }

}
