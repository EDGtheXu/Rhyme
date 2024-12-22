package rhymestudio.rhyme.core.registry.items;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.dataSaver.dataComponent.CardQualityComponent;
import rhymestudio.rhyme.core.dataSaver.dataComponent.ItemDataMapComponent;
import rhymestudio.rhyme.core.dataSaver.dataComponent.ModRarity;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.core.item.AbstractCardItem;
import rhymestudio.rhyme.core.registry.entities.PlantEntities;
import rhymestudio.rhyme.core.registry.ModDataComponentTypes;

import java.util.ArrayList;
import java.util.List;

public class PlantItems {
    public static final DeferredRegister.Items PLANTS = DeferredRegister.createItems(Rhyme.MODID);

    // tip 植物基类
    public static final DeferredItem<Item> SUN_FLOWER = registerPlant("sunflower", "向日葵", PlantEntities.SUN_FLOWER, 50);
    public static final DeferredItem<Item> PEA_ITEM = registerPlant("pea_shooter", "豌豆射手", PlantEntities.PEA,100);
    public static final DeferredItem<Item> SNOW_PEA_ITEM = registerPlant("snow_pea_shooter", "寒冰射手", PlantEntities.SNOW_PEA,175,10,ModRarity.BLUE);
    public static final DeferredItem<Item> REPEATER_ITEM = registerPlant("repeater","双重射手", PlantEntities.DOUBLE_PEA,200,15,ModRarity.GREEN);

    // tip 蘑菇类
    public static final DeferredItem<Item> PUFF_SHROOM_ITEM = registerPlant("puff_shroom", "小喷菇", PlantEntities.PUFF_SHROOM,0);

    // tip 土豆雷类
    public static final DeferredItem<Item> POTATO_MINE_ITEM = registerPlant("potato_mine", "土豆雷", PlantEntities.POTATO_MINE,25);

    // tip 坚果类
    public static final DeferredItem<Item> NUT_WALL_ITEM = registerPlant("nut_wall","坚果墙", PlantEntities.WALL_NUT,50);

    // tip 投手类
    public static final DeferredItem<Item> CABBAGE_PULT_ITEM = registerPlant("cabbage_pult", "卷心菜投手", PlantEntities.CABBAGE_PULT,100);

    // tip 大嘴花类
    public static final DeferredItem<Item> CHOMPER = registerPlant("chomper", "大嘴花", PlantEntities.CHOMPER,150);
    /**
     * @param en id
     * @param zh 中文名
     * @param entityType 召唤植物类型
     * @param consumeSun 消耗阳光数量
     * @param duration 默认铜卡使用次数 10
     * @param rarity 默认白色
     * @return
     */
    public static  <T extends AbstractPlant> DeferredItem<Item> registerPlant(String en, String zh,DeferredHolder<EntityType<?>, EntityType<T>> entityType, int consumeSun, int duration, ModRarity rarity) {
        DeferredItem<Item> item =  PLANTS.register("plant_card/"+en, () -> new AbstractCardItem<>(
                new Item.Properties()
                        .component(ModDataComponentTypes.MOD_RARITY, rarity)
                        .component(ModDataComponentTypes.CARD_QUALITY, CardQualityComponent.COPPER)
                        .stacksTo(1)
                        .durability(duration)
                        .component(ModDataComponentTypes.ITEM_DAT_MAP,
                                ItemDataMapComponent.builder()
                                        .add("key", "name", "1.23")
                                        .add("key", "name2", "1.24")
                                        .add("key", "name3", "1.25")
                                        .add("key2", "name", "1.26")
                                        .build()
                        )
                , entityType, consumeSun));
//        Optional.ofNullable(cardItems).ifPresent(list -> list.add(item));
        Rhyme.chineseProviders.add((c)->c.add(item.get(),zh));
        return item;
    }

    public static  <T extends AbstractPlant>DeferredItem<Item> registerPlant(String en, String zh,DeferredHolder<EntityType<?>, EntityType<T>> entityType, int consumeSun, int duration) {
        return registerPlant(en, zh,entityType, consumeSun, duration, ModRarity.COMMON);
    }

    public static  <T extends AbstractPlant>DeferredItem<Item> registerPlant(String en, String zh, DeferredHolder<EntityType<?>, EntityType<T>> entityType, int consumeSun) {
        return registerPlant(en, zh,entityType, consumeSun, 10);
    }

    public static List<DeferredItem<AbstractCardItem>> cardItems = new ArrayList<>();
}
