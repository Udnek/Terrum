package me.udnek.app.console;

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
        public String getName() {
            return "boolean";
        }

        @Override
        public Boolean convert(String string) {
            try {
                return Boolean.parseBoolean(string);
            } catch (NumberFormatException e) {
                return null;
            }
        }
    };
    public abstract T convert(String string);
    public abstract String getName();
}
