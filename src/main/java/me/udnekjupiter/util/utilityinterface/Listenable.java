package me.udnekjupiter.util.utilityinterface;

import org.jetbrains.annotations.NotNull;

public interface Listenable<T> {

    void addListener(@NotNull T listener);

}
