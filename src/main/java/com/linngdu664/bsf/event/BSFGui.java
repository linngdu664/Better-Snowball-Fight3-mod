package com.linngdu664.bsf.event;

import com.linngdu664.bsf.Main;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class BSFGui {
    public static final GuiTexture SNOWBALL_FRAME = new GuiTexture("textures/gui/snowball_frame.png",23,62);
    public static final GuiTexture TWEAKER_FRAME = new GuiTexture("textures/gui/tweaker_frame.png",100,106);
    public static final GuiImage SNOWBALL_GUI = new GuiImage(SNOWBALL_FRAME,0,0,23,62);
    public static final GuiImage TWEAKER_LOCATOR_GUI = new GuiImage(TWEAKER_FRAME,1,0,22,82);
    public static final GuiImage TWEAKER_STATUS_GUI = new GuiImage(TWEAKER_FRAME,24,0,22,102);
    public static final GuiImage TWEAKER_SELECTOR_GUI = new GuiImage(TWEAKER_FRAME,0,82,24,24);
    public static final GuiImage GOLEM_LOCATOR_GUI = new GuiImage(TWEAKER_FRAME,47,0,22,82);
    public static final GuiImage GOLEM_STATUS_GUI = new GuiImage(TWEAKER_FRAME,70,0,22,102);
    public static final GuiImage GOLEM_SELECTOR_GUI = new GuiImage(TWEAKER_FRAME,46,82,24,24);
    public static final GuiImage SETTER_ARROW = new GuiImage(TWEAKER_FRAME,92,1,8,20);


    public static class GuiTexture{
        public ResourceLocation texture;
        public int holeWidth;
        public int holeHeight;

        public GuiTexture(String path, int holeWidth, int holeHeight) {
            this.texture = new ResourceLocation(Main.MODID, path);
            this.holeWidth = holeWidth;
            this.holeHeight = holeHeight;
        }
    }
    public static class GuiImage {
        public GuiTexture guiTexture;
        public int widthOffset;
        public int heightOffset;
        public int width;
        public int height;

        public GuiImage(GuiTexture texture, int widthOffset, int heightOffset, int width, int height) {
            this.guiTexture = texture;
            this.widthOffset = widthOffset;
            this.heightOffset = heightOffset;
            this.width = width;
            this.height = height;
        }
        public V2I render(GuiGraphics guiGraphics,int x,int y){
            guiGraphics.blit(guiTexture.texture,x,y,widthOffset,heightOffset,width,height,guiTexture.holeWidth,guiTexture.holeHeight);
            return new V2I(x,y);
        }
        public V2I renderCenterVertically(GuiGraphics guiGraphics,Window window,int x){
            return render(guiGraphics,x,centerVertically(window,this.height));
        }
        public V2I renderCenterHorizontally(GuiGraphics guiGraphics,Window window,int y){
            return render(guiGraphics,centerHorizontally(window,this.width),y);
        }
        public V2I renderRatio(GuiGraphics guiGraphics,Window window,double widthRatio,double heightRatio){
            return renderRatio(guiGraphics,window,widthRatio,heightRatio,0,0);
        }
        public V2I renderRatio(GuiGraphics guiGraphics,Window window,double widthRatio,double heightRatio,int xOffset ,int yOffset){
            return render(guiGraphics,horizontallyRatio(window,this.width,widthRatio)+xOffset,verticallyRatio(window,this.height,heightRatio)+yOffset);
        }
    }
    /*
        x width horizontal
        y height vertical
     */

    public static int centerVertically(Window window, int height) {
        return verticallyRatio(window,height,0.5);
    }
    public static int verticallyRatio(Window window, int height, double heightRatio) {
        return (int)((window.getHeight() / window.getGuiScale()-height) * heightRatio);
    }

    public static int centerHorizontally(Window window, int width) {
        return horizontallyRatio(window,width,0.5);
    }
    public static int horizontallyRatio(Window window, int width,double widthRatio) {
        return (int)((window.getWidth() / (int) window.getGuiScale()-width) * widthRatio);
    }
    public static class V2I{
        public int x;
        public int y;

        public V2I(int x, int y) {
            this.x = x;
            this.y = y;
        }
        public void set(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

}
