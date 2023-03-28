package how.autotest.todomvc4tasj.common.selenide.commands;

import com.codeborne.selenide.Command;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.impl.WebElementSource;
import org.openqa.selenium.WebElement;

import javax.annotation.Nonnull;

public class SetValueByJs implements Command<SelenideElement> {
    private final String text;

    public SetValueByJs(String text) {
        this.text = text;
    }

    @Override
    @Nonnull
    public SelenideElement execute(SelenideElement proxy, WebElementSource locator, Object[] args) {
        WebElement element = locator.findAndAssertElementIsEditable();
        locator.driver().executeJavaScript(
                "" +
                        "var webelement = arguments[0]; " +
                        "var text = arguments[1]; " +
                        "var maxlength = webelement.getAttribute('maxlength') == null " +
                        "? -1 " +
                        ": parseInt(webelement.getAttribute('maxlength')); " +
                        "webelement.value = maxlength === -1 " +
                        "? text " +
                        ": text.length<=maxlength " +
                        "  ? text " +
                        "  : text.substring(0, maxlength);",
                element,
                this.text
        );
        return proxy;
    }
}
