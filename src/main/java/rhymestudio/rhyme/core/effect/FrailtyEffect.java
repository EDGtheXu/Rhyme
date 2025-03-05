package rhymestudio.rhyme.core.effect;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import rhymestudio.rhyme.Rhyme;

public class FrailtyEffect extends MobEffect {

    public final static float HURT_DAMAGE_MULTIPLIER_EACH_AMPLIFIER = 0.025f;
    private final static float ATTACK_DAMAGE_MULTIPLIER_EACH_AMPLIFIER = 0.025f;
    private final static float ATTACK_SPEED_MULTIPLIER_EACH_AMPLIFIER = 0.025f;
    private final static float MOVE_SPEED_MULTIPLIER_EACH_AMPLIFIER = 0.025f;

    public FrailtyEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onEffectStarted(LivingEntity livingEntity, int amplifier) {
        super.onEffectStarted(livingEntity, amplifier);
//        Rhyme.LOGGER.info("FrailtyEffect.onEffectStarted " + livingEntity.getUUID() + " " + amplifier);
    }

    @Override
    public void onEffectAdded(LivingEntity livingEntity, int amplifier) {
        super.onEffectAdded(livingEntity, amplifier);
        addAttributeModifier(Attributes.ATTACK_DAMAGE, Rhyme.space("frailty"), -(1 + amplifier) * ATTACK_DAMAGE_MULTIPLIER_EACH_AMPLIFIER, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
        addAttributeModifier(Attributes.ATTACK_SPEED, Rhyme.space("frailty"), (1 + amplifier) * ATTACK_SPEED_MULTIPLIER_EACH_AMPLIFIER, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
        addAttributeModifier(Attributes.MOVEMENT_SPEED, Rhyme.space("frailty"), (1 + amplifier) * MOVE_SPEED_MULTIPLIER_EACH_AMPLIFIER, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
        //        Rhyme.LOGGER.info("FrailtyEffect.onEffectAdded " + livingEntity.getUUID() + " " + amplifier);
    }

    @Override
    public void onMobHurt(LivingEntity livingEntity, int amplifier, DamageSource damageSource, float amount) {
//        Rhyme.LOGGER.info("FrailtyEffect.onMobHurt " + livingEntity.getUUID() + " " + amplifier + " " + damageSource.getMsgId() + " " + amount);
    }
}
