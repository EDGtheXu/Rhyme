package rhymestudio.rhyme.client.render.gui.config;

import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

public class ConfigScreen extends OptionsSubScreen {
    public ConfigScreen(Screen lastScreen, Options options, Component title) {
        super(lastScreen, options, title);
    }

    @Override
    protected void addOptions() {

    }
}
