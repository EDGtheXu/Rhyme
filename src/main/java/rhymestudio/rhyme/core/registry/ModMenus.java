package rhymestudio.rhyme.core.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredRegister;
import rhymestudio.rhyme.core.menu.*;

import java.util.function.Supplier;

import static rhymestudio.rhyme.Rhyme.MODID;

/**
 * 注册屏幕: {@link rhymestudio.rhyme.client.event.ModClientEvent#registerMenuScreens})}
 */
public final class ModMenus {
    public static final DeferredRegister<MenuType<?>> TYPES = DeferredRegister.create(BuiltInRegistries.MENU, MODID);

    public static final Supplier<MenuType<SunCreatorMenu>> SUN_CREATOR_MENU = TYPES.register("sun_creator", () -> new MenuType<>(SunCreatorMenu::new, FeatureFlags.VANILLA_SET));
    public static final Supplier<MenuType<CardUpLevelMenu>> CARD_UP_LEVEL_MENU = TYPES.register("card_upper", () -> new MenuType<>(CardUpLevelMenu::new, FeatureFlags.VANILLA_SET));
    public static final Supplier<MenuType<DaveTradesMenu>> DAVE_TRADES_MENU = TYPES.register("dave_trades", () -> new MenuType<>(DaveTradesMenu::new, FeatureFlags.VANILLA_SET));
    public static final Supplier<MenuType<StaffMenu>> STAFF_MENU = TYPES.register("staff_menu", () -> new MenuType<>(StaffMenu::new, FeatureFlags.VANILLA_SET));
    public static final Supplier<MenuType<ChapterMenu>> CHAPTER_MENU = TYPES.register("chapter_menu", () -> new MenuType<>(ChapterMenu::new, FeatureFlags.VANILLA_SET));


}