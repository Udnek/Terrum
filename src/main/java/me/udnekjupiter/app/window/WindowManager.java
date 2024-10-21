package me.udnekjupiter.app.window;

import me.udnekjupiter.app.StandartApplication;
import me.udnekjupiter.app.controller.Controller;
import me.udnekjupiter.file.FileManager;
import me.udnekjupiter.util.Initializable;
import me.udnekjupiter.util.Tickable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowManager extends WindowAdapter implements Initializable, Tickable {

    public static final String TITLE = "Terrum";

    private Frame frame;
    private Panel panel;

    private final Controller controller = Controller.getInstance();

    private static WindowManager instance;

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

        panel.setPreferredSize(new Dimension(StandartApplication.APPLICATION_SETTINGS.startWindowWidth, StandartApplication.APPLICATION_SETTINGS.startWindowHeight));

        frame.setIgnoreRepaint(true);
        frame.setFocusable(true);

        frame.setVisible(true);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit.getDefaultToolkit().setDynamicLayout(false);

        frame.setIconImage(FileManager.readIcon());
        frame.setTitle(TITLE);
        frame.addWindowListener(this);

        frame.pack();
    }

    @Override
    public void windowClosing(WindowEvent e) {
        frame.dispose();
        StandartApplication.getInstance().stop();
    }

    public void setFrame(Image image){
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

    @Override
    public void tick() {
        controller.setMouseCurrentPosition(panel.getMousePosition());
    }
}
