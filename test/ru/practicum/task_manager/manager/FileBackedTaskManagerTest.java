package ru.practicum.task_manager.manager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.task_manager.exceptions.ManagerException;
import ru.practicum.task_manager.task.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileBackedTaskManagerTest {
    private Path testFilePath;
    private FileBackedTaskManager taskManager;

    @BeforeEach
    public void setUp() throws ManagerException, IOException {
        testFilePath = Files.createTempFile("test", ".csv");
        taskManager = new FileBackedTaskManager(Path.of(testFilePath.toString()));
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(testFilePath);
    }

    @Test
    void testSaveAndLoadTasks() throws ManagerException {
        Task task = new Task("Task", "Описание", Status.NEW, Type.TASK);
        Task task1 = new Task("Task 2", "Описание", Status.NEW, Type.TASK);

        taskManager.createTask(task);
        taskManager.createTask(task1);

        Epic epic = new Epic("Epic 1", "Описание", Status.NEW, Type.EPIC);
        taskManager.createEpicTask(epic);

        Subtask subtask = new Subtask(1, "Subtask 1", "Описание", Status.NEW, epic.getId());
        taskManager.createSubtask(epic.getId(), subtask);

        taskManager.save();

        FileBackedTaskManager loadedTaskManager = FileBackedTaskManager.loadFromFile(testFilePath.toFile());

        List<Task> loadedTasks = loadedTaskManager.getTasks();
        List<Epic> loadedEpics = loadedTaskManager.getEpics();
        List<Subtask> loadedSubtasks = loadedTaskManager.getSubtasks();

        assertEquals(2, loadedTasks.size());
        assertEquals(1, loadedEpics.size());
        assertEquals(1, loadedSubtasks.size());

        assertEquals("Task", loadedTasks.get(0).getName());
        assertEquals("Task 2", loadedTasks.get(1).getName());
        assertEquals("Subtask 1", loadedSubtasks.get(0).getName());
    }
}
