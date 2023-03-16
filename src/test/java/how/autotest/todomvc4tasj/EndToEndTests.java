package how.autotest.todomvc4tasj;

import com.codeborne.selenide.*;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class EndToEndTests {
    @Test
    void todoListBasicOperations(){
        Configuration.browser = "firefox";
        open("https://todomvc4tasj.herokuapp.com/");
        Wait().until(ExpectedConditions.jsReturnsValue(
                "return $._data($('#new-todo').get(0), 'events').hasOwnProperty('keyup');"));

        add("a","b","c");
        toDosShouldHave("a","b","c");

        edit("c"," edited").pressEnter();
        edit("c edited"," edited again").pressEscape();

        toDoBy(exactText("c edited")).$(".toggle").click();
        $("#clear-completed").click();
        toDosShouldHave("a","b");

        toDoBy(exactText("b")).hover().$(".destroy").click();
        toDosShouldHave("a");
    }
    private final ElementsCollection toDoList = $$("#todo-list>li");
    private void add(String... texts) {
        for (String text: texts) {
            $("#new-todo").append(text).pressEnter();
        }
    }
    private void toDosShouldHave(String... texts) {
        toDoList.shouldHave(exactTexts(texts));
    }
    private SelenideElement toDoBy(Condition condition) {
        return toDoList.findBy(condition);
    }
    private SelenideElement edit(String text, String textToAdd) {
        toDoBy(exactText(text)).doubleClick();
        return toDoBy(cssClass("editing")).$(".edit").append(textToAdd);
    }
}
