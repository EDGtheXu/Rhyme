package rhymestudio.rhyme.client.event;

import net.minecraft.client.Minecraft;
import net.minecraft.sounds.Music;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.SelectMusicEvent;
import rhymestudio.rhyme.config.ClientConfig;
import rhymestudio.rhyme.client.animate.ExpertColorAnimation;
import rhymestudio.rhyme.client.animate.MasterColorAnimation;
import rhymestudio.rhyme.client.render.gui.hud.CardHUD;
import rhymestudio.rhyme.core.registry.ModSounds;

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

    public static Music BGM_MUSIC = new Music(ModSounds.BGM,300,3000,true);
    @SubscribeEvent
    public static void onSelectMusic(SelectMusicEvent event) {
        if(Minecraft.getInstance().level==null) return;

        if(event.getPlayingMusic() == null && ClientConfig.IsOpenBgm.get()){
            event.setMusic(BGM_MUSIC);
//            Minecraft.getInstance().getMusicManager().
        }
        if(Minecraft.getInstance().getMusicManager().isPlayingMusic(BGM_MUSIC) && !ClientConfig.IsOpenBgm.get()){
            event.setMusic(null);
        }

    }

}
