package rhymestudio.rhyme;

import com.google.gson.Gson;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import org.slf4j.Logger;

import rhymestudio.rhyme.config.Codec.*;
import rhymestudio.rhyme.config.MainConfig;
import rhymestudio.rhyme.datagen.lang.ModChineseProvider;
import rhymestudio.rhyme.datagen.biome.ModBiomes;
import rhymestudio.rhyme.core.registry.ModRecipes;
import rhymestudio.rhyme.core.registry.*;
import rhymestudio.rhyme.datagen.lang.ModEnglishProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static rhymestudio.rhyme.datagen.lang.ModEnglishProvider.toTitleCase;

@Mod(Rhyme.MODID)
public class Rhyme {
    public static final String MODID = "rhyme";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static ResourceLocation space(String path){return ResourceLocation.fromNamespaceAndPath(MODID, path);}

    public static List<Consumer<ModChineseProvider>> chineseProviders = new ArrayList<>();
    public static List<Consumer<ModEnglishProvider>> englishProviders = new ArrayList<>();
    public static void add_zh_en(DeferredItem<Item> item, String zh){
        Rhyme.chineseProviders.add((c)->c.add(item.get(),zh));
        Rhyme.englishProviders.add((c)->c.add(item.get(),toTitleCase(item.getId().getPath())));
    }
    public static <T extends Entity> void add_zh_en(DeferredHolder<EntityType<?>,EntityType<T>> e, String zh){
        Rhyme.chineseProviders.add((c)->c.add(e.get(),zh));
        Rhyme.englishProviders.add((c)->c.add(e.get(),toTitleCase(e.getId().getPath())));
    }
    public Rhyme(IEventBus modEventBus, ModContainer modContainer) {
        ModItems.registerItems(modEventBus);
        ModEntities.registerEntities(modEventBus);
        ModArmorMaterials.ARMOR_MATERIALS.register(modEventBus);

        ModBlocks.BLOCK_ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModBlocks.BLOCK_ENTITIES.register(modEventBus);

        ModTabs.TABS.register(modEventBus);
        ModDataComponentTypes.TYPES.register(modEventBus);
        ModEffects.EFFECTS.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModMenus.TYPES.register(modEventBus);
        ModBiomes.register(modEventBus);
        ModAttachments.TYPES.register(modEventBus);
        ModParticles.PARTICLES.register(modEventBus);
        ModSounds.SOUNDS.register(modEventBus);
        ModEntityDataSerializer.ENTITY_DATA_SERIALIZERS.register(modEventBus);
        modContainer.registerConfig(ModConfig.Type.COMMON,Config.SPEC);


        MainConfig.cfg.load();
        CodecRegister.registerCodecs();

        Gson gson = ICodec.getGson();

        Price price = new Price(10, ItemStack.EMPTY);
        String json = gson.toJson(price, Price.class);
        LOGGER.info(json);

        Price price2 = gson.fromJson(json, Price.class);
        LOGGER.info(price2.toString());

    }

}
