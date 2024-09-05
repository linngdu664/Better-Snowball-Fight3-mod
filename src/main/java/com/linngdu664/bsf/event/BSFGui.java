package com.linngdu664.bsf.event;

import com.linngdu664.bsf.Main;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import oshi.util.tuples.Pair;

import java.util.List;
import java.util.function.Consumer;

public class BSFGui {
    public static final GuiTexture SNOWBALL_FRAME = new GuiTexture("textures/gui/snowball_frame.png", 23, 62);
    public static final GuiTexture TWEAKER_FRAME = new GuiTexture("textures/gui/tweaker_frame.png", 114, 106);
    public static final GuiImage SNOWBALL_SLOT_FRAME_GUI = new GuiImage(SNOWBALL_FRAME, 0, 0, 23, 62);
    public static final GuiImage TWEAKER_LOCATOR_GUI = new GuiImage(TWEAKER_FRAME, 1, 0, 22, 82);
    public static final GuiImage TWEAKER_STATUS_GUI = new GuiImage(TWEAKER_FRAME, 24, 0, 22, 102);
    public static final GuiImage TWEAKER_SELECTOR_GUI = new GuiImage(TWEAKER_FRAME, 0, 82, 24, 24);
    public static final GuiImage GOLEM_LOCATOR_GUI = new GuiImage(TWEAKER_FRAME, 47, 0, 22, 82);
    public static final GuiImage GOLEM_STATUS_GUI = new GuiImage(TWEAKER_FRAME, 70, 0, 22, 102);
    public static final GuiImage GOLEM_SELECTOR_GUI = new GuiImage(TWEAKER_FRAME, 46, 82, 24, 24);
    public static final GuiImage SETTER_ARROW_GUI = new GuiImage(TWEAKER_FRAME, 92, 1, 8, 20);
    public static final GuiImage ADVANCE_MODE_GUI = new GuiImage(TWEAKER_FRAME, 92, 60, 22, 22);
    public static final GuiImage EQUIPMENT_SLOT_FRAME_GUI = new GuiImage(TWEAKER_FRAME, 92, 84, 22, 22);


    public static class GuiTexture {
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

        public V2I render(GuiGraphics guiGraphics, int x, int y) {
            RenderSystem.enableBlend();
            guiGraphics.blit(guiTexture.texture, x, y, widthOffset, heightOffset, width, height, guiTexture.holeWidth, guiTexture.holeHeight);
            RenderSystem.disableBlend();
            return new V2I(x, y);
        }

        public V2I renderCenterVertically(GuiGraphics guiGraphics, Window window, int x) {
            return render(guiGraphics, x, heightFrameCenter(window, this.height));
        }

        public V2I renderCenterHorizontally(GuiGraphics guiGraphics, Window window, int y) {
            return render(guiGraphics, widthFrameCenter(window, this.width), y);
        }

        public V2I renderRatio(GuiGraphics guiGraphics, Window window, double widthRatio, double heightRatio) {
            return renderRatio(guiGraphics, window, widthRatio, heightRatio, 0, 0);
        }

        public V2I renderRatio(GuiGraphics guiGraphics, Window window, double widthRatio, double heightRatio, int xOffset, int yOffset) {
            return render(guiGraphics, widthFrameRatio(window, this.width, widthRatio) + xOffset, heightFrameRatio(window, this.height, heightRatio) + yOffset);
        }
    }
    /*
        x width horizontal
        y height vertical
     */

    public static int heightFrameCenter(Window window, int height) {
        return heightFrameRatio(window, height, 0.5);
    }

    public static int heightFrameRatio(Window window, int height, double heightRatio) {
        return (int) ((window.getHeight() / window.getGuiScale() - height) * heightRatio);
    }

    public static int heightWinRatio(Window window, double heightRatio) {
        return heightFrameRatio(window, 0, heightRatio);
    }

    public static int widthFrameCenter(Window window, int width) {
        return widthFrameRatio(window, width, 0.5);
    }

    public static int widthFrameRatio(Window window, int width, double widthRatio) {
        return (int) ((window.getWidth() / window.getGuiScale() - width) * widthRatio);
    }

    public static int widthWinRatio(Window window, double widthRatio) {
        return widthFrameRatio(window, 0, widthRatio);
    }

    public static V2I v2IRatio(Window window, int width, int height, double widthRatio, double heightRatio) {
        return v2IRatio(window, width, height, widthRatio, heightRatio, 0, 0);
    }

    public static V2I v2IRatio(Window window, int width, int height, double widthRatio, double heightRatio, int xOffset, int yOffset) {
        return new V2I(widthFrameRatio(window, width, widthRatio) + xOffset, heightFrameRatio(window, height, heightRatio) + yOffset);
    }

    public static class V2I {
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

        public Vec2 getVec2() {
            return new Vec2(this.x, this.y);
        }

        @Override
        public String toString() {
            return "V2I{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    public static void renderProgressBar(GuiGraphics guiGraphics, V2I pos, V2I frame, int padding, int frameColor, int innerColor, float percent) {
        guiGraphics.renderOutline(pos.x, pos.y, frame.x, frame.y, frameColor);
        int innerW = (int) ((frame.x - padding - padding) * percent);
        guiGraphics.fill(pos.x + padding, pos.y + padding, pos.x + padding + innerW, pos.y + frame.y - padding, innerColor);
    }

    public static void renderEquipIntroduced(GuiGraphics guiGraphics, Vec2 equipPoint, Vec2 framePoint, int lineXDistance, int color, ItemStack itemStack) {
        Vec2 linkPoint = new Vec2(framePoint.x + EQUIPMENT_SLOT_FRAME_GUI.width, framePoint.y + (float) EQUIPMENT_SLOT_FRAME_GUI.height / 2);
        Vec2 xPoint = new Vec2(equipPoint.x - lineXDistance, linkPoint.y);
        if (xPoint.x < linkPoint.x) {
            xPoint = linkPoint;
        }
        float d = 2;
        renderLineTool(guiGraphics, xPoint.add(new Vec2(0, xPoint.y < equipPoint.y ? 0 : d)),equipPoint, d, color, xPoint.y < equipPoint.y, 0.3f, 0xff808080);
        renderLineTool(guiGraphics, linkPoint, xPoint, d, color, true, 0.3f, 0xff808080);
        EQUIPMENT_SLOT_FRAME_GUI.render(guiGraphics, (int) framePoint.x, (int) framePoint.y);
        guiGraphics.renderItem(itemStack, (int) (framePoint.x + 3), (int) (framePoint.y + 3));
    }

    public static void renderLineTool(GuiGraphics guiGraphics, Vec2 p1, Vec2 p2, float d, int color, boolean isDown, float padding, int padColor) {
        Vec2 ad = p2.add(p1.negated());
        Vec2 v1 = ad.scale(d / ad.length());
        Vec2 v2 = new Vec2(-v1.y, v1.x);
        if (isDown) {
            Vec2 v2s = v2.scale(padding);
            Vec2 v2d = v2.scale(1 - padding);

            renderFillTool(guiGraphics, p1, p1.add(v2), p2.add(v2), p2, padColor);
            renderFillTool(guiGraphics, p1.add(v2s), p1.add(v2d), p2.add(v2d), p2.add(v2s), color);
        } else {
            v2 = v2.negated();
            Vec2 v2s = v2.scale(padding);
            Vec2 v2d = v2.scale(1 - padding);
            p2=p2.add(v2.negated());
            renderFillTool(guiGraphics, p1.add(v2), p1, p2, p2.add(v2), padColor);
            renderFillTool(guiGraphics, p1.add(v2d), p1.add(v2s), p2.add(v2s), p2.add(v2d), color);
        }

    }

    public static void renderFillTool(GuiGraphics guiGraphics, Vec2 a, Vec2 b, Vec2 c, Vec2 d, int pColor) {
        Matrix4f matrix4f = guiGraphics.pose.last().pose();

        float f3 = (float) FastColor.ARGB32.alpha(pColor) / 255.0F;
        float f = (float) FastColor.ARGB32.red(pColor) / 255.0F;
        float f1 = (float) FastColor.ARGB32.green(pColor) / 255.0F;
        float f2 = (float) FastColor.ARGB32.blue(pColor) / 255.0F;
        VertexConsumer vertexconsumer = guiGraphics.bufferSource.getBuffer(RenderType.gui());
        vertexconsumer.vertex(matrix4f, a.x, a.y, 0).color(f, f1, f2, f3).endVertex();
        vertexconsumer.vertex(matrix4f, b.x, b.y, 0).color(f, f1, f2, f3).endVertex();
        vertexconsumer.vertex(matrix4f, c.x, c.y, 0).color(f, f1, f2, f3).endVertex();
        vertexconsumer.vertex(matrix4f, d.x, d.y, 0).color(f, f1, f2, f3).endVertex();
        guiGraphics.flushIfUnmanaged();
    }

    public static void calcScreenPosFromWorldPos(List<Pair<Vec3, Consumer<Vec2>>> points, int guiWidth, int guiHeight, int widthProtect, int heightProtect, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();
        GameRenderer gameRenderer = mc.gameRenderer;
        Camera camera = gameRenderer.getMainCamera();
        Vec3 cameraPos = camera.getPosition();
        Matrix3f rotMat = new Matrix3f().rotation(camera.rotation().conjugate(new Quaternionf()));      // make rot mat
        Window window = mc.getWindow();
        float fovy = (float) gameRenderer.getFov(camera, partialTicks, true) * Mth.DEG_TO_RAD;
        float tanHalfFovy = Mth.sin(fovy * 0.5F) / Mth.cos(fovy * 0.5F);
        float tanHalfFovx = tanHalfFovy * (float) window.getWidth() / (float) window.getHeight();
        for (Pair<Vec3, Consumer<Vec2>> pMethod : points) {
            Vec3 vec3 = pMethod.getA();
            Vector3f vector3f = new Vector3f((float) (vec3.x - cameraPos.x), (float) (vec3.y - cameraPos.y), (float) (vec3.z - cameraPos.z));
            rotMat.transform(vector3f);
            // 这个版本转完之后摄像机朝向z轴正方向，真他妈无语
            float rx = vector3f.x / vector3f.z / tanHalfFovx;
            float xScreen = vector3f.z <= 0 ? (vector3f.x >= 0 ? widthProtect : guiWidth - widthProtect) : Mth.clamp(guiWidth * 0.5F * (1 - rx), widthProtect, guiWidth - widthProtect);
            float ry = vector3f.y / Mth.sqrt(vector3f.x * vector3f.x + vector3f.z * vector3f.z) / tanHalfFovy;
            float yScreen = Mth.clamp(guiHeight * 0.5F * (1 - ry), heightProtect, guiHeight - heightProtect);
            pMethod.getB().accept(new Vec2(xScreen, yScreen));
        }
    }
}
