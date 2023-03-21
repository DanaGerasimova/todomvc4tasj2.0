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

        complete("c edited");
        clearCompleted();
        todosShouldHave("a","b");

        delete("b");
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
    private SelenideElement startEdit(String text, String textToAdd) {
        todoList.findBy(exactText(text)).doubleClick();

        return todoList.findBy(cssClass("editing"))
                .$(".edit").append(textToAdd);
    }
    private void clearCompleted(){
        $("#clear-completed").click();
    }
    private void complete(String text){
        todoList.findBy(exactText(text)).$(".toggle").click();
    }
    private void delete(String text){
        todoList.findBy(exactText(text)).hover().$(".destroy").click();
    }
}
