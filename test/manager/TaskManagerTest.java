package manager;

import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;

import static java.time.Month.FEBRUARY;
import static org.junit.jupiter.api.Assertions.*;
import static tasks.Status.*;

abstract class TaskManagerTest<T extends TaskManager> {

    T manager;

    @Test
    public void getAllTaskTest() {
        List<Task> tasks = new ArrayList<>();
        List<Task> savedTasks = manager.getAllTask();
        assertNotNull(savedTasks, "Список подзадач не найден");
        assertEquals(tasks, savedTasks, "Список задач не совпадает");

        Task task = new Task("Задача 1", "описание", NEW);
        Task task2 = new Task("Задача 2", "описание", NEW);
        final int taskId = manager.addTask(task);
        final int task2Id = manager.addTask(task2);

        tasks.add(task);
        tasks.add(task2);
        savedTasks = manager.getAllTask();
        assertNotNull(savedTasks, "Список подзадач не найден");
        assertEquals(tasks, savedTasks, "Список задач не совпадает");
    }

    @Test
    public void getAllEpicTest() {
        List<Epic> epics = new ArrayList<>();
        List<Epic> savedEpics = manager.getAllEpic();
        assertNotNull(savedEpics, "Список подзадач не найден");
        assertEquals(epics, savedEpics, "Список задач не совпадает");

        Epic epic = new Epic("Эпик 1", "Описание", NEW);
        Epic epic2 = new Epic("Эпик 2", "Описание", NEW);
        final int epicId = manager.addEpic(epic);
        final int epic2Id = manager.addEpic(epic2);

        epics.add(epic);
        epics.add(epic2);

        savedEpics = manager.getAllEpic();
        assertNotNull(savedEpics, "Список подзадач не найден");
        assertEquals(epics, savedEpics, "Список эпиков не совпадает");
    }

    @Test
    public void getAllSubtaskTest() {
        List<Subtask> subtasks = new ArrayList<>();
        List<Subtask> savedSubtask = manager.getAllSubtask();
        assertEquals(subtasks, savedSubtask, "Список подзадач не совпадает");

        Epic epic = new Epic("Эпик 1", "Описание", NEW);
        final int epicId = manager.addEpic(epic);

        final Epic savedEpic = manager.getEpic(epicId);
        assertNotNull(savedEpic, "Эпик не найден.");

        Subtask subtask = new Subtask("Подзадача 1", "Описание", NEW);
        final int subtaskId = manager.addSubtask(subtask, epicId);

        subtasks = new ArrayList<>();
        subtasks.add(subtask);

        savedSubtask = manager.getAllSubtask();
        assertNotNull(savedSubtask, "Список подзадач не найден");
        assertEquals(subtask, savedSubtask.get(0), "Список подзадач не совпадает");
    }

    @Test
    public void getAllSubtaskByEpicTest() {
        List<Subtask> subtasksNull = manager.getAllSubtask(1);
        assertNull(subtasksNull, "Список подзадач не null");

        Epic epic = new Epic("Эпик 1", "Описание", NEW);
        Subtask subtask = new Subtask("Подзадача 1", "Описание", NEW);
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание", NEW);
        final int epicId = manager.addEpic(epic);

        final Epic savedEpic = manager.getEpic(epicId);
        assertNotNull(savedEpic, "Эпик не найден.");

        final int subtaskId = manager.addSubtask(subtask, epicId);
        final int subtask2Id = manager.addSubtask(subtask2, epicId);

        List<Subtask> subtasks = new ArrayList<>();
        subtasks.add(subtask);
        subtasks.add(subtask2);

        List<Subtask> savedSubtask = manager.getAllSubtask(epicId);
        assertNotNull(savedSubtask, "Список подзадач не найден");
        assertEquals(subtasks, savedSubtask, "список подзадач не совпадает");
    }

    @Test
    public void clearTasksTest() {
        manager.clearTask();
        List<Task> tasks = manager.getAllTask();
        assertEquals(0, tasks.size(), "Список задач не удален");
    }

    @Test
    public void clearEpicsTest() {
        manager.clearEpics();
        List<Epic> epics = manager.getAllEpic();
        assertEquals(0, epics.size(), "Список эпиков не удален");
    }

    @Test
    public void clearSubtasksTest() {
        manager.clearSubtasks();
        List<Subtask> subtasks = manager.getAllSubtask();
        assertEquals(0, subtasks.size(), "Список подзадач не удален");
    }

    @Test
    public void getTaskByIdTest() {
        final Task taskNull = manager.getTask(1);
        assertNull(taskNull, "Не null");

        Task task = new Task("Задача 1", "описание", NEW);
        final int taskId = manager.addTask(task);
        final Task savedTask = manager.getTask(taskId);
        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");
    }

    @Test
    public void getEpicByIdTest() {
        final Epic epicNull = manager.getEpic(1);
        assertNull(epicNull, "Не null");

        Epic epic = new Epic("Эпик 1", "Описание", NEW);
        final int epicId = manager.addEpic(epic);
        final Epic savedEpic = manager.getEpic(epicId);
        assertNotNull(savedEpic, "Эпик не найден.");
        assertEquals(epic, savedEpic, "Эпики не совпадают.");
    }

    @Test
    public void getSubtaskByIdTest() {
        final Subtask subtaskNull = manager.getSubtask(1);
        assertNull(subtaskNull, "Не null");

        Epic epic = new Epic("Эпик 1", "Описание ", NEW);
        final int epicId = manager.addEpic(epic);

        final Epic savedEpic = manager.getEpic(epicId);
        assertNotNull(savedEpic, "Эпик не найден.");

        Subtask subtask = new Subtask("Подзадача 1", "Описание", NEW);
        final int taskId = manager.addSubtask(subtask, epicId);

        final Subtask savedSubtask = manager.getSubtask(taskId);

        assertNotNull(savedSubtask, "Подзадача не найдена.");
        assertEquals(subtask, savedSubtask, "Подзадачи не совпадают.");
    }

    @Test
    public void addTaskTest() {
        Task task = new Task("Задача 1", "Описание", NEW, 30, 2023, FEBRUARY, 10, 14, 30);
        final int taskId = manager.addTask(task);

        final Task savedTask = manager.getTask(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        final Task taskNull = manager.getTask(3);
        assertNull(taskNull, "Не null");

        Task task2 = new Task("Задача 2", "Описание", NEW, 30, 2023, FEBRUARY, 10, 14, 45);
        final Integer task2IdNull = manager.addTask(task2);
        assertNull(task2IdNull, "Не null");

        final List<Task> tasks = manager.getAllTask();

        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    public void addEpicTest() {
        Epic epic = new Epic("Эпик 1", "Описание ", NEW);
        final int epicId = manager.addEpic(epic);

        final Epic savedEpic = manager.getEpic(epicId);

        assertNotNull(savedEpic, "Эпик не найден.");
        assertEquals(epic, savedEpic, "Эпики не совпадают.");

        final Epic epicNull = manager.getEpic(3);
        assertNull(epicNull, "Не null");

        final List<Epic> epics = manager.getAllEpic();

        assertNotNull(epics, "Эпики на возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество эпиков.");
        assertEquals(epic, epics.get(0), "Эпики не совпадают.");
    }

    @Test
    public void addSubtaskTest() {
        Epic epic = new Epic("Эпик 1", "Описание ", NEW);
        final int epicId = manager.addEpic(epic);
        final Epic savedEpic = manager.getEpic(epicId);
        assertNotNull(savedEpic, "Эпик не найден.");

        Subtask subtask = new Subtask("Подзадача 1", "Описание", NEW, 30, 2023, FEBRUARY, 10, 14, 30, epicId);
        final Integer subtaskIdNull = manager.addSubtask(subtask, 3);
        assertNull(subtaskIdNull, "Не null");

        final int subtaskId = manager.addSubtask(subtask, epicId);
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание", NEW, 30, 2023, FEBRUARY, 10, 14, 45, epicId);
        final Integer subtask2IdNull = manager.addSubtask(subtask2, epicId);
        assertNull(subtask2IdNull, "Не null");

        final Subtask savedSubtask = manager.getSubtask(subtaskId);
        assertNotNull(savedSubtask, "Подзадача не найдена.");
        assertEquals(subtask, savedSubtask, "Подзадачи не совпадают.");

        final Subtask subtaskNull = manager.getSubtask(3);
        assertNull(subtaskNull, "Не null");

        final List<Subtask> subtasks = manager.getAllSubtask();
        assertNotNull(subtasks, "Подзадачи на возвращаются.");
        assertEquals(1, subtasks.size(), "Неверное количество подзадач.");
        assertEquals(subtask, subtasks.get(0), "Подзадачи не совпадают.");
    }

    @Test
    public void updateTaskTest() {
        Task task = new Task("Задача 1", "Описание", NEW, 30, 2023, FEBRUARY, 10, 14, 30);
        final int taskId = manager.addTask(task);
        Task task2 = new Task("Задача 2", "Описание", NEW, 30, 2023, FEBRUARY, 10, 15, 45);
        final int task2Id = manager.addTask(task2);

        Task task3 = new Task("Задача 1", "Описание 2", DONE, 30, 2023, FEBRUARY, 10, 15, 30);
        final Task savedTask = manager.updateTask(task3, taskId);
        assertNull(savedTask, "Не null");

        Task task4 = new Task("Задача 1", "Описание 2", DONE, 30, 2023, FEBRUARY, 10, 17, 30);
        final Task savedTask4 = manager.updateTask(task4, taskId);
        Task taskS = manager.getTask(taskId);
        assertNotNull(savedTask4, "Задача не найдена.");
        assertEquals(taskS, savedTask4, "Задачи не совпадают.");

        Task task5 = new Task("Задача 1", "Описание 2", DONE, 50, 2023, FEBRUARY, 10, 15, 30);
        final Task savedTask5 = manager.updateTask(task5, 10);
        assertNull(savedTask5, "Не null");
    }

    @Test
    public void updateEpicTest() {
        Epic epic = new Epic("Эпик 1", "Описание", NEW);
        final int epicId = manager.addEpic(epic);
        Epic epic2 = new Epic("Эпик 1", "Описание 2", NEW);
        Epic savedEpic = manager.updateEpic("Эпик 1", "Описание 2", 4);
        assertNull(savedEpic, "Не null");

        Epic updateEpic2 = manager.updateEpic("Эпик 1", "Описание 2", epicId);
        final Epic savedEpic2 = manager.getEpic(epicId);
        assertNotNull(savedEpic2, "Эпик не найден.");
        assertEquals(updateEpic2, savedEpic2, "Эпики не совпадают.");
    }

    @Test
    public void updateSubtaskTest() {
        Epic epic = new Epic("Эпик 1", "Описание", NEW);
        final int epicId = manager.addEpic(epic);
        final Epic savedEpic = manager.getEpic(epicId);
        assertNotNull(savedEpic, "Эпик не найден.");

        Subtask subtask = new Subtask("Подзадача 1", "Описание", NEW);
        final int subtaskId = manager.addSubtask(subtask, epicId);
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание 2", NEW);
        Subtask updateSubtask = manager.updateSubtask(subtask2, subtaskId, 10);
        assertNull(updateSubtask, "Эпик не найден.");

        Subtask updateSubtask2 = manager.updateSubtask(subtask2, 3, epicId);
        assertNull(updateSubtask2, "Эпик не найден.");

        Subtask updateSubtask3 = manager.updateSubtask(subtask2, subtaskId, epicId);
        final Subtask savedSubtask = manager.getSubtask(subtaskId);
        assertNotNull(updateSubtask3, "Подзадача не найдена.");
        assertEquals(subtask2, updateSubtask3, "Подзадачи не совпадают.");
    }

    @Test
    public void deleteTaskTest() {
        Task task = new Task("Задача 1", "Описание", NEW);
        final int taskId = manager.addTask(task);
        manager.deleteTask(3);
        final Task savedTask = manager.getTask(taskId);
        assertNotNull(savedTask, "Задача удалена.");

        manager.deleteTask(taskId);
        final Task savedTask2 = manager.getTask(taskId);
        assertNull(savedTask2, "Задача не удалена.");
    }

    @Test
    public void deleteEpicTest() {
        Epic epic = new Epic("Эпик 1", "Описание", NEW);
        final int epicId = manager.addEpic(epic);

        manager.deleteEpic(3);
        final Epic savedEpic = manager.getEpic(epicId);
        assertNotNull(savedEpic, "Эпик удален.");

        manager.deleteEpic(epicId);
        final Epic savedEpic2 = manager.getEpic(epicId);
        assertNull(savedEpic2, "Эпик не удален.");
    }

    @Test
    public void deleteSubtaskTest() {
        Epic epic = new Epic("Эпик 1", "Описание", NEW);
        final int epicId = manager.addEpic(epic);

        final Epic savedEpic = manager.getEpic(epicId);
        assertNotNull(savedEpic, "Эпик не найден.");

        Subtask subtask = new Subtask("Подзадача 1", "Описание", DONE);
        final int subtaskId = manager.addSubtask(subtask, epicId);

        manager.deleteSubtask(6, epicId);
        final Subtask savedSubtask = manager.getSubtask(subtaskId);
        assertNotNull(savedSubtask, "Подзадача удалена.");

        manager.deleteSubtask(subtaskId, epicId);
        final Subtask savedSubtask2 = manager.getSubtask(subtaskId);
        assertNull(savedSubtask2, "Подзадача не удалена.");
    }

    @Test
    public void getHistoryTest() {
        List<Task> historyTasks = manager.getHistory();
        assertEquals(0, historyTasks.size(), "История не пустая");

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
        final int taskId = manager.addTask(task);
        final int task2Id = manager.addTask(task2);
        final int epicId = manager.addEpic(epic);
        final int epic2Id = manager.addEpic(epic2);
        final int subtaskId = manager.addSubtask(subtask, epicId);

        manager.getTask(taskId);
        manager.getTask(task2Id);
        manager.getEpic(epicId);
        manager.getEpic(epic2Id);
        manager.getSubtask(subtaskId);


        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        tasks.add(task2);
        tasks.add(epic);
        tasks.add(epic2);
        tasks.add(subtask);

        historyTasks = manager.getHistory();
        assertEquals(tasks, historyTasks, "История не совпадает");
    }

    @Test
    public void updateEpicStatusTest() {
        Epic epic = new Epic("Эпик 1", "Описание", NEW);
        final int epicId = manager.addEpic(epic);
        Epic savedEpic = manager.getEpic(epicId);
        assertEquals(NEW, savedEpic.getStatus(), "Неверный статус эпика.");

        Subtask subtask = new Subtask("Подзадача 1", "Описание", DONE);
        final int subtaskId = manager.addSubtask(subtask, epicId);
        assertEquals(DONE, savedEpic.getStatus(), "Неверный статус эпика.");

        Subtask subtask2 = new Subtask("Подзадача 2", "Описание", NEW);
        final int subtask2Id = manager.addSubtask(subtask2, epicId);
        assertEquals(IN_PROGRESS, savedEpic.getStatus(), "Неверный статус эпика.");

        manager.clearSubtasks();
        assertEquals(NEW, savedEpic.getStatus(), "Неверный статус эпика.");
    }

    @Test
    public void updateEpicTimeTest() {
        Epic epic = new Epic("Эпик 1", "Описание", NEW);
        final int epicId = manager.addEpic(epic);

        Subtask subtask = new Subtask("Подзадача 1", "Описание", NEW, 30, 2023, FEBRUARY, 5, 10, 30, epicId);
        final int subtaskId = manager.addSubtask(subtask, epicId);

        Subtask subtask2 = new Subtask("Подзадача 2", "Описание 2", NEW, 30, 2023, FEBRUARY, 5, 14, 30, epicId);
        final int subtask2Id = manager.addSubtask(subtask2, epicId);

        Integer durationEpic = manager.getEpic(epicId).getDuration();
        assertEquals(60, durationEpic, "неверная продолжительность эпика");

        Subtask subtask3 = new Subtask("Подзадача 3", "Описание 3", NEW, 30, 2023, FEBRUARY, 5, 11, 30, epicId);
        final int subtask3Id = manager.addSubtask(subtask3, epicId);

        int duration2Epic = manager.getEpic(epicId).getDuration();
        assertEquals(90, duration2Epic, "неверная продолжительность эпика");

        Subtask subtask4 = new Subtask("Подзадача 4", "Описание 4", NEW, 15, 2023, FEBRUARY, 5, 12, 30, epicId);
        manager.updateSubtask(subtask4, subtask2Id, epicId);

        int duration3Epic = manager.getEpic(epicId).getDuration();
        assertEquals(75, duration3Epic, "неверная продолжительность эпика");

        manager.deleteSubtask(subtask2Id, epicId);
        int duration4Epic = manager.getEpic(epicId).getDuration();
        assertEquals(60, duration4Epic, "неверная продолжительность эпика");

        manager.clearSubtasks();
        Integer duration5Epic = manager.getEpic(epicId).getDuration();
        assertEquals(null, duration5Epic, "неверная продолжительность эпика");
    }

    @Test
    public void getPrioritizedTasksTest() {
        Task task = new Task("Задача 1", "Описание", NEW, 30, 2023, FEBRUARY, 5, 12, 30);
        final int taskId = manager.addTask(task);

        Task task2 = new Task("Задача 1", "Описание 2", DONE, 10, 2023, FEBRUARY, 5, 12, 40);
        manager.updateTask(task2, taskId);
        final Task savedTask = manager.getTask(taskId);

        Task task3 = new Task("Задача 3", "Описание", NEW);
        final int task3Id = manager.addTask(task3);
        final Task savedTask3 = manager.getTask(task3Id);

        Task task4 = new Task("Задача 4", "Описание", NEW);
        final int task4Id = manager.addTask(task4);
        final Task savedTask4 = manager.getTask(task4Id);

        Task task5 = new Task("Задача 1", "Описание", NEW, 10, 2023, FEBRUARY, 5, 12, 35);
        Integer task5Id = manager.addTask(task5);

        Epic epic = new Epic("Эпик 1", "Описание", NEW);
        final int epicId = manager.addEpic(epic);

        Subtask subtask = new Subtask("Подзадача 1", "Описание", NEW, 20, 2023, FEBRUARY, 5, 10, 30, epicId);
        final int subtaskId = manager.addSubtask(subtask, epicId);

        Subtask subtask2 = new Subtask("Подзадача 1", "Описание 2", NEW, 5, 2023, FEBRUARY, 5, 14, 30, epicId);
        manager.updateSubtask(subtask2, subtaskId, epicId);
        final Task savedSubtask2 = manager.getSubtask(subtaskId);

        final List<Task> prioritizedTasksList = new ArrayList<>();
        prioritizedTasksList.add(savedTask);
        prioritizedTasksList.add(savedSubtask2);
        prioritizedTasksList.add(savedTask3);
        prioritizedTasksList.add(savedTask4);

        final List<Task> prioritizedTasks = manager.getPrioritizedTasks();

        assertEquals(prioritizedTasksList, prioritizedTasks, "списки не совпадают");

        manager.deleteTask(task3Id);
        prioritizedTasksList.remove(savedTask3);
        final List<Task> prioritizedTasks2 = manager.getPrioritizedTasks();

        assertEquals(prioritizedTasksList, prioritizedTasks2, "списки не совпадают");
    }
}
