package ru.practicum.task_manager.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.task_manager.task.Status;
import ru.practicum.task_manager.task.Task;
import ru.practicum.task_manager.task.Type;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    private TaskManager taskManager;
    private HistoryManager historyManager;

    @BeforeEach
    public void beforeEach() {
        historyManager = Managers.getDefaultHistory();
        taskManager = Managers.getDefault();
    }

    @Test
    public void managerHistoryTest() {
        historyManager = Managers.getDefaultHistory();
        assertNotNull(historyManager);
    }

    @Test
    public void historyAddTest() {
        Task task1 = new Task("Задача первая", "Описание первой задачи", Status.NEW, Type.TASK);
        taskManager.createTask(task1);
        historyManager.add(task1);
        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "В истории 1 задача");
        assertEquals(task1, history.get(0), "Задача в истории должна совпадать с добавленной");
    }

    @Test
    public void getHistoryTest() {
        Task task1 = new Task("Задача первая", "Описание первой задачи", Status.NEW, Type.TASK);
        taskManager.createTask(task1);
        historyManager.add(task1);
        List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не null");
        assertFalse(history.isEmpty(), "История не пустая");
    }

    @Test
    public void taskInHistoryCompareTaskInManager() {
        Task task = new Task("Task", "Description", Status.NEW, Type.TASK);
        taskManager.createTask(task);
        historyManager.add(task);

        // Обновляем задачу в taskManager
        task.setName("Updated Task");
        taskManager.updateTask(task);

        List<Task> history = historyManager.getHistory();
        assertEquals("Task", history.get(0).getName());
        assertEquals("Description", history.get(0).getDescription());
        assertEquals(Status.NEW, history.get(0).getStatus(), "Статус = NEW");
    }

    @Test
    public void doubleTaskTest() {
        Task task = new Task("Task", "Description", Status.NEW, Type.TASK);
        taskManager.createTask(task);
        historyManager.add(task);
        historyManager.add(task);

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "История не должна содержать дубликатов задач");
        assertEquals(task, history.get(0), "Задача в истории должна совпадать с добавленной");
    }

    @Test
    public void removeTaskTest() {
        Task task = new Task(1, "Task", "Description", Status.NEW, Type.TASK);
        Task task2 = new Task(2, "Task2", "Description", Status.NEW, Type.TASK);
        historyManager.add(task);
        historyManager.add(task2);
        historyManager.remove(1);
        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size());
        assertEquals("Task2", history.get(0).getName());
    }

}
