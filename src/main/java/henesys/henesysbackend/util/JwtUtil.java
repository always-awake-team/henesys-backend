package henesys.henesysbackend.util;

public class JwtUtil {

    private static final String DELIMITER = "Bearer ";

    public static String substringToken(String token) {
        return token.substring(DELIMITER.length());
    }

}
