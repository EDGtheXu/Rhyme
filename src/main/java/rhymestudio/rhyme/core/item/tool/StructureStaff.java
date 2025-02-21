package rhymestudio.rhyme.core.item.tool;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import rhymestudio.rhyme.client.event.ModKeyBindings;
import rhymestudio.rhyme.core.dataSaver.dataComponent.StructureStaffComponent;
import rhymestudio.rhyme.core.registry.ModAttachments;
import rhymestudio.rhyme.network.c2s.ClientEventBoundPacket;
import rhymestudio.rhyme.utils.AdapterUtils;

import java.util.List;

public class StructureStaff extends Item {

    public StructureStaff(Properties properties) {
        super(properties);
    }

    public static BlockPos getHitBlockPos(Player player){
        final BlockHitResult result = getPlayerPOVHitResult(player.level(), player, ClipContext.Fluid.SOURCE_ONLY);
        BlockPos eyePos = result.getBlockPos();
        return eyePos;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {

        ItemStack itemStack = player.getItemInHand(usedHand);

        var data = player.getData(ModAttachments.STRUCTURE_STAFF_STORAGE.get());
        BlockPos eyePos = getHitBlockPos(player);

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

                    return InteractionResultHolder.pass(itemStack);
                }
            }
            else {
                // 打开菜单
                AdapterUtils.sendPacketToServer(new ClientEventBoundPacket(0));
                return super.use(level, player, usedHand);
            }
        }
        return super.use(level, player, usedHand);
    }

    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(Component.translatable("tooltip.structure_staff.info"));
    }

}
