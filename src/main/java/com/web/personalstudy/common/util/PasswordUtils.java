package com.web.personalstudy.common.util;

import org.mindrot.jbcrypt.BCrypt; // ?
import org.springframework.stereotype.Component;

@Component
public class PasswordUtils {

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
