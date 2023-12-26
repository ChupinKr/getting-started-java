package com.lessons.tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class LoginTests {

    @Test
    void successLoginTest() {
        Configuration.holdBrowserOpen = true;

        open("https://qa.guru/cms/system/login/");
        $(".login-form").shouldHave(text("Войти"));

        $("[name=email]").setValue("chupin.kr@gmail.com");
        $("[name=password]").setValue("Fylhttdf01").pressEnter();

        $(".main-header__login").click();

        //$(".html-content").shouldHave(text("Вы авторизованы в этом аккаунте"));
        $(".logined-form").shouldHave(text("Здравствуйте, "));
    }

    @Test
    void failLoginWrongPassTest() {
        Configuration.holdBrowserOpen = true;

        open("https://qa.guru/cms/system/login/");
        $(".login-form").shouldHave(text("Войти"));

        $("[name=email]").setValue("chupin.kr@gmail.com");
        $("[name=password]").setValue("Fylhttdf00").pressEnter();
        $(".btn-error").shouldHave(text("Неверный пароль"));

    }

    @Test
    void failLoginMissingMailTest() {
        Configuration.holdBrowserOpen = true;

        open("https://qa.guru/cms/system/login/");
        $(".login-form").shouldHave(text("Войти"));

        $("[name=password]").setValue("Fylhttdf00").pressEnter();

        $(".btn-error").shouldHave(text("Не заполнено поле E-Mail"));

    }
}
