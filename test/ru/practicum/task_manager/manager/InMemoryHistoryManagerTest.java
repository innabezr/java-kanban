package ru.practicum.task_manager.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.task_manager.task.Status;
import ru.practicum.task_manager.task.Task;
import ru.practicum.task_manager.task.Type;


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


    @Test
    public void managerHistoryTest() {
        assertNotNull(historyManager = Managers.getDefaultHistory());
    }

    @Test
    public void historyAddTest() {
        Task task1 = new Task("Задача первая", "Описание первой задачи", Status.NEW, Type.TASK);
        taskManager.createTask(task1);
        historyManager.add(task1);
        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size());
    }

    @Test
    public void getHistoryTest() {
        Task task1 = new Task("Задача первая", "Описание первой задачи", Status.NEW, Type.TASK);
        taskManager.createTask(task1);
        historyManager.add(task1);
        assertNotNull(historyManager.getHistory());
    }

    @Test
    public void TaskInHistoryComareTaskInManager() {
        Task task = new Task("Task", "Description", Status.NEW, Type.TASK);
        taskManager.createTask(task);
        historyManager.add(task);
        task.setName("Updated Task");
        taskManager.updateTask(task);
        List<Task> history = historyManager.getHistory();
        System.out.println(history);
        assertEquals("Task", history.get(0).getName());
        assertEquals("Description", history.get(0).getDescription());
        assertEquals(Status.NEW, history.get(0).getStatus());
    }

    @Test
    public void DoubleTaskTest() {
        Task task = new Task("Task", "Description", Status.NEW, Type.TASK);
        taskManager.createTask(task);
        historyManager.add(task);
        historyManager.add(task);
        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size());
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