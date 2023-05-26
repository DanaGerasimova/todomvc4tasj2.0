package how.autotest.todomvc4tasj.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import how.autotest.todomvc4tasj.controls.ListItem;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class TodoMvcPage {
    private final SelenideElement todoNew = $("#new-todo");
    private final SelenideElement itemsLeft = $("#todo-count>strong");
    private final ElementsCollection todoList = $$("#todo-list>li");
    private final ElementsCollection activeTodoList = todoList.filterBy(cssClass("active"));
    private final ElementsCollection completedTodoList = todoList.filterBy(cssClass("completed"));
    public void givenOpened() {
        open("/");
        clearBrowserLocalStorage();
        Wait().until(ExpectedConditions.jsReturnsValue(
                "return $._data($('#new-todo').get(0), 'events').hasOwnProperty('keyup');"));
    }
    public void givenOpenedWith(String... texts){
        givenOpened();
        add(texts);
    }
    public void givenOpenedOnActiveFilterWith(String... texts){
        givenOpenedWith(texts);
        filterByActive();
    }
    public void givenOpenedWithAllCompletedTodosOnCompletedFilter(String... texts){
        givenOpenedWith(texts);
        toggleAll();
        filterByCompleted();
    }
    public void add(String... texts) {
        for (String text: texts) {
            todoNew.append(text).pressEnter();
        }
    }
    public void todosShouldBe(String... texts) {
        todoList.filterBy(visible).shouldHave(exactTexts(texts));
    }
    public SelenideElement todoByText(String text){
        return todoList.findBy(exactText(text));
    }
    public void activeTodosShouldBe(String... texts){
        activeTodoList.shouldHave(exactTexts(texts));
    }
    public void editByTab(String text, String textToAdd) {
        todo(text).startEdit(textToAdd).pressTab();
    }
    public void cancelEdit(String text, String textToAdd) {
        todo(text).startEdit(textToAdd).pressEscape();
    }
    public void activeTodosShouldBeEmpty(){
        activeTodoList.shouldHave(size(0));
    }
    public void completedTodosShouldBe(String... texts){
        completedTodoList.shouldHave(exactTexts(texts));
    }
    public void completedTodosShouldBeEmpty(){
        completedTodoList.shouldHave(size(0));
    }
    public void clearCompleted(){
        $("#clear-completed").click();
    }
    public SelenideElement toggleCheckBox(String text){
        return todoByText(text).$(".toggle");
    }
    public void toggle(String text){
        toggleCheckBox(text).click();
    }
    public void delete(String text){
        todoByText(text).hover().$(".destroy").click();
    }
    public void filterByAll(){
        $("a[href='#/']").click();
    }
    public void filterByActive(){
        $("a[href='#/active']").click();
    }
    public void filterByCompleted(){
        $("a[href='#/completed']").shouldBe(visible)
                .shouldBe(enabled).click();
    }
    public void toggleAll(){
        $("#toggle-all").shouldBe(enabled).click();
    }
    public void itemsLeftShouldBe(Number number){
        itemsLeft.shouldHave(exactText(number.toString()));
    }
    public void todosShouldBeEmpty(){
        todoList.filterBy(visible).shouldHave(size(0));
    }
    public void footerShouldBeHidden(){
        $("#footer").shouldNotBe(visible);
    }
    public ListItem todo(String text){
        return new ListItem(this.todoList.findBy(exactText(text)));
    }
}
