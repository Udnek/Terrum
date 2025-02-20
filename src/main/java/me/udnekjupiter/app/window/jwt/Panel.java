package me.udnekjupiter.app.window.jwt;

import me.udnekjupiter.app.controller.Controller;
import me.udnekjupiter.app.controller.InputKey;

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
        controller.keyChanges(InputKey.getByAwtCode(event.getButton()), true);
    }
    @Override
    public void mouseReleased(MouseEvent event) {
        controller.keyChanges(InputKey.getByAwtCode(event.getButton()), false);
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
