package how.autotest.todomvc4tasj;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import how.autotest.todomvc4tasj.common.selenide.Action;
import how.autotest.todomvc4tasj.testconfigs.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class EndToEndTests extends BaseTest {
    @Test
    void todoListBasicOperations(){
        givenAppOpened();

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
    void filtersTodos() {
        givenAppOpenedWith("a", "b", "c");
        toggle("b");

        filterByCompleted();
        todosShouldBe("b");

        filterByActive();
        todosShouldBe("a","c");

        filterByAll();
        todosShouldBe("a","b","c");
    }
    @Test
    void noTodosOnFreshOpen(){
        givenAppOpened();

        // WHEN add nothing

        todosShouldBeEmpty();
        footerShouldBeHidden();
    }
    @Test
    void addFirstTodo(){
        givenAppOpened();

        add("a");

        todosShouldBe("a");
        itemsLeftShouldBe(1);
        footerShouldBeVisible();
    }
    @Test
    void addManyTodos(){
        givenAppOpened();

        add("a","b","c");

        todosShouldBe("a","b","c");
        itemsLeftShouldBe(3);
    }
    @Test
    void completeTodo(){
        givenAppOpenedWith("a","b","c");

        toggle("b");

        completedTodosShouldBe("b");
        activeTodosShouldBe("a","c");
        itemsLeftShouldBe(2);
    }
    @Test
    void clearCompletedTodos(){
        givenAppOpenedWith("a","b","c");
        toggle("b");

        clearCompleted();

        todosShouldBe("a","c");
        itemsLeftShouldBe(2);
    }
    @Test
    void clearCompletedAllTodos(){
        givenAppOpenedWith("a","b","c");
        toggleAll();

        clearCompleted();

        todosShouldBeEmpty();
        footerShouldBeHidden();
    }
    @Test
    void cancelEditTodo(){
        givenAppOpenedWith("a","b","c");

        cancelEdit("b", "b edited");

        todosShouldBe("a","b","c");
        itemsLeftShouldBe(3);
    }
    @Test
    void editTodoByPressEnter(){
        givenAppOpenedWith("a","b","c");

        edit("b","b edited");

        todosShouldBe("a","b edited","c");
        itemsLeftShouldBe(3);
    }
    @Test
    void editTodoByFocusChange(){
        givenAppOpenedWith("a","b","c");

        editByTab("b","b edited");

        todosShouldBe("a","b edited","c");
        itemsLeftShouldBe(3);
    }
    @Test
    void deleteTodoByEditToEmptyString(){
        givenAppOpenedWith("a","b","c");

        edit("b","");

        todosShouldBe("a","c");
        itemsLeftShouldBe(2);
    }
    @Test
    void activateCompletedTodo(){
        givenAppOpenedWith("a","b","c");
        toggle("b");

        toggle("b");

        toggleCheckBox("b").shouldNotBe(selected);
        completedTodosShouldBeEmpty();
        activeTodosShouldBe("a","b","c");
        itemsLeftShouldBe(3);
    }
    @Test
    void completeAllTodos(){
        givenAppOpenedWith("a","b","c");

        toggleAll();

        completedTodosShouldBe("a","b","c");
        activeTodosShouldBeEmpty();
        itemsLeftShouldBe(0);
    }
    @Test
    void completeAllTodosWithSomeCompleted(){
        givenAppOpenedWith("a","b","c");
        toggle("b");

        toggleAll();

        completedTodosShouldBe("a","b","c");
        activeTodosShouldBeEmpty();
        itemsLeftShouldBe(0);
    }
    @Test
    void uncompleteAllTodos(){
        givenAppOpenedWith("a","b","c");
        toggleAll();

        toggleAll();

        completedTodosShouldBeEmpty();
        activeTodosShouldBe("a","b","c");
        itemsLeftShouldBe(3);
    }
    @Test
    void deleteTodo(){
        givenAppOpenedWith("a","b","c");

        delete("b");

        todosShouldBe("a","c");
        itemsLeftShouldBe(2);
    }
    @Test
    void deleteLastTodo(){
        givenAppOpenedWith("a");

        delete("a");

        todosShouldBeEmpty();
        footerShouldBeHidden();
    }
    private final SelenideElement todoNew = $("#new-todo");
    private final SelenideElement itemsLeft = $("#todo-count>strong");
    private final ElementsCollection todoList = $$("#todo-list>li");
    private final ElementsCollection activeTodoList = todoList.filterBy(cssClass("active"));
    private final ElementsCollection completedTodoList = todoList.filterBy(cssClass("completed"));
    private final SelenideElement clearCompleted = $("#clear-completed");
    private final SelenideElement footer = $("#footer");
    public void givenAppOpened() {
        open("/");
        clearBrowserLocalStorage();
        Wait().until(ExpectedConditions.jsReturnsValue(
                "return $._data($('#new-todo').get(0), 'events').hasOwnProperty('keyup');"));
    }
    public void givenAppOpenedWith(String... texts){
        givenAppOpened();
        add(texts);
    }
    private void add(String... texts) {
        for (String text: texts) {
            todoNew.append(text).pressEnter();
        }
    }
    private void todosShouldBe(String... texts) {
        todoList.filterBy(visible).shouldHave(exactTexts(texts));
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
    private void editByTab(String text, String textToAdd) {
        startEdit(text, textToAdd).pressTab();
    }
    private void activeTodosShouldBe(String... texts){
        activeTodoList.shouldHave(exactTexts(texts));
    }
    private void activeTodosShouldBeEmpty(){
        activeTodoList.shouldHave(size(0));
    }
    private void completedTodosShouldBe(String... texts){
        completedTodoList.shouldHave(exactTexts(texts));
    }
    private void completedTodosShouldBeEmpty(){
        completedTodoList.shouldHave(size(0));
    }
    private void cancelEdit(String text, String textToAdd) {
        startEdit(text, textToAdd).pressEscape();
    }
    private void clearCompleted(){
        clearCompleted.click();
    }
    SelenideElement toggleCheckBox(String text){
        return todoByText(text).$(".toggle");
    }
    private void toggle(String text){
        toggleCheckBox(text).click();
    }
    private void delete(String text){
        todoByText(text).hover().$(".destroy").click();
    }
    private void filterByAll(){
        $("a[href='#/']").click();
    }
    private void filterByActive(){
        $("a[href='#/active']").click();
    }
    private void filterByCompleted(){
        $("a[href='#/completed']").shouldBe(visible)
                .shouldBe(enabled).click();
    }
    private void toggleAll(){
        $("#toggle-all").shouldBe(enabled).click();
    }
    private void itemsLeftShouldBe(Number number){
        itemsLeft.shouldHave(exactText(number.toString()));
    }
    private void todosShouldBeEmpty(){
        todoList.shouldHave(size(0));
    }
    private void footerShouldBeVisible(){
        footer.shouldBe(visible);
    }
    private void footerShouldBeHidden(){
        footer.shouldNotBe(visible);
    }
}
