package rhymestudio.rhyme.core.registry;

import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import rhymestudio.rhyme.core.menu.CardUpLevelMenu;
import rhymestudio.rhyme.core.menu.DaveTradesMenu;
import rhymestudio.rhyme.core.menu.SunCreatorMenu;

import java.util.function.Supplier;

import static rhymestudio.rhyme.Rhyme.MODID;

public final class ModMenus {
    public static final DeferredRegister<MenuType<?>> TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);

    public static final Supplier<MenuType<SunCreatorMenu>> SUN_CREATOR_MENU = TYPES.register("sun_creator", () -> new MenuType<>(SunCreatorMenu::new, FeatureFlags.VANILLA_SET));
    public static final Supplier<MenuType<CardUpLevelMenu>> CARD_UP_LEVEL_MENU = TYPES.register("card_upper", () -> new MenuType<>(CardUpLevelMenu::new, FeatureFlags.VANILLA_SET));
    public static final Supplier<MenuType<DaveTradesMenu>> DAVE_TRADES_MENU = TYPES.register("dave_trades", () -> new MenuType<>(DaveTradesMenu::new, FeatureFlags.VANILLA_SET));


}