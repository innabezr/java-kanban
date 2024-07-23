package ru.practicum.task_manager.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.task_manager.task.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private TaskManager taskManager;

    @BeforeEach
    public void beforeEach() {
        taskManager = Managers.getDefault();
    }

    @Test
    void epicEqualsByIdTest() {
        Task task1 = new Task(1, "Task 1", "Description 1", Status.NEW, Type.TASK);
        Task task2 = new Task(1, "Task 1", "Description 1", Status.NEW, Type.TASK);
        assertEquals(task1, task2, "Задачи одинаковые");
    }

    @Test
    void taskEqualsByIdTest() {
        Epic epic1 = new Epic(1, "Эпическая задача 1", "Описание первой эпической задачи", Status.NEW);
        Epic epic2 = new Epic(1, "Эпическая задача 1", "Описание первой эпической задачи", Status.NEW);
        assertEquals(epic1, epic2, "Эпики одинаковые");
    }

    @Test
    void classManagerTest() {
        assertNotNull(taskManager, "Менеджер задач не должен быть null");
    }

    @Test
    void searchByIdTMTest() {
        Task task = new Task("Task", "Description", Status.NEW, Type.TASK);
        Epic epic = new Epic("Epic", "Description", Status.NEW, Type.EPIC);
        Subtask subtask = new Subtask("Subtask", "Description", Status.NEW, epic.getId());

        taskManager.createTask(task);
        taskManager.createEpicTask(epic);
        taskManager.createSubtask(epic.getId(), subtask);

        assertEquals(task, taskManager.getTaskWithId(task.getId()), "Ищем задачу по ID");
        assertEquals(epic, taskManager.getEpicWithId(epic.getId()), "Ищем эпик по ID");
        assertEquals(subtask, taskManager.getSubtaskWithId(subtask.getId()), "Ищем подзадачу по ID");
    }

    @Test
    void taskWithIdTest() {
        Task task1 = new Task(0, "Задача1", "Описание", Status.NEW, Type.TASK);
        Task task2 = new Task("Задача2", "Описание", Status.NEW, Type.TASK);

        taskManager.createTask(task1);
        taskManager.createTask(task2);

        assertEquals(0, task1.getId(), "ID = 0");
        assertNotEquals(task1.getId(), task2.getId(), "Разные ID");
    }

    @Test
    void compareAllFieldsTest() {
        Task task1 = new Task(0, "Задача1", "Описание", Status.NEW, Type.TASK);
        taskManager.createTask(task1);

        assertEquals(0, task1.getId(), "ID =  0");
        assertEquals("Задача1", task1.getName(), "Имя задачи - Задача1");
        assertEquals("Описание", task1.getDescription(), "Описание задачи - Описание");
        assertEquals(Status.NEW, task1.getStatus(), "Статус = NEW");
    }

    @Test
    void deletedSubtaskTest() {
        Epic epic = new Epic("Epic", "Description", Status.NEW, Type.EPIC);
        taskManager.createEpicTask(epic);

        Subtask subtask1 = new Subtask("Subtask1", "Description", Status.NEW, epic.getId());
        Subtask subtask2 = new Subtask("Subtask2", "Description", Status.NEW, epic.getId());

        taskManager.createSubtask(epic.getId(), subtask1);
        taskManager.createSubtask(epic.getId(), subtask2);

        taskManager.deleteSubtaskWithId(subtask1.getId());

        List<Subtask> subtasks = taskManager.getSubtasks();
        assertEquals(1, subtasks.size(), "После удаления одной сабтаски должна остаться одна сабтаска");
        assertEquals("Subtask2", subtasks.get(0).getName(), "Имя сабтакси = Subtask2");
    }
}
