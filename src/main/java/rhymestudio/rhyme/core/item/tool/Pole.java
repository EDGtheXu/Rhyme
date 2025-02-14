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
        if(player.onGround()) {
            player.setDeltaMovement(player.getDeltaMovement().add(new Vec3(0, 0.3, 0)).scale(7));
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

}
