package rhymestudio.rhyme.core.dataSaver.attactment;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.UnknownNullability;
import rhymestudio.rhyme.network.s2c.SunCountPacketS2C;

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

    public boolean consumeSun(int amount) {
        if (sunCount < amount) {
            return false;
        }
        sunCount -= amount;
        return true;
    }
}