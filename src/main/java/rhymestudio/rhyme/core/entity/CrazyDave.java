package rhymestudio.rhyme.core.entity;

import com.mojang.serialization.DataResult;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import rhymestudio.rhyme.client.animation.zombieAnimations.NormalZombieAnimation;
import rhymestudio.rhyme.core.entity.ai.goals.DaveTradeGoal;
import rhymestudio.rhyme.core.entity.anim.CafeAnimationState;
import rhymestudio.rhyme.core.recipe.DaveTrades;
import rhymestudio.rhyme.core.menu.DaveTradesMenu;
import rhymestudio.rhyme.core.registry.items.MaterialItems;
import rhymestudio.rhyme.datagen.tag.ModTags;

import static rhymestudio.rhyme.config.ServerConfig.DaveDropRate;
import static rhymestudio.rhyme.core.registry.ModEntityDataSerializer.DAVE_TRADES_SERIALIZER;

public class CrazyDave extends PathfinderMob implements ICafeMob{
    public DaveTrades daveTrades;
    public Player tradingPlayer;
    public CafeAnimationState animState = new CafeAnimationState(this);
    private int money = this.random.nextIntBetweenInclusive(10, 50);

    private static final EntityDataAccessor<DaveTrades> DATA_DAVE_DATA = SynchedEntityData.defineId(CrazyDave.class, DAVE_TRADES_SERIALIZER.get());
    public static final EntityDataAccessor<String> DATA_CAFE_POSE_NAME = SynchedEntityData.defineId(CrazyDave.class, EntityDataSerializers.STRING);

    public CrazyDave(EntityType<? extends PathfinderMob> entityType, Level level) {
        super(entityType, level);

        animState.addAnimation("walk", NormalZombieAnimation.walk);
        animState.addAnimation("idle", NormalZombieAnimation.idle);
        animState.addAnimation("hurt", NormalZombieAnimation.hurt);
        animState.addAnimation("run", NormalZombieAnimation.run);

        this.animState.playAnim("idle",0);

    }

    @Override
    protected void registerGoals() {

        this.goalSelector.addGoal(0, new FloatGoal(this));

        this.goalSelector.addGoal(2,new DaveTradeGoal(this));

        this.goalSelector.addGoal(4, new TemptGoal(this, 0.3f, (it) -> it.is(ModTags.Items.DAVE_FOOD), false));

        this.goalSelector.addGoal(5, new AvoidEntityGoal<>(this, Monster.class, 20, 0.3f, 0.3f));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (DATA_DAVE_DATA.equals(key)) {
            this.daveTrades = this.entityData.get(DATA_DAVE_DATA);
        }
        if (this.level().isClientSide() && DATA_CAFE_POSE_NAME.equals(key)) {
            String name = entityData.get(DATA_CAFE_POSE_NAME);
            this.animState.playAnim(name,this.tickCount);
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        daveTrades = DaveTrades.RAND_TRADE.apply(random.nextIntBetweenInclusive(0,DaveTrades.GetAllTradesLength() - 1));
        builder.define(DATA_DAVE_DATA, daveTrades);
        builder.define(DATA_CAFE_POSE_NAME, "idle");

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
    public void aiStep() {
        super.aiStep();
        if(!level().isClientSide) {
            if (navigation.isInProgress()) {
                this.animState.playAnim("walk", this.tickCount);
                this.entityData.set(DATA_CAFE_POSE_NAME, "walk");

            } else {
                this.animState.playAnim("idle", this.tickCount);
                this.entityData.set(DATA_CAFE_POSE_NAME, "idle");
            }
        }
    }

    public void addMoney(int amount) {
        this.money += amount;
    }

    @Override
    public void die(DamageSource damageSource) {
        super.die(damageSource);
        if(!level().isClientSide){

        }
        double rate = DaveDropRate.get();
        int gold = (int) (money / (10 / rate));
        int silver = (int) (money % (10/rate) / (5/rate));
        ItemEntity gold_coin = new ItemEntity(level(), this.getX(), this.getY(), this.getZ(), MaterialItems.GOLD_COIN.toStack(gold));
        ItemEntity silver_coin = new ItemEntity(level(), this.getX(), this.getY(), this.getZ(), MaterialItems.SILVER_COIN.toStack(silver));
        gold_coin.addDeltaMovement(new Vec3(0, 0.3, 0));
        silver_coin.addDeltaMovement(new Vec3(0, 0.2, 0));
        level().addFreshEntity(gold_coin);
        level().addFreshEntity(silver_coin);
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        player.openMenu(new SimpleMenuProvider((id, playerInventory, player1) -> new DaveTradesMenu(id,playerInventory,daveTrades),Component.translatable("rhyme.menu.dave_shop")));
        tradingPlayer = player;
        return InteractionResult.PASS;
    }

    @Override
    public CafeAnimationState getCafeAnimState() {
        return animState;
    }
}
