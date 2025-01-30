package rhymestudio.rhyme.datagen.loot;


import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import net.minecraftforge.registries.RegistryObject;
import rhymestudio.rhyme.core.registry.entities.Zombies;
import rhymestudio.rhyme.core.registry.items.MaterialItems;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

public class ModEntityLootProvider extends EntityLootSubProvider {

    public ModEntityLootProvider() {
        super(FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate() {

        this.add(Zombies.NORMAL_ZOMBIE.get(),ZOMBIE_COMMON_LOOT_TABLE.apply(LootTable.lootTable()));

        this.add(Zombies.CONE_ZOMBIE.get(), ZOMBIE_COMMON_LOOT_TABLE.apply(LootTable.lootTable())
//                .withPool(LOOT_POOL_CONDITIONAL.apply(ArmorItems.CONE_HELMET, 0.2F, 0.5F))
        )
        ;


        this.add(Zombies.IRON_BUCKET_ZOMBIE.get(), ZOMBIE_COMMON_LOOT_TABLE.apply(LootTable.lootTable())
//                .withPool(LOOT_POOL_CONDITIONAL.apply(ArmorItems.IRON_BUCKET_HELMET, 0.2F, 0.3F))
        )
        ;



    }
    private final BiFunction<RegistryObject<Item>,Float, LootPool.Builder> LOOT_POOL = (item, chance)->
            LootPool.lootPool()
                    .setRolls(BinomialDistributionGenerator.binomial(1, chance))
                    .add(LootItem.lootTableItem(item.get())
                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1F)))
                            .apply(EnchantWithLevelsFunction.enchantWithLevels(UniformGenerator.between(0.0F, 1.0F))))
            ;

    private final PropertyDispatch.TriFunction<RegistryObject<Item>,Float,Float, LootPool.Builder> LOOT_POOL_CONDITIONAL = (item, chance, condition)->
            LOOT_POOL.apply(item, chance).when(LootItemRandomChanceCondition.randomChance(condition));

    private final Function<LootTable.Builder, LootTable.Builder> ZOMBIE_COMMON_LOOT_TABLE = (loot)-> loot
            .withPool(LOOT_POOL.apply(MaterialItems.GENERAL_SEED, 0.75F))
            .withPool(LOOT_POOL_CONDITIONAL.apply(MaterialItems.PLANT_GENE, 0.5F, 0.5F))
            .withPool(LOOT_POOL_CONDITIONAL.apply(MaterialItems.PEA_GENE, 0.3F, 0.5F))
            .withPool(LOOT_POOL_CONDITIONAL.apply(MaterialItems.NUT_GENE, 0.2F, 0.5F))
            .withPool(LOOT_POOL_CONDITIONAL.apply(MaterialItems.SNOW_GENE, 0.2F, 0.5F))
            .withPool(LOOT_POOL_CONDITIONAL.apply(MaterialItems.MUSHROOM_GENE, 0.3F, 0.5F))
            .withPool(LOOT_POOL_CONDITIONAL.apply(MaterialItems.THROWABLE_GENE, 0.2F, 0.5F))
            .withPool(LOOT_POOL_CONDITIONAL.apply(MaterialItems.HIDDEN_GENE, 0.2F, 0.5F))
            .withPool(LOOT_POOL_CONDITIONAL.apply(MaterialItems.ANGER_GENE, 0.2F, 0.5F))
            .withPool(LOOT_POOL_CONDITIONAL.apply(MaterialItems.STRONG_GENE, 0.2F, 0.5F))

            ;


    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return Zombies.ZOMBIES.getEntries().stream().map(RegistryObject::get);
    }
}
