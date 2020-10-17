package user.createuser;


import dto.User;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import user.UserBaseTest;

import static org.hamcrest.Matchers.*;

public class CreateUserTest extends UserBaseTest {
    Response response;
    User user;
    String expectedType = "unknown";

    @Test
    public void checkCreateUser() {
        Long id = 170L;

        user = User.builder()
                .id(id)
                .username("HappyPet")
                .firstName("Happy")
                .lastName("Pet")
                .email("petshoptest@mail.com")
                .password("ygvuhb1")
                .phone("8-000-000-00-00")
                .userStatus(1L)
                .build();

        response = userService.addUserRequest(user);

        response
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .time(lessThan(5000L))
                .body("type", equalTo(expectedType))
                .body("message", comparesEqualTo(id.toString()));
    }

    @Test
    public void checkCreateUserMinSet() {
        user = User.builder()
                .username("HappyPet")
                .email("petshoptest@mail.com")
                .build();

        response = userService.addUserRequest(user);

        response
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .time(lessThan(5000L))
                .body("type", equalTo(expectedType));
    }

    @Test
    public void checkCreateUserNegativeUserStatus() {
        String expectedErrorMessage = "something bad happened";

        user = User.builder()
                .id(170L)
                .username("RainyPet")
                .firstName("Rainy")
                .lastName("Pet")
                .email("petshoprainytest@mail.com")
                .password("ygvuhb1")
                .phone("8-000-000-00-01")
                .userStatus(2147483648L)
                .build();

        response = userService.addUserRequest(user);

        response.then()
                .log().all()
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
                .time(lessThan(5000L))
                .body("type", equalTo(expectedType))
                .body("message", comparesEqualTo(expectedErrorMessage));
    }

}
