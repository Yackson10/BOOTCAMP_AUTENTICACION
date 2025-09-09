package co.com.authentication.r2dbc;

import co.com.authentication.r2dbc.entity.UserEntity;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

public class UserEntityTest {

    @Test
    void deberiaCrearEntidadConConstructorYGetters() {
        LocalDate fecha = LocalDate.of(1995, 8, 20);

        UserEntity entidad = new UserEntity(
                1L,
                "Pedro",
                "Gómez",
                fecha,
                "Calle 45 # 10",
                "3012345678",
                "pedro.gomez@mail.com",
                3500.0,
                "1",
                "1",
                3L
        );

        assertThat(entidad.getId()).isEqualTo(1L);
        assertThat(entidad.getFirstName()).isEqualTo("Pedro");
        assertThat(entidad.getLastName()).isEqualTo("Gómez");
        assertThat(entidad.getBirthDate()).isEqualTo(fecha);
        assertThat(entidad.getAddress()).isEqualTo("Calle 45 # 10");
        assertThat(entidad.getPhone()).isEqualTo("3012345678");
        assertThat(entidad.getEmail()).isEqualTo("pedro.gomez@mail.com");
        assertThat(entidad.getBaseSalary()).isEqualTo(3500.0);
        assertThat(entidad.getIdRol()).isEqualTo(3L);
    }

    @Test
    void deberiaPermitirSettersYGetters() {
        UserEntity entidad = new UserEntity();
        entidad.setId(2L);
        entidad.setFirstName("Laura");
        entidad.setLastName("Martínez");

        assertThat(entidad.getId()).isEqualTo(2L);
        assertThat(entidad.getFirstName()).isEqualTo("Laura");
        assertThat(entidad.getLastName()).isEqualTo("Martínez");
    }
}
