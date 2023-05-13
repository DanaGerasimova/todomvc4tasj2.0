package how.autotest.todomvc4tasj;

import how.autotest.todomvc4tasj.pages.TodoMvcPage;
import how.autotest.todomvc4tasj.testconfigs.BaseTest;
import org.junit.jupiter.api.Test;

public class OperationsOnCompletedFilterTest extends BaseTest {
    private TodoMvcPage todoMvc = new TodoMvcPage();
    @Test
    void activeAll(){
        todoMvc.givenOpenedWith("a","b","c");
        todoMvc.filterByCompleted();

        // WHEN add nothing

        todoMvc.todosShouldBeEmpty();
        todoMvc.itemsLeftShouldBe(3);
    }
    @Test
    void noCompletedTodosAfterAddNewTodo(){
        todoMvc.givenOpenedWith("a","b","c");
        todoMvc.filterByCompleted();

        todoMvc.add("d");

        todoMvc.todosShouldBeEmpty();
        todoMvc.itemsLeftShouldBe(4);
    }
    @Test
    void uncompleteTodo(){
        todoMvc.givenOpenedWithAllCompletedTodosOnCompletedFilter("a","b","c");

        todoMvc.toggle("b");

        todoMvc.todosShouldBe("a","c");
        todoMvc.itemsLeftShouldBe(1);
    }
    @Test
    void editCompletedTodo(){
        todoMvc.givenOpenedWithAllCompletedTodosOnCompletedFilter("a","b","c");

        todoMvc.edit("b","b edited");

        todoMvc.todosShouldBe("a","b edited","c");
        todoMvc.itemsLeftShouldBe(0);
    }
    @Test
    void clearCompletedAllTodos() {
        todoMvc.givenOpenedWithAllCompletedTodosOnCompletedFilter("a", "b", "c");

        todoMvc.clearCompleted();

        todoMvc.todosShouldBeEmpty();
        todoMvc.footerShouldBeHidden();
    }
    @Test
    void completeAllTodosWithSomeCompleted(){
        todoMvc.givenOpenedWith("a","b","c");
        todoMvc.toggle("b");
        todoMvc.filterByCompleted();

        todoMvc.toggleAll();

        todoMvc.todosShouldBe("a","b","c");
        todoMvc.itemsLeftShouldBe(0);
    }
    @Test
    void deleteLastCompletedTodo(){
        todoMvc.givenOpenedWith("a","b");
        todoMvc.toggle("b");
        todoMvc.filterByCompleted();

        todoMvc.delete("b");

        todoMvc.todosShouldBeEmpty();
        todoMvc.itemsLeftShouldBe(1);
    }
}
