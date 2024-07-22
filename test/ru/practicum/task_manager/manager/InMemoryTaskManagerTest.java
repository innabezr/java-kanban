package ru.practicum.task_manager.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.task_manager.task.*;


import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private static final Path TEST_FILE_PATH = Path.of("src/path/test.csv");
    private TaskManager taskManager;

    @BeforeEach
    public void BeforeEach() {
        taskManager = Managers.getDefault();
    }

    @Test
    void EpicEqlByIdTest() {
        Task task1 = new Task(1, "Task 1", "Description 1", Status.NEW, Type.TASK);
        Task task2 = new Task(1, "Task 1", "Description 1", Status.NEW, Type.TASK);
        assertEquals(task1, task2);
    }

    @Test
    void TaskEqlByIdTest() {
        Epic epic1 = new Epic("Эпическая задача 1", "Описание первой эпической задачи", Status.NEW, Type.EPIC);
        Epic epic2 = new Epic("Эпическая задача 1", "Описание первой эпической задачи", Status.NEW, Type.EPIC);
        assertEquals(epic1, epic2);
    }

//   Эти 2 теста или я не понимаю задание или так и должно быть.
    //@org.junit.jupiter.api.Test
    //void EpicAddLikeSubtaskTest() {
    //InMemoryTaskManager taskManager = new InMemoryTaskManager();
    //Epic epic1 = new Epic("Эпическая задача 1", "Описание первой эпической задачи");
    //assertTrue(taskManager.createSubtask(epic1.getId(), epic1));
    // }

    // @org.junit.jupiter.api.Test
    //void SubtaskAddLikeEpicTest() {
    //InMemoryTaskManager taskManager = new InMemoryTaskManager();
    //Epic epic1 = new Epic("Эпическая задача 1", "Описание первой эпической задачи");
    //taskManager.createEpicTask(epic1);
    //Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", Status.NEW, epic1.getId());
    //taskManager.createSubtask(epic1.getId(), epic1);
    // }

    @Test
    void classManagerTest() {
        taskManager = Managers.getDefault();
        assertNotNull(Managers.getDefault());
    }

    @Test
    void searchByIdTMTest() {
        Task task = new Task("Task", "Description", Status.NEW, Type.TASK);
        Epic epic = new Epic("Epic", "Название эпика", Status.NEW, Type.EPIC);
        Subtask subtask = new Subtask("Subtask", "Description", Status.NEW, epic.getId());
        taskManager.createTask(task);
        taskManager.createEpicTask(epic);
        taskManager.createSubtask(epic.getId(), subtask);
        assertEquals(task, taskManager.getTaskWithId(task.getId()));
        assertEquals(epic, taskManager.getEpicWithId(epic.getId()));
        assertEquals(subtask, taskManager.getSubtaskWithId(subtask.getId()));
    }

    @Test
    void taskWithIdTest() {
        Task task1 = new Task(0, "Задача1", "Описание", Status.NEW, Type.TASK);
        Task task2 = new Task("Задача2", "Описание", Status.NEW, Type.TASK);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        assertEquals(task1.getId(), 0);
        assertNotEquals(task1.getId(), task2.getId());
    }

    @Test
    void compareAllFieldTest() {
        Task task1 = new Task(0, "Задача1", "Описание", Status.NEW, Type.TASK);
        taskManager.createTask(task1);
        assertEquals(task1.getId(), 0);
        assertEquals(task1.getName(), "Задача1");
        assertEquals(task1.getDescription(), "Описание");
        assertEquals(task1.getStatus(), Status.NEW);
    }

    @Test
    void deletedSubtask() {
        Epic epic = new Epic("Epic", "Description", Status.NEW, Type.EPIC);
        taskManager.createEpicTask(epic);
        Subtask subtask1 = new Subtask("Subtask1", "Description", Status.NEW, epic.getId());
        Subtask subtask2 = new Subtask("Subtask2", "Description", Status.NEW, epic.getId());
        taskManager.createSubtask(epic.getId(), subtask1);
        taskManager.createSubtask(epic.getId(), subtask2);
        int idToDelete = subtask1.getId();
        taskManager.deleteSubtaskWithId(idToDelete);
        Subtask deletedSubtask = taskManager.getSubtaskWithId(idToDelete);
        assertNull(deletedSubtask);
    }

    @Test
    public void epicFreeFromSubtask() {
        Epic epic = new Epic("Epic", "Description", Status.NEW, Type.EPIC);
        taskManager.createEpicTask(epic);
        Subtask subtask1 = new Subtask("Subtask1", "Description", Status.NEW, epic.getId());
        Subtask subtask2 = new Subtask("Subtask2", "Description", Status.NEW, epic.getId());
        taskManager.createSubtask(epic.getId(), subtask1);
        taskManager.createSubtask(epic.getId(), subtask2);
        int idToDelete = subtask1.getId();
        taskManager.deleteSubtaskWithId(idToDelete);
        List<Subtask> epicSubtasks = taskManager.getAllEpicSubtasks(epic.getId());
        boolean idToDeleteExists = false;
        for (Subtask subtask : epicSubtasks) {
            if (subtask.getId() == idToDelete) {
                idToDeleteExists = true;
                break;
            }
        }
        assertFalse(idToDeleteExists);
        for (Subtask subtask : epicSubtasks) {
            assertNotNull(taskManager.getSubtaskWithId(subtask.getId()));
        }
    }

    @Test
    void testSetters() {
        Task task = new Task(1, "Task 1", "Description 1", Status.NEW, Type.TASK);
        task.setName("Updated Task");
        task.setDescription("Updated Description");
        task.setStatus(Status.IN_PROGRESS);
        assertEquals("Updated Task", task.getName());
        assertEquals("Updated Description", task.getDescription());
        assertEquals(Status.IN_PROGRESS, task.getStatus());
    }

    @Test
    void testGetters() {
        Task task = new Task(1, "Task 1", "Description 1", Status.NEW, Type.TASK);
        assertEquals(1, task.getId());
        assertEquals("Task 1", task.getName());
        assertEquals("Description 1", task.getDescription());
        assertEquals(Status.NEW, task.getStatus());
    }

    @Test
    void UpdateTaskTest() {
        Task task = new Task(1, "Task 1", "Description 1", Status.NEW, Type.TASK);
        taskManager.createTask(task);
        task.setName("Updated Task");
        task.setDescription("Updated Description");
        task.setStatus(Status.IN_PROGRESS);
        taskManager.updateTask(task);
        Task updatedTask = taskManager.getTaskWithId(task.getId());
        assertNotNull(updatedTask);
        assertEquals("Updated Task", updatedTask.getName());
        assertEquals("Updated Description", updatedTask.getDescription());
        assertEquals(Status.IN_PROGRESS, updatedTask.getStatus());
    }

}





