package me.udnekjupiter.graphic.engine.opengl;

import me.udnekjupiter.app.window.opengl.GlWindow;
import me.udnekjupiter.file.FileManager;
import me.udnekjupiter.graphic.Camera;
import me.udnekjupiter.graphic.engine.GraphicEngine3d;
import me.udnekjupiter.graphic.object.GraphicObject3d;
import me.udnekjupiter.graphic.scene.GraphicScene3d;
import me.udnekjupiter.graphic.triangle.RenderableTriangle;
import me.udnekjupiter.util.Vector3d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load;

public class GlEngine extends GraphicEngine3d {

    AttributeManager attributes;
    ShaderProgram shaderProgram;
    Uniforms uniforms;
    public int vbo;
    public int vao;
    public int texture;
    public int ebo;
    protected FloatBuffer vertices;
    protected IntBuffer elements;
    protected int elementsAmount = 0;
    protected GlWindow window;

    public GlEngine(@NotNull GlWindow window, @NotNull GraphicScene3d scene) {
        super(scene);
        this.window = window;
    }

    @Override
    public void initialize() {
        super.initialize();
        System.out.println("GLENGINE THREAD: " + Thread.currentThread().getName());

        glEnable(GL_DEPTH_TEST);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_BLEND);

        vao = glGenVertexArrays();
        glBindVertexArray(vao);

        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);

        ebo = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);

        vertices = MemoryUtil.memAllocFloat((int) Math.pow(2, 32));
        glBufferData(GL_ARRAY_BUFFER, (long) vertices.capacity() * Float.BYTES, GL_DYNAMIC_DRAW);

        elements = MemoryUtil.memAllocInt((int) Math.pow(2, 16));
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, (long) elements.capacity() * Integer.BYTES, GL_DYNAMIC_DRAW);

        // TEXTURE
        texture = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, texture);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glGenerateMipmap(GL_TEXTURE_2D);


        stbi_set_flip_vertically_on_load(true);
        BufferedImage bufferedImage = FileManager.readTexture("blank.png");
        byte[] pixelData  = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
        ByteBuffer image = ByteBuffer.allocateDirect(pixelData.length);
        image.order(ByteOrder.nativeOrder());
        image.put(pixelData);
        image.flip();

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, 1, 1, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);

        // END TEXTURE

        shaderProgram = new ShaderProgram();
        attributes = new AttributeManager(shaderProgram);
        uniforms = new Uniforms(shaderProgram);

        shaderProgram.initialize();
        attributes.initialize();
        uniforms.initialize();

        System.out.println("GLENGINE INITIALIZED");
    }

    public void addTriangle(@NotNull Vector3d v0, @NotNull Vector3d v1, @NotNull Vector3d v2, int color){
        float alpha = (color >> 24 & 0xFF) / 255f;
        float red = (color >> 16 & 0xFF) / 255f;
        float green = (color >> 8 & 0xFF) / 255f;
        float blue = (color & 0xFF) / 255f;
        vertices.put(new float[]{(float) v0.x, (float) v0.y, (float) v0.z, red, green, blue, 0, 0});
        vertices.put(new float[]{(float) v1.x, (float) v1.y, (float) v1.z, red, green, blue, 1, 0});
        vertices.put(new float[]{(float) v2.x, (float) v2.y, (float) v2.z, red, green, blue, 1, 1});
        elements.put(new int[]{elementsAmount, elementsAmount+1, elementsAmount+2});
        elementsAmount += 3;
    }

    public void addTriangle(@NotNull RenderableTriangle triangle){
        addTriangle(triangle.getVertex0(), triangle.getVertex1(), triangle.getVertex2(), triangle.getRasterizeColor());
    }

    public void updateTriangles(){
        Camera camera = scene.getCamera();
        Vector3d cameraPosition = camera.getPosition();
        for (GraphicObject3d object : scene.getObjects()) {
            Vector3d objectPosition = object.getPosition();
            object.getRenderTriangles(triangle -> {
                triangle.addToAllVertexes(objectPosition).subFromAllVertexes(cameraPosition);
                addTriangle(triangle);
            });
        }
    }

    @Override
    public @Nullable BufferedImage renderFrame(int width, int height) {
        super.renderFrame(width, height);

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        Camera camera = scene.getCamera();


        Matrix4f projection = new Matrix4f()
                .perspective((float) camera.getFovAngleRadians(), 1, 0.001f, 1000f)
                .rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(-1, 0, 0))
                .rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0));

        glUniformMatrix4fv(uniforms.getLocation(Uniforms.PROJECTION), false, projection.get(new float[4*4]));

        updateTriangles();
        vertices.flip();
        elements.flip();

        glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
        glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, 0, elements);

        glDrawElements(GL_TRIANGLES, elementsAmount, GL_UNSIGNED_INT, 0);

        window.swapBuffers();
        window.pollEvents();

        vertices.clear();
        elements.clear();
        elementsAmount = 0;

        return null;
    }

}
