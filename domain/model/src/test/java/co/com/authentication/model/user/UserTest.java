package co.com.authentication.model.user;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserTest {

    @Test
    void crearUsuarioConConstructorYGetters() {
        LocalDate fechaNacimiento = LocalDate.of(1995, 5, 20);

        User user = new User(
                1L,
                "Juan",
                "Pérez",
                fechaNacimiento,
                "Calle 123",
                "3001234567",
                "juan@example.com",
                2500.0,
                2L
        );

        assertEquals(1L, user.getId());
        assertEquals("Juan", user.getFirstName());
        assertEquals("Pérez", user.getLastName());
        assertEquals(fechaNacimiento, user.getBirthDate());
        assertEquals("Calle 123", user.getAddress());
        assertEquals("3001234567", user.getPhone());
        assertEquals("juan@example.com", user.getEmail());
        assertEquals(2500.0, user.getBaseSalary());
        assertEquals(2L, user.getIdRol());
    }

    @Test
    void crearUsuarioConBuilder() {
        LocalDate fechaNacimiento = LocalDate.of(2000, 1, 15);

        User user = User.builder()
                .id(2L)
                .firstName("María")
                .lastName("Gómez")
                .birthDate(fechaNacimiento)
                .address("Av. Siempre Viva 742")
                .phone("3109876543")
                .email("maria@example.com")
                .baseSalary(3000.0)
                .idRol(1L)
                .build();

        assertNotNull(user);
        assertEquals("María", user.getFirstName());
        assertEquals("Gómez", user.getLastName());
        assertEquals(3000.0, user.getBaseSalary());
    }

    @Test
    void modificarUsuarioConToBuilder() {
        User user = User.builder()
                .id(3L)
                .firstName("Carlos")
                .lastName("López")
                .email("carlos@example.com")
                .build();

        // Creamos una copia y cambiamos el email
        User userModificado = user.toBuilder()
                .email("nuevo@example.com")
                .build();

        assertEquals(user.getId(), userModificado.getId());
        assertEquals("Carlos", userModificado.getFirstName());
        assertEquals("nuevo@example.com", userModificado.getEmail());
    }
}

