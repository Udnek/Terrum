package me.udnekjupiter.graphic.engine;

import me.udnekjupiter.util.Initializable;

import java.awt.image.BufferedImage;

public interface GraphicEngine extends Initializable{
   BufferedImage renderFrame(int width, int height);
   void postVideoRender(BufferedImage image);
}
