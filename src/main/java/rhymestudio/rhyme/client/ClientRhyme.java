package rhymestudio.rhyme.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import rhymestudio.rhyme.config.ClientConfig;

@Mod(value = "rhyme", dist = Dist.CLIENT)
public class ClientRhyme {

    public  ClientRhyme(IEventBus modEventBus, ModContainer container) {



        container.registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}
