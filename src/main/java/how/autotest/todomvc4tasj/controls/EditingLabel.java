package how.autotest.todomvc4tasj.controls;

import com.codeborne.selenide.SelenideElement;
import how.autotest.todomvc4tasj.common.selenide.Action;

import static com.codeborne.selenide.Selenide.$;

public class EditingLabel {
    private final SelenideElement element;
    public EditingLabel(SelenideElement element){
        this.element = element;
    }
    public SelenideElement startEdit(String textToAdd){
        this.element.doubleClick();
        return $(".editing").$(".edit").execute(Action.JS.setValue(textToAdd));
    }
    public EditingLabel edit(String textToAdd) {
        this.startEdit(textToAdd).pressEnter();
        return this;
    }
    public EditingLabel editByTab(String textToAdd) {
        this.startEdit(textToAdd).pressTab();
        return this;
    }
    public EditingLabel cancelEdit(String textToAdd) {
        this.startEdit(textToAdd).pressEscape();
        return this;
    }
}