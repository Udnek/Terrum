package me.udnek.utils;

import java.awt.event.KeyEvent;

public enum UserAction {

    NOTHING(0),

    FORWARD(KeyEvent.VK_W),
    BACKWARD(KeyEvent.VK_S),
    RIGHT(KeyEvent.VK_D),
    LEFT(KeyEvent.VK_A),

    CAMERA_UP(KeyEvent.VK_UP),
    CAMERA_DOWN(KeyEvent.VK_DOWN),
    CAMERA_RIGHT(KeyEvent.VK_RIGHT),
    CAMERA_LEFT(KeyEvent.VK_LEFT);

    public final int code;
    UserAction(int code){
        this.code = code;
    }

    public static UserAction getByCode(int code){
        for (UserAction value : UserAction.values()) {
            if (value.code == code) return value;
        }
        return NOTHING;
    }
}
