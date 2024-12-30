package rhymestudio.rhyme.plugin.jei.suncreator;

import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JeiBackGround implements IDrawable {
    private final int width;
    private final int height;
    private final @Nullable ResourceLocation icon;

    public JeiBackGround(int width, int height, @Nullable ResourceLocation icon) {
        this.width = width;
        this.height = height;
        this.icon = icon;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void draw(@NotNull GuiGraphics guiGraphics, int i, int i1) {
        if (icon != null) {
            guiGraphics.blit(icon, 0, 0, 0, 0, width, height, width, height);
        }
    }
}
