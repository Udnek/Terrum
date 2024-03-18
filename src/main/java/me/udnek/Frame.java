package me.udnek;

import me.udnek.scene.Scene;

import javax.swing.*;

public class Frame extends JFrame {

    private Panel panel;
    Frame(){
        panel = new Panel();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setVisible(true);
        this.setSize(500, 500);

        panel.loop();
    }


}
