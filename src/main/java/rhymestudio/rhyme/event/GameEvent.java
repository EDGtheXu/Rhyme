package rhymestudio.rhyme.event;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import rhymestudio.rhyme.core.checkpoint.CheckPointManager;
import rhymestudio.rhyme.core.effect.FrailtyEffect;
import rhymestudio.rhyme.core.entity.plants.Betelnut;
import rhymestudio.rhyme.core.recipe.DaveTrades;

import static rhymestudio.rhyme.Rhyme.MODID;
import static rhymestudio.rhyme.Rhyme.seconds2ticks;
import static rhymestudio.rhyme.core.registry.ModEffects.FRAILTY_EFFECT;
import static rhymestudio.rhyme.core.registry.ModEffects.FROZEN_EFFECT;


@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.GAME)
public class GameEvent {

    @SubscribeEvent
    public static void setUp(ServerStartedEvent event){
        DaveTrades.readTradesFromJson(event.getServer().getResourceManager());
        CheckPointManager.registerFromJson(event.getServer().getResourceManager());
    }

    @SubscribeEvent
    public static void onAttack(LivingDamageEvent.Pre event){
        LivingEntity targetEntity = event.getEntity();
        DamageSource source = event.getSource();
        Entity directEntity = source.getDirectEntity();
        Entity causingEntity = source.getEntity();

        //修改直接伤害来源
        if (directEntity instanceof LivingEntity) {
            LivingEntity directLivingEntity = (LivingEntity) directEntity;
            if (targetEntity instanceof Betelnut) { //如果目标是槟榔，上脆弱
                if (directLivingEntity.hasEffect(FRAILTY_EFFECT)) { //如果有脆弱效果
                    MobEffectInstance frailtyEffectInstance = directLivingEntity.getEffect(FRAILTY_EFFECT);
                    int newAmplifier = frailtyEffectInstance.getAmplifier() + 1;
                    int newDuration = frailtyEffectInstance.getDuration() + seconds2ticks(20); // 持续时间增加20秒
                    MobEffectInstance newEffectInstance = new MobEffectInstance(FRAILTY_EFFECT, newDuration, newAmplifier);
                    directLivingEntity.addEffect(newEffectInstance);
                } else {
                    directLivingEntity.addEffect(new MobEffectInstance(FRAILTY_EFFECT, seconds2ticks(60), 0));
                }
            }
        }

        //修改伤害目标
        //寒冰射手的冻结效果
        if (targetEntity.hasEffect(FROZEN_EFFECT)){
            MobEffectInstance frozenEffectInstance = targetEntity.getEffect(FROZEN_EFFECT);
            if (frozenEffectInstance.getAmplifier() == 0){
                int newAmplifier = 1;
                int newDuration = frozenEffectInstance.getDuration(); // 保持原有持续时间
                MobEffectInstance newEffectInstance = new MobEffectInstance(FROZEN_EFFECT, newDuration, newAmplifier);
                targetEntity.addEffect(newEffectInstance);
            }
            else{
                event.setNewDamage(event.getNewDamage() * 2);
                targetEntity.removeEffect(FROZEN_EFFECT);
            }
        }
        //脆弱效果
        if (targetEntity.hasEffect(FRAILTY_EFFECT)) {
            int amplifier = targetEntity.getEffect(FRAILTY_EFFECT).getAmplifier();
            float newDamage = event.getNewDamage();
            event.setNewDamage(newDamage * (1 + FrailtyEffect.HURT_DAMAGE_MULTIPLIER_EACH_AMPLIFIER * (amplifier + 1)));
        }
    }

    //打印伤害信息，调试用
//    @SubscribeEvent
//    public static void onAttack(LivingDamageEvent.Post event){
//        Rhyme.LOGGER.info(event.getEntity() + "\t" + event.getSource().getDirectEntity() + "\t" + event.getSource().getEntity()+ "\t" + event.getOriginalDamage() + "\t" + event.getNewDamage());
//    }
}
