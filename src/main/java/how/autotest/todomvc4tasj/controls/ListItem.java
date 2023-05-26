package how.autotest.todomvc4tasj.controls;

import com.codeborne.selenide.SelenideElement;
import how.autotest.todomvc4tasj.common.selenide.Action;

import static com.codeborne.selenide.Selenide.$;

public class ListItem {
    private final SelenideElement element;
    public ListItem(SelenideElement element){
        this.element = element;
    }
    public SelenideElement startEdit(String textToAdd){
        this.element.doubleClick();
        return $(".editing").$(".edit").execute(Action.JS.setValue(textToAdd));
    }
    public ListItem edit(String textToAdd) {
        this.startEdit(textToAdd).pressEnter();
        return this;
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 0a4a1d2 (extract ListItem widget)
