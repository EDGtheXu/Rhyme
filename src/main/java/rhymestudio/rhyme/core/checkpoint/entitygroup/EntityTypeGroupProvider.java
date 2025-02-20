package rhymestudio.rhyme.core.checkpoint.entitygroup;

import com.mojang.serialization.MapCodec;

/**
 * 僵尸组编解码器
 * @param codec
 */
public record EntityTypeGroupProvider(MapCodec<? extends IEntityTypeGroup> codec) {


}
