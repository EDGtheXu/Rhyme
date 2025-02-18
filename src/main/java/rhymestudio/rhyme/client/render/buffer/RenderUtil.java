package rhymestudio.rhyme.client.render.buffer;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.core.BlockPos;

public class RenderUtil {
    public static void renderDebugBlock(VertexConsumer buffer, BlockPos pos, float size, int r, int g, int b, int a){
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        buffer.addVertex(x, y + size, z).setColor(r,g,b,a);
        buffer.addVertex(x + size, y + size, z).setColor(r,g,b,a);
        buffer.addVertex(x + size, y + size, z).setColor(r,g,b,a);
        buffer.addVertex(x + size, y + size, z + size).setColor(r,g,b,a);
        buffer.addVertex(x + size, y + size, z + size).setColor(r,g,b,a);
        buffer.addVertex(x, y + size, z + size).setColor(r,g,b,a);
        buffer.addVertex(x, y + size, z + size).setColor(r,g,b,a);
        buffer.addVertex(x, y + size, z).setColor(r,g,b,a);

        // BOTTaddVertex()
        buffer.addVertex(x + size, y, z).setColor(r,g,b,a);
        buffer.addVertex(x + size, y, z + size).setColor(r,g,b,a);
        buffer.addVertex(x + size, y, z + size).setColor(r,g,b,a);
        buffer.addVertex(x, y, z + size).setColor(r,g,b,a);
        buffer.addVertex(x, y, z + size).setColor(r,g,b,a);
        buffer.addVertex(x, y, z).setColor(r,g,b,a);
        buffer.addVertex(x, y, z).setColor(r,g,b,a);
        buffer.addVertex(x + size, y, z).setColor(r,g,b,a);

        // EdgeaddVertex()
        buffer.addVertex(x + size, y, z + size).setColor(r,g,b,a);
        buffer.addVertex(x + size, y + size, z + size).setColor(r,g,b,a);

        // EdgeaddVertex()
        buffer.addVertex(x + size, y, z).setColor(r,g,b,a);
        buffer.addVertex(x + size, y + size, z).setColor(r,g,b,a);

        // EdgeaddVertex()
        buffer.addVertex(x, y, z + size).setColor(r,g,b,a);
        buffer.addVertex(x, y + size, z + size).setColor(r,g,b,a);

        // EdgeaddVertex()
        buffer.addVertex(x, y, z).setColor(r,g,b,a);
        buffer.addVertex(x, y + size, z).setColor(r,g,b,a);
    }

    public static void renderAABB(VertexConsumer buffer,
                                  float x1, float y1, float z1, float x2, float y2, float z2,
                                  int r, int g, int b, int a,
                                  float size, float u, float v

    ){
        float w = x2 - x1;
        float h = y2 - y1;
        float d = z2 - z1;

        float scaleX =  w / size;
        float scaleY =  h / size;
        float scaleZ =  d / size;

        buffer.addVertex(x1, y1, z1).setColor(r,g,b,a).setUv(0 + u, 0+v);
        buffer.addVertex(x2, y1, z1).setColor(r,g,b,a).setUv(scaleX+ u, 0+v);
        buffer.addVertex(x2, y2, z1).setColor(r,g,b,a).setUv(scaleX+ u, scaleY+v);
        buffer.addVertex(x1, y2, z1).setColor(r,g,b,a).setUv(0 + u, scaleY+v);

        buffer.addVertex(x1, y1, z2).setColor(r,g,b,a).setUv(0 + u, 0+v);
        buffer.addVertex(x2, y1, z2).setColor(r,g,b,a).setUv(scaleX+ u, 0+v);
        buffer.addVertex(x2, y2, z2).setColor(r,g,b,a).setUv(scaleX+ u, scaleY+v);
        buffer.addVertex(x1, y2, z2).setColor(r,g,b,a).setUv(0 + u, scaleY+v);

        buffer.addVertex(x1, y1, z1).setColor(r,g,b,a).setUv(0+ u, 0+v);
        buffer.addVertex(x1, y1, z2).setColor(r,g,b,a).setUv(scaleZ+ u, 0+v);
        buffer.addVertex(x1, y2, z2).setColor(r,g,b,a).setUv(scaleZ+ u, scaleY+v);
        buffer.addVertex(x1, y2, z1).setColor(r,g,b,a).setUv(0 + u, scaleY+v);

        buffer.addVertex(x2, y1, z1).setColor(r,g,b,a).setUv(0+ u, 0+v);
        buffer.addVertex(x2, y1, z2).setColor(r,g,b,a).setUv(scaleZ+ u, 0+v);
        buffer.addVertex(x2, y2, z2).setColor(r,g,b,a).setUv(scaleZ+ u, scaleY+v);
        buffer.addVertex(x2, y2, z1).setColor(r,g,b,a).setUv(0 + u, scaleY+v);

        buffer.addVertex(x1, y1, z1).setColor(r,g,b,a).setUv(0+ u, 0+v);
        buffer.addVertex(x1, y1, z2).setColor(r,g,b,a).setUv(scaleZ+ u, 0+v);
        buffer.addVertex(x2, y1, z2).setColor(r,g,b,a).setUv(scaleZ+ u, scaleX+v);
        buffer.addVertex(x2, y1, z1).setColor(r,g,b,a).setUv(0 + u, scaleX+v);

        buffer.addVertex(x1, y2, z1).setColor(r,g,b,a).setUv(0+ u, 0+v);
        buffer.addVertex(x1, y2, z2).setColor(r,g,b,a).setUv(scaleZ+ u, 0+v);
        buffer.addVertex(x2, y2, z2).setColor(r,g,b,a).setUv(scaleZ+ u, scaleX+v);
        buffer.addVertex(x2, y2, z1).setColor(r,g,b,a).setUv(0+ u, scaleX+v);

    }

    public static void renderAABBOutLine(VertexConsumer buffer,
                                  float x1, float y1, float z1, float x2, float y2, float z2,
                                  int r, int g, int b, int a,
                                  float size

    ){

        buffer.addVertex(x1, y1, z1).setColor(r,g,b,a);
//        buffer.addVertex(x2, y1, z1).setColor(r,g,b,a);
//        buffer.addVertex(x2, y2, z1).setColor(r,g,b,a);
        buffer.addVertex(x1, y2, z1).setColor(r,g,b,a);

//        buffer.addVertex(x1, y1, z2).setColor(r,g,b,a);
        buffer.addVertex(x2, y1, z2).setColor(r,g,b,a);
        buffer.addVertex(x2, y2, z2).setColor(r,g,b,a);
//        buffer.addVertex(x1, y2, z2).setColor(r,g,b,a);

//        buffer.addVertex(x1, y1, z1).setColor(r,g,b,a);
        buffer.addVertex(x1, y1, z2).setColor(r,g,b,a);
        buffer.addVertex(x1, y2, z2).setColor(r,g,b,a);
//        buffer.addVertex(x1, y2, z1).setColor(r,g,b,a);

        buffer.addVertex(x2, y1, z1).setColor(r,g,b,a);
//        buffer.addVertex(x2, y1, z2).setColor(r,g,b,a);
//        buffer.addVertex(x2, y2, z2).setColor(r,g,b,a);
        buffer.addVertex(x2, y2, z1).setColor(r,g,b,a);

        buffer.addVertex(x1, y1, z1).setColor(r,g,b,a);
        buffer.addVertex(x1, y1, z2).setColor(r,g,b,a);
        buffer.addVertex(x1, y1, z2).setColor(r,g,b,a);
        buffer.addVertex(x2, y1, z2).setColor(r,g,b,a);
        buffer.addVertex(x2, y1, z2).setColor(r,g,b,a);
        buffer.addVertex(x2, y1, z1).setColor(r,g,b,a);
        buffer.addVertex(x2, y1, z1).setColor(r,g,b,a);
        buffer.addVertex(x1, y1, z1).setColor(r,g,b,a);

        buffer.addVertex(x1, y2, z1).setColor(r,g,b,a);
        buffer.addVertex(x1, y2, z2).setColor(r,g,b,a);
        buffer.addVertex(x1, y2, z2).setColor(r,g,b,a);
        buffer.addVertex(x2, y2, z2).setColor(r,g,b,a);
        buffer.addVertex(x2, y2, z2).setColor(r,g,b,a);
        buffer.addVertex(x2, y2, z1).setColor(r,g,b,a);
        buffer.addVertex(x2, y2, z1).setColor(r,g,b,a);
        buffer.addVertex(x1, y2, z1).setColor(r,g,b,a);

    }
}
