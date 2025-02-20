package rhymestudio.rhyme.core.checkpoint;

import net.neoforged.bus.api.IEventBus;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.checkpoint.checkpoint.CheckPoint;
import rhymestudio.rhyme.core.checkpoint.checkpoint.CheckPointProviderTypes;
import rhymestudio.rhyme.core.checkpoint.checkpoint.ICheckPoint;
import rhymestudio.rhyme.core.checkpoint.checkpoint.ICheckPointType;
import rhymestudio.rhyme.core.checkpoint.entitygroup.EntityTypeGroupProviderTypes;
import rhymestudio.rhyme.core.registry.ModRegistry;

/**
 * 注册关卡类型
 */
public class ModCheckPoints {

    public static final ModRegistry.CheckPointTypes CHECK_POINTS = ModRegistry.CheckPointTypes.create(Rhyme.MODID);

    public static ICheckPointType<CheckPoint> CHECKPOINT_TYPE = registerSimple("simple_checkpoint");

    static <T extends ICheckPoint<?>> ICheckPointType<T> register(String typeName, ICheckPointType<T> supplier){
        CHECK_POINTS.register(typeName, ()->supplier);

        return supplier;
    }

    static <T extends ICheckPoint<?>, U extends ICheckPointType<T>> ICheckPointType<T> registerSimple(String typeName){
        ICheckPointType<T> type = new ICheckPointType.SimpleCheckPointType<T>(typeName);
        CHECK_POINTS.register(typeName, ()-> type);
        return type;
    }


    public static void register(IEventBus bus){
        CHECK_POINTS.register(bus);
        EntityTypeGroupProviderTypes.ENTITY_TYPE_GROUP_PROVIDERS.register(bus);
        CheckPointProviderTypes.ENTITY_TYPE_GROUP_PROVIDERS.register(bus);
    }
}
