package rhymestudio.rhyme.core.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class BleedEffect extends MobEffect {

    public BleedEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (pLivingEntity.tickCount % 20 == 0) {
            pLivingEntity.hurt(pLivingEntity.damageSources().magic(), (pAmplifier + 1) * 1f);
        }
    }
}

