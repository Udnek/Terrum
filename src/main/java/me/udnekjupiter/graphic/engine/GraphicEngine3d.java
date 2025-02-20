package me.udnekjupiter.graphic.engine;

import me.udnekjupiter.Main;
import me.udnekjupiter.app.Application;
import me.udnekjupiter.app.console.Command;
import me.udnekjupiter.app.console.Console;
import me.udnekjupiter.app.console.ConsoleListener;
import me.udnekjupiter.graphic.Camera;
import me.udnekjupiter.graphic.scene.GraphicScene3d;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.image.BufferedImage;

public abstract class GraphicEngine3d implements GraphicEngine, ConsoleListener {
    protected final GraphicScene3d scene;

    @Override
    public @Nullable BufferedImage renderFrame(int width, int height) {
        Application application = Main.getMain().getApplication();
        scene.beforeFrameUpdate(application.getWindow().getWidth(), application.getWindow().getHeight());
        return null;
    }

    public GraphicEngine3d(@NotNull GraphicScene3d scene){
        this.scene = scene;
    }

    @Override
    public void initialize() {
        Console.getInstance().addListener(this);
        scene.initialize();
    }

    @Override
    public void postVideoRender(@NotNull BufferedImage image) {
        Main.getMain().getApplication().getDebugMenu().draw(image, 15);
    }

    @Override
    public void handleCommand(@NotNull Command command, Object[] args) {
        if (command == Command.SET_FOV) scene.getCamera().setFov((double) args[0]);
        else if (command == Command.GET_CAMERA_POSITION){
            Camera camera = scene.getCamera();
            System.out.println(camera.getPosition().asString() + " " + camera.getYaw() + ", " + camera.getPitch());
        }

    }
}
