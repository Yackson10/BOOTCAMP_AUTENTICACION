package co.com.authentication.model.user.gateways;

import co.com.authentication.model.user.Rol;
import co.com.authentication.model.user.User;

public interface ITokenProvider {

    String generateToken(User user, Rol rol);

}