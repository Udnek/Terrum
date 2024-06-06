package me.udnek.util;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public enum UserAction {

    UNKNOWN(0),

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

    DEBUG_MENU(KeyEvent.VK_F3),

    MOUSE_CAMERA_DRAG(MouseEvent.BUTTON1),
    MOUSE_OBJECT_DRAG(MouseEvent.BUTTON3);

    public final int code;
    UserAction(int code){
        this.code = code;
    }

    public static UserAction getByCode(int code){
        for (UserAction value : UserAction.values()) {
            if (value.code == code) return value;
        }
        return UNKNOWN;
    }
}
