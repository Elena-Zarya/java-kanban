package manager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.KVServer;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static java.time.Month.FEBRUARY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static tasks.Status.NEW;

public class HttpTaskManagerTest extends TaskManagerTest {
    URI uri;
    KVServer kvServer;
    HttpTaskManager httpTaskManager;

    @BeforeEach
    public void setManager() throws IOException {
        kvServer = new KVServer();
        kvServer.start();

        uri = URI.create("http://localhost:8078/");
        manager = Managers.getHttpTaskManager(uri, "1");
        httpTaskManager = new HttpTaskManager(uri, "1");
    }

    @AfterEach
    public void stop() {
        kvServer.stop();
    }

    @Test
    public void SaveTest() {
        List<Task> tasks = manager.getAllTask();
        List<Epic> epics = manager.getAllEpic();
        List<Subtask> subtasks = manager.getAllSubtask();
        assertEquals(tasks.size(), 0, "Список задач не пустой.");
        assertEquals(epics.size(), 0, "Список задач не пустой.");
        assertEquals(subtasks.size(), 0, "Список задач не пустой.");

        Task task = new Task("Задача 1", "описание", NEW, 10, 2023, FEBRUARY, 6, 12, 30);
        int taskId = manager.addTask(task);
        Task savedTasks = manager.getTask(taskId);
        TaskManager managerTest = httpTaskManager.loadFromHttp(uri, "1");
        Task loadTask = managerTest.getTask(taskId);
        assertEquals(savedTasks, loadTask, "Задачи не совпадают");

        Epic epic = new Epic("Эпик 1", "описание", NEW);
        final int epicId = manager.addEpic(epic);
        Epic savedEpic = manager.getEpic(epicId);
        managerTest = httpTaskManager.loadFromHttp(uri, "1");
        Epic loadEpic = managerTest.getEpic(epicId);
        assertEquals(savedEpic, loadEpic, "Эпики не совпадают");

        List<Task> historySave = new ArrayList<>();
        historySave.add(task);
        historySave.add(epic);
        List<Task> historyLoad = manager.getHistory();
        assertEquals(historyLoad, historySave, "Истории просмотров не совпадают");
    }

    @Test
    public void loadFromHttpTest() {
        manager = Managers.getHttpTaskManager(uri, "2");
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
        TaskManager managerTest = httpTaskManager.loadFromHttp(uri, "2");
        Task loadTask = managerTest.getTask(taskId);
        assertEquals(savedTasks, loadTask, "Задачи не совпадают");

        Epic epic = new Epic("Эпик 1", "описание", NEW);
        final int epicId = manager.addEpic(epic);
        Epic savedEpic = manager.getEpic(epicId);
        managerTest = httpTaskManager.loadFromHttp(uri, "2");
        Epic loadEpic = managerTest.getEpic(epicId);
        assertEquals(savedEpic, loadEpic, "Эпики не совпадают");

        List<Task> historySave = new ArrayList<>();
        historySave.add(task);
        historySave.add(epic);
        historyLoad = manager.getHistory();
        assertEquals(historyLoad, historySave, "Истории просмотров не совпадают");
    }
}
