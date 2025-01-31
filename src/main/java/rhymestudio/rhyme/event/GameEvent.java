package rhymestudio.rhyme.event;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import rhymestudio.rhyme.core.recipe.DaveTrades;

import static rhymestudio.rhyme.Rhyme.MODID;
import static rhymestudio.rhyme.core.registry.ModEffects.FROZEN_EFFECT;


@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.GAME)
public class GameEvent {

    @SubscribeEvent
    public static void setUp(ServerStartedEvent event){
        DaveTrades.readTradesFromJson(event.getServer().getResourceManager());
    }

    @SubscribeEvent
    public static void onAttack(LivingDamageEvent.Pre event){
        LivingEntity entity = event.getEntity();
        if (entity.hasEffect(FROZEN_EFFECT)){
            MobEffectInstance frozenEffectInstance = entity.getEffect(FROZEN_EFFECT);
            if (frozenEffectInstance.getAmplifier() == 0){
                int newAmplifier = 1;
                int newDuration = frozenEffectInstance.getDuration(); // 保持原有持续时间
                MobEffectInstance newEffectInstance = new MobEffectInstance(FROZEN_EFFECT, newDuration, newAmplifier);
                entity.addEffect(newEffectInstance);
            }
            else{
                event.setNewDamage(event.getNewDamage() * 2);
                entity.removeEffect(FROZEN_EFFECT);
            }
        }
    }
}
