package rhymestudio.rhyme.core.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredRegister;
import rhymestudio.rhyme.core.menu.CardUpLevelMenu;
import rhymestudio.rhyme.core.menu.SunCreatorMenu;

import java.util.function.Supplier;

import static rhymestudio.rhyme.Rhyme.MODID;

public final class ModMenus {
    public static final DeferredRegister<MenuType<?>> TYPES = DeferredRegister.create(BuiltInRegistries.MENU, MODID);

    public static final Supplier<MenuType<SunCreatorMenu>> SUN_CREATOR_MENU = TYPES.register("sun_creator", () -> new MenuType<>(SunCreatorMenu::new, FeatureFlags.VANILLA_SET));
    public static final Supplier<MenuType<CardUpLevelMenu>> CARD_UP_LEVEL_MENU = TYPES.register("card_upper", () -> new MenuType<>(CardUpLevelMenu::new, FeatureFlags.VANILLA_SET));


}