package rhymestudio.rhyme.client.event;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;
import rhymestudio.rhyme.Rhyme;

@EventBusSubscriber(modid = Rhyme.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ModKeyBindings {

    // 用于防止服务端启动报错
    public static boolean isShifting = false;

    @SubscribeEvent
    public static void keyBinding(RegisterKeyMappingsEvent event) {
        event.register(SHOW_MENU.get());
    }

    public static final Lazy<KeyMapping> SHOW_MENU = Lazy.of(() -> new KeyMapping(
            "key.rhyme.show_menu",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_Y,
            "key.categories.gameplay"
    ));
}
