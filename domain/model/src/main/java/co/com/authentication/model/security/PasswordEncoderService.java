package co.com.authentication.model.security;

public interface PasswordEncoderService {

    String encode(String rawPassword);

    boolean matches(String rawPassword, String encodedPassword);

}
