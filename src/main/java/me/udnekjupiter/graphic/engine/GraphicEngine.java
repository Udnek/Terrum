package me.udnekjupiter.graphic.engine;

import me.udnekjupiter.util.utilityinterface.Initializable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.image.BufferedImage;

public interface GraphicEngine extends Initializable{
   @Nullable BufferedImage renderFrame(int width, int height);
   void postVideoRender(@NotNull BufferedImage image);
   void terminate();
}
