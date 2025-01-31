package rhymestudio.rhyme.core.dataSaver.dataComponent;


import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import rhymestudio.rhyme.core.registry.items.IconItems;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.Supplier;

/**
 * 物品等级组件，用于渲染背景和属性加成
 */
public class CardQualityComponentType extends AbstractDataComponent<CardQualityComponentType> {

    public int level;
    public int color;
    public ResourceLocation source;

    public CardQualityComponentType(ItemStack stack) {
        this.readFromNBT(stack.getOrCreateTag());
    }
    public CardQualityComponentType(int level, int color, ResourceLocation source) {
        this.level = level;
        this.color = color;
        this.source = source;
    }

    public static List<CardQualityComponentType> _levels = new ArrayList<>();
    public static final CardQualityComponentType COPPER     = register(0,0xc49a49,IconItems.COPPER);
    public static final CardQualityComponentType SILVER     = register(1,0xb6c2c6 ,IconItems.SILVER);
    public static final CardQualityComponentType GOLD       = register(2, 0xfce804,IconItems.GOLD);
    public static final CardQualityComponentType DIAMOND    = register(3, 0x04a7fc,IconItems.DIAMOND);
    public static final CardQualityComponentType EMERALD    = register(4,0x04fc4a ,IconItems.EMERALD);


    public static CardQualityComponentType register(int level, int color, Supplier<Item> qualityItem) {
        var q = new CardQualityComponentType(level, color, ForgeRegistries.ITEMS.getKey(qualityItem.get()));
        _levels.add(q);
        return q;
    }

    public CardQualityComponentType increaseLevel() {return _levels.get(level < _levels.size() - 1? level + 1 : _levels.size() - 1);}

    public CardQualityComponentType decreaseLevel() {return _levels.get(level > 0 ? level - 1 : 0);}

    public static CardQualityComponentType of(int level) {return _levels.get(level);}

    public Item getQualityItem() {return BuiltInRegistries.ITEM.get(source);}
    public int getQualityColor() {;return color;}

    public boolean tryUpLevel(ItemStack stack){
        var tag = getNBT(stack);
        if(tag == null) return false;
        var i = increaseLevel();
        if(i.equals(this)) return false;
//        i.writeToNBT(tag);
        return true;
    }

    public boolean tryDownLevel(ItemStack stack){
        var tag = getNBT(stack);
        if(tag == null) return false;
        var d = decreaseLevel();
        if(d.equals(this)) return false;
//        writeToNBT(tag);
        return true;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof CardQualityComponentType component)) return false;
        return component.level == level && component.source.equals(source);
    }

    @Override
    public String name() {
        return "card_quality";
    }

    @Override
    public void readFromNBT(CompoundTag tag) {
        CompoundTag t = getNBT(tag);
        if(!t.contains("level") ||!t.contains("color") ||!t.contains("source")){
            setInvalid();
            return;
        }

        level = t.getInt("level");
        color = t.getInt("color");
        source = new ResourceLocation(t.getString("source"));
//        return new CardQualityComponentType(level, color, source);
    }

    @Override
    public void writeToNBT(CompoundTag tag) {
        CompoundTag t = getNBT(tag);
        t.putInt("level", level);
        t.putInt("color", color);
        t.putString("source", source.toString());
    }

}
