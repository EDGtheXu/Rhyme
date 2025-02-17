//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package rhymestudio.rhyme.datagen.loot;

import java.util.function.BiConsumer;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.registry.items.PlantItems;

public record ModChestLoot(HolderLookup.Provider registries) implements LootTableSubProvider {
    public ModChestLoot(HolderLookup.Provider registries) {
        this.registries = registries;
    }

    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

        output.accept(ResourceKey.create(Registries.LOOT_TABLE, Rhyme.space("chests/dave_chest")), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(Items.GOLDEN_APPLE).setWeight(1))
                        .add(LootItem.lootTableItem(Items.ENCHANTED_GOLDEN_APPLE).setWeight(20)
                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(32))))
//                        .add(LootItem.lootTableItem(Items.NAME_TAG).setWeight(30))
//                        .add(LootItem.lootTableItem(Items.BOOK).setWeight(10)
//                                .apply(EnchantRandomlyFunction.randomApplicableEnchantment(this.registries)))
//                        .add(LootItem.lootTableItem(Items.IRON_PICKAXE).setWeight(5))
//                        .add(EmptyLootItem.emptyItem().setWeight(5))
                        )
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(PlantItems.PEA_ITEM.get()))
                )
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(2.0F, 4.0F))
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
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 8.0F))))
                        .add(LootItem.lootTableItem(Items.BREAD).setWeight(15)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(Items.GLOW_BERRIES).setWeight(15)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(3.0F, 6.0F))))
                        .add(LootItem.lootTableItem(Items.MELON_SEEDS).setWeight(10)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Items.PUMPKIN_SEEDS).setWeight(10)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Items.BEETROOT_SEEDS).setWeight(10)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F)))))
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(3.0F))
                        .add(LootItem.lootTableItem(Blocks.RAIL).setWeight(20)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 8.0F))))
                        .add(LootItem.lootTableItem(Blocks.POWERED_RAIL).setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Blocks.DETECTOR_RAIL).setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Blocks.ACTIVATOR_RAIL).setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 4.0F))))
                        .add(LootItem.lootTableItem(Blocks.TORCH).setWeight(15)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 16.0F))))));

        this.spawnerLootTables(output);
    }

    public void spawnerLootTables(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);

    }


    public HolderLookup.Provider registries() {
        return this.registries;
    }
}
