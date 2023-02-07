package manager;

import tasks.*;

import java.io.*;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static manager.CSVTaskFormatter.*;

public class FileBackedTasksManager extends InMemoryTaskManager {
    public static void main(String[] args) {
        File saveFile = new File("fileToSave.csv");
        TaskManager manager = loadFromFile(saveFile);

        System.out.println(manager.getAllTask());
        System.out.println(manager.getAllEpic());
        System.out.println(manager.getAllSubtask());
        System.out.println(manager.getHistory());
    }

    private final File file;

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    @Override
    public Integer addTask(Task task) {
        Integer a = super.addTask(task);
        save();
        return a;
    }

    @Override
    public Integer addEpic(Epic epic) {
        Integer a = super.addEpic(epic);
        save();
        return a;
    }

    @Override
    public Integer addSubtask(Subtask subtask, int epicId) {
        Integer a = super.addSubtask(subtask, epicId);
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

    protected void save() {
        try {
            CSVTaskFormatter formatter = new CSVTaskFormatter();
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
            fileWriter.write(formatter.title() + "\n");

            for (Integer i : storage.getTasks().keySet()) {
                fileWriter.write(formatter.toString(storage.getTasks().get(i)) + "\n");
            }
            for (Integer i : storage.getEpics().keySet()) {
                fileWriter.write(formatter.toString(storage.getEpics().get(i)) + "\n");
            }
            for (Integer i : storage.getSubtasks().keySet()) {
                fileWriter.write(formatter.toString(storage.getSubtasks().get(i)) + "\n");
            }
            fileWriter.newLine();
            fileWriter.write(formatter.historyToString(getHistoryManager()));
            fileWriter.close();
        } catch (IOException e) {
            throw new ManagerSaveException("Произошла ошибка при сохранении файла");
        }
    }


    /**
     * восстанавление данных менеджера из файла при запуске программы
     */
    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager fileManager = new FileBackedTasksManager(file);
        Storage storage = fileManager.storage;

        try {
            final String csv = Files.readString(file.toPath());
            final String[] lines = csv.split("\r?\n|\r");
            HashMap<Integer, Task> tasksAll = new HashMap<>();

            boolean flag = true;
            for (int i = 1; i < lines.length; i++) {
                if (lines[i].isEmpty()) {
                    flag = false;
                    continue;
                }
                if (flag) {
                    if (lines[i].contains("EPIC")) {
                        Epic epic = fromStringEpic(lines[i]);
                        storage.setEpics(epic.getId(), epic);
                        tasksAll.put(epic.getId(), epic);
                    } else if (lines[i].contains("SUBTASK")) {
                        Subtask subtask = fromStringSubtask(lines[i]);
                        storage.setSubtasks(subtask.getId(), subtask, subtask.getEpicId());
                        tasksAll.put(subtask.getId(), subtask);
                    } else if (lines[i].contains("TASK")) {
                        Task task = fromStringTask(lines[i]);
                        storage.setTasks(task.getId(), task);
                        tasksAll.put(task.getId(), task);
                    }
                } else {
                    List<Integer> listId = historyFromString(lines[i]);
                    for (Integer id : listId) {
                        if (storage.getTasks().containsKey(id)) {
                            Task task = storage.getTasks().get(id);
                            fileManager.getHistoryManager().add(task);
                        } else if (storage.getEpics().containsKey(id)) {
                            Epic task = storage.getEpics().get(id);
                            fileManager.getHistoryManager().add(task);
                        } else if (storage.getSubtasks().containsKey(id)) {
                            Subtask task = storage.getSubtasks().get(id);
                            fileManager.getHistoryManager().add(task);
                        }
                    }
                }
                int maxId = Collections.max(tasksAll.keySet());
                storage.setId(maxId);
            }
        } catch (IOException e) {
            throw new ManagerReaderException("Произошла ошибка во время чтения файла");
        }
        return fileManager;
    }
}
