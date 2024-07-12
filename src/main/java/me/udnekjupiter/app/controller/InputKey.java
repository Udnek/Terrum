package me.udnekjupiter.app.controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public enum InputKey {

    MOVE_FORWARD(KeyEvent.VK_W),
    MOVE_BACKWARD(KeyEvent.VK_S),
    MOVE_RIGHT(KeyEvent.VK_D),
    MOVE_LEFT(KeyEvent.VK_A),

    MOVE_DOWN(KeyEvent.VK_SHIFT),
    MOVE_UP(KeyEvent.VK_SPACE),

    CAMERA_UP(KeyEvent.VK_UP),
    CAMERA_DOWN(KeyEvent.VK_DOWN),
    CAMERA_RIGHT(KeyEvent.VK_RIGHT),
    CAMERA_LEFT(KeyEvent.VK_LEFT),

    DEBUG_MENU(KeyEvent.VK_F3, false),

    MOUSE_CAMERA_DRAG(MouseEvent.BUTTON1),
    MOUSE_OBJECT_DRAG(MouseEvent.BUTTON3),

    PAUSE(KeyEvent.VK_ESCAPE, false),

    RESET(KeyEvent.VK_R, false);

    public final int code;
    public final boolean longPress;

    InputKey(int code){
        this(code, true);
    }

    InputKey(int code, boolean longPress){
        this.code = code;
        this.longPress = longPress;
    }

    public static InputKey getByCode(int code){
        for (InputKey value : InputKey.values()) {
            if (value.code == code) return value;
        }
        return null;
    }
}
