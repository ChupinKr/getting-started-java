package com.lessons.tests;

import com.codeborne.selenide.*;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Keys;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.DragAndDropOptions.to;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class Lesson4 {

    //region Examples
    void browser_command_examples(){
        Configuration.baseUrl = "https://alfabank.ru";
        open("/sme/agent/recomendation-more-new-test/");
        back();//эмуляция кнопки назад
        refresh();//эмуляция обновления странички
        clearBrowserCookies();//чистка куки
        clearBrowserLocalStorage();//чистка стореджа(типа куки, но там больше инфы)
        sessionStorage().clear();//чистка сессии

        //всплывающие окна как на прайме при отправке на аппрув мейкеру(сейчас это редкость)
        confirm();//жмет на всё, что ок в диалоге
        dismiss();//жмет на всё, что отмена в диалоге

        closeWindow();
        closeWebDriver();

        switchTo().window(0);//переход по вкладкам

        //елси ищешь что-то в фрейме(ex: iframe), то нужно сначала перейти внутрь фрейма:
        switchTo().frame(0);
        //вернуться обратно к DOM'у
        switchTo().defaultContent();

        //добавить кукисы через Selenide просто так нельзя, только если так
        var cookie = new Cookie("foo", "bar");
        WebDriverRunner.getWebDriver().manage().addCookie(cookie);
        refresh();
        //после этого стоит делать рефреш странички
    }
    void selectors_examples() {
        //записи ниже идентичны
        $("div");
        element("div");

        $("div", 2);//3rd div

        //xpath
        $x("some xpath");
        $(By.xpath("some xpath"));

        //by text in tag
        $(By.linkText("some text in tag"));//НЕ УВЕРЕН

        //найти элемент по тексту
        $$("some selector").find(text("some text"));
        //equals
        $("some selector").find("cssSelector");
        $("some selector").$("cssSelector");

        $("div").parent();//родитель
        $("div").sibling(0);//ищет соседа вниз
        $("div").preceding(0);//ищет соседа вверх
        $("div").closest("тег");//ищет ближайшего по тегу
        $("div").ancestor("тег", 0);//ищет по тегу и вводимому индексу(как closest почти)

        //псевдо-селектор(но не все работают в селениуме, например div:visible не работает)
        $("div:last-child");
    }
    void action_examples(){

        $("div").sendKeys("c");//нажатие хоткея
        $("div").sendKeys(Keys.chord(Keys.CONTROL, "f"));//нажатие комбинации
        actions().sendKeys(Keys.chord(Keys.CONTROL, "f")).perform();//нажатие комбинации

        actions().moveToElement($("div")).perform();
        actions().moveToElement($("div")).clickAndHold().perform();
        actions().moveToElement($("div")).moveByOffset(300,200).perform();

        //dropdowns
        $("select").selectOption(1);//old
        //*click open list* + *click current element*;//new

        //radiobutton
        $("select").selectRadio("some radio options");
    }
    void assertions_examples(){
        $("div").shouldBe(visible);
        $("div").shouldNotBe(visible);
        $("div").shouldHave(text("abc"));
        $("div").shouldNotHave(text("abc"));
        $("div").should(appear);
        $("div").shouldNot(appear);
        //longer timeouts
        $("div").shouldBe(visible, Duration.ofSeconds(30));

    }
    void conditions_examples(){
        $("div").shouldBe(visible);
        $("div").shouldBe(hidden);

        $("div").shouldHave(text("some text"));

        $("div").shouldHave(cssClass("some class"));
        $("div").shouldHave(cssClass("some class"),cssClass("some class 2"));
        $("div").shouldHave(cssValue("font-size", "300px"));//props from tab "Computed"

        //для введенного текста
        $("div").shouldHave(value("25"));
        $("div").shouldHave(exactValue("25"));
        $("div").shouldHave(empty);

        $("div").shouldHave(attribute("disabled"));
        $("div").shouldHave(attribute("name", "example"));

        //for checkboxes
        $("div").shouldBe(checked);

        //!!!!!!!check if it is in DOM(visible is not necessary)
        $("div").should(exist);

        //check just if it is disabled
        $("div").shouldBe(disabled);
        $("div").shouldBe(enabled);
        //may be replaced by web-devs by class just like disabled-class of something

    }
    void collections_examples(){
        $$("div");
        $$x("div");

        $$("div").filter(disabled);
        $$("div").exclude(disabled);

        $$("div").first();
        $$("div").last();
        $$("div").get(1);
        $$("div").find(enabled);//sing 1st enabled

        $$("div").shouldHave(texts("abc","def"));//1obj contain 1text, 2obj contain 2text
        $$("div").shouldHave(exactTexts("abc","def"));//1obj = 1text, 2obj = 2text



    }
    void uploading_examples() throws FileNotFoundException {
        File file1 = $("a.fileLink").download(); //only for <a href=".."> link
        File file2 = $("div").download(DownloadOptions.using(FileDownloadMode.FOLDER));


        File file = new File("src/test/..../readme.txt");
        $("#file-upload").uploadFile(file);
        $("#file-upload").uploadFromClasspath("readme.txt");
        //dont forget to submit
        $("uploadButton").click();

    }
    void js_examples(){
        executeJavaScript("alert('selenide')");
        executeJavaScript("alert(arguments[0] + arguments[1])", "abc", 12);
        long test = executeJavaScript("return arguments[0] * arguments[1];", 3, 12);
    }
    //endregion

    @Test
    void hw1(){
        open("https://github.com"); //arrange
        $(byText("Solutions")).hover();//act
        $(byText("Enterprise")).click();//act
        $("h1#hero-section-brand-heading").shouldHave(text("The AI-powered"));// assert

    }
    @Test
    void hw2(){
        Configuration.holdBrowserOpen = true;
        open("https://the-internet.herokuapp.com/drag_and_drop"); //arrange
        $("#column-a").shouldHave(text("A"));// assert
        $("#column-b").shouldHave(text("B"));// assert
        actions().moveToElement($("#column-a")).clickAndHold().moveToElement($("#column-b")).release().perform();;//act
        //$("#column-a").dragAndDrop(to($("#column-b")));
        $("#column-a").shouldHave(text("B"));// assert
        $("#column-b").shouldHave(text("A"));// assert

    }
}
