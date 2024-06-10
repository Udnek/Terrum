package me.udnek.app;

import me.jupiter.file_managment.FileManager;
import me.udnek.app.console.Command;
import me.udnek.app.console.Console;
import me.udnek.app.console.ConsoleHandler;
import me.udnek.scene.Scene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class Application extends JFrame implements KeyListener, ConsoleHandler{

    private final Panel panel;
    public Application(Scene scene){

        panel = new Panel(this, scene);
        this.add(panel);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setPreferredSize(new Dimension(400, 400));
        this.setVisible(true);
        this.setFocusable(true);

        addKeyListener(this);

        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                dispose();
                panel.onWindowClosed();
            }
        });


        Toolkit.getDefaultToolkit().setDynamicLayout(false);

        BufferedImage image = FileManager.readIcon();
        setIconImage(image);
        setTitle("WRLS");

        this.pack();

        new Console(this).start();
        panel.loop();
    }

    @Override
    public void handleCommand(Command command, Object[] args) {
        panel.handleCommand(command, args);
    }
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        panel.controller.keyChanges(keyEvent, true);
    }
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        panel.controller.keyChanges(keyEvent, false);
    }


    // UNUSED

    @Override
    public void keyTyped(KeyEvent e) {}

}
