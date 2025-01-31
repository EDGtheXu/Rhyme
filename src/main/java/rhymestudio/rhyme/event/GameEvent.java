package rhymestudio.rhyme.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import rhymestudio.rhyme.core.recipe.DaveTrades;

import static rhymestudio.rhyme.Rhyme.MODID;


@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.GAME)
public class GameEvent {

//    static HashSet<Integer> frozenLivingEntity = new HashSet<Integer>();

    @SubscribeEvent
    public static void setUp(ServerStartedEvent event){
        DaveTrades.readTradesFromJson(event.getServer().getResourceManager());
    }

//    @SubscribeEvent
//    public static void onAttack(Pre event){
//        LivingEntity entity = event.getEntity();
//        int hashCode = entity.hashCode();
//        if (entity.hasEffect(FROZEN_EFFECT)) {
//            if (frozenLivingEntity.contains(hashCode)) {
//                entity.removeEffect(FROZEN_EFFECT);
//                entity.hurt(event.getSource(), event.getNewDamage());
//                frozenLivingEntity.remove(hashCode);
//            }
//            else {
//                frozenLivingEntity.add(hashCode);
//            }
//        }
//    }

//    @SubscribeEvent
//    public static void onAttack(LivingDamageEvent.Pre event){
//        LivingEntity entity = event.getEntity();
//        if (entity.hasEffect(FROZEN_EFFECT)) {
//            event.setNewDamage(event.getNewDamage() * 2);
//            entity.removeEffect(FROZEN_EFFECT);
//        }
//    }
}
