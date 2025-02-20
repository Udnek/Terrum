package me.udnekjupiter.app.window.opengl;

import me.udnekjupiter.Main;
import me.udnekjupiter.app.Application;
import me.udnekjupiter.app.controller.Controller;
import me.udnekjupiter.app.controller.InputKey;
import me.udnekjupiter.app.util.ApplicationSettings;
import me.udnekjupiter.app.window.Window;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.Version;
import org.lwjgl.opengl.GL;

import java.awt.*;

import static java.sql.Types.NULL;
import static org.lwjgl.glfw.GLFW.*;

public class GlWindow implements Window {

    protected int[] widthBuffer = new int[1];
    protected int[] heightBuffer = new int[1];
    protected double[] mouseXBuffer = new double[1];
    protected double[] mouseYBuffer = new double[1];

    protected long id;
    protected final Controller controller = Controller.getInstance();

    @Override
    public void initialize() {
        System.out.println("GLWINDOW THREAD: " + Thread.currentThread().getName());
        System.out.println("LWJGL Version " + Version.getVersion() + " is working.");
        if (!glfwInit()) throw new RuntimeException("Could not init GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
        Application application = Main.getMain().getApplication();
        ApplicationSettings settings = application.getSettings();
        id = glfwCreateWindow(settings.startWindowWidth, settings.startWindowHeight, TITLE, NULL, NULL);
        if (id == NULL) throw new RuntimeException("Can not initialize window");

        glfwSetKeyCallback(id, (wdw, key, scancode, action, mods) -> {
            Controller.getInstance().keyChanges(InputKey.getByGlCode(key),
                    action == GLFW_REPEAT || action == GLFW_PRESS);
        });
        glfwSetMouseButtonCallback(id, (wnd, key, action, mods) -> {
            Controller.getInstance().keyChanges(
                    InputKey.getByGlCode(key),action == GLFW_PRESS);
        });

        glfwMakeContextCurrent(id);
        GL.createCapabilities();

        System.out.println("GLWINDOW INITIALIZED");
    }

    @Override
    public void tick() {
        if (glfwWindowShouldClose(id)){
            Main.getMain().getApplication().stop();
            return;
        }
        glfwGetCursorPos(id, mouseXBuffer, mouseYBuffer);
        controller.setMouseCurrentPosition(new Point((int) mouseXBuffer[0], (int) mouseYBuffer[0]));
    }

    @Override
    public void setFrame(@NotNull Image image) {

    }

    @Override
    public void setTitle(@NotNull String title) {
        glfwSetWindowTitle(id, title);
    }

    public void swapBuffers(){
        glfwSwapBuffers(id);
    }

    public void pollEvents(){
        glfwPollEvents();
    }

    @Override
    public int getWidth() {
        glfwGetWindowSize(id, widthBuffer, heightBuffer);
        return widthBuffer[0];
    }

    @Override
    public int getHeight() {
        glfwGetWindowSize(id, widthBuffer, heightBuffer);
        return heightBuffer[0];
    }

    @Override
    public void setSize(int width, int height) {
        glfwSetWindowSize(id, width, height);
    }
}
