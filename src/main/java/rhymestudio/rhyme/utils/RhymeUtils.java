package rhymestudio.rhyme.utils;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.config.ServerConfig;

public class RhymeUtils {
    public static void attributesBalance(LivingEntity entity, boolean dirty) {
        if(!entity.level().isClientSide) {
            if (dirty) {
                entity.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(new AttributeModifier("97964651-72f8-48ad-a258-3c398aa4d468", ServerConfig.PLANT_ATTRIBUTES_MULTIPLIER_HEALTH.get() - 1, AttributeModifier.Operation.MULTIPLY_BASE));
                entity.setHealth(entity.getMaxHealth());
            }
            entity.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(new AttributeModifier("afd09b44-235c-4dcd-b3db-04db60d3ca9d", ServerConfig.PLANT_ATTRIBUTES_MULTIPLIER_DAMAGE.get() - 1, AttributeModifier.Operation.MULTIPLY_BASE));
        }
    }
}
