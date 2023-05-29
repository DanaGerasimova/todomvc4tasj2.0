package how.autotest.todomvc4tasj;

import how.autotest.todomvc4tasj.pages.TodoMvcPage;
import how.autotest.todomvc4tasj.testconfigs.BaseTest;
import org.junit.jupiter.api.Test;

public class OperationsOnAllFilterTest extends BaseTest {
    private TodoMvcPage todoMvc = new TodoMvcPage();
    @Test
    void noTodosOnFreshOpen(){
        todoMvc.givenOpened();

        // WHEN add nothing

        todoMvc.todosShouldBeEmpty();
        todoMvc.footerShouldBeHidden();
    }
    @Test
    void addFirst(){
        todoMvc.givenOpened();

        todoMvc.add("a");

        todoMvc.todosShouldBe("a");
        todoMvc.itemsLeftShouldBe(1);
    }
    @Test
    void addMany(){
        todoMvc.givenOpened();

        todoMvc.add("a","b","c");

        todoMvc.todosShouldBe("a","b","c");
        todoMvc.itemsLeftShouldBe(3);
    }
    @Test
    void cancelEdit(){
        todoMvc.givenOpenedWith("a","b","c");

        todoMvc.cancelEdit("b","b edited");

        todoMvc.todosShouldBe("a","b","c");
        todoMvc.itemsLeftShouldBe(3);
    }
    @Test
    void editByPressEnter(){
        todoMvc.givenOpenedWith("a","b","c");

        todoMvc.edit("b","b edited");

        todoMvc.todosShouldBe("a","b edited","c");
        todoMvc.itemsLeftShouldBe(3);
    }
    @Test
    void editByFocusChange(){
        todoMvc.givenOpenedWith("a","b","c");

        todoMvc.editByTab("b","b edited");

        todoMvc.todosShouldBe("a","b edited","c");
        todoMvc.itemsLeftShouldBe(3);
    }
    @Test
    void deleteByEditToEmptyString(){
        todoMvc.givenOpenedWith("a","b","c");

        todoMvc.edit("b","");

        todoMvc.todosShouldBe("a","c");
        todoMvc.itemsLeftShouldBe(2);
    }
    @Test
    void complete(){
        todoMvc.givenOpenedWith("a","b","c");

        todoMvc.toggle("b");

        todoMvc.completedTodosShouldBe("b");
        todoMvc.activeTodosShouldBe("a","c");
        todoMvc.itemsLeftShouldBe(2);
    }
    @Test
    void activateCompleted(){
        todoMvc.givenOpenedWith("a","b","c");
        todoMvc.toggle("b");

        todoMvc.toggle("b");

        todoMvc.completedTodosShouldBeEmpty();
        todoMvc.activeTodosShouldBe("a","b","c");
        todoMvc.itemsLeftShouldBe(3);
    }
    @Test
    void completeAll(){
        todoMvc.givenOpenedWith("a","b","c");

        todoMvc.toggleAll();

        todoMvc.completedTodosShouldBe("a","b","c");
        todoMvc.activeTodosShouldBeEmpty();
        todoMvc.itemsLeftShouldBe(0);
    }
    @Test
    void completeAllWithSomeCompleted(){
        todoMvc.givenOpenedWith("a","b","c");
        todoMvc.toggle("b");

        todoMvc.toggleAll();

        todoMvc.completedTodosShouldBe("a","b","c");
        todoMvc.activeTodosShouldBeEmpty();
        todoMvc.itemsLeftShouldBe(0);
    }
    @Test
    void uncompleteAll(){
        todoMvc.givenOpenedWith("a","b","c");
        todoMvc.toggleAll();

        todoMvc.toggleAll();

        todoMvc.completedTodosShouldBeEmpty();
        todoMvc.activeTodosShouldBe("a","b","c");
        todoMvc.itemsLeftShouldBe(3);
    }
    @Test
    void delete(){
        todoMvc.givenOpenedWith("a","b","c");

        todoMvc.delete("b");

        todoMvc.todosShouldBe("a","c");
        todoMvc.itemsLeftShouldBe(2);
    }
    @Test
    void deleteLast(){
        todoMvc.givenOpenedWith("a");

        todoMvc.delete("a");

        todoMvc.todosShouldBeEmpty();
        todoMvc.footerShouldBeHidden();
    }
    @Test
    void clearCompleted(){
        todoMvc.givenOpenedWith("a","b","c");
        todoMvc.toggle("b");

        todoMvc.clearCompleted();

        todoMvc.todosShouldBe("a","c");
        todoMvc.itemsLeftShouldBe(2);
    }
    @Test
    void clearCompletedAll(){
        todoMvc.givenOpenedWith("a","b","c");
        todoMvc.toggleAll();

        todoMvc.clearCompleted();

        todoMvc.todosShouldBeEmpty();
        todoMvc.footerShouldBeHidden();
    }
}