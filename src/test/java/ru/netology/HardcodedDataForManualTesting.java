package ru.netology;

import org.junit.jupiter.api.Test;
import ru.netology.data.RegistrationData;

import static io.restassured.RestAssured.given;
import static ru.netology.data.RegistrationData.requestSpec;

public class HardcodedDataForManualTesting {
    @Test
    void shouldGenerateNewActiveUserWithHardcodedData() {
        given()
                .spec(requestSpec)
                .body(new RegistrationData.UserInfo("Vasya", "12345", "active")) // передаём в теле объект, который будет преобразован в JSON
                .when()
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then()
                .statusCode(200);
    }

    @Test
    void shouldGenerateNewBlockedUserWithHardcodedData() {
        given()
                .spec(requestSpec)
                .body(new RegistrationData.UserInfo("Katya", "12345", "blocked")) // передаём в теле объект, который будет преобразован в JSON
                .when()
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then()
                .statusCode(200);
    }
}
