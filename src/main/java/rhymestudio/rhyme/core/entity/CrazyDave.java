package rhymestudio.rhyme.core.entity;

import com.mojang.serialization.DataResult;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import rhymestudio.rhyme.core.recipe.DaveTrades;
import rhymestudio.rhyme.core.menu.DaveTradesMenu;
import rhymestudio.rhyme.datagen.tag.ModTags;

import static rhymestudio.rhyme.core.registry.ModEntityDataSerializer.DAVE_TRADES_SERIALIZER;

public class CrazyDave extends PathfinderMob {
    public DaveTrades daveTrades;
    private static final EntityDataAccessor<DaveTrades> DATA_DAVE_DATA = SynchedEntityData.defineId(CrazyDave.class, DAVE_TRADES_SERIALIZER.get());

    public CrazyDave(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);
    }
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25));


        this.goalSelector.addGoal(4, new TemptGoal(this, 0.8f, (it) -> it.is(ModTags.Items.DAVE_FOOD), false));

        this.goalSelector.addGoal(5, new AvoidEntityGoal<>(this, Monster.class, 20, 0.2f, 0.4f));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (DATA_DAVE_DATA.equals(key)) {
            this.daveTrades = this.entityData.get(DATA_DAVE_DATA);
        }
    }
    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        daveTrades = DaveTrades.RAND_TRADE.apply(random.nextIntBetweenInclusive(0,DaveTrades.GetAllTradesLength() - 1));
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

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        player.openMenu(new SimpleMenuProvider((id, playerInventory, player1) -> new DaveTradesMenu(id,playerInventory,daveTrades),Component.translatable("rhyme.menu.dave_shop")));
        return InteractionResult.PASS;
    }
}
