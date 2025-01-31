package rhymestudio.rhyme.datagen.dataPack;

import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.registries.ForgeRegistries;
import rhymestudio.rhyme.datagen.biome.ModBiomeModifier;
import rhymestudio.rhyme.datagen.tag.ModTags;

public class RegisterDataPack {
    public static final RegistrySetBuilder DATA_BUILDER = new RegistrySetBuilder()

            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, ModBiomeModifier::createBiomeModifier)
            .add(Registries.DAMAGE_TYPE, ModTags.DamageTypes::createDamageTypes)
//            .add(ModRegistry.DAVE_SHOP, DaveShopProvider::createDataPack)
            ;


}
