package me.udnekjupiter.graphic.scene;

import me.udnekjupiter.graphic.Camera;
import me.udnekjupiter.util.Initializable;
import org.jetbrains.annotations.NotNull;

public interface GraphicScene extends Initializable {
      @NotNull Camera getCamera();
}
