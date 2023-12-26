package com.lessons.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class Lesson3 {
    @Test
    void checkSelenideRepoAtTop(){
        Configuration.holdBrowserOpen = true;


        open("https://github.com");// arrange

        $(".header-search-button").click();// act
        $(".search-suggestions.position-fixed").shouldNotBe(hidden);// assert

        $("[name=query-builder-test]").setValue("selenide").pressEnter();// act

        $$(".search-title a").get(2).click();// act
        $("[rel=\"author\"]").shouldHave(exactText("selenide"));// assert
        $("#repository-container-header>div>div>div>strong a").shouldHave(exactText("selenide"));// assert
        //equals
        $("#repository-container-header").shouldHave(text("selenide / selenide"));// assert

    }

    @Test
    void checkSelenideBestContributor(){
        Configuration.holdBrowserOpen = true;


        open("https://github.com/selenide/selenide");// arrange

        SelenideElement contributors = $(".BorderGrid").$(byText("Contributors"));
        contributors.ancestor(".BorderGrid-row").$$("ul li").get(0).hover();// act
        $(".Truncate a").shouldHave(text("Andrei Solntsev"));// assert

    }

    @Test
    void checkSeletideWikiSoftAssertionsJUnit5(){//hw
        Configuration.holdBrowserOpen = true;


        open("https://github.com/selenide/selenide");// arrange

        $("#wiki-tab").click();// act
        $("input#wiki-pages-filter").setValue("SoftAssertions");// act
        SelenideElement softAssertions = $$("a").find(text("SoftAssertions"));
        softAssertions.exists();// assert

        softAssertions.click();// act
        $$("h4").find(text("JUnit5")).shouldHave(text("JUnit5"));// assert
    }
}
