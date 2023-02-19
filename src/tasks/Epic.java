package tasks;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {
    private final ArrayList<Integer> subtasksList;
    private Integer durationEpic;
    private LocalDateTime startTimeEpic;
    private LocalDateTime endTime;

    public Epic(String nameTask, String description, Status status) {
        super(nameTask, description, status);
        subtasksList = new ArrayList<>();
    }

    public Epic(Integer id, String nameTask, Status status, String description, LocalDateTime startTime, Integer
            duration) {
        super(id, nameTask, status, description, startTime, duration);
        subtasksList = new ArrayList<>();
    }

    @Override
    public Type getType() {
        return Type.EPIC;
    }

    @Override
    public Integer getDuration() {
        return durationEpic;
    }

    @Override
    public LocalDateTime getStartTime() {
        return startTimeEpic;
    }

    @Override
    public void setStartTime(LocalDateTime startTime) {
        this.startTimeEpic = startTime;
    }

    public void setDuration(int duration) {
        this.durationEpic = duration;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Epic epic = (Epic) o;
        return durationEpic == epic.durationEpic && Objects.equals(subtasksList, epic.subtasksList) && Objects.equals(startTimeEpic,
                epic.startTimeEpic) && Objects.equals(endTime, epic.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtasksList, durationEpic, startTimeEpic, endTime);
    }

    @Override
    public String toString() {
        return "tasks.Epic{" +
                "type='" + getType() + '\'' +
                ", id=" + getId() + '\'' +
                ", nameTask='" + getNameTask() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", startTimeEpic=" + getStartTime() + '\'' +
                ", durationEpic=" + getDuration() + '\'' +
                ", subtasksList.size=" + subtasksList.size() +
                '}';
    }

    public ArrayList<Integer> getSubtasksList() {
        return subtasksList;
    }

    public void addSubtasksId(int idSubtask) {
        if (!subtasksList.contains(idSubtask)) {
            subtasksList.add(idSubtask);
        }
    }

    public void zeroingTime() {
        durationEpic = null;
        startTimeEpic = null;
        endTime = null;
    }
}