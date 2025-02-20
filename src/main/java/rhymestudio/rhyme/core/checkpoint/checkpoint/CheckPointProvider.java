package rhymestudio.rhyme.core.checkpoint.checkpoint;

import com.mojang.serialization.MapCodec;

/**
 * CheckPoint 编解码器
 * @param codec
 */
public record CheckPointProvider(MapCodec<? extends ICheckPoint<?>> codec) {


}