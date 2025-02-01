package rhymestudio.rhyme.core.registry.items;

import net.minecraft.world.item.Item;


import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.dataSaver.dataComponent.ModRarity;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.core.item.AbstractCardItem;
import rhymestudio.rhyme.core.registry.entities.PlantEntities;


import java.util.function.Supplier;

import static rhymestudio.rhyme.Rhyme.add_zh_en;
import static rhymestudio.rhyme.core.item.AbstractCardItem.builder;

public class PlantItems {
    public static final DeferredRegister<Item> PLANTS = DeferredRegister.create(ForgeRegistries.ITEMS,Rhyme.MODID);

    // tip 植物基类
    public static final RegistryObject<Item> SUN_FLOWER = registerPlant("sunflower", "向日葵", ()-> builder(PlantEntities.SUN_FLOWER, 50).build());
    public static final RegistryObject<Item> PEA_ITEM = registerPlant("pea_shooter", "豌豆射手", ()-> builder(PlantEntities.PEA,100).build());
    public static final RegistryObject<Item> SNOW_PEA_ITEM = registerPlant("snow_pea_shooter", "寒冰射手", ()-> builder(PlantEntities.SNOW_PEA,175).rarity(ModRarity.BLUE).build());
    public static final RegistryObject<Item> REPEATER_ITEM = registerPlant("repeater","双重射手", ()-> builder(PlantEntities.REPEATER,200).rarity(ModRarity.GREEN).build());

    // tip 蘑菇类
    public static final RegistryObject<Item> PUFF_SHROOM_ITEM = registerPlant("puff_shroom", "小喷菇", ()-> builder(PlantEntities.PUFF_SHROOM,0).build());
    public static final RegistryObject<Item> SUN_SHROOM_ITEM = registerPlant("sun_shroom", "阳光菇", ()-> builder(PlantEntities.SUN_SHROOM,0).build());

    // tip 土豆雷类
    public static final RegistryObject<Item> POTATO_MINE_ITEM = registerPlant("potato_mine", "土豆雷", ()-> builder(PlantEntities.POTATO_MINE,25).cd(30).build());

    // tip 坚果类
    public static final RegistryObject<Item> NUT_WALL_ITEM = registerPlant("nut_wall","坚果墙", ()-> builder(PlantEntities.WALL_NUT,50).cd(30).build());

    // tip 投手类
    public static final RegistryObject<Item> CABBAGE_PULT_ITEM = registerPlant("cabbage_pult", "卷心菜投手", ()-> builder(PlantEntities.CABBAGE_PULT,100).build());

    // tip 大嘴花类
    public static final RegistryObject<Item> CHOMPER_ITEM = registerPlant("chomper", "大嘴花", ()-> builder(PlantEntities.CHOMPER,150).build());
    /**
     * @param en id
     * @param zh 中文名
     */
    public static  <T extends AbstractPlant> RegistryObject<Item> registerPlant(String en, String zh, Supplier<AbstractCardItem<T>> supplier) {
        RegistryObject<Item> item =  PLANTS.register("plant_card/"+en, supplier);
        add_zh_en(item, zh);
        return item;
    }

//    public static List<RegistryObject<AbstractCardItem>> cardItems = new ArrayList<>();
}
