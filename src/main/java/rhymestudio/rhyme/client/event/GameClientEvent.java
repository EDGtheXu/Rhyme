package rhymestudio.rhyme.client.event;

import net.minecraft.sounds.Music;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import rhymestudio.rhyme.client.render.buffer.DebugEntityHelper;
import rhymestudio.rhyme.client.animate.ExpertColorAnimation;
import rhymestudio.rhyme.client.animate.MasterColorAnimation;
import rhymestudio.rhyme.client.render.gui.hud.CardHUD;
import rhymestudio.rhyme.core.registry.ModSounds;

import java.util.function.Supplier;

import static rhymestudio.rhyme.Rhyme.MODID;


@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class GameClientEvent {

    @SubscribeEvent
    public static void RenderGuiEvent(RenderGuiEvent.Post event) {
        CardHUD hud = CardHUD.getInstance();
        hud.render(event.getGuiGraphics());


    }
    @SubscribeEvent
    public static void onTick(TickEvent.ClientTickEvent event) {
        MasterColorAnimation.INSTANCE.updateColor();
        ExpertColorAnimation.INSTANCE.updateColor();

    }

    @SubscribeEvent
    public static void renderLevelStage(RenderLevelStageEvent event) {
//        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_LEVEL) {
//            //PostUtil.postProcess();
//
//        }
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_ENTITIES) {
            DebugEntityHelper.Singleton().render(event);
        }
    }

    public static Supplier<Music> BGM_MUSIC = ()->new Music(ModSounds.BGM.getHolder().get(),300,3000,true);

    // todo
//    @SubscribeEvent
//    public static void onSelectMusic(SelectMusicEvent event) {
//        if(Minecraft.getInstance().level==null) return;
//
//        if(event.getPlayingMusic() == null && ClientConfig.IsOpenBgm.get()){
//            event.setMusic(BGM_MUSIC);
////            Minecraft.getInstance().getMusicManager().
//        }
//        if(Minecraft.getInstance().getMusicManager().isPlayingMusic(BGM_MUSIC) && !ClientConfig.IsOpenBgm.get()){
//            event.setMusic(null);
//        }
//
//    }

}
