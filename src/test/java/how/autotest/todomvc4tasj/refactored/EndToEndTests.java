package how.autotest.todomvc4tasj.refactored;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
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
        toDoList.shouldHave(exactTexts("a","b","c"));
        doubleClick("c");
        edit(" edited").pressEnter();
        doubleClick("c edited");
        edit(" edited again").pressEscape();
        toggleToDo("c edited");
        clearCompleted();
        toDoList.shouldHave(exactTexts("a","b"));
        deleteToDo("b");
        toDoList.shouldHave(exactTexts("a"));
    }
    private final ElementsCollection toDoList = $$("#todo-list>li");
    private SelenideElement getElementFromToDoListFindBy(Condition condition) {
        return toDoList.findBy(condition);
    }
    private SelenideElement edit(String value) {
        return getElementFromToDoListFindBy(cssClass("editing")).$(".edit").append(value);
    }
    private void doubleClick(String value){
        getElementFromToDoListFindBy(exactText(value)).doubleClick();
    }
    private void toggleToDo(String value) {
        getElementFromToDoListFindBy(exactText(value)).$(".toggle").click();
    }
    private void deleteToDo(String value) {
        getElementFromToDoListFindBy(exactText(value)).hover().$(".destroy").click();
    }
    private void clearCompleted() {
        $("#clear-completed").click();
    }
    private void add(String... taskTexts) {
        for (String text: taskTexts) {
            element("#new-todo").append(text).pressEnter();
        }
    }
}
