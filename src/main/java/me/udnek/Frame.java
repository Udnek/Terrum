package me.udnek;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Frame extends JFrame implements KeyListener {

    private Panel panel;
    Frame(){
        panel = new Panel();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setVisible(true);
        this.setSize(800, 800);
        this.setFocusable(true);

        addKeyListener(this);

        panel.loop();
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        panel.handleKeyInput(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
