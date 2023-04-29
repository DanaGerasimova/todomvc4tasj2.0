package how.autotest.todomvc4tasj;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import how.autotest.todomvc4tasj.common.selenide.Action;
import how.autotest.todomvc4tasj.testconfigs.BaseTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.codeborne.selenide.CollectionCondition.exactTexts;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.url;

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
    void filtersTasks() {
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
    void checkEditTodoByClick(){
        givenAppOpenedWith("a");

        editByClick("a","a edited");
        todosShouldBe("a edited");
    }
    @Test
    void checkTrimTodo(){
        givenAppOpenedWith("  a test   ");

        todosShouldBe("a test");
    }
    @Test
    void checkControlElementsHidingWhileEdit(){
        givenAppOpenedWith("a");

        todoDoubleClick("a");
        toggleCheckBox("a").shouldNotBe(visible);
        deleteBtn("a").shouldNotBe(visible);
    }
    @Test
    void checkTodoDeleteButtonShow(){
        givenAppOpenedWith("a");

        toggleCheckBox("a").shouldBe(visible).shouldBe(enabled).hover();
        deleteBtn("a").shouldBe(visible);
    }
    @Test
    void checkDeleteEmptyStringTodo(){
        givenAppOpenedWith("a");

        add("");
        todosShouldBe("a");
    }
    @Test
    void checkEditSelectedTodoByDoubleClick(){
        givenAppOpenedWith("a");

        toggle("a");
        edit("a","a edited");
        toggleCheckBox("a edited").shouldBe(checked);
    }
    @Test
    void checkTodoCountNotVisible(){
        givenAppOpened();

        todoCount.shouldNotBe(visible);
    }
    @Test
    void checkAddedTodoCount(){
        givenAppOpenedWith("a");

        todoCount.shouldHave(text("1 item left"));
    }
    @Test
    void checkSeveralAddedTodoCount(){
        givenAppOpenedWith("a","b","c");

        todoCount.shouldHave(text("3 items left"));
    }
    @Test
    void checkClearCompletedBtnNotVisible(){
        givenAppOpenedWith("a","b","c");

        clearCompleted.shouldNotBe(visible);
    }
    @Test
    public void checkDeleteTodoByEnterEmptyString(){
        givenAppOpenedWith("a","b","c");

        edit("b","");
        todosShouldBe("a","c");
    }
    @Test
    void uncheckCompletedTodo(){
        givenAppOpenedWith("a","b");

        toggle("b");
        toggleCheckBox("b").shouldBe(selected);
        completedTodosShouldBe("b");
        activeTodosShouldBe("a");

        toggle("b");
        toggleCheckBox("b").shouldNotBe(selected);
        activeTodosShouldBe("a","b");
    }
    @Test
    void checkCompletedAllTodos(){
        givenAppOpenedWith("a","b","c");

        toggleAll();
        completedTodosShouldBe("a","b","c");
    }
    @Test
    void uncheckCompletedAllTodos(){
        givenAppOpenedWith("a","b","c");

        toggleAll();
        completedTodosShouldBe("a","b","c");

        toggleAll();
        activeTodosShouldBe("a","b","c");
    }
    @Test
    void checkStaticElements(){
        givenAppOpened();

        header.shouldHave(text("todos"));

        $$("#info>p")
                .shouldHave(CollectionCondition.exactTexts(
                        "Double-click to edit a todo",
                        "Created by Mikael Karon",
                        "Part of TodoMVC"));

        todoNew.shouldHave(attribute("placeholder",
                "What needs to be done?"));
    }
    @Test
    void checkNewTodoUnFocus(){
        givenAppOpened();

        todoNew.click();
        todoNew.shouldBe(focused).shouldBe(editable);
        header.click();
        todoNew.shouldBe(visible).shouldNotBe(focused);
    }
    @Test
    void checkMikaelKaronLink(){
        givenAppOpened();

        $("a[href='https://github.com/mikaelkaron']").shouldBe(visible).shouldBe(enabled).click();
        webdriver().shouldHave(url("https://github.com/mikaelkaron"));
    }
    @Test
    void checkTodoMvcLink(){
        givenAppOpened();

        $("a[href='http://todomvc.com']").shouldBe(visible).shouldBe(enabled).click();
        webdriver().shouldHave(url("https://todomvc.com/"));
    }
    private final SelenideElement todoNew = $("#new-todo");
    private final SelenideElement todoCount = $("#todo-count");
    private final ElementsCollection todoList = $$("#todo-list>li");
    private final SelenideElement clearCompleted = $("#clear-completed");
    private final SelenideElement header = $("#header h1");
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
    private void todoDoubleClick(String text){
        todoByText(text).doubleClick();
    }
    private SelenideElement startEdit(String text, String textToAdd){
        todoDoubleClick(text);
        return todoList.findBy(cssClass("editing")).$(".edit")
                .execute(Action.JS.setValue(textToAdd));
    }
    private void startEditWithoutElementReturn(String text, String textToAdd){
        startEdit(text, textToAdd);
    }
    private void edit(String text, String textToAdd) {
        startEdit(text, textToAdd).pressEnter();
    }
    private void editByClick(String text, String textToAdd) {
        startEditWithoutElementReturn(text, textToAdd);
        header.click();
    }
    private void completedTodosShouldBe(String... texts){
        $$("#todo-list .completed").shouldHave(exactTexts(texts));
    }
    private void activeTodosShouldBe(String... texts){
        $$("#todo-list .active").shouldHave(exactTexts(texts));
    }
    private void cancelEdit(String text, String textToAdd) {
        startEdit(text, textToAdd).pressEscape();
    }
    private void clearCompleted(){
        clearCompleted.click();
    }
    private SelenideElement toggleCheckBox(String text){
        return todoByText(text).$(".toggle");
    }
    private void toggle(String text){
        toggleCheckBox(text).click();
    }
    private SelenideElement deleteBtn(String text){
        return todoByText(text).$(".destroy");
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
}
