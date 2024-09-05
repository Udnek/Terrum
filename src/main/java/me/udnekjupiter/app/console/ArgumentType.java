package me.udnekjupiter.app.console;

public abstract class ArgumentType<T> {

    public static final ArgumentType<Integer> INTEGER = new ArgumentType<>() {
        @Override
        public String getName() {
            return "int";
        }
        @Override
        public Integer convert(String string) {
            try {
                return Integer.parseInt(string);
            } catch (NumberFormatException e) {
                return null;
            }
        }
    };
    public static final ArgumentType<Double> DOUBLE = new ArgumentType<>() {
        @Override
        public String getName() {
            return "double";
        }
        @Override
        public Double convert(String string) {
            try {
                return Double.parseDouble(string);
            } catch (NumberFormatException e) {
                return null;
            }
        }
    };
    public static final ArgumentType<Boolean> BOOLEAN = new ArgumentType<>() {
        @Override
        public String getName() {return "boolean";}
        @Override
        public Boolean convert(String string) {
            try {
                double number = Double.parseDouble(string);
                return number > 0;
            } catch (NumberFormatException e) {
                return Boolean.parseBoolean(string);
            }
        }
    };
    public abstract T convert(String string);
    public abstract String getName();
}
