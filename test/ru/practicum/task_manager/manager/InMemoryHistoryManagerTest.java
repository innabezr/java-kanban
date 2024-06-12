import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.task_manager.manager.HistoryManager;
import ru.practicum.task_manager.manager.TaskManager;
import ru.practicum.task_manager.task.Status;
import ru.practicum.task_manager.task.Task;


import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class InMemoryHistoryManagerTest {
    private TaskManager taskManager;
    private HistoryManager historyManager;

    @BeforeEach
    public void beforeEach() {
        historyManager = Managers.getDefaultHistory();
        taskManager = Managers.getDefault();
    }

    @AfterEach
    public void afterEach() {
        historyManager.clearHistory();
    }

    @Test
    public void managerHistoryTest() {
        assertNotNull(historyManager = Managers.getDefaultHistory());
    }

    @Test
    public void historyAddTest() {
        Task task1 = new Task("Задача первая", "Описание первой задачи", Status.NEW);
        taskManager.createTask(task1);
        historyManager.add(task1);
        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size());
    }

    @Test
    public void getHistoryTest() {
        Task task1 = new Task("Задача первая", "Описание первой задачи", Status.NEW);
        taskManager.createTask(task1);
        historyManager.add(task1);
        assertNotNull(historyManager.getHistory());
    }

    @Test
    public void TaskInHistoryComareTaskInManager() {
        Task task1 = new Task(0, "Задача первая", "Описание первой задачи", Status.NEW);
        taskManager.createTask(task1);
        taskManager.getTaskWithId(task1.getId());
        assertEquals(historyManager.getHistory(), taskManager.getTasks());
    }

}