package ru.practicum.task_manager.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.practicum.task_manager.exceptions.ManagerException;
import ru.practicum.task_manager.task.*;


import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {
    private static final Path TEST_FILE_PATH = Path.of("src/path/test.csv");
    private FileBackedTaskManager taskManager;

    @BeforeEach
    public void beforeEach() throws ManagerException {
        taskManager = (FileBackedTaskManager) Managers.getDefault();
    }


    @Test
    public void testSaveAndLoadTasks() {
        Task task = new Task("Task", "Описание задачи 1", Status.NEW, Type.TASK);
        Task task1 = new Task("Task 2", "Описание задачи 2", Status.NEW, Type.TASK);

        taskManager.createTask(task);
        taskManager.createTask(task1);

        Epic epic = new Epic("Epic 1", "Описание Epic", Status.NEW, Type.EPIC);
        taskManager.createEpicTask(epic);

        Subtask subtask = new Subtask(1, "Subtask 1", "Описание Subtask", Status.NEW, epic.getId());
        taskManager.createSubtask(epic.getId(), subtask);

        taskManager.save();

        FileBackedTaskManager loadedTaskManager = FileBackedTaskManager.loadFromFile(TEST_FILE_PATH.toFile());
        List<Task> loadedTasks = loadedTaskManager.getTasks();
        List<Epic> loadedEpics = loadedTaskManager.getEpics();
        List<Subtask> loadedSubtasks = loadedTaskManager.getSubtasks();

        assertEquals(2, loadedTasks.size());
        assertEquals(1, loadedEpics.size());
        assertEquals(1, loadedSubtasks.size());

        assertEquals("Task", loadedTasks.get(0).getName());
        assertEquals("Subtask 1", loadedSubtasks.get(0).getName());
    }
}
