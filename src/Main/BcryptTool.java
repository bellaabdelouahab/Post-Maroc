package Main;


import org.springframework.security.crypto.bcrypt.BCrypt;

public class BcryptTool {
    // hash a password
    public static String hashPassword(String password_text) {
        return BCrypt.hashpw(password_text, BCrypt.gensalt());
    }
    // check a password against a hash
    public static boolean checkPassword(String password_text, String hashed) {
        return BCrypt.checkpw(password_text, hashed);
    }
    public static String generateRandomId() {
        return BCrypt.hashpw(String.valueOf(Math.random()), BCrypt.gensalt()).substring(0, 10);
    }
    
}
