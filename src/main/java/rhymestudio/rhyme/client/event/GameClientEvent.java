package rhymestudio.rhyme.client.event;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import rhymestudio.rhyme.client.animate.ExpertColorAnimation;
import rhymestudio.rhyme.client.animate.MasterColorAnimation;
import rhymestudio.rhyme.client.render.gui.hud.CardHUD;

import static rhymestudio.rhyme.Rhyme.MODID;


@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.GAME,value = Dist.CLIENT)
public class GameClientEvent {

    @SubscribeEvent
    public static void RenderGuiEvent(RenderGuiEvent.Post event) {
        CardHUD hud = CardHUD.getInstance();
        hud.render(event.getGuiGraphics());

    }
    @SubscribeEvent
    public static void onTick(ClientTickEvent.Pre event) {
        MasterColorAnimation.INSTANCE.updateColor();
        ExpertColorAnimation.INSTANCE.updateColor();

    }

    @SubscribeEvent
    public static void renderLevelStage(RenderLevelStageEvent event) {
//        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_LEVEL) {
//            //PostUtil.postProcess();
//
//        }
    }
    @SubscribeEvent
    public static void onTickEnd(ClientTickEvent.Pre event) {
        //PostUtil.clear();
    }



}
