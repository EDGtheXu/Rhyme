package rhymestudio.rhyme.datagen.lang;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import rhymestudio.rhyme.Rhyme;

import java.util.Arrays;
import java.util.stream.Collectors;


public class ModEnglishProvider extends LanguageProvider {

    public ModEnglishProvider(PackOutput output) {
        super(output, Rhyme.MODID, "en_us");
    }
    public static String toTitleCase(String raw) {
        return Arrays.stream(raw.split("_"))
                .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }
    @Override
    protected void addTranslations() {

        add("creativetab.rhyme", "Rhyme| PVZ");

        add("plantcard.tooltip.consumed_sun","consume sun");
        add("plantcard.tooltip.card_quality","quality");
        add("plantcard.tooltip.card_quality.card_quality_0","copper");
        add("plantcard.tooltip.card_quality.card_quality_1","silver");
        add("plantcard.tooltip.card_quality.card_quality_2","gold");
        add("plantcard.tooltip.card_quality.card_quality_3","diamond");
        add("plantcard.tooltip.card_quality.card_quality_4","emerald");
        add("plantcard.summon_success","you plant one ");
        add("plantcard.tooltip.damage","use remaining");



        add("container.rhyme.card_up_level", "Card Up Level");

        add("container.rhyme.sun_creator", "Sun Creator");
        add("card_up_level.error_tooltip", "Error Ingredient");
        add("card_up_level.missing_base_tooltip", "Missing Base Card");


        add("rhyme.menu.dave_shop", "Crazy Dave's Shop");
        add("dave.trades", "Dave supplies");


        //config
        add("rhyme.configuration.is_open_bgm", "if open bgm");
        add("rhyme.configuration.dave_drop_rate", "dave drop money rate");

        Rhyme.englishProviders.forEach(a->a.accept(this));
//        PlantItems.PLANTS.getEntries().forEach(entity -> add(entity.get(), toTitleCase(entity.getId().getPath())));
//        MaterialItems.MATERIALS.getEntries().forEach(entity -> add(entity.get(), toTitleCase(entity.getId().getPath())));
//        ModBlocks.BLOCK_ITEMS.getEntries().forEach(entity -> add(entity.get(), toTitleCase(entity.getId().getPath())));
//
    }

}