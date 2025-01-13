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

        add("plantcard.tooltip.consumed_sun","消耗阳光");
        add("plantcard.tooltip.card_quality","品质");
        add("plantcard.tooltip.card_quality.card_quality_0","黄铜");
        add("plantcard.tooltip.card_quality.card_quality_1","白银");
        add("plantcard.tooltip.card_quality.card_quality_2","黄金");
        add("plantcard.tooltip.card_quality.card_quality_3","钻石");
        add("plantcard.tooltip.card_quality.card_quality_4","翡翠");
        add("plantcard.summon_success","你种植了 ");
        add("plantcard.tooltip.damage","使用次数");
        add("plantcard.not_enough_sun","你没有足够阳光");

        add("container.rhyme.sun_creator", "光萃台");

        add("container.rhyme.card_up_level", "卡片进阶台");
        add("card_up_level.error_tooltip", "材料不正确");
        add("card_up_level.missing_base_tooltip", "缺少低级卡片");


        add("rhyme.menu.dave_shop", "疯狂戴夫商店");
        add("dave.trades", "窝闷提供");

        //config
        add("rhyme.configuration.is_open_bgm", "打开背景音乐");
        add("rhyme.configuration.dave_drop_rate", "戴夫掉落金币比率");
        add("rhyme.configuration.plant_consume_addition_step", "额外消耗阳光/植物个");
        add("rhyme.configuration.is_open_effect_overlay", "打开特效颜色遮罩");


        Rhyme.chineseProviders.forEach(a->a.accept(this));

    }

}