package rhymestudio.rhyme.core.entity;

import com.mojang.serialization.DataResult;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
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
    private static final EntityDataAccessor<DaveTrades> DATA_DAVE_DATA = SynchedEntityData.defineId(CrazyDave.class, DAVE_TRADES_SERIALIZER.get());

    public CrazyDave(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);

    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (DATA_DAVE_DATA.equals(key)) {
            this.daveTrades = this.entityData.get(DATA_DAVE_DATA);
        }
    }
    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        daveTrades = DaveTrades.RAND_TRADE.get();
        builder.define(DATA_DAVE_DATA, daveTrades);
    }
    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("dave_data", 10)) {
            DataResult<DaveTrades> data = DaveTrades.CODEC.parse(NbtOps.INSTANCE, compound.get("dave_data"));
            this.entityData.set(DATA_DAVE_DATA, data.result().get());
            this.daveTrades = data.result().get();
        }
    }
    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        DataResult<Tag> data = DaveTrades.CODEC.encodeStart(NbtOps.INSTANCE, daveTrades);
        compound.put("dave_data",data.result().get());
    }
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {

        player.openMenu(new SimpleMenuProvider((id, playerInventory, trade) -> {
            return new DaveTradesMenu(id,playerInventory,daveTrades);},Component.literal("dave shop")));

        return InteractionResult.PASS;
    }
}
