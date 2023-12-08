package tests;

import models.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static specs.UserSpec.*;

public class ApiTest extends TestBase {

    @Test
    void successfulRequestSingleUserTest() {
        step("Запросить информацию о пользователе", () ->
                given()
                        .spec(singleUserRequestSpec)
                        .when()
                        .get("/users/2")
                        .then()
                        .spec(singleUserResponseSpec)
                        .statusCode(200));
    }

    @Test
    void negativeRequestSingleUserTest() {
        step("Запросить информацию о несуществующем пользователе", () ->
                given()
                        .spec(singleUserRequestSpec)
                        .when()
                        .get("/users/23")
                        .then()
                        .spec(singleUserResponseSpec)
                        .statusCode(404));
    }

    @Test
    void successfulCreateUserTest() {
        CreateUserRequestModel createUserModel = new CreateUserRequestModel();
        createUserModel.setName("morpheus");
        createUserModel.setJob("leader");

        CreateUserResponseModel response = step("Создать пользователя", () ->
                given()
                        .spec(createRequestSpec)
                        .body(createUserModel)
                        .when()
                        .post("/users")
                        .then()
                        .spec(createResponseSpec)
                        .extract().as(CreateUserResponseModel.class));

        step("Проверить имя пользователя", () ->
                assertEquals("morpheus", response.getName()));

        step("Проверить должность пользователя", () ->
                assertEquals("leader", response.getJob()));
    }

    @Test
    void successfulUpdateUserTest() {
        UpdateUserRequestModel updateUserModel = new UpdateUserRequestModel();
        updateUserModel.setName("morpheus");
        updateUserModel.setJob("zion resident");

        UpdateUserResponseModel response = step("Обновить должность пользователя", () ->
                given()
                        .spec(updateRequestSpec)
                        .body(updateUserModel)
                        .when()
                        .put("/users/2")
                        .then()
                        .spec(updateResponseSpec)
                        .extract().as(UpdateUserResponseModel.class));

        step("Проверить новую должность", () ->
                assertEquals("zion resident", response.getJob()));
    }

    @Test
    void successfulDeleteUserTest() {
        step("Удалить пользователя", () ->
                given()
                        .spec(deleteUserRequestSpec)
                        .when()
                        .delete("/users/2")
                        .then()
                        .spec(deleteUserResponseSpec)
                        .statusCode(204));
    }

    @Test
    void negativeRegisterUnsuccessfulTest() {
        RegisterUserRequestModel registerUserModel = new RegisterUserRequestModel();
        registerUserModel.setEmail("sydney@fife");

        RegisterUserResponseModel response = step("Зарегистрировать пользователя", () ->
                given()
                        .spec(registerRequestSpec)
                        .body(registerUserModel)
                        .when()
                        .post("/register")
                        .then()
                        .spec(registerNegativeResponseSpec)
                        .extract().as(RegisterUserResponseModel.class));

        step("Проверить, что пришла ошибка", () ->
                assertEquals("Missing password", response.getError()));
    }

    @Test
    void successfulListUsersTest() {
        ListUserResponseModel response = step("Запросить список пользователей", () ->
                given(listUsersRequestSpec)
                        .when()
                        .get("users?page=2")
                        .then()
                        .spec(listUsersResponseSpec)
                        .statusCode(200)
                        .extract().as(ListUserResponseModel.class));
        step("Проверить общие данные", () -> {
            assertEquals(2, response.getPage());
            assertEquals(6, response.getPerPage());
            assertEquals(12, response.getTotal());
            assertEquals(2, response.getTotalPages());
        });
        step("Проверить данные о первом пользователе", () -> {
            List<ListUserResponseModel.DataList> data = response.getData();
            assertEquals(7, data.get(0).getId());
            assertEquals("michael.lawson@reqres.in", data.get(0).getEmail());
            assertEquals("Michael", data.get(0).getFirstName());
            assertEquals("Lawson", data.get(0).getLastName());
            assertEquals("https://reqres.in/img/faces/7-image.jpg", data.get(0).getAvatar());
        });
    }
}
