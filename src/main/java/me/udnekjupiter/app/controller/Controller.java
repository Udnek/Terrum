package me.udnekjupiter.app.controller;

import me.udnekjupiter.util.Listenable;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Controller implements Listenable<ControllerListener>{

    // TODO: 10/7/2024 USE VECTOR ISNTEAD OF LIST

    private final List<InputKey> pressedKeys = Collections.synchronizedList(new ArrayList<>());
    private Point mousePreviousPosition;
    private Point mouseCurrentPosition;
    private InputKey currentMouseKey;

    private final List<ControllerListener> listeners = new ArrayList<>();
    private static Controller instance;

    public static Controller getInstance() {
        if (instance == null){
            instance = new Controller();
        }


        return instance;
    }

    @Override
    public void addListener(@NotNull ControllerListener listener){
        listeners.add(listener);
    }

    private void keyEventForEveryListener(InputKey key, boolean pressed){
        for (ControllerListener listener : listeners) {
            listener.keyEvent(key, pressed);
        }
    }

    public void keyChanges(KeyEvent event, boolean nowPressed){
        InputKey key = InputKey.getByCode(event.getKeyCode());
        if (key == null) return;
        keyEventForEveryListener(key, nowPressed);

        if (key.longPress){
            boolean pressed = pressedKeys.contains(key);
            if (pressed == nowPressed) return;
            if (pressed) pressedKeys.remove(key);
            else pressedKeys.add(key);
        }
    }

    public List<InputKey> getPressedKeys(){
        return new ArrayList<>(pressedKeys);
    }
    public void mouseChanges(MouseEvent event, boolean pressed){
        if (!mouseIsPressed() && !pressed) return;
        if (mouseIsPressed() && pressed) return;

        InputKey key = InputKey.getByCode(event.getButton());

        keyEventForEveryListener(key, pressed);

        // PRESSING
        if (pressed){
            currentMouseKey = key;
        }
        // UN PRESSING
        else {
            currentMouseKey = null;
        }
    }

    public boolean mouseIsPressed(){
        return currentMouseKey != null;
    }
    public InputKey getMouseKey() {
        return currentMouseKey;
    }
    public void setMouseCurrentPosition(Point position){
        if (position == null) return;
        mousePreviousPosition = mouseCurrentPosition;
        mouseCurrentPosition = position;
    }
    public Point getMouseCurrentPosition() {
        return mouseCurrentPosition;
    }

    public Point getMouseDifference(){
        return new Point(
                mouseCurrentPosition.x - mousePreviousPosition.x,
                mouseCurrentPosition.y - mousePreviousPosition.y
        );
    }
}
