package rhymestudio.rhyme.core.registry;

import net.minecraft.network.syncher.EntityDataSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import rhymestudio.rhyme.core.menu.DaveTradesMenu;

import java.util.function.Supplier;

import static rhymestudio.rhyme.Rhyme.MODID;

public class ModEntityDataSerializer {

    public static final DeferredRegister<EntityDataSerializer<?>> ENTITY_DATA_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.ENTITY_DATA_SERIALIZERS, MODID);

    public static Supplier<EntityDataSerializer<DaveTradesMenu.DaveTrades>> DAVE_TRADES_SERIALIZER = ENTITY_DATA_SERIALIZERS.register("dave_trades",()->EntityDataSerializer.forValueType(DaveTradesMenu.DaveTrades.STREAM_CODEC));


}
