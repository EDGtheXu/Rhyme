package rhymestudio.rhyme.core.registry.items;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.dataSaver.dataComponent.ModRarity;
import rhymestudio.rhyme.core.item.CustomRarityItem;
import rhymestudio.rhyme.core.registry.ModDataComponentTypes;

public class MaterialItems {
    public static final DeferredRegister.Items MATERIALS = DeferredRegister.createItems(Rhyme.MODID);

    public static final DeferredItem<Item> SUN_ITEM = register("sun_item","阳光");
    public static final DeferredItem<Item> SOLID_SUN =register("solid_sun","固态阳光",ModRarity.BLUE);
    public static final DeferredItem<Item> GENERAL_SEED =register("general_seed","通用种子");
    public static final DeferredItem<Item> PLANT_GENE =register("plant_gene","植物基因");
    public static final DeferredItem<Item> PEA_GENE =register("pea_gene","豌豆基因",ModRarity.GREEN);
    public static final DeferredItem<Item> ANGER_GENE =register("anger_gene","易怒基因",ModRarity.RED);
    public static final DeferredItem<Item> SNOW_GENE =register("snow_gene","寒冷基因",ModRarity.BLUE);
    public static final DeferredItem<Item> NUT_GENE =register("nut_gene","坚果基因", ModRarity.YELLOW);
    public static final DeferredItem<Item> POTATO_GENE =register("potato_gene","土豆基因", ModRarity.YELLOW);
    public static final DeferredItem<Item> SPORE_GENE =register("spore_gene","孢子基因", ModRarity.PURPLE);
    public static final DeferredItem<Item> CABBAGE_GENE =register("cabbage_gene","卷心菜基因", ModRarity.GREEN);
    public static final DeferredItem<Item> STRONG_GENE =register("strong_gene","壮力基因", ModRarity.ORANGE);







    public static DeferredItem<Item> register(String en, String zh, ModRarity rarity) {
        DeferredItem<Item> item =  MATERIALS.register("material/"+en, () -> new CustomRarityItem(new Item.Properties().component(ModDataComponentTypes.MOD_RARITY,rarity)));
        Rhyme.chineseProviders.add((c)->c.add(item.get(),zh));
        return item;
    }
    public static DeferredItem<Item> register(String en, String zh) {
        return register(en, zh, ModRarity.COMMON);
    }

}
