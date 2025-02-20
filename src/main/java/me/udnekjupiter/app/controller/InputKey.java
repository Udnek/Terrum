package me.udnekjupiter.app.controller;

import org.jetbrains.annotations.Nullable;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import static org.lwjgl.glfw.GLFW.*;

public enum InputKey {

    MOVE_FORWARD(KeyEvent.VK_W, GLFW_KEY_W),
    MOVE_BACKWARD(KeyEvent.VK_S, GLFW_KEY_S),
    MOVE_RIGHT(KeyEvent.VK_D, GLFW_KEY_D),
    MOVE_LEFT(KeyEvent.VK_A, GLFW_KEY_A),

    MOVE_DOWN(KeyEvent.VK_SHIFT, GLFW_KEY_LEFT_SHIFT),
    MOVE_UP(KeyEvent.VK_SPACE, GLFW_KEY_SPACE),

    CAMERA_UP(KeyEvent.VK_UP, GLFW_KEY_UP),
    CAMERA_DOWN(KeyEvent.VK_DOWN, GLFW_KEY_DOWN),
    CAMERA_RIGHT(KeyEvent.VK_RIGHT, GLFW_KEY_RIGHT),
    CAMERA_LEFT(KeyEvent.VK_LEFT, GLFW_KEY_LEFT),

    DEBUG_MENU(KeyEvent.VK_F3, GLFW_KEY_F3, false),

    MOUSE_CAMERA_DRAG(MouseEvent.BUTTON1, GLFW_MOUSE_BUTTON_1),
    MOUSE_OBJECT_DRAG(MouseEvent.BUTTON3, GLFW_MOUSE_BUTTON_2),

    PAUSE(KeyEvent.VK_ESCAPE, GLFW_KEY_ESCAPE, false),

    RESET(KeyEvent.VK_R, GLFW_KEY_R, false);

    public final int awtCode;
    public final int glCode;
    public final boolean longPress;

    InputKey(int awtCode, int glCode){
        this(awtCode, glCode, true);
    }

    InputKey(int awtCode, int glCode, boolean longPress){
        this.awtCode = awtCode;
        this.glCode = glCode;
        this.longPress = longPress;
    }

    public static @Nullable InputKey getByAwtCode(int code){
        for (InputKey value : InputKey.values()) {
            if (value.awtCode == code) return value;
        }
        return null;
    }
    public static @Nullable InputKey getByGlCode(int code){
        for (InputKey value : InputKey.values()) {
            if (value.glCode == code) return value;
        }
        return null;
    }
}
