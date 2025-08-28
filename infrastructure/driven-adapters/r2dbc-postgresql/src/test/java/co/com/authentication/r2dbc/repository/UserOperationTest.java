package co.com.authentication.r2dbc.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;


@DataR2dbcTest
public class UserOperationTest {

    @Autowired
    private UserOperation userOperation;


}
