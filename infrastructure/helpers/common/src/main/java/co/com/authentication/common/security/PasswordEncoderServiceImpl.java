package co.com.authentication.common.security;

import co.com.authentication.model.security.PasswordEncoderService;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoderServiceImpl  implements PasswordEncoderService {

    @Override
    public String encode(String rawPassword) {
        return "";
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return false;
    }
}
