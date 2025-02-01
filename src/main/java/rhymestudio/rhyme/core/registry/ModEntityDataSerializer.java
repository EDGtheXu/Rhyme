package rhymestudio.rhyme.core.registry;

import net.minecraft.network.syncher.EntityDataSerializer;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import rhymestudio.rhyme.core.recipe.DaveTrades;

import java.util.function.Supplier;

import static rhymestudio.rhyme.Rhyme.MODID;

public class ModEntityDataSerializer {

    public static final DeferredRegister<EntityDataSerializer<?>> ENTITY_DATA_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, MODID);
//
    public static Supplier<EntityDataSerializer<DaveTrades>> DAVE_TRADES_SERIALIZER = ENTITY_DATA_SERIALIZERS.register("dave_trades",()->EntityDataSerializer.simple(DaveTrades.WRITER, DaveTrades.READER));


//        public static Supplier<EntityDataSerializer<DaveTrades>> DAVE_TRADES_SERIALIZER = ()->EntityDataSerializer.simple(DaveTrades.WRITER, DaveTrades.READER);

}
