package manager;

import history.HistoryManager;
import tasks.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CSVTaskFormatter {

    public String title() {
        return "id, type, name, status, description, epic, startTime, duration";
    }

    /**
     * сохранение задачи в строку
     */
    public String toString(Task task) {
        return task.getId() + ", " + task.getType() + ", " + task.getNameTask() + ", " + task.getStatus() + ", "
                + task.getDescription() + ", " + task.getEpicId() + ", " + task.getStartTime() + ", "
                + task.getDuration();
    }

    /**
     * создание задачи из строки
     */
    public static Task fromStringTask(String value) {
        String[] splitTo = value.split(", ");
        if (splitTo[6].equals("null")) {
            return new Task(Integer.parseInt(splitTo[0]), Type.valueOf(splitTo[1]), splitTo[2], Status.valueOf(splitTo[3]),
                    splitTo[4], null, Integer.parseInt(splitTo[7]));
        } else {
            return new Task(Integer.parseInt(splitTo[0]), Type.valueOf(splitTo[1]), splitTo[2], Status.valueOf(splitTo[3]),
                    splitTo[4], LocalDateTime.parse(splitTo[6]), Integer.parseInt(splitTo[7]));
        }
    }

    public static Epic fromStringEpic(String value) {
        String[] splitTo = value.split(", ");
        if (splitTo[6].equals("null")) {
            return new Epic(Integer.parseInt(splitTo[0]), Type.valueOf(splitTo[1]), splitTo[2], Status.valueOf(splitTo[3]),
                    splitTo[4], null, Integer.parseInt(splitTo[7]));
        } else {
            return new Epic(Integer.parseInt(splitTo[0]), Type.valueOf(splitTo[1]), splitTo[2], Status.valueOf(splitTo[3]),
                    splitTo[4], LocalDateTime.parse(splitTo[6]), Integer.parseInt(splitTo[7]));
        }
    }

    public static Subtask fromStringSubtask(String value) {
        String[] splitTo = value.split(", ");
        if (splitTo[6].equals("null")) {
            return new Subtask(Integer.parseInt(splitTo[0]), Type.valueOf(splitTo[1]), splitTo[2],
                    Status.valueOf(splitTo[3]), splitTo[4], Integer.parseInt(splitTo[5]), null,
                    Integer.parseInt(splitTo[7]));
        } else {
            return new Subtask(Integer.parseInt(splitTo[0]), Type.valueOf(splitTo[1]), splitTo[2],
                    Status.valueOf(splitTo[3]), splitTo[4], Integer.parseInt(splitTo[5]), LocalDateTime.parse(splitTo[6]),
                    Integer.parseInt(splitTo[7]));
        }
    }

    /**
     * сохранение менеджера истории
     */
    static String historyToString(HistoryManager manager) {
        StringBuilder builder = new StringBuilder();
        List<Task> history = manager.getHistory();

        if (history.isEmpty()) {
            return "";
        } else {
            for (int i = 0; i < (history.size() - 1); i++) {
                builder.append(history.get(i).getId() + ", ");
            }
            builder.append(history.get(history.size() - 1).getId());
            return builder.toString();
        }
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
