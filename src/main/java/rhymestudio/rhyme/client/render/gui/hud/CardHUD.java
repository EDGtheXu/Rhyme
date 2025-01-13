package rhymestudio.rhyme.client.render.gui.hud;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.client.ModRenderTypes;
import rhymestudio.rhyme.core.registry.ModAttachments;
import rhymestudio.rhyme.mixinauxiliary.IShaderInstance;

import java.awt.*;

import static rhymestudio.rhyme.client.render.util.ShaderUtil.drawFloatGlow;

public class CardHUD {

    private long lastCacheTime = 0;
    private int cacheSunStrSize = 0;
    private int cacheMoneyStrSize = 0;

    private String cacheSunStr = "";
    private String cacheMoneyStr = "";

    public static int cachedMoney = 0;
    private int cachedSunNumber = 0;

    private int itemInternalX0 = 40;
    private int itemInternalX = 40;

    public Minecraft mc;
    public Player player;

    public CardHUD(Minecraft mc,Player player) {
        this.mc = mc;
        this.player = player;
    }

    private static CardHUD instance;
    public static CardHUD getInstance() {
        if(instance == null || instance.player!= Minecraft.getInstance().player){
            instance = new CardHUD(Minecraft.getInstance(),Minecraft.getInstance().player);
        }
        return instance;
    }

    public void render(GuiGraphics guiGraphics) {

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(10,10,0);
        drawSunCard(guiGraphics,0,0,30,42);
        guiGraphics.pose().translate(itemInternalX0,0,0);
//        drawCard(guiGraphics,ModItems.SUN_FLOWER.get(),0,0,2);
        guiGraphics.pose().translate(itemInternalX,0,0);
        guiGraphics.pose().popPose();

        guiGraphics.setColor(1,1,0.5f,1);

        if(cachedSunNumber > 1000) {
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(10, 8, 0);

            double seconds = System.currentTimeMillis() % 100000000 / 1000f; // seconds
            ((IShaderInstance) ModRenderTypes.Shaders.rectPolar).getRhyme$Time().set((float) (seconds + 0.5 + 0.1f * Math.sin(seconds * Math.PI)));
            ((IShaderInstance) ModRenderTypes.Shaders.rectPolar).getRhyme$Radius().set((float) (Math.sin(seconds * Math.PI) * 0.05f + 1f));

            drawFloatGlow(guiGraphics.pose().last().pose(), Rhyme.space("textures/gui/float_glow.png"), 30, 30);
            guiGraphics.pose().popPose();
            guiGraphics.setColor(1, 1, 1, 1);
        }
    }

    public static void drawItemBar(ItemStack stack,GuiGraphics g,int x,int y){
        if (stack.isBarVisible()) {
            int l = stack.getBarWidth();
            int i = stack.getBarColor();
            int i1 = x + 2;
            int j1 = y + 13;
            g.fill(RenderType.guiOverlay(), i1, j1, i1 + 13, j1 + 2, -16777216);
            g.fill(RenderType.guiOverlay(), i1, j1, i1 + l, j1 + 1, i | -16777216);
        }
    }

    public static void drawIcon(GuiGraphics guiGraphics, String icon, int x, int y, int w, int h){
        guiGraphics.blit(Rhyme.space(icon),x, y, 0, 0, w, h, w, h);
    }

    public void drawSunCard(GuiGraphics guiGraphics, int x, int y,int w,int h){
        drawIcon(guiGraphics,"textures/hud/sun_hud.png",0, 0, w, h);
        long time = System.currentTimeMillis();
        if(time > lastCacheTime + 500){
            lastCacheTime = time;
//            int cacheSunNumber = Computer.getInventoryItemCount(player, MaterialItems.SUN_ITEM.get());
            var data = player.getData(ModAttachments.PLAYER_STORAGE);
            cachedSunNumber = data.sunCount;
            cacheSunStr =String.valueOf(cachedSunNumber) ;
            cacheSunStrSize = cacheSunStr.length();

            cachedMoney = data.moneys;
            cacheMoneyStr = String.valueOf(cachedMoney);
            cacheMoneyStrSize = cacheMoneyStr.length();

        }

        guiGraphics.drawString(mc.font,cacheSunStr,w/2-3* cacheSunStrSize,h-12, Color.yellow.getRGB());
        guiGraphics.drawString(mc.font,cacheMoneyStr,w/2-3* cacheMoneyStrSize,h+2, Color.white.getRGB());

    }

    public void drawCard(GuiGraphics guiGraphics, Item item, int x, int y, float scale){
        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(scale,scale,scale);
        guiGraphics.renderItem(item.getDefaultInstance(),x, y);

//        String countStr =String.valueOf(count.get()) ;
//        guiGraphics.drawString(mc.font,countStr,10,35, Color.yellow.getRGB());

        guiGraphics.pose().popPose();
    }

/*
    private void drawBar(GuiGraphics guiGraphics, int x, int y, BossEvent bossEvent, int progress, ResourceLocation[] barProgressSprites, ResourceLocation[] overlayProgressSprites) {
        RenderSystem.enableBlend();
        guiGraphics.blitSprite(barProgressSprites[bossEvent.getColor().ordinal()], 182, 5, 0, 0, x, y, progress, 5);
        if (bossEvent.getOverlay() != BossEvent.BossBarOverlay.PROGRESS) {
            guiGraphics.blitSprite(overlayProgressSprites[bossEvent.getOverlay().ordinal() - 1], 182, 5, 0, 0, x, y, progress, 5);
        }

        RenderSystem.disableBlend();
    }
*/

}