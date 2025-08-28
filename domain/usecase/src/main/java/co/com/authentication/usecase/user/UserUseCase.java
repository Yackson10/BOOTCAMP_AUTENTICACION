package co.com.authentication.usecase.user;

import co.com.authentication.model.user.User;
import co.com.authentication.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
//import org.springframework.transaction.reactive.TransactionalOperator;

import java.util.regex.Pattern;

@RequiredArgsConstructor
public class UserUseCase {

    private static final double MIN_SALARY = 1;
    private static final double MAX_SALARY = 15000000;
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private final UserRepository userRepository;
    //private final TransactionalOperator txOperator;

    public Mono<String> create (User user){
        return Mono.just(user)
                .filter(u-> u.getFirstName()!=null && !u.getFirstName().isEmpty())
                .filter(u -> u.getLastName()!=null && !u.getLastName().isEmpty())
                .filter(u -> u.getEmail()!=null && !u.getEmail().isEmpty())
                .filter(u -> EMAIL_PATTERN.matcher(u.getEmail()).matches())
                .switchIfEmpty(Mono.defer(() -> Mono.error(new RuntimeException("Faltan datos por ingresar"))))
                .flatMap(u -> validateSalary(u)
                        .thenReturn(u))
                .flatMap(this::save);

    }

    public Mono<String> save(User user){
        return userRepository.emailDuplicate(user)
                .filter(isDuplicate -> !isDuplicate)
                .switchIfEmpty(Mono.error(new RuntimeException("El email se encuentra duplicado")))
                .flatMap(valid -> userRepository.save(user))
                .thenReturn("El usuario ha sido creado");
    }

    private Mono<Void> validateSalary(User user) {
        double salary = user.getBaseSalary();

        if (salary < MIN_SALARY) {
            return Mono.error(new RuntimeException("El salario debe ser mayor o igual a " + MIN_SALARY));
        }
        if (salary > MAX_SALARY) {
            return Mono.error(new RuntimeException("El salario debe ser menor o igual a " + String.format("%,.0f", MAX_SALARY)));
        }
        return Mono.empty();
    }





}