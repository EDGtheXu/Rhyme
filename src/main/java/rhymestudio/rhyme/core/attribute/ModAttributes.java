package rhymestudio.rhyme.core.attribute;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import rhymestudio.rhyme.Rhyme;

public class ModAttributes {

    public static DeferredRegister<Attribute> ATTRIBUTES_TYPES = DeferredRegister.create(Registries.ATTRIBUTE, Rhyme.MODID);

    private static Holder<Attribute> register(String name, Attribute attribute) {
        return Registry.registerForHolder(BuiltInRegistries.ATTRIBUTE, ResourceLocation.fromNamespaceAndPath(Rhyme.MODID,name), attribute);
    }

    public static final DeferredHolder<Attribute,Attribute> MAX_SUN_COUNT = ATTRIBUTES_TYPES.register(
            "max_sun_count",
            ()->new RangedAttribute("attribute.max_sun_count", 2000.0, 2000.0, 9999.0).setSyncable(true)
    );

}
