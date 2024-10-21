package me.udnekjupiter.graphic.engine;

import me.udnekjupiter.util.Initializable;
import org.jetbrains.annotations.NotNull;

import java.awt.image.BufferedImage;

public interface GraphicEngine extends Initializable{
   @NotNull BufferedImage renderFrame(int width, int height);
   void postVideoRender(@NotNull BufferedImage image);
}
