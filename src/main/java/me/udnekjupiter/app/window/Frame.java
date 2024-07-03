package me.udnekjupiter.app.window;

import me.udnekjupiter.app.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Frame extends JFrame implements KeyListener {
    private final Controller controller;
    public Frame(){
        addKeyListener(this);
        controller = Controller.getInstance();
    }

    // TODO: 7/3/2024 FUCKING BULLSHIT IDK FUCK MY ASS
    @Override
    public void paint(Graphics graphics) {
        System.out.println("CALLED FRAME PAINT");
    }

    @Override
    public void keyTyped(KeyEvent event) {}

    @Override
    public void keyPressed(KeyEvent event) {
        controller.keyChanges(event, true);
    }

    @Override
    public void keyReleased(KeyEvent event) {
        controller.keyChanges(event, false);
    }
}
