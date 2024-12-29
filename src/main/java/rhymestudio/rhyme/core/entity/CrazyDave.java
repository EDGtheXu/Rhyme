package rhymestudio.rhyme.core.entity;

import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import rhymestudio.rhyme.core.recipe.DaveTrades;
import rhymestudio.rhyme.core.menu.DaveTradesMenu;

import static rhymestudio.rhyme.core.registry.ModEntityDataSerializer.DAVE_TRADES_SERIALIZER;

public class CrazyDave extends Mob {
    public DaveTrades daveTrades;
    private static final EntityDataAccessor<DaveTrades> DATA_VILLAGER_DATA = SynchedEntityData.defineId(CrazyDave.class, DAVE_TRADES_SERIALIZER.get());

    public CrazyDave(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);

    }


    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        daveTrades = DaveTrades.RAND_TRADE.get();
        builder.define(DATA_VILLAGER_DATA, daveTrades);
    }


    protected InteractionResult mobInteract(Player player, InteractionHand hand) {

        player.openMenu(new SimpleMenuProvider((id, playerInventory, trade) -> {
            return new DaveTradesMenu(id,playerInventory,daveTrades);},Component.literal("dave")));

        return InteractionResult.PASS;
    }
}
