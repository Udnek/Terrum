package me.udnekjupiter.util;

import org.jetbrains.annotations.NotNull;

public interface Listenable<T> {

    void addListener(@NotNull T listener);

}
