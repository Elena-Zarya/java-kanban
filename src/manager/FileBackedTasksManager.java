package manager;

import tasks.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {
    public static void main(String[] args) {
        File saveFile = new File("fileToSave.csv");
        TaskManager manager = Managers.getFileBackedTasksManager(saveFile);
        loadFromFile(saveFile);
        System.out.println(manager.getAllTask());
        System.out.println(manager.getAllEpic());
        System.out.println(manager.getAllSubtask());
        System.out.println(manager.getHistory());


//        Task task = new Task("Сходить в магазин", "купить продукты", Status.NEW);
//        Task task2 = new Task("Сходить в аптеку", "купить лекарства", Status.NEW);
//
//        Epic epic = new Epic("Уборка", "Уборка в квартире ", Status.NEW);
//        Epic epic2 = new Epic("Тренажерный зал", "Сходить на тренировку", Status.NEW);
//
//        Subtask subtask = new Subtask("Помыть окна", "В комнате и в кухне", Status.IN_PROGRESS);
//        Subtask subtask2 = new Subtask("Помыть окна", "В комнате и в кухне", Status.DONE);
//
//        Subtask subtask21 = new Subtask("Беговая дорожка", "30 минут ", Status.NEW);
//        Subtask subtask22 = new Subtask("Силовые тренажеры", "30 минут", Status.NEW);
//        Subtask subtask23 = new Subtask("Беговая дорожка", "30 минут ", Status.IN_PROGRESS);
//        Subtask subtask24 = new Subtask("Силовые тренажеры", "30 минут", Status.DONE);
//
//        System.out.println("\ntasks.Task test:");
//        System.out.println("Добавлена задача: " + manager.addTask(task));
//        System.out.println("Добавлена задача: " + manager.addTask(task2));
//        System.out.println("Список задач: " + manager.getAllTask());
//
//        System.out.println("\ntasks.Epic & subtask test:");
//        System.out.println("Добавлен эпик: " + manager.addEpic(epic));
//        System.out.println("Добавлен эпик: " + manager.addEpic(epic2));
//        System.out.println("Список эпиков: " + manager.getAllEpic());
//        System.out.println("Добавлена подзадача: " + manager.addSubtask(subtask, 3));
//        System.out.println("Добавлена подзадача: " + manager.addSubtask(subtask21, 4));
//        System.out.println("Добавлена подзадача: " + manager.addSubtask(subtask22, 4));
//        System.out.println("Список всех подзадач: " + manager.getAllSubtask());
//
//        manager.getTask(1);
//        manager.getTask(2);
//        manager.getEpic(3);
//        manager.getEpic(4);
//        manager.getTask(1);
//        manager.getTask(1);
//        manager.getEpic(4);
//        manager.getSubtask(5);
//        manager.getSubtask(7);
//        manager.getSubtask(6);
//        manager.getEpic(4);
//        manager.getSubtask(7);
//        manager.getSubtask(6);
//        manager.getSubtask(7);
//        System.out.println("История просмотров: " + manager.getHistory());

    }

    File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    @Override
    public Integer addTask(Task task) {
        int a = super.addTask(task);
        //toString(task);
        save();
        return a;
    }

    @Override
    public Integer addEpic(Epic epic) {
        int a = super.addEpic(epic);
        //toString(epic);
        save();
        return a;
    }

    @Override
    public Integer addSubtask(Subtask subtask, int epicId) {
        int a = super.addSubtask(subtask, epicId);
        //toString(subtask);
        save();
        return a;
    }

    @Override
    public void clearTask() {
        super.clearTask();

        save();
    }

    @Override
    public void clearEpics() {
        super.clearEpics();
        save();
    }

    @Override
    public void clearSubtasks() {
        super.clearSubtasks();
        save();
    }

    @Override
    public Task getTask(int id) {
        Task a = super.getTask(id);
        save();
        return a;
    }

    @Override
    public Epic getEpic(int id) {
        Epic a = super.getEpic(id);
        save();
        return a;
    }

    @Override
    public Subtask getSubtask(int id) {
        Subtask a = super.getSubtask(id);
        save();
        return a;
    }

    @Override
    public Task updateTask(Task task, int id) {
        Task a = super.updateTask(task, id);
        save();
        return a;
    }

    @Override
    public Epic updateEpic(String nameTask, String description, int id) {
        Epic a = super.updateEpic(nameTask, description, id);
        save();
        return a;
    }

    @Override
    public Subtask updateSubtask(Subtask subtask, int id, int epicId) {
        Subtask a = super.updateSubtask(subtask, id, epicId);
        save();
        return a;
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        save();
    }

    @Override
    public void deleteSubtask(int id, int epicId) {
        super.deleteSubtask(id, epicId);
        save();
    }

    @Override
    public void updateEpicStatus(int epicId) {
        super.updateEpicStatus(epicId);
        save();
    }

    public void save() {
        try {
            Writer fileWriter = new FileWriter(file);
            fileWriter.write("id, type, name, status, description, epic\n");

            for (Integer i : storage.getTasks().keySet()) {
                fileWriter.write(toString(storage.getTasks().get(i)) + "\n");
            }
            for (Integer i : storage.getEpics().keySet()) {
                fileWriter.write(toString(storage.getEpics().get(i)) + "\n");
            }
            for (Integer i : storage.getSubtasks().keySet()) {
                fileWriter.write(toString(storage.getSubtasks().get(i)) + "\n");
            }
            fileWriter.write("\n");
            fileWriter.write(historyToString(getHistoryManager()));
            fileWriter.close();
        } catch (IOException e) {
            throw new ManagerSaveException("custom message", e);
        }
    }

    /**
     * сохранение задачи в строку
     */
    public String toString(tasks.Task task) {
        StringBuilder builder = new StringBuilder();
        builder.append(task.getId() + ", " + task.getType() + ", " + task.getNameTask() + ", " + task.getStatus() + ", "
                + task.getDescription() + ", " + task.getEpicId());
        return builder.toString();
    }

    /**
     * создание задачи из строки
     */
    public static Task fromString(String value) {
        Task task;
        Epic epic;
        Subtask subtask;
        String[] splitTo = value.split(", ");

        if (splitTo[1].equals("TASK")) {
            task = new Task(Integer.parseInt(splitTo[0]), Type.valueOf(splitTo[1]), splitTo[2], Status.valueOf(splitTo[3]), splitTo[4]);
            storage.setTasks(task.getId(), task);
            return task;
        } else if (splitTo[1].equals("EPIC")) {
            epic = new Epic(Integer.parseInt(splitTo[0]), Type.valueOf(splitTo[1]), splitTo[2], Status.valueOf(splitTo[3]), splitTo[4]);
            storage.setEpics(epic.getId(), epic);
            return epic;
        } else {
            subtask = new Subtask(Integer.parseInt(splitTo[0]), Type.valueOf(splitTo[1]), splitTo[2], Status.valueOf(splitTo[3]), splitTo[4], Integer.parseInt(splitTo[5]));
            storage.setSubtasks(subtask.getId(), subtask, subtask.getEpicId());
            return subtask;
        }
    }

    /**
     * сохранение менеджера истории
     */
    static String historyToString(HistoryManager manager) {
        StringBuilder builder = new StringBuilder();

        for (Task t : manager.getHistory()) {
            builder.append(t.getId() + ", ");
        }
        return builder.toString();
    }

    /**
     * восстановление менеджера истории из CSV
     */
    static List<Integer> historyFromString(String value) {
        String[] ids = value.split(", ");
        List<Integer> listId = new ArrayList<>();

        for (String id : ids) {
            listId.add(Integer.parseInt(id));
        }
        return listId;
    }

    /**
     * восстанавление данных менеджера из файла при запуске программы
     */
    static FileBackedTasksManager loadFromFile(File file) {
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            List<String> list = new ArrayList<>();
            HashMap<Integer, Task> tasksAll = new HashMap<>();

            while (fileReader.ready()) {
                list.add(fileReader.readLine());
            }
            boolean flag = true;
            for (int i = 1; i < (list.size()); i++) {
                if (list.get(i).isEmpty()) {
                    flag = false;
                    continue;
                }
                if (flag) {
                    Task t = fromString(list.get(i));
                    tasksAll.put(t.getId(), t);
                } else {
                    List<Integer> listId = historyFromString(list.get(i));
                    for (Integer id : listId) {
                        if (storage.getTasks().containsKey(id)) {
                            Task task = storage.getTasks().get(id);
                            getHistoryManager().add(task);
                        } else if (storage.getEpics().containsKey(id)) {
                            Epic task = storage.getEpics().get(id);
                            getHistoryManager().add(task);
                        } else if ((storage.getSubtasks().containsKey(id))) {
                            Subtask task = storage.getSubtasks().get(id);
                            getHistoryManager().add(task);
                        }
                    }
                }
            }
            int maxId = Collections.max(tasksAll.keySet());
            storage.setId(maxId);
            fileReader.close();
        } catch (IOException e) {
            throw new ManagerSaveException("custom message", e);
        }
        return new FileBackedTasksManager(file);
    }
}
