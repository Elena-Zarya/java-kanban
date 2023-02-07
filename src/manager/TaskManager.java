package manager;

import java.util.List;

import tasks.*;

public interface TaskManager {

    List<Task> getAllTask();

    List<Epic> getAllEpic();

    List<Subtask> getAllSubtask();

    List<Subtask> getAllSubtask(int epicId);

    void clearTask();

    void clearEpics();

    void clearSubtasks();

    Task getTask(int id);

    Epic getEpic(int id);

    Subtask getSubtask(int id);

    Integer addTask(Task task);

    Integer addEpic(Epic epic);

    Integer addSubtask(Subtask subtask, int epicId);

    Task updateTask(Task task, int id);

    Epic updateEpic(String nameTask, String description, int id);

    Subtask updateSubtask(Subtask subtask, int id, int epicId);

    void deleteTask(int id);

    void deleteEpic(int id);

    void deleteSubtask(int id, int epicId);

    void updateEpicStatus(int epicId);

    List<Task> getHistory();

    List<Task> getPrioritizedTasks();
}
