package how.autotest.todomvc4tasj.testconfigs;

import com.codeborne.selenide.Configuration;

public class BaseTest {
    {
        Configuration.browser = System.getProperty(
                "selenide.browser", "firefox");
        Configuration.timeout = Long.parseLong(
                System.getProperty("selenide.timeout", "6000"));
        Configuration.fastSetValue = true;
        Configuration.baseUrl = System.getProperty(
                "selenide.baseUrl",
                "https://todomvc4tasj.herokuapp.com");
    }
}
