package me.udnekjupiter.app.window.jwt;

import me.udnekjupiter.Main;
import me.udnekjupiter.app.controller.Controller;
import me.udnekjupiter.app.util.ApplicationSettings;
import me.udnekjupiter.app.window.Window;
import me.udnekjupiter.file.FileManager;
import me.udnekjupiter.util.utilityinterface.Initializable;
import me.udnekjupiter.util.utilityinterface.Tickable;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class JwtWindow extends WindowAdapter implements Window, Initializable, Tickable {

    private Frame frame;
    private Panel panel;

    protected final Controller controller = Controller.getInstance();

    @Override
    public void initialize() {

        ApplicationSettings settings = Main.getMain().getApplication().getSettings();
        frame = new Frame();
        panel = new Panel();

        frame.add(panel);

        panel.setPreferredSize(new Dimension(settings.startWindowWidth, settings.startWindowHeight));

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
    public void setTitle(@NotNull String title) {
        frame.setTitle(title);
    }

    @Override
    public void windowClosing(WindowEvent e) {
        frame.dispose();
        Main.getMain().getApplication().stop();
    }

    @Override
    public void setFrame(@NotNull Image image){
        Graphics graphics = panel.getGraphics();
        if (graphics == null) return;
        graphics.drawImage(image, 0, 0, panel.getWidth(), panel.getHeight(), null);
    }

    @Override
    public int getWidth() {return panel.getWidth();}
    @Override
    public int getHeight() {return panel.getHeight();}

    @Override
    public void setSize(int width, int height){
        panel.setPreferredSize(new Dimension(width, height));
        frame.pack();
    }

    @Override
    public void tick() {
        controller.setMouseCurrentPosition(panel.getMousePosition());
    }
}
