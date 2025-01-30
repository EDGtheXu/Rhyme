package rhymestudio.rhyme.client.model;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 注册物品的头部盔甲模型方法：
 * 在models目录下对应位置创建"_head.json"结尾的模型
 */
@OnlyIn(Dist.CLIENT)
public class ModelUtils {

    public static Map<Item, List<ResourceLocation>> HEAD_MODEL_ITEMS = new HashMap<>();


    public static ResourceLocation getHeadModelResourceLocation(Item item,int index) {
        ResourceLocation location = ForgeRegistries.ITEMS.getKey(item);
        String name =  location.getPath() + "_head" + (index > 1? "_" + index : "");
        return new ResourceLocation(location.getNamespace(), name);
    }


}
