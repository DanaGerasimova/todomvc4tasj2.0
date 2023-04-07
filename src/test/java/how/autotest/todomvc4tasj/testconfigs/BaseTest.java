package how.autotest.todomvc4tasj.testconfigs;

import com.codeborne.selenide.Configuration;

public class BaseTest {
    {
        Configuration.browser = "firefox";
        Configuration.timeout = 6000;
        Configuration.fastSetValue = true;
    }
}
