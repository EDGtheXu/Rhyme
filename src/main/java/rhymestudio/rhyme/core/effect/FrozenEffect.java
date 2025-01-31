package rhymestudio.rhyme.core.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import rhymestudio.rhyme.Rhyme;

public class FrozenEffect extends MobEffect {

//    private int count = -1;

    public FrozenEffect(MobEffectCategory category, int color) {
        super(category, color);
        addAttributeModifier(Attributes.MOVEMENT_SPEED, Rhyme.space("frozen"),-1.0, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
    }

//    @Override
//    public void onEffectStarted(LivingEntity livingEntity, int amplifier) {
//        addAttributeModifier(Attributes.MOVEMENT_SPEED, Rhyme.space("frozen"),-1.0, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
//        count++;
//        System.out.println("onEffectStarted");
//    }

//    @Override
//    public void onEffectAdded(LivingEntity livingEntity, int amplifier) {
//        count = 0;
//        System.out.println(livingEntity.getId() + ", " + "onEffectAdded: " + livingEntity.hasEffect(FROZEN_EFFECT) + "\tcount: " + count);
//    }
//
//    @Override
//    public void onMobHurt(LivingEntity livingEntity, int amplifier, DamageSource damageSource, float amount) {
//        System.out.println("onMobHurt");
//        if (count >= 2) {
//            livingEntity.removeEffect(FROZEN_EFFECT);
//            livingEntity.hurt(damageSource, amount);
//            System.out.println(livingEntity.getId() + ", " + "double amount: " + livingEntity.hasEffect(FROZEN_EFFECT) + "\tcount: " + count);
//        }
//    }


}
