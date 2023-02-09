package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {
    private final ArrayList<Integer> subtasksList;
    private Integer duration;
    private LocalDateTime startTime;
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
        return duration;
    }

    @Override
    public LocalDateTime getStartTime() {
        return startTime;
    }

    @Override
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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
        return duration == epic.duration && Objects.equals(subtasksList, epic.subtasksList) && Objects.equals(startTime,
                epic.startTime) && Objects.equals(endTime, epic.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtasksList, duration, startTime, endTime);
    }

    @Override
    public String toString() {
        return "tasks.Epic{" +
                "type='" + getType() + '\'' +
                ", id=" + getId() + '\'' +
                ", nameTask='" + getNameTask() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", status='" + getStatus() + '\'' +
                ", startTime=" + getStartTime() + '\'' +
                ", duration=" + getDuration() + '\'' +
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
        duration = null;
        startTime = null;
        endTime = null;
    }
}