package rhymestudio.rhyme.core.particle;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.particle.options.BrokenProjOptions;

import java.awt.image.RenderedImage;

public class BrokenProjParticle extends TextureSheetParticle {
    private final ResourceLocation texture;
    private final SpriteSet sprites;
    private boolean bg = false;
    private float u = 0;
    private float v = 0;
    public BrokenProjParticle(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet sprites, String pTexture){
        super(pLevel, pX, pY, pZ);
        this.sprites = sprites;
        this.setSpriteFromAge(this.sprites);
        this.friction = 0.96F;
        this.xd = (float) (Math.random() * 0.2 - 0.1);
        this.yd = (float) (Math.random() * 0.2 - 0.1);
        this.zd = (float) (Math.random() * 0.2 - 0.1);
//        setColor(0,1,0);
        this.quadSize *= 0.75F;
        this.lifetime = (int)(20.0 / (Math.random() * 0.8 + 0.2));
        this.hasPhysics = false;
        this.gravity = 0.3F;
        this.texture = Rhyme.space(pTexture);
        this.hasPhysics = true;
        u = (float) Math.random();
        v = (float) Math.random();
    }

    @Override
    @NotNull
    public ParticleRenderType getRenderType(){
        return ParticleRenderType.CUSTOM;
    }

    static BufferBuilder bf = new BufferBuilder(2097152);
    @Override
    public void render(@NotNull VertexConsumer pBuffer, Camera camera, float pPartialTicks){
//        RenderSystem.colorMask(false, false, false, true);
//        super.render(pBuffer, camera, pPartialTicks);

        bg = true;
        // todo

        getRenderType().begin(bf,Minecraft.getInstance().getTextureManager());

        if (bf != null) {
            bf.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
            super.render(bf, camera, pPartialTicks);
            BufferBuilder.RenderedBuffer meshdata = bf.end();
            if (meshdata != null) {
                int id = RenderSystem.getShaderTexture(0);
                RenderSystem.setShaderTexture(0, this.texture);
                BufferUploader.drawWithShader(meshdata);
                RenderSystem.setShaderTexture(0, id);
                bf.clear();
            }
        }

        bg = false;
    }
    protected float getU0() {
//        return super.getU0();
        return bg? u : super.getU0();
    }

    protected float getU1() {
        return bg? u + 0.2F : super.getU1();
    }

    protected float getV0() {
        return  bg? v : super.getV0();
    }

    protected float getV1() {
         return bg? v + 0.2F : super.getV1();
    }

    @Override
    public void tick(){
        super.tick();
//        if (this.sprites!= null && this.age >= this.lifetime) {
//            this.setSpriteFromAge(this.sprites);
//        }


    }

    @Override
    protected int getLightColor(float pPartialTick){
        return super.getLightColor(pPartialTick);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<BrokenProjOptions> {
        private final SpriteSet sprite;
        public Provider(SpriteSet pSprite){
            this.sprite = pSprite;
        }
        @Nullable
        @Override
        public Particle createParticle(@NotNull BrokenProjOptions options, @NotNull ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed){

            return new BrokenProjParticle(pLevel, pX, pY, pZ, sprite, options.texture());
        }
    }
}
