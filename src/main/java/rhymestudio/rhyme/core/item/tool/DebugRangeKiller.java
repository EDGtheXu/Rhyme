package rhymestudio.rhyme.core.item.tool;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import rhymestudio.rhyme.core.dataSaver.dataComponent.ModRarity;
import rhymestudio.rhyme.core.item.CustomRarityItem;

public class DebugRangeKiller extends CustomRarityItem {
    public DebugRangeKiller(Properties properties, ModRarity rarity) {
        super(properties, rarity);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if(!level.isClientSide){
            level.getEntities(player, player.getBoundingBox().inflate(5), e -> e != player).forEach(e-> e.hurt(player.damageSources().magic(),999));
        }
        return super.use(level, player, usedHand);
    }
}