package how.autotest.todomvc4tasj.common.selenide;

import com.codeborne.selenide.Command;
import com.codeborne.selenide.SelenideElement;
import how.autotest.todomvc4tasj.common.selenide.commands.SetValueByJs;

public class Action {
    public static class JS {
        public static Command<SelenideElement> setValue(String text){
            return new SetValueByJs(text);
        }
    }
}
