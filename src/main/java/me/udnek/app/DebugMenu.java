package me.udnek.app;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DebugMenu {

    private int textY = 0;
    private Graphics graphics;
    private final int textSize;
    private final Font font;
    private boolean enabled = false;

    public DebugMenu(Settings settings){
        textSize = 20/settings.pixelScaling;
        font = new Font("Arial", Font.PLAIN, textSize);
    }

    public void resetForNewFrame(BufferedImage image){
        textY = 0;
        this.graphics = image.getGraphics();
        graphics.setFont(font);
        graphics.setColor(Color.WHITE);
    }

    public void addText(String text){
        textY += textSize;
        graphics.drawString(text, 0, textY);
    }


    public boolean isEnabled() {return enabled;}
    public void toggle() {enabled = !enabled;}
}
