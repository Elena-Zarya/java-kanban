public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();

        Task task = new Task("Сходить в магазин", "купить продукты", "NEW");
        Task task2 = new Task("Сходить в аптеку", "купить лекарства", "NEW");
        Task task3 = new Task("Сходить в аптеку", "купить лекарства", "IN_PROGRESS");
        Task task4 = new Task("Сходить в магазин", "купить продукты", "DONE");

        Epic epic = new Epic("Уборка", "Уборка в квартире ", "NEW");
        Epic epic2 = new Epic("Тренажерный зал", "Сходить на тренировку", "NEW");

        Subtask subtask = new Subtask("Помыть окна", "В комнате и в кухне", "IN_PROGRESS");
        Subtask subtask2 = new Subtask("Помыть окна", "В комнате и в кухне", "DONE");

        Subtask subtask21 = new Subtask("Беговая дорожка", "30 минут ", "NEW");
        Subtask subtask22 = new Subtask("Силовые тренажеры", "30 минут", "NEW");
        Subtask subtask23 = new Subtask("Беговая дорожка", "30 минут ", "IN_PROGRESS");
        Subtask subtask24 = new Subtask("Силовые тренажеры", "30 минут", "DONE");

        System.out.println("Task test:");
        System.out.println("Добавлена задача: " + manager.addTask(task));
        System.out.println("Добавлена задача: " + manager.addTask(task2));
        System.out.println("Список задач: " + manager.getAllTask());
        System.out.println("Обновлена задача: " + manager.updateTask(task3, 2));
        System.out.println("Обновлена задача: " + manager.updateTask(task4, 1));
        System.out.println("Обновленный список задач: " + manager.getAllTask());
        manager.deleteTask(2);
        System.out.println("Задача удалена, обновленный список задач: " + manager.getAllTask());

        System.out.println("Epic & subtask test:");
        System.out.println("Добавлен эпик: " + manager.addEpic(epic));
        System.out.println("Добавлен эпик: " + manager.addEpic(epic2));
        System.out.println("Список эпиков: " + manager.getAllEpic());
        System.out.println("Добавлена подзадача: " + manager.addSubtask(subtask, 1));
        System.out.println("Добавлена подзадача: " + manager.addSubtask(subtask21, 2));
        System.out.println("Добавлена подзадача: " + manager.addSubtask(subtask22, 2));
        System.out.println("Список всех подзадач: " + manager.getAllSubtask());
        System.out.println("Обновление подзадачи: " + manager.updateSubtask(subtask2, 1, 1));
        System.out.println("Обновление подзадачи: " + manager.updateSubtask(subtask23, 2, 2));
        System.out.println("Обновление подзадачи: " + manager.updateSubtask(subtask24, 3, 2));
        System.out.println("Список эпиков: " + manager.getAllEpic());
        System.out.println("Список всех подзадач: " + manager.getAllSubtask());
        manager.deleteEpic(1);
        System.out.println("Эпик удален, обновленный список эпиков: " + manager.getAllEpic());
        System.out.println("Список всех подзадач: " + manager.getAllSubtask());
        manager.deleteSubtask(3, 2);
        System.out.println("Список всех подзадач эпика: " + manager.getAllSubtask(2));

    }
}
