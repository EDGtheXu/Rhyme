package rhymestudio.rhyme.core.registry;

import net.minecraft.network.syncher.EntityDataSerializer;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

import static rhymestudio.rhyme.Rhyme.MODID;

public class ModEntityDataSerializer {

    public static final DeferredRegister<EntityDataSerializer<?>> ENTITY_DATA_SERIALIZERS = DeferredRegister.create(ForgeRegistries.ENTITY_DATA_SERIALIZERS.get(), MODID);

//    public static Supplier<EntityDataSerializer<DaveTrades>> DAVE_TRADES_SERIALIZER = ENTITY_DATA_SERIALIZERS.register("dave_trades",()->EntityDataSerializer.simple(DaveTrades.STREAM_CODEC));


}
