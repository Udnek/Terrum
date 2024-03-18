package me.udnek;

import me.udnek.scene.Scene;

import java.awt.*;

public class Screen extends Canvas
{

    private Scene scene;

    public Screen() {
        setBackground(Color.GRAY);
        setSize(this.getWidth(), this.getHeight());
        scene = new Scene();
    }

    public void paint(Graphics graphics){
        System.out.println("D");
        graphics.drawImage(scene.renderFrame(this.getWidth(), this.getHeight()), 0, 0, null);
    }

    @Override
    public void repaint() {
        super.repaint();
    }
}
