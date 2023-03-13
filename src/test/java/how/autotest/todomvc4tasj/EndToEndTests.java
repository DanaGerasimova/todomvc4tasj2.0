package how.autotest.todomvc4tasj;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class EndToEndTests {
    @Test
    void todoListBasicOperations(){
        open("https://todomvc4tasj.herokuapp.com/");
        Wait().until(ExpectedConditions.jsReturnsValue(
                "return $._data($('#new-todo').get(0), 'events').hasOwnProperty('keyup');"));

        $("#new-todo").append("a").pressEnter();
        $("#new-todo").append("b").pressEnter();
        $("#new-todo").append("c").pressEnter();
        $$("#todo-list>li").shouldHave(exactTexts("a","b","c"));

        $$("#todo-list>li").findBy(exactText("c")).doubleClick();
        $$("#todo-list>li").findBy(cssClass("editing")).$(".edit")
                .append(" edited").pressEnter();

        $$("#todo-list>li").findBy(exactText("c edited")).doubleClick();
        $$("#todo-list>li").findBy(cssClass("editing")).$(".edit")
                .append(" edited again").pressEscape();

        $$("#todo-list>li").findBy(exactText("c edited")).$(".toggle").click();
        $("#clear-completed").click();
        $$("#todo-list>li").shouldHave(exactTexts("a","b"));

        $$("#todo-list>li").findBy(exactText("b")).hover()
                .$(".destroy").click();
        $$("#todo-list>li").shouldHave(exactTexts("a"));
    }
}
