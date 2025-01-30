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
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.registries.RegistryObject;
import rhymestudio.rhyme.core.entity.AbstractPlant;

import java.util.concurrent.atomic.AtomicInteger;


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
        Vec3 vec3 = player.getEyePosition();
        Vec3 vec31 = vec3.add(player.getLookAngle().scale(player.getAttributeValue(ForgeMod.BLOCK_REACH.get())));
        BlockHitResult result =  player.level().clip(new ClipContext(vec3, vec31, net.minecraft.world.level.ClipContext.Block.OUTLINE, ClipContext.Fluid.WATER, player));
        final BlockHitResult raytraceResult = result.withPosition(result.getBlockPos().above());
        final BlockPos pos = raytraceResult.getBlockPos();
        return pos;
    }

    public static Vec3 getBlockPosCenter(BlockPos pos, RandomSource random){
        return  new Vec3(pos.getX() + 0.5+random.nextFloat()*0.1f, pos.getY(), pos.getZ() + 0.5+random.nextFloat()*0.1f);

    }

    public static void playSound(Entity entity, RegistryObject<SoundEvent> sound, float volume, BlockPos pos){
        entity.level().playSound(entity,pos,sound.get(),SoundSource.AMBIENT,volume,1F);

    }
    public static void playSound(Entity entity, RegistryObject<SoundEvent> sound, float volume){
        playSound(entity, sound, volume, entity.blockPosition().above());
    }
    public static void playSound(Entity entity, RegistryObject<SoundEvent> sound){
        playSound(entity, sound, 1F);
    }

}
