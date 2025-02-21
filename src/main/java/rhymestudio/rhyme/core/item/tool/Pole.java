package rhymestudio.rhyme.core.item.tool;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import rhymestudio.rhyme.core.item.CustomRarityItem;

import java.util.List;

public class Pole extends CustomRarityItem {

    public Pole(Properties properties) {
        super(properties);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if(usedHand == InteractionHand.MAIN_HAND && player.onGround()) {
            Vec3 v2 = getVelocity(player.getDeltaMovement());
            player.setDeltaMovement(v2);
            ItemStack itemstack = player.getItemInHand(usedHand);
            if (level instanceof ServerLevel sl)
                itemstack.hurtAndBreak(1, sl, player, c -> {
                });
            return InteractionResultHolder.success(player.getItemInHand(usedHand));
        }
        return InteractionResultHolder.fail(player.getItemInHand(usedHand));
    }

    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {


    }

    public static Vec3 getVelocity(Vec3 ori) {
        double f = Math.min(ori.length() * 20, 2);
        Vec3 v = ori.normalize().scale(f).add(0, 2.5, 0);
        Vec3 v2 = new Vec3(v.x, Math.min(v.y, 1.5), v.z);
        return v2;
    }

}
