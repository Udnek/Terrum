package me.udnek.app;

import me.udnek.app.console.Command;
import me.udnek.app.console.Console;
import me.udnek.app.console.ConsoleHandler;
import me.udnek.scene.Scene;
import me.udnek.util.UserAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Application extends JFrame implements KeyListener, ConsoleHandler {

    private Panel panel;
    public Application(Scene scene, AppSettings appSettings){


        panel = new Panel(this, scene, appSettings);

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

        new Console(this).start();

        panel.loop();
    }

    @Override
    public void handleCommand(Command command, String[] args) {}

    @Override
    public void keyPressed(KeyEvent e) {
        panel.handleKeyInput(UserAction.getByCode(e.getKeyCode()));
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}

}
