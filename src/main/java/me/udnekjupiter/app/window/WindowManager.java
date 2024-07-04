package me.udnekjupiter.app.window;

import me.udnekjupiter.app.Application;
import me.udnekjupiter.app.controller.Controller;
import me.udnekjupiter.file.FileManager;
import me.udnekjupiter.util.Initializable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class WindowManager extends WindowAdapter implements Initializable {

    private Frame frame;
    private Panel panel;

    public static final Controller CONTROLLER;

    private static WindowManager instance;

    static {
        CONTROLLER = new Controller();
    }

    private WindowManager(){}

    public static WindowManager getInstance(){
        if (instance == null){
            instance = new WindowManager();
        }
        return instance;
    }

    @Override
    public void initialize() {

        frame = new Frame();
        panel = new Panel();

        frame.add(panel);

        panel.setPreferredSize(new Dimension(700, 700));

        frame.setIgnoreRepaint(true);
        frame.setFocusable(true);

        frame.setVisible(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit.getDefaultToolkit().setDynamicLayout(false);

        frame.setIconImage(FileManager.readIcon());
        frame.setTitle("WRLS");
        frame.addWindowListener(this);

        frame.pack();
    }

    @Override
    public void windowClosing(WindowEvent e) {
        frame.dispose();
        Application.getInstance().stop();
    }

    public void setFrame(BufferedImage image){
        Graphics graphics = panel.getGraphics();
        if (graphics == null) return;
        graphics.drawImage(image, 0, 0, panel.getWidth(), panel.getHeight(), null);
    }

    public int getWidth() {return panel.getWidth();}
    public int getHeight() {return panel.getHeight();}

    public void setSize(int width, int height){
        panel.setPreferredSize(new Dimension(width, height));
        frame.pack();
    }
}
