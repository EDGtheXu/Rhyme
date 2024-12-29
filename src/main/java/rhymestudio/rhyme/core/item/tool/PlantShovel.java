package rhymestudio.rhyme.core.item.tool;

import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import rhymestudio.rhyme.core.entity.AbstractPlant;
import rhymestudio.rhyme.core.registry.ModSounds;

import static rhymestudio.rhyme.utils.Computer.getEyeTraceHitResult;

public class PlantShovel extends Item {
    public PlantShovel(Properties properties) {
        super(properties);
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        EntityHitResult result = getEyeTraceHitResult(player,5);
        if(result != null){
            AbstractPlant plant = (AbstractPlant) result.getEntity();
            player.playSound(ModSounds.SHOVEL.get());
            plant.discard();


        }
        var m = Minecraft.getInstance().getResourceManager().listResources(
                "dave_shop",
                p_251575_ -> {
                    String s = p_251575_.getPath();
                    return s.endsWith(".json");
                }
        );
//        ItemStack it = player.getItemInHand(InteractionHand.OFF_HAND);
//        var a = ItemStack.CODEC.encodeStart(JavaOps.INSTANCE,it).result().get();
//        System.out.println(a);
//        var b = ItemStack.CODEC.decode(JavaOps.INSTANCE,a).result().get().getFirst();
//        player.drop(b,true);
//        JsonElement jsonObject = JsonParser.parseString(ICodec.getGson().toJson(a));



        return super.use(level, player, usedHand);
    }
}
