package com.lessons.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.selector.ByText;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class SearchTests {
    @Test
    void successfulSearchTest() {
        open("https://www.google.com/");
        $("[name=q]").setValue("selenide").pressEnter();
    }
    @Test
    void alphaSearchTest() {
        Configuration.holdBrowserOpen = true;

        open("https://alfabank.ru");
        $(byText("Вклады")).click();
        $("body").shouldHave(text("Счета и вклады"));
        ElementsCollection bills = $$(byText("Накопительные счета"));
        System.out.println("BILLS");
        System.out.println(bills.size());
        bills.find(visible).click();
        $$("button").find(text("Накопительные счета")).shouldHave(attribute("data-test-selected", "true"));
    }
    @Test
    void amazonSearchTest() {
        Configuration.holdBrowserOpen = true;

        open("https://amazon.com");
    }
}
