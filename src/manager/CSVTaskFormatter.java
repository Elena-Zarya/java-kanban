package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class CSVTaskFormatter {

    public String title() {
        String title = "id, type, name, status, description, epic";
        return title;
    }

    /**
     * сохранение задачи в строку
     */
    public String toString(Task task) {
        String stringTask = task.getId() + ", " + task.getType() + ", " + task.getNameTask() + ", " + task.getStatus() + ", "
                + task.getDescription() + ", " + task.getEpicId();
        return stringTask;
    }

    /**
     * создание задачи из строки
     */
    public Task fromStringTask(String value) {
        String[] splitTo = value.split(", ");
        return new Task(Integer.parseInt(splitTo[0]), Type.valueOf(splitTo[1]), splitTo[2], Status.valueOf(splitTo[3]), splitTo[4]);
    }

    public Epic fromStringEpic(String value) {
        String[] splitTo = value.split(", ");
        return new Epic(Integer.parseInt(splitTo[0]), Type.valueOf(splitTo[1]), splitTo[2], Status.valueOf(splitTo[3]), splitTo[4]);
    }

    public Subtask fromStringSubtask(String value) {
        String[] splitTo = value.split(", ");
        return new Subtask(Integer.parseInt(splitTo[0]), Type.valueOf(splitTo[1]), splitTo[2], Status.valueOf(splitTo[3]), splitTo[4], Integer.parseInt(splitTo[5]));
    }

    /**
     * сохранение менеджера истории
     */
    static String historyToString(HistoryManager manager) {
        StringBuilder builder = new StringBuilder();

        if (!manager.getHistory().isEmpty()) {
            for (int i = 0; i < (manager.getHistory().size() - 1); i++) {
                builder.append(manager.getHistory().get(i).getId() + ", ");
            }
            builder.append(manager.getHistory().get(manager.getHistory().size() - 1).getId());
        }
        return builder.toString();
    }

    /**
     * восстановление менеджера истории из CSV
     */
    static List<Integer> historyFromString(String value) {
        String[] ids = value.split(", ");
        List<Integer> listId = new ArrayList<>(ids.length);

        for (String id : ids) {
            listId.add(Integer.parseInt(id));
        }
        return listId;
    }
}
