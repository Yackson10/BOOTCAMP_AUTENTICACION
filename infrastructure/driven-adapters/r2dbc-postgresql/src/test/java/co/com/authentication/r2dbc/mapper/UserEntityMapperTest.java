package co.com.authentication.r2dbc.mapper;

import co.com.authentication.model.user.User;
import co.com.authentication.r2dbc.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

public class UserEntityMapperTest {

    @InjectMocks
    private final UserEntityMapper mapper = Mappers.getMapper(UserEntityMapper.class);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void MapearModeloAEntidad() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Juan");
        user.setLastName("Pérez");
        user.setBirthDate(LocalDate.of(1990, 5, 10));
        user.setAddress("Calle Falsa 123");
        user.setPhone("3001234567");
        user.setEmail("juan.perez@mail.com");
        user.setBaseSalary(2500.0);


        UserEntity entity = mapper.toEntityFromModel(user);


        assertThat(entity).isNotNull();
        assertThat(entity.getFirstName()).isEqualTo("Juan");
        assertThat(entity.getLastName()).isEqualTo("Pérez");
        assertThat(entity.getBirthDate()).isEqualTo(LocalDate.of(1990, 5, 10));
        assertThat(entity.getBaseSalary()).isEqualTo(2500.0);
        assertThat(entity.getIdRol()).isEqualTo(2L);
    }

}
