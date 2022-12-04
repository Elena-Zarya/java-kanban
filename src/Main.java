public class Main {

    public static void main(String[] args) {
        System.out.println("Поехали!");


        Task task = new Task("Сходить в магазин", "купить продукты", "NEW");
        Task task2 = new Task("Сходить в аптеку", "купить лекарства", "NEW");


        Epic epic = new Epic("Уборка", "Уборка в квартире ", "NEW");
        Subtask subtask = new Subtask("Помыть окна", "В комнате и в кухне", "IN_PROGRESS");


        Epic epic2 = new Epic("Тренажерный зал", "Сходить на тренировку", "NEW");
        Subtask subtask21 = new Subtask("Беговая дорожка", "30 минут ", "NEW");
        Subtask subtask22 = new Subtask("Силовые тренажеры", "30 минут", "NEW");

        Manager manager = new Manager();



        manager.addTask(task);
        manager.addTask(task2);


        manager.addEpic(epic);
        manager.addSubtask(subtask,1);


        manager.addEpic(epic2);
        manager.addSubtask(subtask21,2);
        manager.addSubtask(subtask22, 2);

        System.out.println(manager.getAllTask());
        System.out.println(manager.getAllEpic());
        System.out.println(manager.getAllSubtask());


        Task task3 = new Task("Сходить в аптеку", "купить лекарства", "IN_PROGRESS");
        Task task4 = new Task("Сходить в магазин", "купить продукты", "DONE");
        manager.updateTask(task3, 2);
        manager.updateTask(task4, 1);

        Subtask subtask2 = new Subtask("Помыть окна", "В комнате и в кухне", "DONE");
        manager.updateSubtask(subtask2, 1, 1);

        Subtask subtask23 = new Subtask("Беговая дорожка", "30 минут ", "IN_PROGRESS");
        Subtask subtask24 = new Subtask("Силовые тренажеры", "30 минут", "DONE");
        manager.updateSubtask(subtask23, 1, 2);
        manager.updateSubtask(subtask24, 2, 2);


        System.out.println(manager.getAllTask());
        System.out.println(manager.getAllEpic());
        System.out.println(manager.getAllSubtask());

        manager.deleteTask(2);
        manager.deleteEpic(1);


        System.out.println(manager.getAllTask());
        System.out.println(manager.getAllEpic());
        System.out.println(manager.getAllSubtask());
        //System.out.println(manager.deleteTask(2));

//        System.out.println(manager.tasks);



//        System.out.println(manager.tasks);


//        System.out.println(manager.tasks);

       // manager.toString();



//        manager.clearTask();
//        manager.clearEpics();
//        manager.clearSubtasks();
//        System.out.println(manager.getTask(1));
//        System.out.println(manager.getEpic(1));
//        System.out.println(manager.getSubtask(1));

       // manager.setEpicStatus();

//        System.out.println(manager.deleteTask(2));
//        System.out.println(manager.deleteEpic(2));
      //   manager.clearSubtasks();




       // System.out.println(manager.getAllSubtask(1));

       // System.out.println(manager.getSubtask(1));
    }
}
