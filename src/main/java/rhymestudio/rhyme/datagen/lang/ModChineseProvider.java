package rhymestudio.rhyme.datagen.lang;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import rhymestudio.rhyme.Rhyme;

import static rhymestudio.rhyme.Rhyme.MODID;

public class ModChineseProvider extends LanguageProvider {
    public ModChineseProvider(PackOutput output) {
        super(output, MODID, "zh_cn");
    }

    @Override
    protected void addTranslations() {

        add("creativetab.rhyme", "开花与终结有限公司");
        add("creativetab.rhyme.materials", "开花与终结有限公司：产品");
        add("creativetab.rhyme.cards", "开花与终结有限公司：种子包");
        add("creativetab.rhyme.blocks", "开花与终结有限公司：方块");
        add("creativetab.rhyme.armors", "开花与终结有限公司：护具");

        add("container.rhyme.sun_creator", "光萃台");
        add("plantcard.tooltip.consumed_sun","消耗阳光");
        add("plantcard.tooltip.card_quality","品质");
        add("plantcard.tooltip.card_quality.card_quality_0","黄铜");
        add("plantcard.tooltip.card_quality.card_quality_1","白银");
        add("plantcard.tooltip.card_quality.card_quality_2","黄金");
        add("plantcard.tooltip.card_quality.card_quality_3","钻石");
        add("plantcard.tooltip.card_quality.card_quality_4","翡翠");
        add("plantcard.summon_success","你种植了 ");

        Rhyme.chineseProviders.forEach(a->a.accept(this));

    }

}