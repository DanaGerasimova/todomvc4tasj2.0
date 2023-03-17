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
        todosShouldHave("a","b","c");

        startEdit("c"," edited").pressEnter();
        startEdit("c edited"," edited again").pressEscape();

        toDoBy(exactText("c edited")).$(".toggle").click();
        $("#clear-completed").click();
        todosShouldHave("a","b");

        toDoBy(exactText("b")).hover().$(".destroy").click();
        todosShouldHave("a");
    }
    private final ElementsCollection todoList = $$("#todo-list>li");
    private void add(String... texts) {
        for (String text: texts) {
            $("#new-todo").append(text).pressEnter();
        }
    }
    private void todosShouldHave(String... texts) {
        todoList.shouldHave(exactTexts(texts));
    }
    private SelenideElement toDoBy(Condition condition) {
        return todoList.findBy(condition);
    }
    private SelenideElement startEdit(String text, String textToAdd) {
        toDoBy(exactText(text)).doubleClick();
        return toDoBy(cssClass("editing")).$(".edit").append(textToAdd);
    }
}
