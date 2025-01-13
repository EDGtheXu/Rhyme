package rhymestudio.rhyme.core.registry.items;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.dataSaver.dataComponent.ItemDataMapComponent;
import rhymestudio.rhyme.core.dataSaver.dataComponent.ModRarity;
import rhymestudio.rhyme.core.item.CustomRarityItem;
import rhymestudio.rhyme.core.registry.ModDataComponentTypes;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static rhymestudio.rhyme.Rhyme.add_zh_en;

public class MaterialItems {
    public static final DeferredRegister.Items MATERIALS = DeferredRegister.createItems(Rhyme.MODID);

//    public static final DeferredItem<Item> SUN_ITEM = register("sun_item","阳光");

    public static final DeferredItem<Item> SOLID_SUN =register("solid_sun","固态阳光",ModRarity.BLUE);
    public static final DeferredItem<Item> GENERAL_SEED =register("general_seed","通用种子");
    public static final DeferredItem<Item> PLANT_GENE =register("plant_gene","植物基因");
    public static final DeferredItem<Item> PEA_GENE =register("pea_gene","豌豆基因",ModRarity.GREEN);
    public static final DeferredItem<Item> SNOW_GENE =register("snow_gene","冰霜基因",ModRarity.BLUE);
    public static final DeferredItem<Item> NUT_GENE =register("nut_gene","筑墙基因", ModRarity.YELLOW);
    public static final DeferredItem<Item> HIDDEN_GENE =register("hidden_gene","藏匿基因", ModRarity.YELLOW);
    public static final DeferredItem<Item> MUSHROOM_GENE =register("mushroom_gene","孢菌基因", ModRarity.PURPLE);
    public static final DeferredItem<Item> THROWABLE_GENE =register("throwable_gene","掷物基因", ModRarity.GREEN);
    public static final DeferredItem<Item> ANGER_GENE =register("anger_gene","易怒基因",ModRarity.RED);
    public static final DeferredItem<Item> STRONG_GENE =register("strong_gene","壮力基因", ModRarity.ORANGE);


    public static final DeferredItem<Item> SILVER_COIN = register("silver_coin", "银币", p->p.component(ModDataComponentTypes.ITEM_DAT_MAP, ItemDataMapComponent.builder().add("money","value",5).build()));
    public static final DeferredItem<Item> GOLD_COIN = register("gold_coin", "金币", p->p.component(ModDataComponentTypes.ITEM_DAT_MAP, ItemDataMapComponent.builder().add("money","value",10).build()).component(ModDataComponentTypes.MOD_RARITY,ModRarity.YELLOW));

    public static final DeferredItem<Item> TACO = register("taco", "玉米卷",p->p.component(ModDataComponentTypes.MOD_RARITY,ModRarity.ORANGE).food(new FoodProperties(
            5,50,true,2, Optional.of(ItemStack.EMPTY), List.of(
                    new FoodProperties.PossibleEffect(()->new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 0),0.8f),
                    new FoodProperties.PossibleEffect(()->new MobEffectInstance(MobEffects.ABSORPTION, 200, 0),0.2f),
                    new FoodProperties.PossibleEffect(()->new MobEffectInstance(MobEffects.SATURATION, 200, 0),1f)
    ))));


    public static DeferredItem<Item> register(String en, String zh, Function<Item.Properties, Item.Properties> properties) {
        DeferredItem<Item> item =  MATERIALS.register("material/"+en, () -> new CustomRarityItem(properties.apply(new Item.Properties())));
        add_zh_en(item, zh);
        return item;
    }

    public static DeferredItem<Item> register(String en, String zh, ModRarity rarity) {
        return register(en, zh, p->p.component(ModDataComponentTypes.MOD_RARITY,rarity));
    }
    public static DeferredItem<Item> register(String en, String zh) {
        return register(en, zh, ModRarity.COMMON);
    }




}
