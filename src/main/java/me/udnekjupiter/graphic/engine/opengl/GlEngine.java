package me.udnekjupiter.graphic.engine.opengl;

import de.matthiasmann.twl.utils.PNGDecoder;
import me.udnekjupiter.Main;
import me.udnekjupiter.app.window.opengl.GlWindow;
import me.udnekjupiter.file.FileManager;
import me.udnekjupiter.graphic.Camera;
import me.udnekjupiter.graphic.engine.GraphicEngine3d;
import me.udnekjupiter.graphic.object.GraphicObject3d;
import me.udnekjupiter.graphic.scene.GraphicScene3d;
import me.udnekjupiter.graphic.triangle.RenderableTriangle;
import me.udnekjupiter.util.vector.Vector3d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryUtil;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.stb.STBImage.stbi_set_flip_vertically_on_load;

public class GlEngine extends GraphicEngine3d {

    protected AttributeManager attributes;
    protected ShaderProgram shaderProgram;
    protected Uniforms uniforms;
    protected int vbo;
    protected int vao;
    protected int texture;
    protected int ebo;
    protected FloatBuffer vertices;
    protected IntBuffer elements;
    protected int elementsAmount = 0;
    protected GlWindow window;

    protected boolean debugColorPlanes;

    protected boolean renderBufferImage = false;


    public GlEngine(@NotNull GlWindow window, @NotNull GraphicScene3d scene) {
        super(scene);
        this.window = window;
    }

    public void setRenderBufferImage(boolean renderBufferImage){
        this.renderBufferImage = renderBufferImage;
    }

    @Override
    public void initialize() {
        super.initialize();
        System.out.println("GLENGINE THREAD: " + Thread.currentThread().getName());

        setRenderBufferImage(Main.getMain().getApplication().getSettings().recordVideo);
        debugColorPlanes = Main.getMain().getApplication().getSettings().debugColorizePlanes;

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
        InputStream stream = FileManager.Directory.TEXTURE.getStream("atlas.png");
        ByteBuffer imageBuffer;
        PNGDecoder decoder;
        try {
            decoder = new PNGDecoder(stream);
            imageBuffer = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(imageBuffer, decoder.getWidth()*4, PNGDecoder.Format.RGBA);
            imageBuffer.flip();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, Texture.ATLAS_SIZE, Texture.ATLAS_SIZE, 0, GL_RGBA, GL_UNSIGNED_BYTE, imageBuffer);

        // END TEXTURE

        shaderProgram = new ShaderProgram();
        attributes = new AttributeManager(shaderProgram);
        uniforms = new Uniforms(shaderProgram);

        shaderProgram.initialize();
        attributes.initialize();
        uniforms.initialize();

        System.out.println("GLENGINE INITIALIZED");
    }

    public void addTriangle(@NotNull Vector3d v0, @NotNull Vector3d v1, @NotNull Vector3d v2, int color, @NotNull Texture texture, @NotNull TextureCorners corers){
        float alpha = (color >> 24 & 0xFF) / 255f;
        if (debugColorPlanes) {
            vertices.put(new float[]{(float) v0.x, (float) v0.y, (float) v0.z, 1f, 0f, 0f, alpha});
            texture.getCornerPosition(corers.id0, vertices);
            vertices.put(new float[]{(float) v1.x, (float) v1.y, (float) v1.z, 0f, 1f, 0f, alpha});
            texture.getCornerPosition(corers.id1, vertices);
            vertices.put(new float[]{(float) v2.x, (float) v2.y, (float) v2.z, 0f, 0f, 1f, alpha});
            texture.getCornerPosition(corers.id2, vertices);
        } else {
            float red = (color >> 16 & 0xFF) / 255f;
            float green = (color >> 8 & 0xFF) / 255f;
            float blue = (color & 0xFF) / 255f;
            vertices.put(new float[]{(float) v0.x, (float) v0.y, (float) v0.z, red, green, blue, alpha});
            texture.getCornerPosition(corers.id0, vertices);
            vertices.put(new float[]{(float) v1.x, (float) v1.y, (float) v1.z, red, green, blue, alpha});
            texture.getCornerPosition(corers.id1, vertices);
            vertices.put(new float[]{(float) v2.x, (float) v2.y, (float) v2.z, red, green, blue, alpha});
            texture.getCornerPosition(corers.id2, vertices);
        }
        elements.put(new int[]{elementsAmount, elementsAmount+1, elementsAmount+2});
        elementsAmount += 3;
    }

    public void addTriangle(@NotNull RenderableTriangle triangle){
        addTriangle(triangle.getVertex0(), triangle.getVertex1(), triangle.getVertex2(),
                triangle.getRasterizeColor(), triangle.getTexture(), triangle.getTextureCorners());
    }

    public void updateTriangles(){
        Camera camera = scene.getCamera();
        Vector3d cameraPosition = camera.getPosition();
        List<RenderableTriangle> polygons = new ArrayList<>();
        for (GraphicObject3d object : scene.getObjects()) {
            Vector3d objectPosition = object.getPosition();
            object.getRenderTriangles(triangle -> {
                triangle.addToAllVertexes(objectPosition).subFromAllVertexes(cameraPosition);
                camera.rotateBackTriangle(triangle);
                polygons.add(triangle);
            });
        }
        polygons.sort((o0, o1) -> Double.compare(o0.getCenter().z, o1.getCenter().z));
        polygons.forEach(this::addTriangle);
    }

    @Override
    public @Nullable BufferedImage renderFrame(int width, int height) {
        super.renderFrame(width, height);
        debugColorPlanes = Main.getMain().getApplication().getSettings().debugColorizePlanes;

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        glViewport(0, 0, width, height);

        Camera camera = scene.getCamera();

/*        Matrix4f model = new Matrix4f().translate(camera.getPosition().toJoml().mul(-1));
        uniforms.set(uniforms.getId(Uniforms.MODEL), model);

        Matrix4f projection = new Matrix4f()
                .perspective((float) camera.getFovAngleRadians(), (float) width /height, 0.001f, 1000f)
                .rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(-1, 0, 0))
                .rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0));
        uniforms.set(uniforms.getId(Uniforms.PROJECTION), projection);*/

        Matrix4f projection = new Matrix4f()
                .perspective((float) camera.getFovAngleRadians(), (float) width /height, 0.001f, 1000f);
        uniforms.set(uniforms.getId(Uniforms.PROJECTION), projection);

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

        if (!renderBufferImage) return null;

        return getRender(width, height);
    }


    public @NotNull BufferedImage getRender(int width, int height){
        int[] buffer = new int[width * height];
        glReadPixels(0, 0, width, height, GL_BGRA, GL_UNSIGNED_BYTE, buffer);

        int[] data = new int[buffer.length];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                data[y*width+x] = buffer[(height-y-1)*width+x];
            }
        }

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.setRGB(0, 0, width, height, data, 0, width);

        return image;
    }

    @Override
    public void terminate() {
        glDeleteVertexArrays(vao);
        glDeleteBuffers(vbo);
        glDeleteBuffers(ebo);
        shaderProgram.terminate();
        glfwTerminate();
    }
}
