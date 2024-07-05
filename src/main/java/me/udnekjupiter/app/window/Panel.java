package me.udnekjupiter.app.window;

import me.udnekjupiter.app.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Panel extends JPanel implements MouseListener, MouseMotionListener {
    private final Controller controller;
    public Panel(){
        addMouseListener(this);
        addMouseMotionListener(this);
        controller = Controller.getInstance();
    }
    public void paint(Graphics g) {}
    public void paintComponent(Graphics g) {}
    public void repaint() {}
    public void update(Graphics g) {}
    public void updateUI()  {}

    @Override
    public void mousePressed(MouseEvent event) {
        controller.mouseChanges(event, true);
    }
    @Override
    public void mouseReleased(MouseEvent event) {
        controller.mouseChanges(event, false);
    }
    @Override
    public void mouseMoved(MouseEvent event) {
        controller.setMouseCurrentPosition(event.getPoint());
    }

    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseDragged(MouseEvent e) {}
    @Override
    public void mouseClicked(MouseEvent e) {}

}
