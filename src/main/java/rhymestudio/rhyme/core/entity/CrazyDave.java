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
import rhymestudio.rhyme.core.menu.DaveTradesMenu;
import rhymestudio.rhyme.core.registry.items.MaterialItems;

import java.util.List;

import static rhymestudio.rhyme.core.registry.ModEntityDataSerializer.DAVE_TRADES_SERIALIZER;

public class CrazyDave extends Mob {
    public DaveTradesMenu.DaveTrades daveTrades;
    private static final EntityDataAccessor<DaveTradesMenu.DaveTrades> DATA_VILLAGER_DATA = SynchedEntityData.defineId(CrazyDave.class, DAVE_TRADES_SERIALIZER.get());

    public CrazyDave(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);

    }


    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        daveTrades = new DaveTradesMenu.DaveTrades(List.of(
                new DaveTradesMenu.DaveTrades.Trade(5,List.of(MaterialItems.SOLID_SUN.toStack(0),MaterialItems.GENERAL_SEED.toStack(5)),MaterialItems.PEA_GENE.toStack(0)),
                new DaveTradesMenu.DaveTrades.Trade(5,List.of(MaterialItems.SOLID_SUN.toStack(1),MaterialItems.GENERAL_SEED.toStack(5)),MaterialItems.PEA_GENE.toStack(1)),
                new DaveTradesMenu.DaveTrades.Trade(5,List.of(MaterialItems.SOLID_SUN.toStack(2),MaterialItems.GENERAL_SEED.toStack(5)),MaterialItems.PEA_GENE.toStack(2)),
                new DaveTradesMenu.DaveTrades.Trade(5,List.of(MaterialItems.SOLID_SUN.toStack(3),MaterialItems.GENERAL_SEED.toStack(5)),MaterialItems.PEA_GENE.toStack(3)),
                new DaveTradesMenu.DaveTrades.Trade(5,List.of(MaterialItems.SOLID_SUN.toStack(4),MaterialItems.GENERAL_SEED.toStack(5)),MaterialItems.PEA_GENE.toStack(4)),
                new DaveTradesMenu.DaveTrades.Trade(5,List.of(MaterialItems.SOLID_SUN.toStack(5),MaterialItems.GENERAL_SEED.toStack(5)),MaterialItems.PEA_GENE.toStack(8)),
                new DaveTradesMenu.DaveTrades.Trade(5,List.of(MaterialItems.SOLID_SUN.toStack(6),MaterialItems.GENERAL_SEED.toStack(5)),MaterialItems.PEA_GENE.toStack(9)),
                new DaveTradesMenu.DaveTrades.Trade(5,List.of(MaterialItems.SOLID_SUN.toStack(7),MaterialItems.GENERAL_SEED.toStack(5)),MaterialItems.PEA_GENE.toStack(7)),
                new DaveTradesMenu.DaveTrades.Trade(5,List.of(MaterialItems.SOLID_SUN.toStack(8),MaterialItems.GENERAL_SEED.toStack(5)),MaterialItems.PEA_GENE.toStack(8))
                ));
        builder.define(DATA_VILLAGER_DATA, daveTrades);
    }


    protected InteractionResult mobInteract(Player player, InteractionHand hand) {

        player.openMenu(new SimpleMenuProvider((id, playerInventory, trade) -> {
            return new DaveTradesMenu(id,playerInventory,daveTrades);},Component.literal("dave")));

        return InteractionResult.PASS;
    }
}
