package rhymestudio.rhyme.core.item.tool;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.serialization.JsonOps;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.fml.loading.FMLPaths;
import rhymestudio.rhyme.client.event.ModKeyBindings;
import rhymestudio.rhyme.core.dataSaver.dataComponent.StructureStaffComponent;
import rhymestudio.rhyme.core.menu.StaffMenu;
import rhymestudio.rhyme.core.registry.ModAttachments;
import rhymestudio.rhyme.network.c2s.ClientEventBoundPacket;
import rhymestudio.rhyme.network.c2s.GenerateStructurePacket;
import rhymestudio.rhyme.utils.AdapterUtils;

import java.io.*;
import java.nio.file.Path;

import static rhymestudio.rhyme.Rhyme.MODID;

public class StructureStaff extends Item {

    // client
    public static boolean press = false;

    public StructureStaff(Properties properties) {
        super(properties);
    }



    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {

        ItemStack itemStack = player.getItemInHand(usedHand);

        var data = player.getData(ModAttachments.STRUCTURE_STAFF_STORAGE.get());
        final BlockHitResult result = getPlayerPOVHitResult(player.level(), player, ClipContext.Fluid.SOURCE_ONLY);
        BlockPos eyePos = result.getBlockPos();



        if(level.isClientSide()) {
//            var component = itemStack.get(ModDataComponentTypes.STRUCTURE_STAFF);
            if(ModKeyBindings.isShifting){
                if(level.getBlockState(eyePos).is(Blocks.AIR)){
                    // 命中空气，清除顶点
                    player.sendSystemMessage(Component.literal("clear vertex "));
                    return InteractionResultHolder.pass(itemStack);
                }
                // 命中方块，添加顶点
                int count = data.setPoint(eyePos);
                player.sendSystemMessage(Component.literal("pos: "+ eyePos + " count: " + count));
                if (count == 2) {
                    // 顶点数量达到2，保存结构
                    BlockPos p1 = data.first;
                    BlockPos p2 = data.second;

                    StructureStaffComponent data1 = StructureStaffComponent.loadFromVertex(level, p1, p2);
                    data.saveTempStructure(data1);

                    try {
                        // 传输数据量过大会崩溃
//                        AdapterUtils.sendPacketToServer(new SyncStructureStaffComponentPacket(data1, itemStack));
//                        itemStack.set(ModDataComponentTypes.STRUCTURE_STAFF, data1);
                    }catch (Exception e){
                        player.sendSystemMessage(Component.literal("nbt too big"));
                    }


                    return InteractionResultHolder.pass(itemStack);
                }
            }
            else {
                // 打开菜单
                AdapterUtils.sendPacketToServer(new ClientEventBoundPacket(0));

//                // 传输数据量过大会崩溃
//                component.split(50).forEach(c->{
//                    AdapterUtils.sendPacketToServer(new GenerateStructurePacket(c, eyePos));
//                });

                return super.use(level, player, usedHand);
            }
        }
        return super.use(level, player, usedHand);
    }


//    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
//        var component = livingEntity.getData(ModAttachments.STRUCTURE_STAFF_STORAGE.get()).structureStaffComponent;
//        if(level.isClientSide && component != null){
//            if(level.isClientSide() && timeCharged >= 50){
//                // 生成结构
//                    // 传输数据量过大会崩溃
//                    component.split(50).forEach(c->{
//                        AdapterUtils.sendPacketToServer(new GenerateStructurePacket(c, livingEntity.blockPosition()));
//                    });
//            }else if(livingEntity instanceof Player player){
//
//            }
//        }
//    }

}
