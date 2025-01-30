package rhymestudio.rhyme.core.registry.items;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.dataSaver.dataComponent.ModRarity;
import rhymestudio.rhyme.core.item.CustomRarityItem;


import java.util.List;
import java.util.function.Function;

import static rhymestudio.rhyme.Rhyme.add_zh_en;

public class MaterialItems {
    public static final DeferredRegister<Item> MATERIALS =DeferredRegister.create(ForgeRegistries.ITEMS,Rhyme.MODID);

//    public static final RegistryObject<Item> SUN_ITEM = register("sun_item","阳光");

    public static final RegistryObject<Item> SOLID_SUN =register("solid_sun","固态阳光",ModRarity.BLUE);
    public static final RegistryObject<Item> GENERAL_SEED =register("general_seed","通用种子");
    public static final RegistryObject<Item> PLANT_GENE =register("plant_gene","植物基因");
    public static final RegistryObject<Item> PEA_GENE =register("pea_gene","豌豆基因",ModRarity.GREEN);
    public static final RegistryObject<Item> SNOW_GENE =register("snow_gene","冰霜基因",ModRarity.BLUE);
    public static final RegistryObject<Item> NUT_GENE =register("nut_gene","筑墙基因", ModRarity.YELLOW);
    public static final RegistryObject<Item> HIDDEN_GENE =register("hidden_gene","藏匿基因", ModRarity.YELLOW);
    public static final RegistryObject<Item> MUSHROOM_GENE =register("mushroom_gene","孢菌基因", ModRarity.PURPLE);
    public static final RegistryObject<Item> THROWABLE_GENE =register("throwable_gene","掷物基因", ModRarity.GREEN);
    public static final RegistryObject<Item> ANGER_GENE =register("anger_gene","易怒基因",ModRarity.RED);
    public static final RegistryObject<Item> STRONG_GENE =register("strong_gene","壮力基因", ModRarity.ORANGE);


    public static final RegistryObject<Item> SILVER_COIN = register("silver_coin", "银币", p->p/*.component(ModDataComponentTypes.ITEM_DAT_MAP, ItemDataMapComponent.builder().add("money","value",5).build())*/);
    public static final RegistryObject<Item> GOLD_COIN = register("gold_coin", "金币", p->p/*.component(ModDataComponentTypes.ITEM_DAT_MAP, ItemDataMapComponent.builder().add("money","value",10).build()).component(ModDataComponentTypes.MOD_RARITY,ModRarity.YELLOW)*/);

    public static final RegistryObject<Item> TACO = register("taco", "玉米卷",p->p/*.component(ModDataComponentTypes.MOD_RARITY,ModRarity.ORANGE)*/.food(new FoodProperties.Builder()
                    .nutrition(5).effect(() -> new MobEffectInstance(MobEffects.SATURATION, 50, 0), 1f)
                    .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 50, 0), 1f).build()));
//            5,50,true,2, Optional.of(ItemStack.EMPTY), List.of(
//                    new FoodProperties.PossibleEffect(()->new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 0),0.5f),
//                    new FoodProperties.PossibleEffect(()->new MobEffectInstance(MobEffects.SATURATION, 50, 0),1f)
//    ))));


    public static RegistryObject<Item> register(String en, String zh, Function<Item.Properties, Item.Properties> properties) {
        RegistryObject<Item> item =  MATERIALS.register("material/"+en, () -> new CustomRarityItem(properties.apply(new Item.Properties())));
        add_zh_en(item, zh);
        return item;
    }

    public static RegistryObject<Item> register(String en, String zh, ModRarity rarity) {
        return register(en, zh, p->p/*.component(ModDataComponentTypes.MOD_RARITY,rarity)*/);
    }
    public static RegistryObject<Item> register(String en, String zh) {
        return register(en, zh, ModRarity.COMMON);
    }




}
