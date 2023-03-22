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
        todosShouldBe("a","b","c");

        edit("c"," edited");
        cancelEdit("c edited"," edited again");

        complete("c edited");
        clearCompleted();
        todosShouldBe("a","b");

        delete("b");
        todosShouldBe("a");
    }
    private final ElementsCollection todoList = $$("#todo-list>li");
    private void add(String... texts) {
        for (String text: texts) {
            $("#new-todo").append(text).pressEnter();
        }
    }
    private void todosShouldBe(String... texts) {
        todoList.shouldHave(exactTexts(texts));
    }
    private void edit(String text, String textToAdd) {
        todoList.findBy(exactText(text)).doubleClick();
        todoList.findBy(cssClass("editing")).$(".edit").append(textToAdd).pressEnter();
    }
    private void cancelEdit(String text, String textToAdd) {
        todoList.findBy(exactText(text)).doubleClick();
        todoList.findBy(cssClass("editing")).$(".edit").append(textToAdd).pressEscape();
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
