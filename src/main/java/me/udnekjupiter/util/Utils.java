package me.udnekjupiter.util;

import java.text.NumberFormat;

public class Utils {
    public static String roundToPrecision(double number, int precision){
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMinimumFractionDigits(precision);
        return numberFormat.format(number);
    }

}
