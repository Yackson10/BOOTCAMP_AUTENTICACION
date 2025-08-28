package co.com.authentication.r2dbc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@EnableR2dbcRepositories(
        basePackages = "co.com.authentication.r2dbc.repository",
        entityOperationsRef = "mysqlEntityTemplate"
)
public class MysqlRepositoryConfig {
}
