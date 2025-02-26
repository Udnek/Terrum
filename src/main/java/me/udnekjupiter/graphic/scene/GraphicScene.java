package me.udnekjupiter.graphic.scene;

import me.udnekjupiter.graphic.Camera;
import me.udnekjupiter.graphic.object.GraphicObject;
import me.udnekjupiter.util.utilityinterface.Initializable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface GraphicScene<GObject extends GraphicObject> extends Initializable {
      @NotNull Camera getCamera();
      void addObject(@NotNull GObject object);
      default void addObjects(@NotNull Iterable<? extends @NotNull GObject> objects){
            for (@NotNull GObject object : objects) addObject(object);
      }
      @NotNull List<? extends @NotNull GObject> getObjects();
}
