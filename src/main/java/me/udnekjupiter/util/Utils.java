package me.udnekjupiter.util;

import java.text.NumberFormat;
import java.util.function.Consumer;

public class Utils {
    public static String roundToPrecision(double number, int precision){
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMinimumFractionDigits(precision);
        return numberFormat.format(number);
    }

    public static <T> void iterate(T[][] array, Consumer<T> consumer){
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                consumer.accept(array[i][j]);
            }
        }
    }

}
