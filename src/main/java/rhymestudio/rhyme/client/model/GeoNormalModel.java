package rhymestudio.rhyme.client.model;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import rhymestudio.rhyme.Rhyme;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;

public class GeoNormalModel<T extends GeoEntity> extends DefaultedEntityGeoModel<T> {
    public GeoNormalModel(String path) {
        super(Rhyme.space(path));
    }

    @Override
    public RenderType getRenderType(T animatable, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureResource(animatable));
    }
}