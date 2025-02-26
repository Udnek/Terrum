package me.udnekjupiter.app.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class DebugMenu {

    public static final String FONT_NAME = "Arial";

    private int textYLeft = 0;
    private int textYRight = 0;
    private Graphics graphics;
    private int width;
    private int height;
    private Font font;
    private int textSize;
    private boolean enabled = false;
    private final List<String> leftText = new ArrayList<>();
    private final List<String> rightText = new ArrayList<>();

    public DebugMenu(){}

    public void reset(){
        textYLeft = 0;
        textYRight = 0;
        leftText.clear();
        rightText.clear();
    }

    public void draw(BufferedImage image, int textSize){
        draw(image.getGraphics(), textSize, image.getWidth(), image.getHeight());
    }
    public void draw(Graphics graphics, int textSize, int width, int height){
        if (!enabled) return;
        this.graphics = graphics;
        this.width = width;
        this.height = height;
        graphics.setFont(font);
        graphics.setColor(Color.WHITE);
        addCenterCrosshair();

        this.textSize = textSize;
        font = new Font(FONT_NAME, Font.PLAIN, textSize);

        leftText.forEach(s -> drawText(TextPosition.LEFT, s));
        rightText.forEach(s -> drawText(TextPosition.RIGHT, s));
    }

    public void addText(TextPosition textPosition, String text) {
        if (textPosition == TextPosition.LEFT){
            leftText.add(text);
            return;
        }
        rightText.add(text);

    }
    public void addTextToRight(String text){rightText.add(text);}
    public void addTextToLeft(String text){leftText.add(text);}

    private void drawText(TextPosition textPosition, String text) {
        if (textPosition == TextPosition.LEFT) {
            textYLeft += textSize;
            graphics.drawString(text, 0, textYLeft);
            return;
        }
        textYRight += textSize;
        int stringWidth = graphics.getFontMetrics(font).stringWidth(text);
        graphics.drawString(text, width - stringWidth, textYRight);

    }

    private void addCenterCrosshair(){
        int size = 4;
        graphics.drawRect(width/2 - size/2, height/2 - size/2, size, size);
    }

    public boolean isEnabled() {return enabled;}
    public void toggle() {enabled = !enabled;}

    public enum TextPosition{
        RIGHT,
        LEFT
    }
}
