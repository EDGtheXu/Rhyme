package rhymestudio.rhyme.core.dataSaver.attactment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.serialization.JsonOps;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;
import rhymestudio.rhyme.core.dataSaver.dataComponent.StructureStaffComponent;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

import static rhymestudio.rhyme.Rhyme.MODID;

public class StructureStaffAttachment implements INBTSerializable<CompoundTag> {
    private long lastUpdate;
    public BlockPos first;
    public BlockPos second;

    public BlockPos targetPos;

    public StructureStaffComponent tempStructureStaffComponent;
    public StructureStaffComponent selectedStructureStaffComponent;
    public Map<String , StructureStaffComponent> structureStaffComponents = new LinkedHashMap<>();



    public void saveTempStructure(StructureStaffComponent structureStaffComponent){
        tempStructureStaffComponent = structureStaffComponent;
    }

    public void updateLastUpdate() {

    }

    @OnlyIn(Dist.CLIENT)
    public void saveStructure(String name){
        // 保存结构
        if(tempStructureStaffComponent== null) return;
        Player player = Minecraft.getInstance().player;
        if(player == null) return;
        Path CONFIG_PATH = FMLPaths.CONFIGDIR.get().resolve(MODID).resolve("structures");
        Path configFile = CONFIG_PATH.resolve(name + ".json");
        File file = configFile.toFile();
        try {
            if (!file.exists()) {
                CONFIG_PATH.toFile().mkdirs();
                file.createNewFile();

                Writer writer = new FileWriter(file);
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                writer.write(gson.toJson(StructureStaffComponent.CODEC.encodeStart(JsonOps.INSTANCE, tempStructureStaffComponent).result().get()));
                writer.close();
                structureStaffComponents.put(name, tempStructureStaffComponent);

                player.sendSystemMessage(Component.literal("saved to " + file.getAbsolutePath()));
            }else{
                structureStaffComponents.put(name, tempStructureStaffComponent);
                player.sendSystemMessage(Component.literal("already saved to " + file.getAbsolutePath()));
            }

        }catch (Exception e){

        }
    }

    public void loadStructures() {
        Path CONFIG_PATH = FMLPaths.CONFIGDIR.get().resolve(MODID).resolve("structures");
        File[] files = CONFIG_PATH.toFile().listFiles();
        if(files == null) return;
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".json")) {
                try {
                    String json = Files.readString(file.toPath());
                    StructureStaffComponent structureStaffComponent = StructureStaffComponent.CODEC.parse(JsonOps.INSTANCE, GsonHelper.parse(json)).result().get();
                    structureStaffComponents.put(file.getName().replace(".json", ""), structureStaffComponent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int setPoint(BlockPos pos) {
        if(System.currentTimeMillis() -  lastUpdate > 1000 * 10 ) {
            first = null;
            second = null;
        }
        lastUpdate = System.currentTimeMillis();
        if(first == null) {
            first = pos;
            return 1;
        } else if(second == null) {
            second = pos;
            return 2;
        }else{
            first = pos;
            second = null;
            return 1;
        }
    }

    public void clear() {
        first = null;
        second = null;
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
//        if(first!= null && second!= null) {
//            tag.put("first", NbtUtils.writeBlockPos(first));
//            tag.put("second", NbtUtils.writeBlockPos(second));
//        }
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag compoundTag) {
//        NbtUtils.readBlockPos(compoundTag,"first").ifPresent(b -> {
//            first = b;
//        });
//        NbtUtils.readBlockPos(compoundTag,"second").ifPresent(b -> {
//            second = b;
//        });


    }
}