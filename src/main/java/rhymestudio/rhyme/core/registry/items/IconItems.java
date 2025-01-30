package rhymestudio.rhyme.core.registry.items;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.item.Item;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.item.CustomRarityItem;

public class IconItems {
    public static final DeferredRegister<Item> QUALITY_ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,Rhyme.MODID);
    public static final RegistryObject<Item> COPPER   = register("quality/card_quality_0");
    public static final RegistryObject<Item> SILVER   = register("quality/card_quality_1");
    public static final RegistryObject<Item> GOLD     = register("quality/card_quality_2");
    public static final RegistryObject<Item> DIAMOND  = register("quality/card_quality_3");
    public static final RegistryObject<Item> EMERALD  = register("quality/card_quality_4");



    private static RegistryObject<Item> register(String name){
        return QUALITY_ITEMS.register(name, () -> new CustomRarityItem(new Item.Properties()));
    }

    public static void adjustItemEntityPose(PoseStack poseStack){

        poseStack.scale(0.99f, 0.99f, 0.9f);
    }
}
