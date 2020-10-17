package services;

import dto.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class UserService {
    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private static final String CREATE_USER = "/user";
    private static final String USER_OTHERS = "/user/";

    RequestSpecification reqSpec;

    public UserService(){
        reqSpec = given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL);
    }

    public Response addUserRequest(User user){
        return given()
                .spec(reqSpec)
                .with()
                .body(user)
                .when()
                .log().all()
                .post(CREATE_USER);
    }

    public Response getUserRequest(String username){
        return given()
                .spec(reqSpec)
                .with()
                .body(username)
                .when()
                .log().all()
                .get(USER_OTHERS + username);
    }
}
