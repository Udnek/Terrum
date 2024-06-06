package me.udnek.app;

import me.udnek.app.console.Command;
import me.udnek.app.console.Console;
import me.udnek.app.console.ConsoleHandler;
import me.udnek.scene.Scene;
import me.udnek.util.UserAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Application extends JFrame implements KeyListener, MouseListener, ConsoleHandler {

    private Panel panel;
    public Application(Scene scene){


        panel = new Panel(this, scene);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setVisible(true);
        this.setSize(500, 500);
        this.setFocusable(true);

        addKeyListener(this);
        addMouseListener(this);


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
    public void handleCommand(Command command, Object[] args) {
        panel.handleCommand(command, args);
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        panel.handleKeyInput(UserAction.getByCode(keyEvent.getKeyCode()));
    }
    @Override
    public void mousePressed(MouseEvent e) {
        panel.setMousePressed(true);
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        panel.setMousePressed(false);
    }

    // UNUSED
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
