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
        addAttributeModifier(Attributes.MOVEMENT_SPEED, "956e92ab-eab9-408d-88e3-1bb2b5f2e87f",-0.5, AttributeModifier.Operation.ADDITION);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int pAmplifier) {
        super.applyEffectTick(entity,pAmplifier);
    }
}
