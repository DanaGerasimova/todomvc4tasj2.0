package how.autotest.todomvc4tasj;

import how.autotest.todomvc4tasj.pages.TodoMvcPage;
import how.autotest.todomvc4tasj.testconfigs.BaseTest;
import org.junit.jupiter.api.Test;

public class EndToEndTests extends BaseTest {
    private TodoMvcPage todoMvc = new TodoMvcPage();
    @Test
    void todoListBasicOperations(){
        todoMvc.givenOpened();

        todoMvc.add("a","b","c");
        todoMvc.todosShouldBe("a","b","c");

        todoMvc.edit("c","c edited");
        todoMvc.cancelEdit("c edited","c edited again");

        todoMvc.toggle("c edited");
        todoMvc.clearCompleted();
        todoMvc.todosShouldBe("a","b");

        todoMvc.delete("b");
        todoMvc.todosShouldBe("a");
    }
    @Test
    void filtersTodos() {
        todoMvc.givenOpenedWith("a", "b", "c");
        todoMvc.toggle("b");

        todoMvc.filterByCompleted();
        todoMvc.todosShouldBe("b");

        todoMvc.filterByActive();
        todoMvc.todosShouldBe("a","c");

        todoMvc.filterByAll();
        todoMvc.todosShouldBe("a","b","c");
    }
}
