package rhymestudio.rhyme.core.item.misc;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.item.CustomRarityItem;
import rhymestudio.rhyme.core.registry.ModDataComponentTypes;

import java.util.List;

public class CheckpointItem extends CustomRarityItem {

    public CheckpointItem(Properties properties) {
        super(properties);
    }


    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        var data = stack.get(ModDataComponentTypes.CHECKPOINT_LOCATION);
        if(data!= null){
            tooltipComponents.add(
                    Component.translatable("tooltip.rhyme.open_checkpoint")
                            .append(Component.translatable(Rhyme.toLang(data.getLocation()))));
        }
    }

}
