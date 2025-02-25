//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package rhymestudio.rhyme.datagen.loot;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntry;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.apache.commons.lang3.function.TriFunction;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.dataSaver.dataComponent.CardQualityComponent;
import rhymestudio.rhyme.core.registry.ModBlocks;
import rhymestudio.rhyme.core.registry.ModDataComponentTypes;
import rhymestudio.rhyme.core.registry.items.MaterialItems;
import rhymestudio.rhyme.core.registry.items.PlantItems;
import rhymestudio.rhyme.datagen.tag.ModTags;

public record ModChestLoot(HolderLookup.Provider registries) implements LootTableSubProvider {
    public ModChestLoot(HolderLookup.Provider registries) {
        this.registries = registries;
    }

    public static ResourceKey<LootTable> daveChest = ResourceKey.create(Registries.LOOT_TABLE, Rhyme.space("chests/dave_chest"));

    public static ResourceKey<LootTable> checkpoint_loot_lvl_1_1 = ResourceKey.create(Registries.LOOT_TABLE, Rhyme.space("chests/checkpoint_loot_lvl_1_1"));
    public static ResourceKey<LootTable> checkpoint_loot_lvl_1_2 = ResourceKey.create(Registries.LOOT_TABLE, Rhyme.space("chests/checkpoint_loot_lvl_1_2"));
    public static ResourceKey<LootTable> checkpoint_loot_lvl_1_3 = ResourceKey.create(Registries.LOOT_TABLE, Rhyme.space("chests/checkpoint_loot_lvl_1_3"));
    public static ResourceKey<LootTable> checkpoint_loot_lvl_1_4 = ResourceKey.create(Registries.LOOT_TABLE, Rhyme.space("chests/checkpoint_loot_lvl_1_4"));
    public static ResourceKey<LootTable> checkpoint_loot_lvl_1_5 = ResourceKey.create(Registries.LOOT_TABLE, Rhyme.space("chests/checkpoint_loot_lvl_1_5"));
    public static ResourceKey<LootTable> checkpoint_loot_lvl_1_6 = ResourceKey.create(Registries.LOOT_TABLE, Rhyme.space("chests/checkpoint_loot_lvl_1_6"));
    public static ResourceKey<LootTable> checkpoint_loot_lvl_1_7 = ResourceKey.create(Registries.LOOT_TABLE, Rhyme.space("chests/checkpoint_loot_lvl_1_7"));
    public static ResourceKey<LootTable> checkpoint_loot_lvl_1_8 = ResourceKey.create(Registries.LOOT_TABLE, Rhyme.space("chests/checkpoint_loot_lvl_1_8"));
    public static ResourceKey<LootTable> checkpoint_loot_lvl_1_9 = ResourceKey.create(Registries.LOOT_TABLE, Rhyme.space("chests/checkpoint_loot_lvl_1_9"));
    public static ResourceKey<LootTable> checkpoint_loot_lvl_1_10 = ResourceKey.create(Registries.LOOT_TABLE, Rhyme.space("chests/checkpoint_loot_lvl_1_10"));


    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

        output.accept(daveChest, LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(0F, 1F))
                                        .add(addPlantCard.apply(PlantItems.PEA_ITEM.get(),10,0))
//                        .add(TagEntry.expandTag(ModTags.Items.CARD).setWeight(10)
//                                .apply(SetComponentsFunction.setComponent(ModDataComponentTypes.CARD_QUALITY.get(), CardQualityComponent.GOLD))
//                        )
                        )
                        .withPool(addZombieFlag())
                        .withPool(addIngotsPool.apply(LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(2.0F, 4.0F)))
//                        .add(LootItem.lootTableItem(Items.MELON_SEEDS).setWeight(10)
//                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
//                        .add(LootItem.lootTableItem(Items.PUMPKIN_SEEDS).setWeight(10)
//                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
//                        .add(LootItem.lootTableItem(Items.BEETROOT_SEEDS).setWeight(10)
//                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                        )
                        .withPool(addMaterialsPool.apply(5))
        );

        output.accept(checkpoint_loot_lvl_1_1, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(0F, 1F))
                                .add(addPlantCard.apply(PlantItems.PEA_ITEM.get(),10,0))

                )
                .withPool(addZombieFlag())
                .withPool(addIngotsPool.apply(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(2.0F, 4.0F)))
                )
                .withPool(addMaterialsPool.apply(5))
        );

        output.accept(checkpoint_loot_lvl_1_2, LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(0F, 1F))
                                        .add(addPlantCard.apply(PlantItems.SUN_FLOWER.get(),10,0))
                        )
                        .withPool(addZombieFlag())
                        .withPool(addIngotsPool.apply(LootPool.lootPool()
                                        .setRolls(UniformGenerator.between(2.0F, 4.0F)))
                        )
                .withPool(addMaterialsPool.apply(6))

        );
        output.accept(checkpoint_loot_lvl_1_3, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(0F, 1F))
                        .add(addPlantCard.apply(PlantItems.SNOW_PEA_ITEM.get(),10,0))
                )
                .withPool(addZombieFlag())
                .withPool(addIngotsPool.apply(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(2.0F, 4.0F)))
                )
                .withPool(addMaterialsPool.apply(7))
        );

        this.spawnerLootTables(output);
    }

    /**
     * 添加各种锭
     */
    static Function<LootPool.Builder, LootPool.Builder> addIngotsPool = (builder) -> builder
            .add(LootItem.lootTableItem(Items.IRON_INGOT).setWeight(10)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F))))
            .add(LootItem.lootTableItem(Items.GOLD_INGOT).setWeight(5)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
            .add(LootItem.lootTableItem(Items.REDSTONE).setWeight(5)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 9.0F))))
            .add(LootItem.lootTableItem(Items.LAPIS_LAZULI).setWeight(5)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 9.0F))))
            .add(LootItem.lootTableItem(Items.DIAMOND).setWeight(3)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
            .add(LootItem.lootTableItem(Items.COAL).setWeight(10)
                    .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 8.0F))));
    /**
     * 添加各种材料
     * 抽取count个
     */
    static Function<Integer, LootPool.Builder> addMaterialsPool = (count)-> LootPool.lootPool()
            .setRolls(ConstantValue.exactly(count))
            .add(LootItem.lootTableItem(MaterialItems.GENERAL_SEED.get()).setWeight(10))
            .add(LootItem.lootTableItem(MaterialItems.PLANT_GENE.asItem()).setWeight(10))
            .add(LootItem.lootTableItem(MaterialItems.PEA_GENE.get()).setWeight(10))
            .add(LootItem.lootTableItem(MaterialItems.NUT_GENE.get()).setWeight(10))
            .add(LootItem.lootTableItem(MaterialItems.SNOW_GENE.get()).setWeight(10))
            .add(LootItem.lootTableItem(MaterialItems.MUSHROOM_GENE.get()).setWeight(10))
            .add(LootItem.lootTableItem(MaterialItems.THROWABLE_GENE.get()).setWeight(10))
            .add(LootItem.lootTableItem(MaterialItems.HIDDEN_GENE.get()).setWeight(10))
            .add(LootItem.lootTableItem(MaterialItems.ANGER_GENE.get()).setWeight(10))
            .add(LootItem.lootTableItem(MaterialItems.STRONG_GENE.get()).setWeight(10))
            .add(LootItem.lootTableItem(MaterialItems.CHECKPOINT_ITEM.get()).setWeight(10))
            .add(LootItem.lootTableItem(ModBlocks.ZOMBIE_BANNER_ITEM.get()).setWeight(10))
            ;

    /**
     * 添加植物卡片
     */
    static TriFunction<Item, Integer, Integer,LootPoolEntryContainer.Builder<?>> addPlantCard = (item, weight, lvl)->
            LootItem.lootTableItem(item).setWeight(weight)
            .apply(SetComponentsFunction.setComponent(ModDataComponentTypes.CARD_QUALITY.get(), CardQualityComponent.of(lvl)));

    /**
     * 添加一个僵尸旗帜
     */
    static LootPool.Builder addZombieFlag() {
        return LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F))
                .add(LootItem.lootTableItem(ModBlocks.ZOMBIE_BANNER_ITEM));
    }


    public void spawnerLootTables(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

    }


    public HolderLookup.Provider registries() {
        return this.registries;
    }
}
