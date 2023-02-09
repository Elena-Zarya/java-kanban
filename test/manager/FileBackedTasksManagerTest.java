package manager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.time.Month.FEBRUARY;
import static manager.FileBackedTasksManager.loadFromFile;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static tasks.Status.NEW;

public class FileBackedTasksManagerTest extends TaskManagerTest {
    File saveFile;

    @BeforeEach
    public void setManagerTest() {
        saveFile = new File("fileToSave.csv");
        manager = Managers.getFileBackedTasksManager(saveFile);
    }

    @AfterEach
    public void newFile() {
        try {
            new FileWriter(saveFile);
        } catch (IOException e) {
            throw new ManagerSaveException("Произошла ошибка при создании файла");
        }
    }

    @Test
    public void saveTest() {
        manager = loadFromFile(saveFile);
        List<Task> tasks = manager.getAllTask();
        List<Epic> epics = manager.getAllEpic();
        List<Subtask> subtasks = manager.getAllSubtask();
        assertEquals(tasks.size(), 0, "Список задач не пустой.");
        assertEquals(epics.size(), 0, "Список задач не пустой.");
        assertEquals(subtasks.size(), 0, "Список задач не пустой.");

        Task task = new Task("Задача 1", "описание", NEW, 10, 2023, FEBRUARY, 6, 12, 30);
        int taskId = manager.addTask(task);
        Task savedTasks = manager.getTask(taskId);
        TaskManager managerTest = loadFromFile(saveFile);
        Task loadTask = managerTest.getTask(taskId);
        assertEquals(savedTasks, loadTask, "Задачи не совпадают");

        Epic epic = new Epic("Эпик 1", "описание", NEW);
        final int epicId = manager.addEpic(epic);
        Epic savedEpic = manager.getEpic(epicId);
        managerTest = loadFromFile(saveFile);
        Epic loadEpic = managerTest.getEpic(epicId);
        assertEquals(savedEpic, loadEpic, "Эпики не совпадают");

        List<Task> historySave = new ArrayList<>();
        historySave.add(task);
        historySave.add(epic);
        List<Task> historyLoad = manager.getHistory();
        assertEquals(historyLoad, historySave, "Истории просмотров не совпадают");
    }

    @Test
    public void loadFromFileTest() {
        TaskManager manager = loadFromFile(saveFile);

        List<Task> loadTasks = manager.getAllTask();
        List<Epic> loadEpics = manager.getAllEpic();
        List<Subtask> loadSubtasks = manager.getAllSubtask();
        List<Task> historyLoad = manager.getHistory();

        assertEquals(0, loadTasks.size(), "Список задач не пустой");
        assertEquals(0, loadEpics.size(), "Список эпиков не пустой");
        assertEquals(0, loadSubtasks.size(), "Список подзадач не пустой");
        assertEquals(0, historyLoad.size(), "Список истории не пустой");


        Task task = new Task("Задача 1", "описание", NEW, 10, 2023, FEBRUARY, 6, 12, 30);
        int taskId = manager.addTask(task);
        Task savedTasks = manager.getTask(taskId);
        TaskManager managerTest = loadFromFile(saveFile);
        Task loadTask = managerTest.getTask(taskId);
        assertEquals(savedTasks, loadTask, "Задачи не совпадают");

        Epic epic = new Epic("Эпик 1", "описание", NEW);
        final int epicId = manager.addEpic(epic);
        Epic savedEpic = manager.getEpic(epicId);
        managerTest = loadFromFile(saveFile);
        Epic loadEpic = managerTest.getEpic(epicId);
        assertEquals(savedEpic, loadEpic, "Эпики не совпадают");
    }
}
