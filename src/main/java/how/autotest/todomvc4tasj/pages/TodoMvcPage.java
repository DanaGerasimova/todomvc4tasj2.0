package how.autotest.todomvc4tasj.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import how.autotest.todomvc4tasj.common.selenide.Action;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class TodoMvcPage {
    private final SelenideElement todoNew = $("#new-todo");
    private final SelenideElement itemsLeft = $("#todo-count>strong");
    private final ElementsCollection todoList = $$("#todo-list>li");
    private final ElementsCollection activeTodoList = todoList.filterBy(cssClass("active"));
    private final ElementsCollection completedTodoList = todoList.filterBy(cssClass("completed"));
    private final SelenideElement footer = $("#footer");
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
    public void givenOpenedWithAllActiveTodosOnActiveFilter(String... texts){
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
    public SelenideElement startEdit(String text, String textToAdd){
        todoByText(text).doubleClick();
        return todoList.findBy(cssClass("editing")).$(".edit")
                .execute(Action.JS.setValue(textToAdd));
    }
    public void edit(String text, String textToAdd) {
        startEdit(text, textToAdd).pressEnter();
    }
    public void editByTab(String text, String textToAdd) {
        startEdit(text, textToAdd).pressTab();
    }
    public void activeTodosShouldBe(String... texts){
        activeTodoList.shouldHave(exactTexts(texts));
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
    public void cancelEdit(String text, String textToAdd) {
        startEdit(text, textToAdd).pressEscape();
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
    public void footerShouldBeVisible(){
        footer.shouldBe(visible);
    }
    public void footerShouldBeHidden(){
        footer.shouldNotBe(visible);
    }
}
