package me.udnekjupiter.graphic.engine;

import me.udnekjupiter.graphic.scene.GraphicScene3d;

public abstract class GraphicScene3dEngine implements GraphicEngine{

    protected final GraphicScene3d scene;
    public GraphicScene3d getScene() {return scene;}

    public GraphicScene3dEngine(GraphicScene3d scene){
        this.scene = scene;
    }
}
