package me.udnek.app;

import me.udnek.scene.Scene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Application extends JFrame implements KeyListener {

    private Panel panel;
    public Application(Scene scene, Settings settings){


        panel = new Panel(this, scene, settings);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setVisible(true);
        this.setSize(500, 500);
        this.setFocusable(true);

        addKeyListener(this);

        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                dispose();
                panel.onWindowClosed();
            }
        });

        Toolkit.getDefaultToolkit().setDynamicLayout(false);

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
