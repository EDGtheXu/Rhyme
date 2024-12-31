package rhymestudio.rhyme.core.registry;

import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import rhymestudio.rhyme.core.dataSaver.attactment.PlantRecorderAttachment;
import rhymestudio.rhyme.core.dataSaver.attactment.SunCountAttachment;

import java.util.function.Supplier;

import static rhymestudio.rhyme.Rhyme.MODID;

public final class ModAttachments {
    public static final DeferredRegister<AttachmentType<?>> TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, MODID);

    public static final Supplier<AttachmentType<SunCountAttachment>> PLAYER_STORAGE = TYPES.register("player_storage", () -> AttachmentType.serializable(SunCountAttachment::new).copyOnDeath().build());
    public static final Supplier<AttachmentType<PlantRecorderAttachment>> PLANT_RECORDER_STORAGE = TYPES.register("plant_recorder_storage", () -> AttachmentType.serializable(PlantRecorderAttachment::new).copyOnDeath().build());


}
