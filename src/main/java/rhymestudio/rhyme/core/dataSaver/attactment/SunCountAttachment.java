package rhymestudio.rhyme.core.dataSaver.attactment;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.UnknownNullability;
import rhymestudio.rhyme.core.registry.items.MaterialItems;
import rhymestudio.rhyme.network.s2c.SunCountPacketS2C;
import rhymestudio.rhyme.utils.Computer;

public class SunCountAttachment implements INBTSerializable<CompoundTag> {
    public int sunCount = 0;
    public int x,y,z;
    public int moneys = 0;

    public void sendSunCountUpdate(ServerPlayer player) {
        PacketDistributor.sendToPlayer(player,new SunCountPacketS2C(sunCount, moneys));
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("sunCount", sunCount);
        tag.putInt("x", x);
        tag.putInt("y", y);
        tag.putInt("z", z);
        tag.putInt("moneys", moneys);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag compoundTag) {
        sunCount = compoundTag.getInt("sunCount");
        x = compoundTag.getInt("x");
        y = compoundTag.getInt("y");
        z = compoundTag.getInt("z");
        moneys = compoundTag.getInt("moneys");
    }

    public boolean consumeSun(Player player, int amount) {
        if (sunCount < amount) {
            // 消耗固态阳光
            int count = Computer.getInventoryItemCount(player, MaterialItems.SOLID_SUN.get());
            if(sunCount + count * 25 < amount){
                return false;
            }
            int need = amount - sunCount;
            int consume = need / 25;
            int remain = need % 25;
            int real = remain > 0? consume+1 : consume;
            Computer.consumeInventoryItemCount(player, MaterialItems.SOLID_SUN.get(), real);
            sunCount -= amount - real * 25;

            return true;
        }
        sunCount -= amount;
        return true;
    }
}