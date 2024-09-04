package me.udnekjupiter.graphic.engine;

import me.udnekjupiter.app.console.Command;
import me.udnekjupiter.app.console.Console;
import me.udnekjupiter.app.console.ConsoleListener;
import me.udnekjupiter.graphic.scene.GraphicScene3d;

public abstract class GraphicScene3dEngine implements GraphicEngine, ConsoleListener {

    protected final GraphicScene3d scene;
    public GraphicScene3dEngine(GraphicScene3d scene){
        this.scene = scene;
    }

    @Override
    public void initialize() {
        Console.getInstance().addListener(this);
        scene.initialize();
    }

    public GraphicScene3d getScene() {return scene;}

    @Override
    public void handleCommand(Command command, Object[] args) {
        if (command != Command.SET_FOV) return;
        scene.getCamera().setFov((double) args[0]);
    }
}
