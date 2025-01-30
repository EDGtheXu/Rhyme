package rhymestudio.rhyme.client.render.buffer;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.core.BlockPos;

public class RenderUtil {
    public static void renderDebugBlock(VertexConsumer buffer, BlockPos pos, float size, int r, int g, int b, int a){
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        buffer.vertex(x, y + size, z).color(r,g,b,a);
        buffer.vertex(x + size, y + size, z).color(r,g,b,a);
        buffer.vertex(x + size, y + size, z).color(r,g,b,a);
        buffer.vertex(x + size, y + size, z + size).color(r,g,b,a);
        buffer.vertex(x + size, y + size, z + size).color(r,g,b,a);
        buffer.vertex(x, y + size, z + size).color(r,g,b,a);
        buffer.vertex(x, y + size, z + size).color(r,g,b,a);
        buffer.vertex(x, y + size, z).color(r,g,b,a);

        // BOTTvertex()
        buffer.vertex(x + size, y, z).color(r,g,b,a);
        buffer.vertex(x + size, y, z + size).color(r,g,b,a);
        buffer.vertex(x + size, y, z + size).color(r,g,b,a);
        buffer.vertex(x, y, z + size).color(r,g,b,a);
        buffer.vertex(x, y, z + size).color(r,g,b,a);
        buffer.vertex(x, y, z).color(r,g,b,a);
        buffer.vertex(x, y, z).color(r,g,b,a);
        buffer.vertex(x + size, y, z).color(r,g,b,a);

        // Edgevertex()
        buffer.vertex(x + size, y, z + size).color(r,g,b,a);
        buffer.vertex(x + size, y + size, z + size).color(r,g,b,a);

        // Edgevertex()
        buffer.vertex(x + size, y, z).color(r,g,b,a);
        buffer.vertex(x + size, y + size, z).color(r,g,b,a);

        // Edgevertex()
        buffer.vertex(x, y, z + size).color(r,g,b,a);
        buffer.vertex(x, y + size, z + size).color(r,g,b,a);

        // Edgevertex()
        buffer.vertex(x, y, z).color(r,g,b,a);
        buffer.vertex(x, y + size, z).color(r,g,b,a);
    }
}
