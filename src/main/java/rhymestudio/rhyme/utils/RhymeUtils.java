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
                entity.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(new AttributeModifier(Rhyme.space("server_modifier_max_health"), ServerConfig.PLANT_ATTRIBUTES_MULTIPLIER_HEALTH.get() - 1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
                entity.setHealth(entity.getMaxHealth());
            }
            entity.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(new AttributeModifier(Rhyme.space("server_modifier_max_health"), ServerConfig.PLANT_ATTRIBUTES_MULTIPLIER_DAMAGE.get() - 1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }
    }
}
