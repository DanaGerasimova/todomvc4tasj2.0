package how.autotest.todomvc4tasj;

import how.autotest.todomvc4tasj.pages.TodoMvcPage;
import how.autotest.todomvc4tasj.testconfigs.BaseTest;
import org.junit.jupiter.api.Test;

public class OperationsOnActiveFilterTest extends BaseTest {
    private TodoMvcPage todoMvc = new TodoMvcPage();
    @Test
    void completedAll(){
        todoMvc.givenOpenedWith("a","b","c");
        todoMvc.toggleAll();
        todoMvc.filterByActive();

        // WHEN add nothing

        todoMvc.todosShouldBeEmpty();
        todoMvc.itemsLeftShouldBe(0);
    }
    @Test
    void addFirstActive(){
        todoMvc.givenOpenedOnActiveFilterWith("a","b","c");

        todoMvc.toggleAll();
        todoMvc.add("d");

        todoMvc.todosShouldBe("d");
        todoMvc.itemsLeftShouldBe(1);
    }
    @Test
    void editActive(){
        todoMvc.givenOpenedOnActiveFilterWith("a","b","c");

        todoMvc.edit("b","b edited");

        todoMvc.todosShouldBe("a","b edited","c");
        todoMvc.itemsLeftShouldBe(3);
    }
    @Test
    void complete(){
        todoMvc.givenOpenedOnActiveFilterWith("a","b","c");

        todoMvc.toggle("b");

        todoMvc.todosShouldBe("a","c");
        todoMvc.itemsLeftShouldBe(2);
    }
    @Test
    void completeAllWithSomeCompleted(){
        todoMvc.givenOpenedWith("a","b","c");
        todoMvc.toggle("b");
        todoMvc.filterByActive();

        todoMvc.toggleAll();

        todoMvc.todosShouldBeEmpty();
        todoMvc.itemsLeftShouldBe(0);
    }
    @Test
    void deleteLastActive(){
        todoMvc.givenOpenedWith("a","b");
        todoMvc.toggle("b");
        todoMvc.filterByActive();

        todoMvc.delete("a");

        todoMvc.todosShouldBeEmpty();
        todoMvc.itemsLeftShouldBe(0);
    }
}