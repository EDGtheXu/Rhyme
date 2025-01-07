package rhymestudio.rhyme.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import rhymestudio.rhyme.core.recipe.DaveTrades;

import static rhymestudio.rhyme.Rhyme.MODID;


@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.GAME)
public class GameEvent {

    @SubscribeEvent
    public static void setUp(ServerStartedEvent event){
        DaveTrades.readTradesFromJson(event.getServer().getResourceManager());
    }
}
