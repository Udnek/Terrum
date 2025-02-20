package me.udnekjupiter.app.window;

import me.udnekjupiter.util.utilityinterface.Initializable;
import me.udnekjupiter.util.utilityinterface.Tickable;
import org.jetbrains.annotations.NotNull;

import java.awt.*;


public interface Window extends Initializable, Tickable {

    String TITLE = "Terrum";

    void setTitle(@NotNull String title);
    void setFrame(@NotNull Image image);
    int getWidth();
    int getHeight();
    void setSize(int width, int height);
}
