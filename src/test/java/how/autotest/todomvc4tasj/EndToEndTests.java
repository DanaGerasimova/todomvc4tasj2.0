package how.autotest.todomvc4tasj;

import com.codeborne.selenide.*;
import how.autotest.todomvc4tasj.common.selenide.Action;
import how.autotest.todomvc4tasj.testconfigs.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class EndToEndTests extends BaseTest {
    @Test
    void todoListBasicOperations(){
        openPage();

        add("a","b","c");
        todosShouldBe("a","b","c");

        edit("c","c edited");
        cancelEdit("c edited","c edited again");

        toggle("c edited");
        clearCompleted();
        todosShouldBe("a","b");

        delete("b");
        todosShouldBe("a");
    }
    private final SelenideElement todoNew = $("#new-todo");
    private final ElementsCollection todoList = $$("#todo-list>li");
    private final SelenideElement btnClearCompleted = $("#clear-completed");
    private void openPage(){
        String baseUrl = System.getProperty("baseUrl");
        open(baseUrl);
        Wait().until(ExpectedConditions.jsReturnsValue(
                "return $._data($('#new-todo').get(0), 'events').hasOwnProperty('keyup');"));
    }
    private void add(String... texts) {
        for (String text: texts) {
            todoNew.append(text).pressEnter();
        }
    }
    private void todosShouldBe(String... texts) {
        todoList.shouldHave(exactTexts(texts));
    }
    private SelenideElement todoByText(String text){
        return todoList.findBy(exactText(text));
    }
    private SelenideElement startEdit(String text, String textToAdd){
        todoByText(text).doubleClick();
        return todoList.findBy(cssClass("editing")).$(".edit")
                .execute(Action.JS.setValue(textToAdd));
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
    private void toggle(String text){
        todoByText(text).$(".toggle").click();
    }
    private void delete(String text){
        todoByText(text).hover().$(".destroy").click();
    }
}
