package ru.netology;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.RegistrationData.*;
import static ru.netology.data.RegistrationData.Registration.getRegisteredUser;
import static ru.netology.data.RegistrationData.getRandomLogin;
import static ru.netology.data.RegistrationData.getRandomPassword;

public class AuthTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
        void shouldLoginIfRegisteredActiveUser() {
        UserInfo registeredUser = getRegisteredUser("active");
        SelenideElement form = $(".form");
        form.$("[data-test-id=login] input").setValue(registeredUser.getLogin());
        form.$("[data-test-id=password] input").setValue(registeredUser.getPassword());
        form.$(".button[data-test-id=action-login]").click();
        $(".heading").find(withText("Личный кабинет"));
    }


    @Test
        void shouldGetErrorIfNotRegisteredUser() {
        SelenideElement form = $(".form");
        form.$("[data-test-id=login] input").setValue(getRandomLogin());
        form.$("[data-test-id=password] input").setValue(getRandomPassword());
        form.$(".button[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка! Неверно указан логин или пароль"));
    }

    @Test
    void shouldGetErrorIfBlockedUser() {
        UserInfo registeredUser = getRegisteredUser("blocked");
        SelenideElement form = $(".form");
        form.$("[data-test-id=login] input").setValue(registeredUser.getLogin());
        form.$("[data-test-id=password] input").setValue(registeredUser.getPassword());
        form.$(".button[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldBe(appear, Duration.ofSeconds(7))
                .shouldHave(text("Ошибка! Пользователь заблокирован"));
    }

    @Test
    void shouldGetErrorIfWrongLogin() {
        UserInfo registeredUser = getRegisteredUser("active");
        SelenideElement form = $(".form");
        form.$("[data-test-id=login] input").setValue(getRandomLogin());
        form.$("[data-test-id=password] input").setValue(registeredUser.getPassword());
        form.$(".button[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка! Неверно указан логин или пароль"));

    }

    @Test
        void shouldGetErrorIfWrongPassword() {
        UserInfo registeredUser = getRegisteredUser("active");
        SelenideElement form = $(".form");
        form.$("[data-test-id=login] input").setValue(registeredUser.getLogin());
        form.$("[data-test-id=password] input").setValue(getRandomPassword());
        form.$(".button[data-test-id=action-login]").click();
        $("[data-test-id=error-notification]").shouldHave(text("Ошибка! Неверно указан логин или пароль"));

    }
}
