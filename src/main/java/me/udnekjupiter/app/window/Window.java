package me.udnekjupiter.app.window;

import me.udnekjupiter.util.Initializable;
import me.udnekjupiter.util.Tickable;
import org.jetbrains.annotations.NotNull;

import java.awt.*;


public interface Window extends Initializable, Tickable {

    String TITLE = "Terrum";

    void setFrame(@NotNull Image image);
    int getWidth();
    int getHeight();
    void setSize(int width, int height);
}
