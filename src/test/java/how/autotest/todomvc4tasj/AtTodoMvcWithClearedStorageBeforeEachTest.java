package how.autotest.todomvc4tasj;

import how.autotest.todomvc4tasj.testconfigs.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.codeborne.selenide.Selenide.*;

public class AtTodoMvcWithClearedStorageBeforeEachTest extends BaseTest {
    @BeforeEach
    public void openPage() {
        open("/");
        clearBrowserLocalStorage();
        Wait().until(ExpectedConditions.jsReturnsValue(
                "return $._data($('#new-todo').get(0), 'events').hasOwnProperty('keyup');"));
    }
}
