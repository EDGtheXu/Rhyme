package rhymestudio.rhyme.event;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.dataSaver.attactment.PlantRecordProvider;
import rhymestudio.rhyme.core.dataSaver.attactment.SunCountProvider;
import rhymestudio.rhyme.core.recipe.DaveTrades;

import static rhymestudio.rhyme.Rhyme.MODID;
import static rhymestudio.rhyme.core.registry.ModEffects.FROZEN_EFFECT;


@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class GameEvent {

    @SubscribeEvent
    public static void setUp(ServerStartedEvent event){
        DaveTrades.readTradesFromJson(event.getServer().getResourceManager());
    }

    @SubscribeEvent
    public static void onAttack(LivingDamageEvent event){
        LivingEntity entity = event.getEntity();
        if (entity.hasEffect(FROZEN_EFFECT.get())){
            MobEffectInstance frozenEffectInstance = entity.getEffect(FROZEN_EFFECT.get());
            if (frozenEffectInstance.getAmplifier() == 0){
                int newAmplifier = 1;
                int newDuration = frozenEffectInstance.getDuration(); // 保持原有持续时间
                MobEffectInstance newEffectInstance = new MobEffectInstance(FROZEN_EFFECT.get(), newDuration, newAmplifier);
                entity.addEffect(newEffectInstance);
            }
            else{
                event.setAmount(event.getAmount() * 2);
                entity.removeEffect(FROZEN_EFFECT.get());
            }
        }
    }

    @SubscribeEvent
    public static void attachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
//        if (event.getObject() instanceof Player player) {
        if(event.getObject() instanceof Player){
            event.addCapability(Rhyme.space("plant_record"), new PlantRecordProvider());
            event.addCapability(Rhyme.space("sun_count"), new SunCountProvider());
        }
//        }
    }
}
