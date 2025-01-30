package rhymestudio.rhyme.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;


import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.DeferredRegister;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.registry.items.*;


import java.util.ArrayList;
import java.util.List;

import static rhymestudio.rhyme.Rhyme.MODID;

public class ModItemModelProvider extends ItemModelProvider {

    private static final ResourceLocation MISSING_ITEM = Rhyme.space("item/missing");
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        List<DeferredRegister<Item>> ALL = new ArrayList<>();
        ALL.add(MaterialItems.MATERIALS);
        ALL.add(IconItems.QUALITY_ITEMS);
        ALL.add(ArmorItems.ARMORS);
        ALL.add(PlantItems.PLANTS);
        ALL.forEach(registry -> registry.getEntries().forEach(item -> {
            String path = item.getId().getPath().toLowerCase();
            try {
                withExistingParent("item/"+path, "item/generated").texture("layer0", Rhyme.space("item/"+path));
            }catch (Exception e){
                withExistingParent("item/"+path,MISSING_ITEM);
            }
        }));

        ToolItems.TOOLS.getEntries().forEach(item -> {
            String path = item.getId().getPath().toLowerCase();
            try {
                withExistingParent("item/"+path, "item/handheld").texture("layer0", Rhyme.space("item/"+path));
            }catch (Exception e){
                withExistingParent("item/"+path,MISSING_ITEM);
            }
        });

        SpawnEggItems.EGGS.getEntries().forEach(item -> {
            String path = item.getId().getPath().toLowerCase();
            withExistingParent("item/"+path, "minecraft:item/template_spawn_egg");
        });


    }
}
