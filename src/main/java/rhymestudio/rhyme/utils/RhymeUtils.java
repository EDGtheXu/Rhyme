package rhymestudio.rhyme.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.config.ServerConfig;

import java.util.List;

public class RhymeUtils {
    public static final Direction[] HORIZONTAL = new Direction[]{Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.NORTH};

    public static Vector3d toVector3d(BlockPos blockPos) {
        Vec3 center = blockPos.getCenter();
        return new Vector3d(center.x, center.y, center.z);
    }

    public static BlockPos fromVector3d(Vector3d vector3d) {
        return new BlockPos(Mth.floor(vector3d.x), Mth.floor(vector3d.y), Mth.floor(vector3d.z));
    }

    public static void lightningPathList(List<Vector3d> locationList, double dist, int move, RandomSource random) {
        double distSqr = dist * dist;
        boolean refined;
        do {
            refined = false;
            for (int i = 0; i < locationList.size() - 1; i++) {
                Vector3d point1 = locationList.get(i);
                Vector3d point2 = locationList.get(i + 1);
                double distanceSqr = point2.distanceSquared(point1);
                if (distanceSqr > distSqr) {
                    Vector3d midpoint = new Vector3d();
                    point1.add(point2, midpoint).mul(0.5);
                    double offset = Math.sqrt(distanceSqr) / move;
                    double twoOffset = offset * 2;
                    midpoint.x = midpoint.x + (random.nextDouble() - 0.5) * twoOffset;
                    midpoint.y = midpoint.y + (random.nextDouble() - 0.5) * twoOffset;
                    midpoint.z = midpoint.z + (random.nextDouble() - 0.5) * twoOffset;
                    locationList.add(i + 1, midpoint);
                    refined = true;
                }
            }
        } while (refined);
    }

    public static void attributesBalance(LivingEntity entity, boolean dirty) {
        if(!entity.level().isClientSide) {
            if (dirty) {
                entity.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(new AttributeModifier(Rhyme.space("server_modifier_max_health"), ServerConfig.PLANT_ATTRIBUTES_MULTIPLIER_HEALTH.get() - 1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
                entity.setHealth(entity.getMaxHealth());
            }
            entity.getAttribute(Attributes.ATTACK_DAMAGE).addTransientModifier(new AttributeModifier(Rhyme.space("server_modifier_max_health"), ServerConfig.PLANT_ATTRIBUTES_MULTIPLIER_DAMAGE.get() - 1, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
        }
    }


}
