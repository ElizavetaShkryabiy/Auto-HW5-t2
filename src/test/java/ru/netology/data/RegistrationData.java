package ru.netology.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class RegistrationData {
    public RegistrationData() {
    }

    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static void sendRequest(UserInfo user) {
        given()
                .spec(requestSpec)
                .body(user) // передаём в теле объект, который будет преобразован в JSON
                .when()
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then()
                .statusCode(200);
    }

    static final Faker faker = new Faker(Locale.forLanguageTag("en"));

    public static String getRandomLogin() {
        String login = faker.name().username();
        return login;
    }

    public static String getRandomPassword() {
        String password = faker.code().asin();
        return password;
    }


    public static class Registration {
        private Registration() {
        }

        public static UserInfo getUser(String status) {
            String login = RegistrationData.getRandomLogin();
            String password = RegistrationData.getRandomPassword();
            UserInfo user = new UserInfo(login, password, status);
            return user;
        }

        public static UserInfo getRegisteredUser(String status) {
            UserInfo registeredUser = Registration.getUser(status);
            new RegistrationData().sendRequest(registeredUser);
            return registeredUser;
        }

    }

    @Value
    public static class UserInfo {
        String login;
        String password;
        String status;
    }

    @Test
    void shouldGenerateNewActiveUserWithHardcodedData() {
        given()
                .spec(requestSpec)
                .body(new UserInfo("Vasya", "12345", "active")) // передаём в теле объект, который будет преобразован в JSON
                .when()
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then()
                .statusCode(200);
    }

    @Test
    void shouldGenerateNewBlockedUserWithHardcodedData() {
        given()
                .spec(requestSpec)
                .body(new UserInfo("Katya", "12345", "blocked")) // передаём в теле объект, который будет преобразован в JSON
                .when()
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then()
                .statusCode(200);
    }


}
