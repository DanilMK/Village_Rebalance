package net.smok.villagerebalance;

public final class Debug {
    public static void log(String message) {
        Values.LOGGER.info(message);
    }

    public static void log(String message, Object... objects) {
        Values.LOGGER.info(message, objects);
    }

    public static void warn(String message) {
        Values.LOGGER.warn(message);
    }

    public static void warn(String message, Object... objects) {
        Values.LOGGER.warn(message, objects);
    }

    public static void warn(String message, Throwable throwable) {
        Values.LOGGER.warn(message, throwable);
    }

    public static void err(String message) {
        Values.LOGGER.error(message);
    }

    public static void err(String message, Object... objects) {
        Values.LOGGER.error(message, objects);
    }

    public static void err(String message, Throwable throwable) {
        Values.LOGGER.error(message, throwable);
    }
}
