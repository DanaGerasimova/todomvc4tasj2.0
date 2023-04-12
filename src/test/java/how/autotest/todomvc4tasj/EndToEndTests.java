package how.autotest.todomvc4tasj;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import how.autotest.todomvc4tasj.common.selenide.Action;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class EndToEndTests extends AtTodoMvcWithClearedStorageBeforeEachTest {
    @Test
    void todoListBasicOperations(){
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
    @Test
    void filtersTasks() {
        add("a", "b", "c");

        toggle("b");
        filterByCompleted();
        todosCompletedShouldBe("b");
        todosShouldBe("","b","");

        filterByActive();
        todosActiveShouldBe("a","c");
        todosShouldBe("a","","c");

        filterByAll();
        todosShouldBe("a","b","c");
    }
    private final SelenideElement todoNew = $("#new-todo");
    private final ElementsCollection todoList = $$("#todo-list>li");
    private final ElementsCollection todoListActive = $$("#todo-list>li.active");
    private final ElementsCollection todoListCompleted = $$("#todo-list>li.completed");
    private final SelenideElement btnClearCompleted = $("#clear-completed");
    private final SelenideElement btnAll = $("a[href='#/']");
    private final SelenideElement btnActive = $("a[href='#/active']");
    private final SelenideElement btnCompleted = $("a[href='#/completed']");
    private void add(String... texts) {
        for (String text: texts) {
            todoNew.append(text).pressEnter();
        }
    }
    private void todosShouldBe(String... texts) {
        todoList.shouldHave(exactTexts(texts));
    }
    private void todosActiveShouldBe(String... texts) {
        todoListActive.shouldHave(exactTexts(texts));
    }
    private void todosCompletedShouldBe(String... texts) {
        todoListCompleted.shouldHave(exactTexts(texts));
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
    private void filterByAll(){
        btnAll.click();
    }
    private void filterByActive(){
        btnActive.click();
    }
    private void filterByCompleted(){
        btnCompleted.click();
    }
}
