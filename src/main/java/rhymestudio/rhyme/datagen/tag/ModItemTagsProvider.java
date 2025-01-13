package rhymestudio.rhyme.datagen.tag;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rhymestudio.rhyme.core.registry.items.PlantItems;

import java.util.concurrent.CompletableFuture;

import static rhymestudio.rhyme.Rhyme.MODID;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, CompletableFuture<TagLookup<Block>> b, @Nullable ExistingFileHelper helper) {
        super(output, provider, b, MODID, helper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(ModTags.Items.DAVE_FOOD).add(Items.PUMPKIN_PIE);

        PlantItems.PLANTS.getEntries().forEach(e->tag(ModTags.Items.CARD).add(e.get().asItem()));

    }
}
