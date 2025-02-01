package rhymestudio.rhyme.core.dataSaver.dataComponent;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;


public class EntitySaverComponentType extends AbstractDataComponent<EntitySaverComponentType> {

    public CompoundTag tag;
    public ResourceLocation type;

    public EntitySaverComponentType(CompoundTag tag, ResourceLocation type) {
        this.tag = tag.copy();
        this.type = type;
    }
    public EntitySaverComponentType(ItemStack stack){
        this.tag = stack.getOrCreateTag();
        this.readFromNBT(tag);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof EntitySaverComponentType component)) return false;
        return component.tag.equals(tag);
    }

    @Override
    public String name() {
        return "entity_saver_component";
    }

    @Override
    public void readFromNBT(CompoundTag tag) {
        CompoundTag t = getNBT(tag);
        if(!t.contains("tag")||!t.contains("type")) {
            setInvalid();
            return;
        }
        this.tag = t.getCompound("tag");
        this.type = new ResourceLocation(t.getString("type"));
    }

    @Override
    public void writeToNBT(CompoundTag tag) {
        CompoundTag t = getNBT(tag);
        t.put("tag", this.tag);
        t.putString("type", type.toString());
    }

}
