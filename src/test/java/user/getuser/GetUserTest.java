package user.getuser;

import dto.User;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import user.UserBaseTest;

import static org.hamcrest.Matchers.*;

public class GetUserTest extends UserBaseTest {
    Response response;
    User user;

    @Test
    public void checkGetUser() {
        String username = "HappyPet";
        String expectedemail = "petshoptest@mail.com";
        Long expectedid = 170L;

        user = User.builder()
                .username(username)
                .build();

        response = userService.getUserRequest(username);

        response
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .time(lessThan(5000L))
                .body("email", equalTo(expectedemail))
                .body("id", comparesEqualTo(expectedid.intValue()));
    }

    @Test
    public void checkGetUnexistingUser() {
        int expectedCode = 1;
        String expectedMessageType = "error";
        String expectedErrorMessage = "User not found";
        String username = "HeWhoDoesNotExist";

        user = User.builder()
                .username(username)
                .build();
        response = userService.getUserRequest(username);

        response
                .then()
                .log().all()
                .time(lessThan(5000L))
                .body("code", equalTo(expectedCode))
                .body("type", equalToIgnoringCase(expectedMessageType))
                .body("message", equalToIgnoringCase(expectedErrorMessage));
    }


}
