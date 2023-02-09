package manager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import history.HistoryManager;
import tasks.*;

public class InMemoryTaskManager implements TaskManager {
    protected final Storage storage = new Storage();
    private final HistoryManager historyManager = Managers.getDefaultHistory();

    public HistoryManager getHistoryManager() {
        return historyManager;
    }

    /**
     * получить списки задач
     */
    @Override
    public List<Task> getAllTask() {
        return new ArrayList<>(storage.getTasks().values());
    }

    @Override
    public List<Epic> getAllEpic() {
        return new ArrayList<>(storage.getEpics().values());
    }

    @Override
    public List<Subtask> getAllSubtask() {
        return new ArrayList<>(storage.getSubtasks().values());
    }

    /**
     * Получение списка всех подзадач определённого эпика
     */
    @Override
    public List<Subtask> getAllSubtask(int epicId) {
        if (storage.getEpics().containsKey(epicId)) {
            ArrayList<Subtask> subtasksList = new ArrayList<>();
            for (Integer subtaskId : storage.getEpics().get(epicId).getSubtasksList()) {
                subtasksList.add(storage.getSubtasks().get(subtaskId));
            }
            return subtasksList;
        } else {
            return null;
        }
    }

    /**
     * удаление всех задач
     */
    @Override
    public void clearTask() {
        for (Integer id : storage.getTasks().keySet()) {
            historyManager.remove(id);
            storage.getPrioritizedTasks().remove(storage.getTasks().get(id));
        }
        storage.getTasks().clear();
    }

    @Override
    public void clearEpics() {
        for (Integer id : storage.getEpics().keySet()) {
            historyManager.remove(id);
        }
        storage.getEpics().clear();
        clearSubtasks();
    }

    @Override
    public void clearSubtasks() {
        for (Integer id : storage.getSubtasks().keySet()) {
            historyManager.remove(id);
            storage.getPrioritizedTasks().remove(storage.getSubtasks().get(id));
        }
        storage.getSubtasks().clear();
        for (Integer epicId : storage.getEpics().keySet()) {
            storage.getEpics().get(epicId).getSubtasksList().clear();
            updateEpicStatus(epicId);
            updateEpicTime(epicId);
        }
    }

    /**
     * получение по идентификатору
     */
    @Override
    public Task getTask(int id) {
        if (storage.getTasks().containsKey(id)) {
            Task task = storage.getTasks().get(id);
            if (task != null) {
                historyManager.add(task);
            }
            return task;
        }
        return null;
    }

    @Override
    public Epic getEpic(int id) {
        if (storage.getEpics().containsKey(id)) {
            Epic epic = storage.getEpics().get(id);
            if (epic != null) {
                historyManager.add(epic);
            }
            return epic;
        }
        return null;
    }

    @Override
    public Subtask getSubtask(int id) {
        if (storage.getSubtasks().containsKey(id)) {
            Subtask subtask = storage.getSubtasks().get(id);
            if (subtask != null) {
                historyManager.add(subtask);
            }
            return subtask;
        }
        return null;
    }

    /**
     * добавление задач
     */
    @Override
    public Integer addTask(Task task) {
        if (!checkTimeIsFree(task)) {
            return null;
        }
        int id = storage.getId() + 1;
        storage.setId(id);
        task.setId(id);
        storage.setTasks(id, task);
        storage.getPrioritizedTasks().add(task);
        return task.getId();
    }

    @Override
    public Integer addEpic(Epic epic) {
        int id = storage.getId() + 1;
        storage.setId(id);
        epic.setId(id);
        storage.setEpics(id, epic);
        return epic.getId();
    }

    @Override
    public Integer addSubtask(Subtask subtask, int epicId) {
        if (storage.getEpics().containsKey(epicId)) {
            if (!checkTimeIsFree(subtask)) {
                return null;
            }
            int id = storage.getId() + 1;
            storage.setId(id);
            subtask.setId(id);
            storage.setSubtasks(id, subtask, epicId);
            subtask.setEpicId(epicId);
            updateEpicStatus(epicId);
            updateEpicTime(epicId);
            storage.getPrioritizedTasks().add(subtask);
            return subtask.getId();
        } else {
            return null;
        }
    }

    /**
     * обновление задач
     */
    @Override
    public Task updateTask(Task task, int id) {
        if (storage.getTasks().containsKey(id)) {
            if (storage.getPrioritizedTasks().contains(storage.getTasks().get(id))) {
                storage.getPrioritizedTasks().remove(storage.getTasks().get(id));
            }
            if (!checkTimeIsFree(task)) {
                return null;
            }
            storage.setTasks(id, task);
            storage.getTasks().get(id).setId(id);
            storage.getPrioritizedTasks().add(task);
            return storage.getTasks().get(id);
        }
        return null;
    }

    @Override
    public Epic updateEpic(String nameTask, String description, int id) {
        Epic epic = storage.getEpics().get(id);
        if (storage.getEpics().containsKey(id)) {
            epic.setNameTask(nameTask);
            epic.setDescription(description);
            return epic;
        }
        return null;
    }

    @Override
    public Subtask updateSubtask(Subtask subtask, int id, int epicId) {
        if (storage.getEpics().containsKey(epicId)) {
            if (storage.getSubtasks().containsKey(id)) {
                if (storage.getPrioritizedTasks().contains(storage.getSubtasks().get(id))) {
                    storage.getPrioritizedTasks().remove(storage.getSubtasks().get(id));
                }
                if (!checkTimeIsFree(subtask)) {
                    return null;
                }
                storage.setSubtasks(id, subtask, epicId);
                storage.getSubtasks().get(id).setId(id);
                subtask.setEpicId(epicId);
                updateEpicStatus(epicId);
                updateEpicTime(epicId);
                storage.getPrioritizedTasks().add(subtask);
                return storage.getSubtasks().get(id);
            }
            return null;
        } else {
            return null;
        }
    }

    /**
     * удаление по идентификатору
     */
    @Override
    public void deleteTask(int id) {
        if (storage.getTasks().containsKey(id)) {
            if (storage.getPrioritizedTasks().contains(storage.getTasks().get(id))) {
                storage.getPrioritizedTasks().remove(storage.getTasks().get(id));
            }
            storage.getTasks().remove(id);
            historyManager.remove(id);
        }
    }

    @Override
    public void deleteEpic(int id) {
        if (storage.getEpics().containsKey(id)) {
            for (Integer i : storage.getEpics().get(id).getSubtasksList()) {
                storage.getSubtasks().remove(i);
                historyManager.remove(i);
            }
            storage.getEpics().remove(id);
            historyManager.remove(id);
        }
    }

    @Override
    public void deleteSubtask(int id, int epicId) {
        Integer idS = Integer.valueOf(id);
        if (storage.getEpics().containsKey(epicId)) {
            if (storage.getSubtasks().containsKey(id)) {
                if (storage.getPrioritizedTasks().contains(storage.getSubtasks().get(id))) {
                    storage.getPrioritizedTasks().remove(storage.getSubtasks().get(id));
                }
                storage.getSubtasks().remove(idS);
                historyManager.remove(idS);
                if (storage.getEpics().get(epicId).getSubtasksList().contains(idS)) {
                    storage.getEpics().get(epicId).getSubtasksList().remove(idS);
                    updateEpicStatus(epicId);
                    updateEpicTime(epicId);
                }
            }
        }
    }

    /**
     * расчет статуса для эпика
     */
    @Override
    public void updateEpicStatus(int epicId) {
        ArrayList<Status> status = new ArrayList<>();
        Epic epicsId = storage.getEpics().get(epicId);

        for (Integer s : epicsId.getSubtasksList()) {
            status.add(storage.getSubtasks().get(s).getStatus());
        }
        if ((epicsId.getSubtasksList() == null) || (!status.contains(Status.DONE) && !status.contains(Status.IN_PROGRESS))) {
            epicsId.setStatus(Status.NEW);
        } else if (!status.contains(Status.NEW) && !status.contains(Status.IN_PROGRESS)) {
            epicsId.setStatus(Status.DONE);
        } else
            epicsId.setStatus(Status.IN_PROGRESS);
    }

    /**
     * показать историю просмотров
     */
    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }


    /**
     * обновление duration, startTime и endTime эпика
     */
    @Override
    public void updateEpicTime(int epicId) {
        Epic epic = storage.getEpics().get(epicId);
        epic.zeroingTime();
        for (Integer i : epic.getSubtasksList()) {
            Subtask subtask = storage.getSubtasks().get(i);
            LocalDateTime startTimeSubtask = subtask.getStartTime();
            LocalDateTime startTimeEpic = epic.getStartTime();
            LocalDateTime endTimeEpic = epic.getEndTime();
            Integer durationSubtask = subtask.getDuration();
            Integer durationEpic = epic.getDuration();

            if (startTimeSubtask != null && durationSubtask != null) {
                LocalDateTime endTimeSubtask = subtask.getEndTime();

                if (startTimeEpic != null) {
                    if (startTimeSubtask.isBefore(startTimeEpic)) {
                        epic.setStartTime(startTimeSubtask);
                    }
                } else {
                    epic.setStartTime(startTimeSubtask);
                }

                if (endTimeEpic != null) {
                    if (endTimeSubtask.isAfter(endTimeEpic)) {
                        epic.setEndTime(endTimeSubtask);
                    }
                } else {
                    epic.setEndTime(endTimeSubtask);
                }

                if (durationEpic == null) {
                    epic.setDuration(durationSubtask);
                } else {
                    epic.setDuration(durationEpic + durationSubtask);
                }
            }
        }
    }

    /**
     * список задач и подзадач в заданном порядке
     */
    public List<Task> getPrioritizedTasks() {
        return new ArrayList<Task>(storage.getPrioritizedTasks());
    }

    protected boolean checkTimeIsFree(Task task) {
        for (Task t : storage.getPrioritizedTasks()) {
            if (t.getStartTime() == null || task.getStartTime() == null) {
                continue;
            } else if ((task.getStartTime().isAfter(t.getStartTime()) && task.getStartTime().isBefore(t.getEndTime()))
                    || (task.getEndTime().isBefore(t.getEndTime()) && task.getEndTime().isAfter(t.getStartTime()))) {
                return false;
            }
        }
        return true;
    }
}

