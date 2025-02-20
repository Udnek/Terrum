package me.udnekjupiter.app.controller;

import me.udnekjupiter.util.utilityinterface.Listenable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
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

    private void keyEventForEveryListener(@NotNull InputKey key, boolean pressed){
        for (ControllerListener listener : listeners) {
            listener.keyEvent(key, pressed);
        }
    }

    public void keyChanges(@Nullable InputKey key, boolean nowPressed){
        if (key == null) return;
        keyEventForEveryListener(key, nowPressed);

        if (key == InputKey.MOUSE_CAMERA_DRAG || key == InputKey.MOUSE_OBJECT_DRAG){
            if (nowPressed) currentMouseKey = key;
            else currentMouseKey = null;
        }

        if (key.longPress){
            boolean pressed = pressedKeys.contains(key);
            if (pressed == nowPressed) return;
            if (pressed) pressedKeys.remove(key);
            else pressedKeys.add(key);
        }
    }

    public @NotNull List<InputKey> getPressedKeys(){
        return new ArrayList<>(pressedKeys);
    }

    public boolean mouseIsPressed(){
        return currentMouseKey != null;
    }
    public @Nullable InputKey getMouseKey() {
        return currentMouseKey;
    }
    public void setMouseCurrentPosition(Point position){
        if (position == null) return;
        mousePreviousPosition = mouseCurrentPosition;
        mouseCurrentPosition = position;
    }
    public @NotNull Point getMouseCurrentPosition() {
        return mouseCurrentPosition;
    }

    public @NotNull Point getMouseDifference(){
        return new Point(
                mouseCurrentPosition.x - mousePreviousPosition.x,
                mouseCurrentPosition.y - mousePreviousPosition.y
        );
    }
}
