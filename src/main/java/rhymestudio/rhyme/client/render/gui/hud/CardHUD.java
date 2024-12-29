package rhymestudio.rhyme.client.render.gui.hud;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import rhymestudio.rhyme.Rhyme;
import rhymestudio.rhyme.core.registry.ModAttachments;

import java.awt.*;

public class CardHUD {

    private long lastCacheTime = 0;
    private int cacheSunStrSize = 0;
    private int cacheMoneyStrSize = 0;

    private String cacheSunStr = "";
    private String cacheMoneyStr = "";

    public static int cachedMoney = 0;

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
//        drawCard(guiGraphics,ModItems.PEA_ITEM.get(),0,0,2);

//        var bf =Minecraft.getInstance().renderBuffers().bufferSource().getBuffer(RenderType.gui());

/*
        //装备
        for(int i=0;i<4;i++){
            int x2 = x + 30 + 18 * i;
            int y2 = y;
            //guiGraphics.vLine(x2-2,y2, y2 + 18,Color.MAGENTA.getRGB());
            ItemStack stack = player.getInventory().getArmor(3-i);
            guiGraphics.renderItem(stack,x2, y2 );
            drawItemBar(stack,guiGraphics,x2, y2 );
        }

        //右手物品
        guiGraphics.pose().translate(x,y,0);
        guiGraphics.pose().scale(1.1f,1.1f,1.1f);
        ItemStack stack = player.getMainHandItem();
        guiGraphics.renderItem(stack,0,0);

        //名称
        String str = stack.getDisplayName().getString();
        guiGraphics.drawString(minecraft.font,str.substring(1,str.length()-1),0,-10, stack.getRarity().getStyleModifier().apply(Style.EMPTY).getColor().getValue());

        //耐久条
        if (stack.isBarVisible()) {
            drawItemBar(stack, guiGraphics, 0, 0);
            int dur = stack.getDamageValue();
            int full = stack.getMaxDamage();
            guiGraphics.pose().translate(3, 15, 0);
            guiGraphics.pose().scale(0.7f, 0.7f, 0.7f);
            guiGraphics.drawString(minecraft.font, String.valueOf(full - dur), 1, 1, stack.getBarColor());
        }
*/
        guiGraphics.pose().popPose();
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
            int cacheSunNumber = data.sunCount;
            cacheSunStr =String.valueOf(cacheSunNumber) ;
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