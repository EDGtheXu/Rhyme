package rhymestudio.rhyme.core.particle;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.behavior.WakeUp;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.particle.options.BrokenProjOptions;

public class PuffProjParticle extends TextureSheetParticle {

    public PuffProjParticle(ClientLevel pLevel, double pX, double pY, double pZ){
        super(pLevel, pX, pY, pZ);
        this.friction = 0.96F;
        this.xd = (float) (Math.random() * 0.2 - 0.1);
        this.yd = (float) (Math.random() * 0.2 - 0.1);
        this.zd = (float) (Math.random() * 0.2 - 0.1);
//        setColor(0,1,0);
        this.quadSize *= 0.5F * ((float) Math.random() + 0.5F);

        this.lifetime = (int)(30 + (Math.random() * 20));
        this.gravity = 0.05F;
        this.hasPhysics = true;
//        this.setColor(97/255f,35/255f,132/255f);

    }

    @Override
    @NotNull
    public ParticleRenderType getRenderType(){
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Override
    public void render(@NotNull VertexConsumer pBuffer, Camera camera, float pPartialTicks){
        super.render(pBuffer, camera, pPartialTicks);
    }

    @Override
    public void tick(){
        super.tick();
//        if (this.sprites!= null && this.age >= this.lifetime) {
//            this.setSpriteFromAge(this.sprites);
//        }

        this.quadSize -= 0.005F;
        if(this.quadSize <= 0.0F) this.remove();
    }

    @Override
    protected int getLightColor(float pPartialTick){
        return super.getLightColor(pPartialTick);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;
        public Provider(SpriteSet pSprite){
            this.sprite = pSprite;
        }
        @Nullable
        @Override
        public Particle createParticle(@NotNull SimpleParticleType options, @NotNull ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed){
            var particle = new PuffProjParticle(pLevel, pX, pY, pZ);
            particle.pickSprite(this.sprite);
            return particle;
        }
    }
}
