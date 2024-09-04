package com.linngdu664.bsf.event;

import com.linngdu664.bsf.Main;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class BSFGui {
    public static final GuiTexture SNOWBALL_FRAME = new GuiTexture("textures/gui/snowball_frame.png",23,62);
    public static final GuiTexture TWEAKER_FRAME = new GuiTexture("textures/gui/tweaker_frame.png",114,106);
    public static final GuiImage SNOWBALL_SLOT_FRAME_GUI = new GuiImage(SNOWBALL_FRAME,0,0,23,62);
    public static final GuiImage TWEAKER_LOCATOR_GUI = new GuiImage(TWEAKER_FRAME,1,0,22,82);
    public static final GuiImage TWEAKER_STATUS_GUI = new GuiImage(TWEAKER_FRAME,24,0,22,102);
    public static final GuiImage TWEAKER_SELECTOR_GUI = new GuiImage(TWEAKER_FRAME,0,82,24,24);
    public static final GuiImage GOLEM_LOCATOR_GUI = new GuiImage(TWEAKER_FRAME,47,0,22,82);
    public static final GuiImage GOLEM_STATUS_GUI = new GuiImage(TWEAKER_FRAME,70,0,22,102);
    public static final GuiImage GOLEM_SELECTOR_GUI = new GuiImage(TWEAKER_FRAME,46,82,24,24);
    public static final GuiImage SETTER_ARROW_GUI = new GuiImage(TWEAKER_FRAME,92,1,8,20);
    public static final GuiImage ADVANCE_MODE_GUI = new GuiImage(TWEAKER_FRAME,92,60,22,22);
    public static final GuiImage EQUIPMENT_SLOT_FRAME_GUI = new GuiImage(TWEAKER_FRAME,92,84,22,22);



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
        return (int)((window.getWidth() / window.getGuiScale()-width) * widthRatio);
    }

    public static void renderProgressBar(GuiGraphics guiGraphics,V2I pos,V2I frame,int padding,int frameColor,int innerColor,float percent){
        guiGraphics.renderOutline(pos.x,pos.y,frame.x,frame.y,frameColor);
        int innerW = (int)((frame.x-padding-padding)*percent);
        guiGraphics.fill(pos.x+padding,pos.y+padding,pos.x+padding+innerW,pos.y+frame.y-padding,innerColor);
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

        @Override
        public String toString() {
            return "V2I{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
    public static void renderLineTool(GuiGraphics guiGraphics, Vec2 p1, Vec2 p2,float d, int pColor){
        Vec2 ad = p2.add(p1.negated());
        Vec2 v1 = ad.scale(d/ad.length());
        Vec2 v2 = new Vec2(-v1.y, v1.x);
        renderFillTool(guiGraphics,p1,p1.add(v2),p2.add(v2),p2,pColor);
    }
    public static void renderFillTool(GuiGraphics guiGraphics, Vec2 a, Vec2 b, Vec2 c, Vec2 d, int pColor){
        Matrix4f matrix4f = guiGraphics.pose.last().pose();

        float f3 = (float) FastColor.ARGB32.alpha(pColor) / 255.0F;
        float f = (float) FastColor.ARGB32.red(pColor) / 255.0F;
        float f1 = (float) FastColor.ARGB32.green(pColor) / 255.0F;
        float f2 = (float) FastColor.ARGB32.blue(pColor) / 255.0F;
        VertexConsumer vertexconsumer = guiGraphics.bufferSource.getBuffer(RenderType.gui());
        vertexconsumer.vertex(matrix4f, a.x, a.y, 0).color(f, f1, f2, f3).endVertex();
        vertexconsumer.vertex(matrix4f, b.x,b.y, 0).color(f, f1, f2, f3).endVertex();
        vertexconsumer.vertex(matrix4f, c.x,c.y, 0).color(f, f1, f2, f3).endVertex();
        vertexconsumer.vertex(matrix4f, d.x,d.y, 0).color(f, f1, f2, f3).endVertex();
        guiGraphics.flushIfUnmanaged();
    }


    public static ArrayList<V2I> calcScreenPosFromWorldPos(List<Vec3> points, int guiWidth, int guiHeight, int widthProtect, int heightProtect, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();
        GameRenderer gameRenderer = mc.gameRenderer;
        Camera camera = gameRenderer.getMainCamera();
        Vec3 cameraPos = camera.getPosition();
        Matrix3f rotMat = new Matrix3f().rotation(camera.rotation().conjugate(new Quaternionf()));      // make rot mat
        Window window = mc.getWindow();
        float fovy = (float) gameRenderer.getFov(camera, partialTicks, true) * Mth.DEG_TO_RAD;
        float tanHalfFovy = Mth.sin(fovy * 0.5F) / Mth.cos(fovy * 0.5F);
        float tanHalfFovx = tanHalfFovy * (float) window.getWidth() / (float) window.getHeight();
        ArrayList<V2I> screenPoints = new ArrayList<>();
        for (Vec3 vec3 : points) {
            Vector3f vector3f = new Vector3f((float) (vec3.x - cameraPos.x), (float) (vec3.y - cameraPos.y), (float) (vec3.z - cameraPos.z));
            rotMat.transform(vector3f);
            // 这个版本转完之后摄像机朝向z轴正方向，真他妈无语
            float rx = vector3f.x / vector3f.z / tanHalfFovx;
            int xScreen = vector3f.z <= 0 ? (vector3f.x >= 0 ? widthProtect : guiWidth - widthProtect) : Mth.clamp((int) (guiWidth * 0.5F * (1 - rx)), widthProtect, guiWidth - widthProtect);
            float ry = vector3f.y / Mth.sqrt(vector3f.x * vector3f.x + vector3f.z * vector3f.z) / tanHalfFovy;
            int yScreen = Mth.clamp((int) (guiHeight * 0.5F * (1 - ry)), heightProtect, guiHeight - heightProtect);
            screenPoints.add(new BSFGui.V2I(xScreen, yScreen));
        }
        return screenPoints;
    }
}
