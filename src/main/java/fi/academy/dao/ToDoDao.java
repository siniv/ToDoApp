package fi.academy.dao;
import fi.academy.Task;

import java.util.List;
import java.util.Optional;

public interface ToDoDao {

        List<Task> showAll();
        Optional<Task> showTasksWithId(int id);
        int add(Task task);
        Task delete(int id);
        boolean modify(int id, Task name);

}
