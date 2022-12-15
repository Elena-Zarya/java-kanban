import manager.Managers;
import manager.Status;
import manager.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

public class Main {
    public static void main(String[] args) {
        TaskManager inMemoryTaskManager = Managers.getDefault();

        Task task = new Task("Сходить в магазин", "купить продукты", Status.NEW);
        Task task2 = new Task("Сходить в аптеку", "купить лекарства", Status.NEW);
        Task task3 = new Task("Сходить в аптеку", "купить лекарства", Status.IN_PROGRESS);
        Task task4 = new Task("Сходить в магазин", "купить продукты", Status.DONE);

        Epic epic = new Epic("Уборка", "Уборка в квартире ", Status.NEW);
        Epic epic2 = new Epic("Тренажерный зал", "Сходить на тренировку", Status.NEW);

        Subtask subtask = new Subtask("Помыть окна", "В комнате и в кухне", Status.IN_PROGRESS);
        Subtask subtask2 = new Subtask("Помыть окна", "В комнате и в кухне", Status.DONE);

        Subtask subtask21 = new Subtask("Беговая дорожка", "30 минут ", Status.NEW);
        Subtask subtask22 = new Subtask("Силовые тренажеры", "30 минут", Status.NEW);
        Subtask subtask23 = new Subtask("Беговая дорожка", "30 минут ", Status.IN_PROGRESS);
        Subtask subtask24 = new Subtask("Силовые тренажеры", "30 минут", Status.DONE);

        System.out.println("\ntasks.Task test:");
        System.out.println("Добавлена задача: " + inMemoryTaskManager.addTask(task));
        System.out.println("Добавлена задача: " + inMemoryTaskManager.addTask(task2));
        System.out.println("Список задач: " + inMemoryTaskManager.getAllTask());
        System.out.println("Обновлена задача: " + inMemoryTaskManager.updateTask(task3, 2));
        System.out.println("Обновлена задача: " + inMemoryTaskManager.updateTask(task4, 1));
        System.out.println("Обновленный список задач: " + inMemoryTaskManager.getAllTask());
        inMemoryTaskManager.deleteTask(2);
        System.out.println("Задача удалена, обновленный список задач: " + inMemoryTaskManager.getAllTask());

        System.out.println("\ntasks.Epic & subtask test:");
        System.out.println("Добавлен эпик: " + inMemoryTaskManager.addEpic(epic));
        System.out.println("Добавлен эпик: " + inMemoryTaskManager.addEpic(epic2));
        System.out.println("Список эпиков: " + inMemoryTaskManager.getAllEpic());
        System.out.println("Добавлена подзадача: " + inMemoryTaskManager.addSubtask(subtask, 1));
        System.out.println("Добавлена подзадача: " + inMemoryTaskManager.addSubtask(subtask21, 2));
        System.out.println("Добавлена подзадача: " + inMemoryTaskManager.addSubtask(subtask22, 2));
        System.out.println("Список всех подзадач: " + inMemoryTaskManager.getAllSubtask());
        System.out.println("Обновление подзадачи: " + inMemoryTaskManager.updateSubtask(subtask2, 1, 1));
        System.out.println("Обновление подзадачи: " + inMemoryTaskManager.updateSubtask(subtask23, 2, 2));
        System.out.println("Обновление подзадачи: " + inMemoryTaskManager.updateSubtask(subtask24, 3, 2));
        System.out.println("Список эпиков: " + inMemoryTaskManager.getAllEpic());
        System.out.println("Список всех подзадач: " + inMemoryTaskManager.getAllSubtask());
        inMemoryTaskManager.deleteEpic(1);
        System.out.println("Эпик удален, обновленный список эпиков: " + inMemoryTaskManager.getAllEpic());
        System.out.println("Список всех подзадач: " + inMemoryTaskManager.getAllSubtask());
        inMemoryTaskManager.deleteSubtask(3, 2);
        System.out.println("Список всех подзадач эпика: " + inMemoryTaskManager.getAllSubtask(2));

        inMemoryTaskManager.getTask(1);
        inMemoryTaskManager.getEpic(2);
        inMemoryTaskManager.getEpic(2);
        System.out.println("\nИстория просмотров: " + inMemoryTaskManager.getHistory());
        inMemoryTaskManager.getTask(1);
        inMemoryTaskManager.getTask(1);
        inMemoryTaskManager.getEpic(2);
        inMemoryTaskManager.getSubtask(2);
        inMemoryTaskManager.getSubtask(2);
        System.out.println("История просмотров: " + inMemoryTaskManager.getHistory());
        inMemoryTaskManager.getEpic(2);
        inMemoryTaskManager.getSubtask(2);
        inMemoryTaskManager.getSubtask(2);
        inMemoryTaskManager.getSubtask(2);
        System.out.println("История просмотров: " + inMemoryTaskManager.getHistory());
    }
}
