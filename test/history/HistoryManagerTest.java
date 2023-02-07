package history;

import manager.Managers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static tasks.Status.NEW;

public class HistoryManagerTest {

    HistoryManager historyManager;

    @BeforeEach
    public void setHistoryManager() {
        historyManager = Managers.getDefaultHistory();
    }

    @Test
    public void addTasksToHistory() {
        Task task = new Task("Задача 1", "описание", NEW);
        task.setId(1);
        Task task2 = new Task("Задача 2", "описание", NEW);
        task2.setId(2);
        Epic epic = new Epic("Эпик 1", "Описание", NEW);
        epic.setId(3);
        Epic epic2 = new Epic("Эпик 2", "Описание", NEW);
        epic2.setId(4);
        Subtask subtask = new Subtask("Подзадача 1", "Описание", NEW);
        subtask.setId(5);
        historyManager.add(task);
        historyManager.add(task2);
        historyManager.add(epic);
        historyManager.add(epic2);
        historyManager.add(subtask);

        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        tasks.add(task2);
        tasks.add(epic);
        tasks.add(epic2);
        tasks.add(subtask);

        List<Task> historyTasks = historyManager.getHistory();

        assertEquals(tasks, historyTasks, "История не совпадает");

        historyManager.add(task);
        tasks.add(task);
        tasks.remove(0);
        historyTasks = historyManager.getHistory();

        assertEquals(tasks, historyTasks, "История не совпадает");
    }

    @Test
    public void removeTasksByHistory() {
        Task task = new Task("Задача 1", "описание", NEW);
        task.setId(1);
        Task task2 = new Task("Задача 2", "описание", NEW);
        task2.setId(2);
        Epic epic = new Epic("Эпик 1", "Описание", NEW);
        epic.setId(3);
        Epic epic2 = new Epic("Эпик 2", "Описание", NEW);
        epic2.setId(4);
        Subtask subtask = new Subtask("Подзадача 1", "Описание", NEW);
        subtask.setId(5);
        historyManager.add(task);
        historyManager.add(task2);
        historyManager.add(epic);
        historyManager.add(epic2);
        historyManager.add(subtask);

        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        tasks.add(task2);
        tasks.add(epic);
        tasks.add(epic2);
        tasks.add(subtask);

        List<Task> historyTasks = historyManager.getHistory();

        assertEquals(tasks, historyTasks, "История не совпадает");

        historyManager.remove(1);
        tasks.remove(0);
        historyTasks = historyManager.getHistory();

        assertEquals(tasks, historyTasks, "История не совпадает");

        historyManager.remove(3);
        tasks.remove(1);
        historyTasks = historyManager.getHistory();

        assertEquals(tasks, historyTasks, "История не совпадает");

        historyManager.remove(5);
        tasks.remove(2);
        historyTasks = historyManager.getHistory();

        assertEquals(tasks, historyTasks, "История не совпадает");
    }

    @Test
    public void shouldGetHistoryIsEmpty() {
        List<Task> historyTasks = historyManager.getHistory();
        assertEquals(0, historyTasks.size(), "История не пустая");
    }
}
