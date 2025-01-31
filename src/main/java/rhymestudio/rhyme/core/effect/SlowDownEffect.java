package rhymestudio.rhyme.core.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import rhymestudio.rhyme.Rhyme;

public class SlowDownEffect extends MobEffect {

    public SlowDownEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
        addAttributeModifier(Attributes.MOVEMENT_SPEED, Rhyme.space("slowdown"),-0.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int pAmplifier) {
        super.applyEffectTick(entity,pAmplifier);
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int p_295368_, int p_294232_){
        return true;
    }

}
