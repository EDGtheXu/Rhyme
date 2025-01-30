package rhymestudio.rhyme.datagen.lang;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;
import rhymestudio.rhyme.Rhyme;

import java.util.Arrays;
import java.util.stream.Collectors;


public class ModEnglishProvider extends LanguageProvider {

    public ModEnglishProvider(PackOutput output) {
        super(output, Rhyme.MODID, "en_us");
    }
    public static String toTitleCase(String raw) {
        return Arrays.stream(raw.replaceFirst("material.|plant_card.|armor.|egg.", "")
                        .split("[_/]"))
                .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }
    @Override
    protected void addTranslations() {

        add("creativetab.rhyme", "Rhyme| PVZ");

        add("plantcard.tooltip.consumed_sun","Consume Sun");
        add("plantcard.tooltip.card_quality","Quality");
        add("plantcard.tooltip.card_quality.card_quality_0","Copper");
        add("plantcard.tooltip.card_quality.card_quality_1","Silver");
        add("plantcard.tooltip.card_quality.card_quality_2","Gold");
        add("plantcard.tooltip.card_quality.card_quality_3","Diamond");
        add("plantcard.tooltip.card_quality.card_quality_4","Emerald");
        add("plantcard.summon_success","You Plant One ");
        add("plantcard.tooltip.damage","Use Remaining");
        add("plantcard.not_enough_sun","Not Enough Sun");
        add("plantcard.cannot_put_plant","You Cannot Put Plant Here!");

        add("tooltip.rhyme.plant_putter.entity_picked","Entity Picked: ");





        add("container.rhyme.card_up_level", "Card Up Level");

        add("container.rhyme.sun_creator", "Sun Creator");
        add("card_up_level.error_tooltip", "Error Ingredient");
        add("card_up_level.missing_base_tooltip", "Missing Base Card");


        add("rhyme.menu.dave_shop", "Crazy Dave's Shop");
        add("dave.trades", "Dave Supplies");


        //config
        add("rhyme.configuration.is_open_bgm", "If Open Bgm");
        add("rhyme.configuration.dave_drop_rate", "Dave Drop Money Rate");
        add("rhyme.configuration.plant_consume_addition_step", "Consume Addition Sun Per Plant");
        add("rhyme.configuration.is_open_effect_overlay", "If Open Effect Overlay");




        Rhyme.englishProviders.forEach(a->a.accept(this));
//        PlantItems.PLANTS.getEntries().forEach(entity -> add(entity.get(), toTitleCase(entity.getId().getPath())));
//        MaterialItems.MATERIALS.getEntries().forEach(entity -> add(entity.get(), toTitleCase(entity.getId().getPath())));
//        ModBlocks.BLOCK_ITEMS.getEntries().forEach(entity -> add(entity.get(), toTitleCase(entity.getId().getPath())));
//
    }

}