package rhymestudio.rhyme.client.render.gui;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractContainerWidget extends AbstractWidget implements ContainerEventHandler {
    public AbstractContainerWidget(int pX, int pY, int pWidth, int pHeight, Component pMessage) {
        super(pX, pY, pWidth, pHeight, pMessage);
    }


    @Override
    public boolean isDragging() {
        return false;
    }

    @Override
    public void setDragging(boolean b) {

    }

    @Override
    public @Nullable GuiEventListener getFocused() {
        return null;
    }

    @Override
    public void setFocused(@Nullable GuiEventListener guiEventListener) {

    }

}
