package me.udnek.util;

import java.awt.event.KeyEvent;

public enum UserAction {

    NOTHING(0),

    MOVE_FORWARD(KeyEvent.VK_W),
    MOVE_BACKWARD(KeyEvent.VK_S),
    MOVE_RIGHT(KeyEvent.VK_D),
    MOVE_LEFT(KeyEvent.VK_A),

    MOVE_DOWN(KeyEvent.VK_SHIFT),
    MOVE_UP(KeyEvent.VK_SPACE),

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
