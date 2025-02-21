package rhymestudio.rhyme.core.item.misc;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.checkpoint.checkpoint.CheckPointManager;
import rhymestudio.rhyme.core.dataSaver.dataComponent.CheckpointComponent;
import rhymestudio.rhyme.core.item.CustomRarityItem;
import rhymestudio.rhyme.core.registry.ModDataComponentTypes;

import java.util.List;

public class CheckpointItem extends CustomRarityItem {

    public CheckpointItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        var data = stack.get(ModDataComponentTypes.CHECKPOINT_LOCATION);
        if(!level.isClientSide && data == null){
            CheckPointManager.getRandom().ifPresent(cp->{
                stack.set(ModDataComponentTypes.CHECKPOINT_LOCATION.get(), new CheckpointComponent(cp.name()));
            });

        }
        return super.use(level, player, usedHand);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        var data = stack.get(ModDataComponentTypes.CHECKPOINT_LOCATION);
        if(data!= null){
            tooltipComponents.add(
                    Component.translatable("tooltip.rhyme.open_checkpoint")
                            .append(Component.translatable(Rhyme.toLang(data.getLocation()))));
        }else{
            tooltipComponents.add(
                    Component.translatable("tooltip.rhyme.init_checkpoint"));
        }
    }

}
