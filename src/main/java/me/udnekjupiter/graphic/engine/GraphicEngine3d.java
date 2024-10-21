package me.udnekjupiter.graphic.engine;

import me.udnekjupiter.app.StandartApplication;
import me.udnekjupiter.app.console.Command;
import me.udnekjupiter.app.console.Console;
import me.udnekjupiter.app.console.ConsoleListener;
import me.udnekjupiter.graphic.scene.GraphicScene3d;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;

public abstract class GraphicEngine3d implements GraphicEngine, ConsoleListener {

    protected final GraphicScene3d scene;
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
        StandartApplication.DEBUG_MENU.draw(image, 15);
    }

    public @NotNull GraphicScene3d getScene() {return scene;}

    @Override
    public void handleCommand(@NotNull Command command, Object[] args) {
        if (command != Command.SET_FOV) return;
        scene.getCamera().setFov((double) args[0]);
    }
}
