package it.scopped.nordregions.utility;

public class StringsUtil {

    public static String replace(String message, Object... params) {
        if (params.length % 2 != 0) {
            throw new IllegalArgumentException("Parameters should be in key-value pairs.");
        }

        for (int i = 0; i < params.length; i += 2) {
            message = message.replace(params[i].toString(), params[i + 1].toString());
        }

        return message;
    }

}
