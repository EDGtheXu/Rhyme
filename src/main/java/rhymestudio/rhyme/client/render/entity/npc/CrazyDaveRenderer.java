//package rhymestudio.rhyme.client.render.entity.npc;
//
//import net.minecraft.client.renderer.entity.EntityRendererProvider;
//import net.minecraft.client.renderer.entity.MobRenderer;
//import net.minecraft.resources.ResourceLocation;
//import rhymestudio.rhyme.Rhyme;
//import rhymestudio.rhyme.client.model.CrazyDaveModel;
//import rhymestudio.rhyme.core.entity.CrazyDave;
//
//public class CrazyDaveRenderer<T extends CrazyDave> extends MobRenderer<T, CrazyDaveModel<T>> {
//
//    public CrazyDaveRenderer(EntityRendererProvider.Context context) {
//        super(context,new CrazyDaveModel<>(context.bakeLayer(CrazyDaveModel.LAYER_LOCATION)), 0.5F);
//    }
//
//    @Override
//    public ResourceLocation getTextureLocation(CrazyDave crazyDave) {
//        String s = "textures/entity/zombies/normal_zombie.png";
//        return Rhyme.space(s);
//    }
//}
