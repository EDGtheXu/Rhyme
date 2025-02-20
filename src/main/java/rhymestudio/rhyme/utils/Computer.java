package rhymestudio.rhyme.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredHolder;
import rhymestudio.rhyme.core.entity.AbstractPlant;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import static net.minecraft.world.item.Item.getPlayerPOVHitResult;

public class Computer {

    public static double angle(Vec3 line1, Vec3 line2){return Math.acos(line1.dot(line2)/line1.length()/line2.length());}

    public static int getInventoryItemCount(Player player, Item item){
        AtomicInteger count = new AtomicInteger();
        player.getInventory().items.forEach(stack -> count.addAndGet(stack.is(item)? stack.getCount():0));
        return count.get();
    }

    public static void consumeInventoryItemCount(Player player, Item item, int consumeCount){
        AtomicInteger count = new AtomicInteger();
        player.getInventory().items.forEach(stack -> {
            if(stack.is(item) && count.get() < consumeCount){
                int toConsume = Math.min(stack.getCount(), consumeCount - count.get());
                stack.shrink(toConsume);
                count.addAndGet(toConsume);
            }
        });
    }

    public static boolean tryCombineInventoryItem(Player player, Item item, int count){
        int have = getInventoryItemCount(player, item);
        if(have < count) return false;
        consumeInventoryItemCount(player, item, count);
        return true;
    }

    public static EntityHitResult getEyeTraceHitResult(Player player, double distance){
        AABB aabb = player.getBoundingBox().inflate(distance);
        Vec3 from = player.getEyePosition();
        Vec3 to = player.getEyePosition().add(player.getLookAngle().scale(distance));
        return ProjectileUtil.getEntityHitResult(player.level(), player, from, to, aabb, e-> e instanceof AbstractPlant, 0.1F);
    }

    public static BlockPos getEyeBlockHitResult(Player player){
        final BlockHitResult result = getPlayerPOVHitResult(player.level(), player, ClipContext.Fluid.SOURCE_ONLY);
        final BlockHitResult raytraceResult = result.withPosition(result.getBlockPos().above());
        final BlockPos pos = raytraceResult.getBlockPos();
        return pos;
    }

    public static Vec3 getBlockPosCenter(BlockPos pos, RandomSource random){
        return  new Vec3(pos.getX() + 0.5+random.nextFloat()*0.1f, pos.getY(), pos.getZ() + 0.5+random.nextFloat()*0.1f);

    }

    public static void playSound(Entity entity, DeferredHolder<SoundEvent,SoundEvent> sound, float volume, BlockPos pos){
        entity.level().playSound(entity,pos,sound.get(),SoundSource.AMBIENT,volume,1F);

    }
    public static void playSound(Entity entity, DeferredHolder<SoundEvent,SoundEvent> sound, float volume){
        playSound(entity, sound, volume, entity.blockPosition().above());
    }
    public static void playSound(Entity entity, DeferredHolder<SoundEvent,SoundEvent> sound){
        playSound(entity, sound, 1F);
    }

    /**
     * 根据权重随机获取物品
     */
    public static <T> T getRandomByWeight(Map<T, Float> map) {
        // 计算总权重
        float totalWeight = 0.0f;

        for (var pair : map.values()) {
            totalWeight += pair;
        }

        if (totalWeight == 0.0f) {
            throw new IllegalArgumentException("Total weight cannot be zero.");
        }

        float randomValue = ThreadLocalRandom.current().nextFloat(0, totalWeight);

        // 遍历物品，累积权重，直到累积权重超过随机数
        float cumulativeWeight = 0.0f;
        for (var entry : map.entrySet()) {
            cumulativeWeight += entry.getValue();
            if (cumulativeWeight >= randomValue) {
                return entry.getKey();
            }
        }
        // 理论上不会走到这里
        throw new IllegalStateException("Failed to find random item.");
    }

}
