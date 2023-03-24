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
    private final SelenideElement todoNew = $("#new-todo");
    private final ElementsCollection todoList = $$("#todo-list>li");
    private final SelenideElement btnClearCompleted = $("#clear-completed");
    private void add(String... texts) {
        for (String text: texts) {
            todoNew.append(text).pressEnter();
        }
    }
    private void todosShouldBe(String... texts) {
        todoList.shouldHave(exactTexts(texts));
    }
    private SelenideElement todoBy(Condition condition){
        return todoList.findBy(condition);
    }
    private SelenideElement byText(String text){
        return todoBy(exactText(text));
    }
    private SelenideElement startEdit(String text, String textToAdd){
        byText(text).doubleClick();
        return todoBy(cssClass("editing")).$(".edit").append(textToAdd);
    }
    private void edit(String text, String textToAdd) {
        startEdit(text, textToAdd).pressEnter();
    }
    private void cancelEdit(String text, String textToAdd) {
        startEdit(text, textToAdd).pressEscape();
    }
    private void clearCompleted(){
        btnClearCompleted.click();
    }
    private void complete(String text){
        byText(text).$(".toggle").click();
    }
    private void delete(String text){
        byText(text).hover().$(".destroy").click();
    }
}
