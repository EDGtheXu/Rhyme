package rhymestudio.rhyme.config.Codec;

import com.google.gson.Gson;
import net.minecraft.world.item.ItemStack;

public class CodecRegister {
    public static Gson getGson() {return ICodec.getGson();}
    public static void registerCodecs() {
        ICodec.register(Price.class, new PriceCodec());
        ICodec.register(ItemStack.class, ItemStackCodec.ITEM_STACK_COMPONENT_CODEC);

    }
}
