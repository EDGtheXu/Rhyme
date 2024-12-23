package rhymestudio.rhyme.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import rhymestudio.rhyme.datagen.dataPack.RegisterDataPack;
import rhymestudio.rhyme.datagen.lang.ModChineseProvider;
import rhymestudio.rhyme.datagen.lang.ModEnglishProvider;
import rhymestudio.rhyme.datagen.loot.ModLootTableProvider;
import rhymestudio.rhyme.datagen.recipe.ModRecipeProvider;
import rhymestudio.rhyme.datagen.recipe.SunCreatorRecipeProvider;
import rhymestudio.rhyme.datagen.tag.ModBlockTagsProvider;
import rhymestudio.rhyme.datagen.tag.ModDamageTypeTagsProvider;
import rhymestudio.rhyme.datagen.tag.ModEntityTypeTagsProvider;
import rhymestudio.rhyme.datagen.tag.ModItemTagsProvider;


import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static rhymestudio.rhyme.Rhyme.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
public class DataGenerator {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        net.minecraft.data.DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper helper = event.getExistingFileHelper();

        CompletableFuture<HolderLookup.Provider> lookup = event.getLookupProvider();
        boolean server = event.includeServer();


        DatapackBuiltinEntriesProvider provider = new DatapackBuiltinEntriesProvider(output, lookup, RegisterDataPack.DATA_BUILDER, Set.of(MODID));
        lookup = provider.getRegistryProvider();


        generator.addProvider(server, provider);
        generator.addProvider(server, new ModEntityTypeTagsProvider(output, lookup, helper));
        generator.addProvider(server, new ModDamageTypeTagsProvider(output, lookup, helper));
        ModBlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(output, lookup, helper);
        generator.addProvider(server, blockTagsProvider);
        generator.addProvider(server, new ModItemTagsProvider(output, lookup, blockTagsProvider.contentsGetter(), helper));
        generator.addProvider(server,new ModRecipeProvider(output,lookup));
        generator.addProvider(server, ModLootTableProvider.getProvider(output, lookup));

        boolean client = event.includeClient();
        generator.addProvider(client, new ModChineseProvider(output));
        generator.addProvider(client, new ModEnglishProvider(output));
        generator.addProvider(client, new ModItemModelProvider(output, helper));

        generator.addProvider(client, new SunCreatorRecipeProvider(output));

    }

}
