package rhymestudio.rhyme.core.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

import static net.minecraft.SharedConstants.TICKS_PER_SECOND;

public class SeverePoisonEffect extends MobEffect {

    public SeverePoisonEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    @Override
    public boolean applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (pLivingEntity.tickCount % TICKS_PER_SECOND == 0) {
            pLivingEntity.hurt(pLivingEntity.damageSources().magic(), (pAmplifier + 1) * 1f);
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
