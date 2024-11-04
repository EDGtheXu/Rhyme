package rhymestudio.rhyme.registry.items;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.entity.AbstractPlant;
import rhymestudio.rhyme.item.AbstructCardItem;
import rhymestudio.rhyme.registry.ModEntities;

import java.util.Optional;

public class MaterialItems {
    public static final DeferredRegister.Items MATERIALS = DeferredRegister.createItems(Rhyme.MODID);

    public static final DeferredItem<Item> SUN_ITEM = register("sun_item");
    public static final DeferredItem<Item> SOLID_SUN =register("solid_sun");
    public static final DeferredItem<Item> GENERAL_SEED =register("general_seed");
    public static final DeferredItem<Item> PLANT_GENE =register("plant_gene");
    public static final DeferredItem<Item> PEA_GENE =register("pea_gene");
    public static final DeferredItem<Item> ANGER_GENE =register("anger_gene");





    public static DeferredItem<Item> register(String name) {
        DeferredItem<Item> item =  MATERIALS.register("material/"+name, () -> new Item(new Item.Properties()));
        return item;
    }

}
