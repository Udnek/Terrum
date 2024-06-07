package me.udnek.app;

import me.udnek.app.console.Command;
import me.udnek.app.console.Console;
import me.udnek.app.console.ConsoleHandler;
import me.udnek.scene.Scene;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Application extends JFrame implements KeyListener, MouseListener, ConsoleHandler {

    private Panel panel;
    public Application(Scene scene){

        panel = new Panel(this, scene);
        this.add(panel);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setFocusable(true);
        int pixelScaling = AppSettings.globalSettings.pixelScaling;
        panel.setPreferredSize(new Dimension(200* pixelScaling, 200*pixelScaling));

        addKeyListener(this);
        addMouseListener(this);

        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                dispose();
                panel.onWindowClosed();
            }
        });

        this.pack();

        Toolkit.getDefaultToolkit().setDynamicLayout(false);

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
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        panel.controller.mouseChanges(mouseEvent, true);
    }
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        panel.controller.mouseChanges(mouseEvent, false);
    }

    // UNUSED
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
