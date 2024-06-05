package me.udnek.app;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DebugMenu {

    private int textYleft = 0;
    private int textYright = 0;
    private Graphics graphics;
    private int width;
    private final int textSize;
    private final Font font;
    private boolean enabled = false;

    public DebugMenu(int textSize){
        this.textSize = textSize;
        font = new Font("Arial", Font.PLAIN, this.textSize);
    }

    public void resetForNewFrame(BufferedImage image){
        resetForNewFrame(image.getGraphics(), image.getWidth());
    }
    public void resetForNewFrame(Graphics graphics, int width){
        textYleft = 0;
        textYright = 0;
        this.graphics = graphics;
        this.width = width;
        graphics.setFont(font);
        graphics.setColor(Color.WHITE);
    }

    public void addTextToLeft(String text){
        textYleft += textSize;
        graphics.drawString(text, 0, textYleft);
    }

    public void addTextToRight(String text){
        textYright += textSize;
        int stringWidth = graphics.getFontMetrics(font).stringWidth(text);
        graphics.drawString(text, width-stringWidth, textYright);
    }

    public boolean isEnabled() {return enabled;}
    public void toggle() {enabled = !enabled;}
}
