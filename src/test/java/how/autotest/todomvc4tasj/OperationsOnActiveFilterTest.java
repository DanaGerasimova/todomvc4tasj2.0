package how.autotest.todomvc4tasj;

import how.autotest.todomvc4tasj.pages.TodoMvcPage;
import how.autotest.todomvc4tasj.testconfigs.BaseTest;
import org.junit.jupiter.api.Test;

public class OperationsOnActiveFilterTest extends BaseTest {
    private TodoMvcPage todoMvc = new TodoMvcPage();
    @Test
    void noActiveTodos(){
        todoMvc.givenOpenedWith("a","b","c");
        todoMvc.toggleAll();
        todoMvc.filterByActive();

        // WHEN add nothing

        todoMvc.activeFilteredTodosShouldBeEmpty();
        todoMvc.itemsLeftShouldBe(0);
    }
    @Test
    void completeTodo(){
        todoMvc.givenOpenedWith("a","b","c");
        todoMvc.filterByActive();

        todoMvc.toggle("b");

        todoMvc.activeFilteredTodosShouldBe("a","c");
        todoMvc.itemsLeftShouldBe(2);
    }
    @Test
    void completeAllTodosWithSomeCompleted(){
        todoMvc.givenOpenedWith("a","b","c");
        todoMvc.toggle("b");
        todoMvc.filterByActive();

        todoMvc.toggleAll();

        todoMvc.completedFilteredTodosShouldBeEmpty();
        todoMvc.itemsLeftShouldBe(0);
    }
    @Test
    void deleteLastActiveTodo(){
        todoMvc.givenOpenedWith("a","b");
        todoMvc.toggle("b");
        todoMvc.filterByActive();

        todoMvc.delete("a");

        todoMvc.activeFilteredTodosShouldBeEmpty();
        todoMvc.itemsLeftShouldBe(0);
    }
}
